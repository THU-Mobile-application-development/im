package com.ceocho.kakaotalk;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ceocho.kakaotalk.Model.User;
import com.ceocho.kakaotalk.Utill.MaptoJsonUtill;
import com.ceocho.kakaotalk.Utill.OkhttpUtill;
import com.ceocho.kakaotalk.adapter.UserAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserAddActivity extends AppCompatActivity {

    EditText search_user;
    Button search_btn, add_btn, cancel_btn;
    TextView username, nickname;
    CircleImageView image_profile;

    LinearLayout search_block;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_search_user);

        search_block = findViewById(R.id.search_block);
        search_block.setVisibility(LinearLayout.INVISIBLE);

        search_user = findViewById(R.id.search_users);
        search_btn = findViewById(R.id.search_btn);
        add_btn = findViewById(R.id.add_friend_btn);
        cancel_btn = findViewById(R.id.cancel_btn);
        username = findViewById(R.id.username);
        nickname = findViewById(R.id.nickname);
        image_profile = findViewById(R.id.profile_image);


        search_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String contact_username = search_user.getText().toString();


                if (contact_username.equals("")) {
                    Toast.makeText(UserAddActivity.this, "username are required!", Toast.LENGTH_SHORT).show();
                } else {
                    Map map = new HashMap();


                    map.put("contact_username", contact_username);
                    String input = MaptoJsonUtill.getJson(map);
                    Map result = OkhttpUtill.post("contact/find", input);

                    //검색성공
                    if (result.get("success").toString() == "true") {
                        username.setText(result.get("contact_username").toString());
                        nickname.setText(result.get("contact_nickname").toString());
                        search_block.setVisibility(LinearLayout.VISIBLE);
                        //친구로 추가 되어있지 않을
                        if (result.get("already_contact").toString() == "false") {
                            System.out.println("친구로 추가되어있지 않음 ");
                            add_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String target_username = result.get("contact_username").toString();
                                    Map map_add = new HashMap();

                                    map_add.put("add_contact", target_username);
                                    String input_add = MaptoJsonUtill.getJson(map_add);
                                    Map result_add = OkhttpUtill.post("contact/add", input_add);
                                    if (result_add.get("success").toString() == "true") {
                                        //그냥 친구 목록화면으로 이동
                                        startActivity(new Intent(UserAddActivity.this, MainActivity.class));

                                    } else {
                                        Toast.makeText(UserAddActivity.this, "somethig wrong while add friedn!", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        } else {
                            System.out.println("친구로 추가되어있음  ");

                            add_btn.setText("Go to Chat");
                            //상응하는 챗 화면으로 이동

                        }

                    } else {//검색 실패
                        Toast.makeText(UserAddActivity.this, "not exist!", Toast.LENGTH_SHORT).show();

                    }


                }
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserAddActivity.this, MainActivity.class));


            }

        });


        Intent intent = getIntent();
        String data = intent.getStringExtra("data");

    }
}
