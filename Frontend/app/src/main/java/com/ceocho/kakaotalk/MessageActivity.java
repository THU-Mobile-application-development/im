package com.ceocho.kakaotalk;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ceocho.kakaotalk.Fragments.APIService;
import com.ceocho.kakaotalk.Model.Chat;
import com.ceocho.kakaotalk.Model.User;
import com.ceocho.kakaotalk.Notifications.Client;
import com.ceocho.kakaotalk.Notifications.Data;
import com.ceocho.kakaotalk.Notifications.MyResponse;
import com.ceocho.kakaotalk.Notifications.Sender;
import com.ceocho.kakaotalk.Notifications.Token;
import com.ceocho.kakaotalk.R;
import com.ceocho.kakaotalk.Utill.GpsTracker;
import com.ceocho.kakaotalk.Utill.JWebSocketClient;
import com.ceocho.kakaotalk.Utill.MaptoJsonUtill;
import com.ceocho.kakaotalk.Utill.OkhttpUtill;
import com.ceocho.kakaotalk.adapter.MessageAdapter;
import com.ceocho.kakaotalk.adapter.UserAdapter;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MessageActivity extends AppCompatActivity {


//    URI uri = URI.create("ws://172.30.1.6:520/ws");
//    JWebSocketClient client = new JWebSocketClient(uri) {
//        @Override
//        public void onMessage(String message) {
//            //message????????????????????????
//            Log.e("JWebSClientService", message);
//            Log.e("JWebSClientService", "fuckkk");
//
//        }
//    };




    URI uri = URI.create("ws://172.30.1.6:520/ws");
    JWebSocketClient client = new JWebSocketClient(uri) {
        @Override
        public void onMessage(String message) {
            //message????????????????????????
            Log.e("JWebSClientService", message);
        }
    };

    //public JWebSocketClient client;


    CircleImageView profile_image;
    TextView username;


    ImageButton btn_send;
    ImageView send_etc;
    EditText text_send, search_users;

    MessageAdapter messageAdapter;
    List<Chat> mchat;

    RecyclerView recyclerView;

    Intent intent;


    String userid, avatar;

    APIService apiService;

    boolean notfiy = false;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        try {
            client.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // initSocketClient();
        //  mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//??????????????????


        setContentView(R.layout.activity_message);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        search_users = findViewById(R.id.search_users);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);
        send_etc = findViewById(R.id.send_etc);
        intent = getIntent();
        userid = intent.getStringExtra("username");
        avatar = intent.getStringExtra("Avatar");

        username.setText(userid);


        Glide.with(this)
                .load(avatar)
                .into(profile_image);


        readMessages(userid, avatar);


        send_etc.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {


                startActivityForResult(new Intent(MessageActivity.this, SendEtcActivity.class), 0);



                    }
                });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                notfiy = true;
                String msg = text_send.getText().toString();
                Map my_result = OkhttpUtill.get("user/myinfo");
                String username = my_result.get("username").toString();

                if (!msg.equals("")) {
                    Map map = new HashMap();
                    map.put("content", msg);
                    map.put("type", 0);
                    map.put("to_username", userid);
                    map.put("from_username", username);
                    map.put("bizType", "CHAT_SEND");
                    String input = MaptoJsonUtill.getJson(map);
                    Map result = OkhttpUtill.post("chat/chat_send", input);
                    if (result.get("success").toString() == "true") {
                        Toast.makeText(MessageActivity.this, "send message success", Toast.LENGTH_SHORT).show();
                        if (client != null && client.isOpen()) {
                            client.send(input);
                        }

                    } else {
                        Toast.makeText(MessageActivity.this, "send message error", Toast.LENGTH_SHORT).show();

                    }
                    // sendMessage(fuser.getUid(), userid, msg);
                } else {
                    Toast.makeText(MessageActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });


    }


//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case GPS_ENABLE_REQUEST_CODE: //???????????? GPS ?????? ???????????? ??????
//                if (checkLocationServicesStatus()) {
//                    if (checkLocationServicesStatus()) {
//                        Log.d("@@@", "onActivityResult : GPS ????????? ?????????");
//                        checkRunTimePermission();
//                        return;
//                    }
//                }
//                break;
//        }
//    }


    private void readMessages(String userid, String avatar) {


        mchat = new ArrayList<>();

        Map map_history = new HashMap();
        map_history.put("to_username", userid);
        String input_history = MaptoJsonUtill.getJson(map_history);

        Map result_history = OkhttpUtill.post("chat/chat_history", input_history);


//        String propsContent = (String) result_history.get("chat");
//        System.out.println(propsContent);
        List<String> propsContent = MaptoJsonUtill.jsonlisttolist((JSONArray) result_history.get("chat"), "chatContent");
        List<String> propsType = MaptoJsonUtill.jsonlisttolist((JSONArray) result_history.get("chat"), "chatType");
        List<String> propsToUsername = MaptoJsonUtill.jsonlisttolist((JSONArray) result_history.get("chat"), "toUsername");
        List<String> propsFromUsername = MaptoJsonUtill.jsonlisttolist((JSONArray) result_history.get("chat"), "fromUsername");
        List<String> propsSendTime = MaptoJsonUtill.jsonlisttolist((JSONArray) result_history.get("chat"), "sendTime");

        //List<String> propsAvatar = MaptoJsonUtill.jsonlisttolist((JSONArray) result_history.get("chat"),"toAvatar");
        if (search_users.getText().toString().equals("")) {
            mchat.clear();
            for (int i = 0; i < propsContent.size(); i++) {
                String from_username = propsFromUsername.get(i);
                String to_username = propsToUsername.get(i);
                String type = propsType.get(i);
                String content = propsContent.get(i);
                String time = propsSendTime.get(i);
                //String chat_avatar = propsAvatar.get(i);
                Chat chat = new Chat();
                chat.setMessage(content);
                chat.setReceiver(to_username);
                chat.setSender(from_username);
                chat.setTime(time);
                chat.setType(type);
                mchat.add(chat);
            }
            messageAdapter = new MessageAdapter(MessageActivity.this, mchat, avatar, userid);
            recyclerView.setAdapter(messageAdapter);
        }


    }


    @Override
    public void onDestroy() {
        closeConnect();
        super.onDestroy();
    }

    private void closeConnect() {
        try {
            if (null != client) {
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 0) {
            //Geo
            Map my_result = OkhttpUtill.get("user/myinfo");
            String username = my_result.get("username").toString();

            String msg = data.getStringExtra("data");
            if (!msg.equals("")) {
                Map map = new HashMap();
                map.put("content", msg);
                map.put("type", "4");
                map.put("to_username", userid);
                map.put("from_username", username);
                map.put("bizType", "CHAT_SEND");
                String input = MaptoJsonUtill.getJson(map);
                Map result = OkhttpUtill.post("chat/chat_send", input);
                if (result.get("success").toString() == "true") {
                    Toast.makeText(MessageActivity.this, "result ok!", Toast.LENGTH_SHORT).show();

                }

            }


        }

        if (resultCode == 1) {
            Map my_result = OkhttpUtill.get("user/myinfo");
            String username = my_result.get("username").toString();

            String imagerealpath = data.getStringExtra("data");
            if (!imagerealpath.equals("")) {
                File file = new File(imagerealpath);
                try {
                    OkhttpUtill.uploadchat(file,"1",userid,username,"CHAT_SEND");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }




    }




//
//    private void initSocketClient() {
//
//
//
//
//        URI uri = URI.create("ws://172.30.1.6:520/ws");
//        client = new JWebSocketClient(uri) {
//            @Override
//            public void onMessage(String message) {
//
//                Log.e("JWebSocketClientService", "??????????????????" + message);
//
//                Intent intent = new Intent();
//                intent.setAction("com.xch.servicecallback.content");
//                intent.putExtra("message", message);
//                sendBroadcast(intent);
//
//            }
//
//            @Override
//            public void onOpen(ServerHandshake handshakedata) {
//                super.onOpen(handshakedata);
//                Log.e("JWebSocketClientService", "websocket????????????");
//            }
//        };
//        connect();
//    }


//    private void connect() {
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    //connectBlocking?????????????????????????????????????????????????????????????????????????????????
//                    client.connectBlocking();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//
//    }
//

//    private static final long HEART_BEAT_RATE = 10 * 1000;//??????10??????????????????????????????????????????
//    private Handler mHandler = new Handler();
//    private Runnable heartBeatRunnable = new Runnable() {
//        @Override
//        public void run() {
//            Log.e("JWebSocketClientService", "???????????????websocket????????????");
//            if (client != null) {
//                if (client.isClosed()) {
//                    reconnectWs();
//                }
//            } else {
//                //??????client?????????????????????????????????
//                client = null;
//               // initSocketClient();
//            }
//            //????????????????????????????????????????????????????????????
//            mHandler.postDelayed(this, HEART_BEAT_RATE);
//        }
//    };
//
//    /**
//     * ????????????
//     */
//    private void reconnectWs() {
//        mHandler.removeCallbacks(heartBeatRunnable);
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    Log.e("JWebSocketClientService", "????????????");
//                    client.reconnectBlocking();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }


}
