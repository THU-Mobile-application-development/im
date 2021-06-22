package com.ceocho.kakaotalk.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.renderscript.Sampler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ceocho.kakaotalk.Model.Chat;
import com.ceocho.kakaotalk.Model.Chatlist;
import com.ceocho.kakaotalk.Model.User;
import com.ceocho.kakaotalk.Notifications.Data;
import com.ceocho.kakaotalk.Notifications.Token;
import com.ceocho.kakaotalk.R;
import com.ceocho.kakaotalk.UserAddActivity;
import com.ceocho.kakaotalk.Utill.MaptoJsonUtill;
import com.ceocho.kakaotalk.Utill.OkhttpUtill;
import com.ceocho.kakaotalk.adapter.UserAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ChatsFragment extends Fragment {


    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mUsers, filteredList;

    EditText search_users;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        filteredList = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        mUsers = new ArrayList<>();

        search_users = view.findViewById(R.id.search_users);
        readUsers();


        search_users.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String searchText = search_users.getText().toString();
                searchFilter(searchText);

            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void searchFilter(String searchText) {
        filteredList.clear();
        System.out.println("wordfsgsgsgsfasfasfasf?");
        System.out.println(mUsers.size());
        for (int i = 0; i < mUsers.size(); i++) {
            System.out.println(mUsers.get(i).getUsername().toLowerCase());
            System.out.println(searchText.toLowerCase());
            if (mUsers.get(i).getUsername().toLowerCase().contains(searchText.toLowerCase())) {
                System.out.println("workiong?");
                filteredList.add(mUsers.get(i));
            }
        }

        userAdapter.filterList(filteredList);
    }


    private void readUsers() {
        Map result = OkhttpUtill.get("chat/list");


        List<String> propsLastChat = MaptoJsonUtill.jsonlisttolist((JSONArray) result.get("chat"), "lastChat");
        List<String> propsLastTime = MaptoJsonUtill.jsonlisttolist((JSONArray) result.get("chat"), "lastTime");
        List<String> propsAvatar = MaptoJsonUtill.jsonlisttolist((JSONArray) result.get("chat"), "toAvatar");
        List<String> propsToUsername = MaptoJsonUtill.jsonlisttolist((JSONArray) result.get("chat"), "toUsername");
        List<String> propsUnreadNum = MaptoJsonUtill.jsonlisttolist((JSONArray) result.get("chat"), "unreadNum");


        if (search_users.getText().toString().equals("")) {
            mUsers.clear();
            for (int i = 0; i < propsLastChat.size(); i++) {
                String username = propsToUsername.get(i);
                String lastchat = propsLastChat.get(i);
                String lasttime = propsLastTime.get(i);
                String avatar = propsAvatar.get(i);
                String unreadnum = propsUnreadNum.get(i);
                User user = new User();
                user.setAvatar(avatar);
                user.setUsername(username);
                user.setLastchat(lastchat);
                user.setAvatar(avatar);
                user.setLasttime(lasttime);
                user.setUnreadnum(unreadnum);
                mUsers.add(user);
            }
            userAdapter = new UserAdapter(getContext(), mUsers, false);
            recyclerView.setAdapter(userAdapter);
        }


    }

}
