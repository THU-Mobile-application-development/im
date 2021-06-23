package com.ceocho.kakaotalk.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
//import com.ceocho.kakaotalk.MessageActivity;
import com.ceocho.kakaotalk.MessageActivity;
import com.ceocho.kakaotalk.Model.Chat;
import com.ceocho.kakaotalk.Model.User;
import com.ceocho.kakaotalk.R;
import com.ceocho.kakaotalk.Utill.MaptoJsonUtill;
import com.ceocho.kakaotalk.Utill.OkhttpUtill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private List<Chat> mChat;
    private String imageurl;
    private String userid;


    public MessageAdapter(Context mContext, List<Chat> mChat, String imageurl,String userid) {
        this.mChat = mChat;
        this.mContext = mContext;
        this.imageurl = imageurl;
        this.userid = userid;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {

        Chat chat = mChat.get(position);

        holder.show_message.setText(chat.getMessage());


        String url = imageurl;
        url = url.replace("/home/uploads/", "");
        Glide.with(mContext)
                .load(OkhttpUtill.contentURL + url)
                .into(holder.profile_image);


//사진에 대해서 설정
//        if (imageurl.equals("default")) {
//            holder.profile_image.setmageResource(R.mipmap.ic_launcher);
//        } else {
//            Glide.with(mContext).load(imageurl).into(holder.profile_image);
//        }

//시간에 대해서 설정
//        if (position == mChat.size() - 1) {
//            if (chat.getTime()) {
//                holder.txt_seen.setText("");
//            } else {
//                holder.txt_seen.setText("1");
//            }
//        } else {
//            holder.txt_seen.setVisibility(View.GONE);
//        }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



        public TextView show_message;
        public ImageView profile_image;
        public TextView txt_seen;


        public ViewHolder(View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            txt_seen = itemView.findViewById(R.id.time);

        }
    }

    @Override
    public int getItemViewType(int position) {
        Map map_chat = new HashMap();
        map_chat.put("to_username", userid);
        String input = MaptoJsonUtill.getJson(map_chat);
        Map result_chat = OkhttpUtill.post("chat/check_relation", input);

        if (mChat.get(position).getSender().equals(userid)) {
            return MSG_TYPE_LEFT;
        } else {
            return MSG_TYPE_RIGHT;
        }
    }
}
