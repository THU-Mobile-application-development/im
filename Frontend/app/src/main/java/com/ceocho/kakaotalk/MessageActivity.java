package com.ceocho.kakaotalk;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.ceocho.kakaotalk.Utill.MaptoJsonUtill;
import com.ceocho.kakaotalk.Utill.OkhttpUtill;
import com.ceocho.kakaotalk.adapter.MessageAdapter;
import com.ceocho.kakaotalk.adapter.UserAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username;


    ImageButton btn_send;
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
        setContentView(R.layout.activity_message);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //뭔지 모르겠다밍

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // crash
//                startActivity(new Intent(getApplicationContext(), StartActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//            }
//        });

        //apiService = Client.getClient("http://fcm.googleapis.com/").create(APIService.class);
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

        intent = getIntent();
        userid = intent.getStringExtra("username");
        avatar = intent.getStringExtra("Avatar");

        username.setText(userid);
        //나중에 바꾸기
        profile_image.setImageResource(R.mipmap.ic_launcher);

        readMessages(userid, avatar);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                notfiy = true;
                String msg = text_send.getText().toString();
                if (!msg.equals("")) {
                    Map map = new HashMap();
                    map.put("content", msg);
                    map.put("type", 0);
                    map.put("to_username", userid);
                    String input = MaptoJsonUtill.getJson(map);
                    Map result = OkhttpUtill.post("chat/chat_send", input);
                    if (result.get("success").toString() == "true") {
                        Toast.makeText(MessageActivity.this, "send message success", Toast.LENGTH_SHORT).show();

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
//
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                User user = dataSnapshot.getValue(User.class);
//                username.setText(user.getUsername());
//                if (user.getAvatar().equals("default")) {
//                    profile_image.setImageResource(R.mipmap.ic_launcher);
//                } else {
//                    // change
//                    Glide.with(getApplicationContext()).load(user.getAvatar()).into(profile_image);
//                }
//
//                readMessages(fuser.getUid(), userid, user.getAvatar());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        seenMessage(userid);
    }

    //    private void seenMessage(final String userid) {
//        reference = FirebaseDatabase.getInstance().getReference("Chats");
//        seenListener = reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    Chat chat = snapshot.getValue(Chat.class);
//                    if (chat.getReceiver().equals(fuser.getUid()) && chat.getSender().equals(userid)){
//                        HashMap<String, Object> hashMap = new HashMap<>();
//                        hashMap.put("isseen", true);
//                        snapshot.getRef().updateChildren(hashMap);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    private void sendMessage(String sender, final String receiver, String message){
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("sender", sender);
//        hashMap.put("receiver", receiver);
//        hashMap.put("message", message);
//        hashMap.put("isseen", false);
//
//        reference.child("Chats").push().setValue(hashMap);
//
//        // add user to chat fragment
//        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
//                .child(fuser.getUid())
//                .child(userid);
//
//        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (!dataSnapshot.exists()) {
//                    chatRef.child("id").setValue(userid);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        final String msg = message;
//
//        reference = FirebaseDatabase.getInstance().getReference().child(fuser.getUid());
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                User user = dataSnapshot.getValue(User.class);
//                if (notfiy){
//                    sendNotification(receiver, user.getUsername(), msg);
//                }
//                boolean notify = false;
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }
//
//    private void sendNotification(String receiver, final String username, final String message){
//        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
//        Query query = tokens.orderByKey().equalTo(receiver);
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Token token = snapshot.getValue(Token.class);
//                    Data data = new Data(fuser.getUid(), R.mipmap.ic_launcher, username+": "+message, "New Message",
//                            userid);
//
//                    Sender sender = new Sender(data, token.getToken());
//
//                    apiService.sendNotification(sender)
//                            .enqueue(new Callback<MyResponse>() {
//                                @Override
//                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
//                                    if (response.code() == 200){
//                                        if (response.body().success != 1){
//                                            Toast.makeText(MessageActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<MyResponse> call, Throwable t) {
//
//                                }
//                            });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//
    private void readMessages(String userid, String avatar) {


        mchat = new ArrayList<>();

        Map map_history = new HashMap();
        map_history.put("to_username", userid);
        String input_history = MaptoJsonUtill.getJson(map_history);

        Map result_history = OkhttpUtill.post("chat/chat_history", input_history);


//        String propsContent = (String) result_history.get("chat");
        System.out.println("이거라고고고고고고고고곡고");
//        System.out.println(propsContent);
        List<String> propsContent = MaptoJsonUtill.jsonlisttolist((JSONArray) result_history.get("chat"), "chatContent");
        List<String> propsType = MaptoJsonUtill.jsonlisttolist((JSONArray) result_history.get("chat"), "chatType");
        List<String> propsToUsername = MaptoJsonUtill.jsonlisttolist((JSONArray) result_history.get("chat"), "toUsername");
        List<String> propsFromUsername = MaptoJsonUtill.jsonlisttolist((JSONArray) result_history.get("chat"), "fromUsername");
        List<String> propsSendTime = MaptoJsonUtill.jsonlisttolist((JSONArray) result_history.get("chat"), "sendTime");

        //List<String> propsAvatar = MaptoJsonUtill.jsonlisttolist((JSONArray) result.get("contacts"),"propsAvatar");
        if (search_users.getText().toString().equals("")) {
            mchat.clear();
            for (int i = 0; i < propsContent.size(); i++) {
                String from_username = propsFromUsername.get(i);
                String to_username = propsToUsername.get(i);
                String type = propsType.get(i);
                String content = propsContent.get(i);
                String time = propsSendTime.get(i);
                //String avatar = propsAvatar.get(i)
                Chat chat = new Chat();
                //user.setAvatar(avatar);
                chat.setMessage(content);
                chat.setReceiver(to_username);
                chat.setSender(from_username);
                chat.setTime(time);
                chat.setType(type);
                mchat.add(chat);
            }
            messageAdapter = new MessageAdapter(MessageActivity.this, mchat, avatar,userid);
            recyclerView.setAdapter(messageAdapter);
        }




        }
    }
//
//    private void status(String status) {
//        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
//
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("status", status);
//
//        reference.updateChildren(hashMap);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        status("online");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        reference.removeEventListener(seenListener);
//        status("offline");
//    }
