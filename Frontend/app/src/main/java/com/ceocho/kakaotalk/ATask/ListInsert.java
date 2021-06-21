package com.ceocho.kakaotalk.ATask;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;


import com.ceocho.kakaotalk.Utill.OkhttpUtill;

import java.io.File;
import java.io.IOException;

public class ListInsert extends AsyncTask<Void, Void, String> {
    private static final String TAG = "main ListInsert";
    ProgressDialog progressDialog;

    // 데이터베이스에 삽입결과 0보다크면 삽입성공, 같거나 작으면 실패
    // 필수 부분

    //메인 액티비티의 변수를 받기 위해 변수를 선언하고 생성자를 만든다.
    //무언가를 받고자 할때는 대부분 생성자를 이용한다.
    String imagedbpath, imagerealpath,order;

    public ListInsert(String imagedbpath, String imagerealpath,String order) {
        this.imagedbpath = imagedbpath;
        this.imagerealpath = imagerealpath;
        this.order = order;
    }

    public ListInsert() {

    }

    //기본으로 생성되는 메서드, 지금은 필요 없다
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        progressDialog = new ProgressDialog(getActivity().getApplicationContext());
//        progressDialog.setMessage("Please Wait....");
//        progressDialog.show();

    }

    @Override
    protected String doInBackground(Void... voids) {

        System.out.println(imagerealpath);
        File file = new File(imagerealpath);

        try {
            OkhttpUtill.upload(file,order);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Log.d(TAG, "onPostExecute: " + result);
    }
}
