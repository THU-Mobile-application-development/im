package com.ceocho.kakaotalk.Utill;

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


}
