//package com.ceocho.kakaotalk.ATask;
//
//
//
//
//        import android.graphics.Bitmap;
//        import android.graphics.BitmapFactory;
//        import android.graphics.drawable.Drawable;
//        import android.icu.text.SymbolTable;
//        import android.os.AsyncTask;
//        import android.util.Log;
//
//        import com.ceocho.kakaotalk.Utill.OkhttpUtill;
//
//        import org.apache.http.HttpResponse;
//
//        import java.io.InputStream;
//        import java.net.HttpURLConnection;
//        import java.net.URL;
//
//public class BitmapUtil {
//    public static Bitmap getHttpBitmap(String url) {
//        URL myFileURL;
//        Bitmap bitmap = null;
//        try {
//            AsyncTask<String, Void, HttpResponse> asyncTask = new AsyncTask<String, Void, HttpResponse>() {
//                @Override
//                protected HttpResponse doInBackground(String... url) {
//                    url = url.replace("/home/uploads/", "");
//                    System.out.println(OkhttpUtill.contentURL + url);
//                    myFileURL = new URL(OkhttpUtill.contentURL + url);
//                    // Get connected
//                    HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
//                    // set the timeout time is 6000 milliseconds, conn.setConnectionTiem (0); means no time limit
//                    conn.setConnectTimeout(6000);
//                    // connection settings data stream is obtained
//                    conn.setDoInput(true);
//                    // do not use cache
//                    conn.setUseCaches(false);
//                    // phrase dispensable, had no effect
//                    //conn.connect();
//                    // get the data stream
//                    InputStream is = conn.getInputStream();
//                    // parsed Pictures
//                    bitmap = BitmapFactory.decodeStream(is);
//                    // Close the stream
//                    is.close();
//
//                    return bitmap;
//
//                }
//
//
//            };
//        }catch(
//                Exception e)
//
//                {
//                    e.printStackTrace();
//                }
//            return bitmap;
//            }
//
//    }


package com.ceocho.kakaotalk.ATask;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;


import com.ceocho.kakaotalk.Utill.OkhttpUtill;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BitmapUtil extends AsyncTask<Void, Void, Bitmap> {
    private static final String TAG = "main ListInsert";
    ProgressDialog progressDialog;

    // ????????????????????? ???????????? 0???????????? ????????????, ????????? ????????? ??????
    // ?????? ??????

    //?????? ??????????????? ????????? ?????? ?????? ????????? ???????????? ???????????? ?????????.
    //???????????? ????????? ????????? ????????? ???????????? ????????????.
    String url;

    public BitmapUtil(String url) {
        this.url = url;
    }

    public BitmapUtil() {

    }

    //???????????? ???????????? ?????????, ????????? ?????? ??????
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }


    @Override
    protected Bitmap doInBackground(Void... voids) {
        URL myFileURL;
        Bitmap bitmap = null;

        try {
            url = url.replace("/home/uploads/", "");
            System.out.println(OkhttpUtill.contentURL + url);
            myFileURL = new URL(OkhttpUtill.contentURL + url);
            // Get connected
            HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
            // set the timeout time is 6000 milliseconds, conn.setConnectionTiem (0); means no time limit
            conn.setConnectTimeout(6000);
            // connection settings data stream is obtained
            conn.setDoInput(true);
            // do not use cache
            conn.setUseCaches(false);
            // phrase dispensable, had no effect
            //conn.connect();
            // get the data stream
            InputStream is = conn.getInputStream();
            // parsed Pictures
            bitmap = BitmapFactory.decodeStream(is);
            // Close the stream
            is.close();


        } catch (
                Exception e) {
            e.printStackTrace();
        }

        return bitmap;


    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);

        Log.d(TAG, "onPostExecute: " + result);
        returnbitmap(result);
    }

    private Bitmap returnbitmap(Bitmap result){
        return result;

    }


}
