package com.ceocho.kakaotalk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ceocho.kakaotalk.Utill.JWebSocketClient;
import com.ceocho.kakaotalk.Utill.MaptoJsonUtill;
import com.ceocho.kakaotalk.Utill.OkhttpUtill;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    MaterialEditText id, password;
    Button btn_login;

//    URI uri = URI.create("ws://172.30.1.6:520/ws");
//    JWebSocketClient client = new JWebSocketClient(uri) {
////        @Override
////        public void onMessage(String message) {
////            //message就是接收到的消息
////            Log.e("JWebSClientService", message);
////
////        }
//    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        try {
//            client.connectBlocking();
//            System.out.println("it is connectt?");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            System.out.println("or not?");
//
//        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        id = findViewById(R.id.id);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map map = new HashMap();

                if (TextUtils.isEmpty(id.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    map.put("username", id.getText().toString());
                    map.put("password", password.getText().toString());
                    map.put("bizType","USER_LOGIN");

                    String input = MaptoJsonUtill.getJson(map);
                    Map result = OkhttpUtill.post("user/login", input);
//                    if (client != null && client.isOpen()) {
//                        client.send(input);
//                    }

                    System.out.println(result);

                    if (result != null && !result.isEmpty()) {

                        if (result.get("success").toString() == "true") {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            }
        });




    }






}


