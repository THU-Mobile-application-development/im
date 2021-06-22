package com.ceocho.kakaotalk.ATask;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;


import com.ceocho.kakaotalk.Utill.OkhttpUtill;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;

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
        String extension = getExtension(imagerealpath);
        String type;
        if (extension.equals("mp4") || extension.equals("MP4") || extension.equals("MOV") || extension.equals("mov") || extension.equals("AVI") || extension.equals("avi") ||
                extension.equals("MKV") || extension.equals("mkv") || extension.equals("WMV") || extension.equals("wmv") || extension.equals("TS") || extension.equals("ts") ||
                extension.equals("TP") || extension.equals("tp") || extension.equals("FLV") || extension.equals("flv") || extension.equals("3GP") || extension.equals("3gp") ||
                extension.equals("MPG") || extension.equals("mpg") || extension.equals("MPEG") || extension.equals("mpeg") || extension.equals("MPE") || extension.equals("mpe") ||
                extension.equals("ASF") || extension.equals("asf") || extension.equals("ASX") || extension.equals("asx") || extension.equals("DAT") || extension.equals("dat") ||
                extension.equals("RM") || extension.equals("rm")) {
            type = "1";
        }
        else{
            type = "0";
        }



        try {
            OkhttpUtill.upload(file,order,type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    private String getExtension(String url) {
        return url.substring(url.lastIndexOf(".") + 1, url.length());
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Log.d(TAG, "onPostExecute: " + result);
    }
}
