package com.ceocho.kakaotalk.Utill;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpUtill {
    //private  static String baseURL = "http://172.30.1.7:7000/";
    //private  static String baseURL = "http://172.20.10.5:7000/";
    //private static String baseURL = "http://172.30.1.59:7000/";
    //private static String baseURL = "http://172.30.1.5:7000/";
    public static String baseURL = "http://172.30.1.6:7000/";


    private static OkHttpClient client = new OkHttpClient.Builder()
            .cookieJar(new CookieJar() {
                private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    cookieStore.put(url.host(), cookies);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> cookies = cookieStore.get(url.host());
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            })
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .build();


    public static Map get(String requestURL) {
        final Map[] result = {new HashMap()};

        final CountDownLatch latch = new CountDownLatch(1);


        Request request = new Request.Builder()
                .url(baseURL.concat(requestURL))
                .get()
                .build();

        //비동기 처리 (enqueue 사용)
        client.newCall(request).enqueue(new Callback() {
            //비동기 처리를 위해 Callback 구현
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("error + Connect Server Error is " + e.toString());
                latch.countDown();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBodyString = response.body().string();

                System.out.println("Response Body is " + responseBodyString);
                final Map res = MaptoJsonUtill.getMap(responseBodyString);


                result[0] = res;

                latch.countDown();

            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
        }
        return result[0];

    }


    public static Map post(String requestURL, String message) {
        final Map[] result = {new HashMap()};
        final CountDownLatch latch = new CountDownLatch(1);


        Request request = new Request.Builder()
                .url(baseURL.concat(requestURL))
                .post(RequestBody.create(MediaType.parse("application/json"), message))
                .build();

        //비동기 처리 (enqueue 사용)
        client.newCall(request).enqueue(new Callback() {
            //비동기 처리를 위해 Callback 구현
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("error + Connect Server Error is " + e.toString());
                latch.countDown();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseBodyString = response.body().string();

                System.out.println("Response Body is " + responseBodyString);

                final Map res = MaptoJsonUtill.getMap(responseBodyString);


                result[0] = res;
                latch.countDown();


            }

        });

        try {
            latch.await();
        } catch (InterruptedException e) {
        }
        return result[0];
    }


    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info != null){
            if(info.getType() == ConnectivityManager.TYPE_WIFI){
                Log.d("isconnected : ", "WIFI 로 설정됨");
            }else if(info.getType() == ConnectivityManager.TYPE_MOBILE){
                Log.d("isconnected : ", "일반망으로 설정됨");
            }
            Log.d("isconnected : ", "OK => " + info.isConnected());
            return true;
        }else {
            Log.d("isconnected : ", "False => 데이터 통신 불가!!!" );
            return false;
        }

    }

    public static void upload(File file,String order) throws IOException {
       // OkHttpClient client = new OkHttpClient();
        final Map[] result = {new HashMap()};

        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
        final CountDownLatch latch = new CountDownLatch(1);

        RequestBody formBody;
        if(order.equals("user/avatar")) {
            formBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("avatar", file.getName(),
                            RequestBody.create(MEDIA_TYPE_PNG, file))
                    //.addFormDataPart("other_field", "other_field_value")
                    .build();
        }
        else{
            formBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("content", file.getName(),
                            RequestBody.create(MEDIA_TYPE_PNG, file))
                    //.addFormDataPart("other_field", "other_field_value")
                    .build();

        }


        Request request = new Request.Builder()
                .url(baseURL + order)
                .post(formBody)
                .build();


//        Request request = new Request.Builder().url(baseURL+"user/avatar").post(formBody).build();
//        System.out.println("여기까지 fafdasfsafs?");

        client.newCall(request).enqueue(new Callback() {


                                            public void onFailure(Call call, IOException e) {
                                                System.out.println("error + Connect Server Error is " + e.toString());
                                                latch.countDown();

                                            }

                                            @Override
                                            public void onResponse(Call call, Response response) throws IOException {

                                                String responseBodyString = response.body().string();

                                                System.out.println("Response Body is " + responseBodyString);

                                                final Map res = MaptoJsonUtill.getMap(responseBodyString);


                                                result[0] = res;
                                                latch.countDown();


                                            }
                                        });
    }
}
//        String res = response.body().string();
//        Log.e("TAG", "Response : " + res);


//
//        MediaType mediaType = MediaType.parse("image/jpg");
//        RequestBody requestBody = RequestBody.create(mediaType, file);
//
//        MultipartBody multipartBody = new MultipartBody.Builder("---DevApp")
//                .addFormDataPart("AWSAccessKeyId", "AKIAJCBCKQITFUEAAITA")
//                .addFormDataPart("acl", "public-read")
//                .addFormDataPart("key", "formtasks/images/19463-selfie.jpg")
//                .addFormDataPart("signature", "kos3NHJW+qUaQMpYZfaOs19hUMRw=")
//                .addFormDataPart("policy", "eyJjb25kaXRpb25zIjogd3siYWNsIjogInB1YmxpYy1yZWFkIn0sIHsiQ29udGVudC1UeXBlIjogImltYWdlL2pwZyJ9LCB7ImJ1Y2tldCI6ICJzaG9wcGluZy1kZXZlbCJ9LCB7ImtleSI6ICJmb3JtdGFza3MvaW1hZ2VzLzE5NDYzLXNlbGZpZS5qcGcifV0sICJleHBpcmF0aW9uIjogIjIwMTgtMDQtMTdUMjE6NTU6NDhaIn0=")
//                .addFormDataPart("Content-Type", "image/jpg")
//                .addFormDataPart("file", null, requestBody).build();
//
//
//        Request request = new Request.Builder()
//                .url(baseURL+"user/avatar")
//                .post(multipartBody)
//                .addHeader("content-type", "multipart/form-data;")
//                .addHeader("cache-control", "no-cache")
//                .build();
//
//        Response execute = client.newCall(request).execute();
//        System.out.println(execute.body().string());







