package com.ceocho.kakaotalk.Fragments;

import com.ceocho.kakaotalk.Notifications.MyResponse;
import com.ceocho.kakaotalk.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                   "Content-Type:application/json",
                   "Authorization:key=AAAAN8jeGS8:APA91bGKwPkQxDx7VXWn1kJBYp9U_xnu4QMGzSD0ubYLyFIssl-hi4G34Mswo_mgIguG5nCc1MzWnoGc_4qIrM6ZoxVF6bMYrOoNZbnQCHJZmmPQRgqWnr9Rb9qCHh72SyHKGX_CMaRI"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
