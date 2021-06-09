package com.ceocho.kakaotalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ceocho.kakaotalk.Fragments.ProfileFragment;
import com.ceocho.kakaotalk.Utill.MaptoJsonUtill;
import com.ceocho.kakaotalk.Utill.OkhttpUtill;

import java.util.HashMap;
import java.util.Map;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText old_password, new_password, confirm_password;
    Button reset_password_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reset Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        old_password = findViewById(R.id.old_password);
        new_password = findViewById(R.id.new_password);
        confirm_password = findViewById(R.id.confirm_password);
        reset_password_btn = findViewById(R.id.reset_password_btn);


        reset_password_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String old_pass = old_password.getText().toString();
                String new_pass = new_password.getText().toString();
                String con_pass = confirm_password.getText().toString();


                if (old_pass.equals("") || new_pass.equals("") || con_pass.equals("")) {
                    Toast.makeText(ResetPasswordActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                } else {
                    Map map = new HashMap();


                    map.put("old_password", old_pass);
                    map.put("new_password", new_pass);
                    map.put("confirm_password", con_pass);
                    String input = MaptoJsonUtill.getJson(map);
                    Map result = OkhttpUtill.post("user/modify_password", input);


                    if (result.get("success").toString() == "true") {
                        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                        Toast.makeText(ResetPasswordActivity.this, "请重新登录!", Toast.LENGTH_SHORT).show();

                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(ResetPasswordActivity.this, "Register failed!", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });
    }
}
