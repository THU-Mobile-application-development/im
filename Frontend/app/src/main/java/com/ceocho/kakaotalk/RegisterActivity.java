package com.ceocho.kakaotalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ceocho.kakaotalk.Utill.MaptoJsonUtill;
import com.ceocho.kakaotalk.Utill.OkhttpUtill;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    MaterialEditText username, nickname, password, phonenumber;
    Button btn_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username = findViewById(R.id.username);
        phonenumber = findViewById(R.id.phonenumber);
        password = findViewById(R.id.password);
        nickname = findViewById(R.id.nickname);

        btn_register = findViewById(R.id.btn_register);



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String txt_username = username.getText().toString();
                String txt_phonenumber = phonenumber.getText().toString();
                String txt_password = password.getText().toString();
                String txt_nickname = nickname.getText().toString();


                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_phonenumber) || TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "password must be at least 6 character", Toast.LENGTH_SHORT).show();
                } else {
                    register(txt_username, txt_password, txt_phonenumber, txt_nickname);
                }
            }
        });
    }

    private void register(final String username, String password, String phonenumber, String nickname) {
        Map map = new HashMap();


        map.put("username", username);
        map.put("password", password);
        map.put("phonenumber", phonenumber);
        map.put("nickname", nickname);
        String input = MaptoJsonUtill.getJson(map);
        Map result = OkhttpUtill.post("user/register", input);

        if (result.get("success").toString() == "true") {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(RegisterActivity.this, "Register failed!", Toast.LENGTH_SHORT).show();
        }

    }
}
