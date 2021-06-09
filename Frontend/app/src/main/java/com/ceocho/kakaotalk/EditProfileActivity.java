package com.ceocho.kakaotalk;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;

import com.ceocho.kakaotalk.Fragments.ProfileFragment;
import com.ceocho.kakaotalk.Utill.MaptoJsonUtill;
import com.ceocho.kakaotalk.Utill.OkhttpUtill;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends Activity {

    EditText username, nickname, phonenumber;
    Button confirm_btn, cancel_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit_info);

        username = findViewById(R.id.username);
        phonenumber = findViewById(R.id.phonenumber);
        nickname = findViewById(R.id.nickname);
        confirm_btn = findViewById(R.id.confirm_edit);
        cancel_btn = findViewById(R.id.cancel_edit);

        Map result = OkhttpUtill.get("user/myinfo");

        username.setText(result.get("username").toString());
        nickname.setText(result.get("nickname").toString());
        phonenumber.setText(result.get("phonenumber").toString());


        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String txt_test = test.getText().toString();

                String txt_username = username.getText().toString();
                String txt_phonenumber = phonenumber.getText().toString();
                String txt_nickname = nickname.getText().toString();


//                Toast.makeText(RegisterActivity.this, txt_test, Toast.LENGTH_SHORT).show();

                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_phonenumber) || TextUtils.isEmpty(txt_nickname)) {
                    Toast.makeText(EditProfileActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    edit_profile(txt_username, txt_phonenumber, txt_nickname, v);
                }
            }
        });


        //데이터 가져오기
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
    }


    private void edit_profile(final String username, String phonenumber, String nickname, View v) {
        Map map = new HashMap();


        map.put("username", username);
        map.put("phonenumber", phonenumber);
        map.put("nickname", nickname);
        String input = MaptoJsonUtill.getJson(map);
        Map result = OkhttpUtill.post("user/edit_info", input);


        if (result.get("success").toString() == "true") {
            mOnClose(v);
        } else {
            Toast.makeText(EditProfileActivity.this, "Register failed!", Toast.LENGTH_SHORT).show();
        }

    }


    //확인 버튼 클릭
    public void mOnClose(View v) {
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "ok");
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}


