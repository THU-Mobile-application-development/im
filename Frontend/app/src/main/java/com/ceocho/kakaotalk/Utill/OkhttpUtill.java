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
    public static String baseURL = "http://8.140.133.34:7641/";
    public static String contentURL = "http://8.140.133.34:7643/";



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

        //????????? ?????? (enqueue ??????)
        client.newCall(request).enqueue(new Callback() {
            //????????? ????????? ?????? Callback ??????
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

        //????????? ?????? (enqueue ??????)
        client.newCall(request).enqueue(new Callback() {
            //????????? ????????? ?????? Callback ??????
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
                Log.d("isconnected : ", "WIFI ??? ?????????");
            }else if(info.getType() == ConnectivityManager.TYPE_MOBILE){
                Log.d("isconnected : ", "??????????????? ?????????");
            }
            Log.d("isconnected : ", "OK => " + info.isConnected());
            return true;
        }else {
            Log.d("isconnected : ", "False => ????????? ?????? ??????!!!" );
            return false;
        }

    }

    public static void upload(File file,String order,String type) throws IOException {
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
                    .addFormDataPart("type", type)
                    .build();

        }


        Request request = new Request.Builder()
                .url(baseURL + order)
                .post(formBody)
                .build();


//        Request request = new Request.Builder().url(baseURL+"user/avatar").post(formBody).build();
//        System.out.println("???????????? fafdasfsafs?");

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


    public static void uploadchat(File file,String type,String to_username,String from_username,String biz_type) throws IOException {
        // OkHttpClient client = new OkHttpClient();
        final Map[] result = {new HashMap()};

        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
        final CountDownLatch latch = new CountDownLatch(1);

        RequestBody formBody;

            formBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("content", file.getName(),
                            RequestBody.create(MEDIA_TYPE_PNG, file))
                    .addFormDataPart("type", type)
                    .addFormDataPart("to_username",to_username)
                    .addFormDataPart("from_username",from_username)
                    .addFormDataPart("bizType",biz_type)
                    .build();




        Request request = new Request.Builder()
                .url(baseURL + "chat/chat_send")
                .post(formBody)
                .build();


//        Request request = new Request.Builder().url(baseURL+"user/avatar").post(formBody).build();
//        System.out.println("???????????? fafdasfsafs?");

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
