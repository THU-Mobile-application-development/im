package com.ceocho.kakaotalk.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ceocho.kakaotalk.Fragments.UsersFragment;
import com.ceocho.kakaotalk.MainActivity;
//import com.ceocho.kakaotalk.MessageActivity;
import com.ceocho.kakaotalk.MessageActivity;
import com.ceocho.kakaotalk.Model.Chat;
import com.ceocho.kakaotalk.Model.User;
import com.ceocho.kakaotalk.R;
import com.ceocho.kakaotalk.UserAddActivity;
import com.ceocho.kakaotalk.Utill.MaptoJsonUtill;
import com.ceocho.kakaotalk.Utill.OkhttpUtill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUsers = new ArrayList<>();
    private boolean ischat;

    String theLastMessage;

    public UserAdapter(Context mContext, List<User> mUsers, boolean ischat) {
        this.mUsers = mUsers;
        this.mContext = mContext;
        this.ischat = ischat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final User user = mUsers.get(position);
        holder.username.setText(user.getUsername());
        holder.last_msg.setText(user.getLastchat());
        holder.last_time.setText(user.getLasttime());
        String url = user.getAvatar();
        url = url.replace("/home/uploads/", "");
        Glide.with(mContext)
                .load(OkhttpUtill.contentURL + url)
                .into(holder.profile_image);




        String unreadnum = user.getUnreadnum();
        String zero = "0";
        if (unreadnum == null) {
            System.out.println("sorry");
            holder.relativelayout.setVisibility(View.INVISIBLE);

        } else if (unreadnum.equals(zero)) {
            holder.relativelayout.setVisibility(View.INVISIBLE);
            System.out.println("it is 0");

        } else {
            System.out.println("not 0 then what");
            holder.unread_num.setText(user.getUnreadnum());

        }
        if (user.getLasttime() != null) {
            String finalUrl = url;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(mContext, MessageActivity.class);
                    intent.putExtra("username", user.getUsername());
                    intent.putExtra("Avatar", OkhttpUtill.contentURL + finalUrl);
                    mContext.startActivity(intent);

                }
            });
        } else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show(user.getUsername(), user.getAvatar());


                }
            });
        }


    }

    private void show(String contact_username, String Avatar) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("用户操作");
        builder.setPositiveButton("cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mContext.getApplicationContext(), "cancel.", Toast.LENGTH_LONG).show();
                    }
                });
        builder.setNeutralButton("delete",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Map map = new HashMap();
                        map.put("delete_username", contact_username);
                        String input = MaptoJsonUtill.getJson(map);
                        Map result = OkhttpUtill.post("contact/delete", input);
                        //삳제성공
                        if (result.get("success").toString() == "true") {
                            Toast.makeText(mContext.getApplicationContext(), "delete complete.", Toast.LENGTH_LONG).show();
                            //mContext.startActivity(new Intent(mContext, UsersFragment.class));
                            Intent it = new Intent(mContext, MainActivity.class);
                            mContext.startActivity(it);
                        } else {
                            Toast.makeText(mContext.getApplicationContext(), "delete failed.", Toast.LENGTH_LONG).show();

                        }

                    }
                });

        builder.setNegativeButton("chat",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Map map_chat = new HashMap();
                        map_chat.put("to_username", contact_username);
                        String input = MaptoJsonUtill.getJson(map_chat);
                        Map result_chat = OkhttpUtill.post("chat/check_relation", input);
                        if (result_chat.get("success").toString() == "true") {
                            Intent intent = new Intent(mContext, MessageActivity.class);
                            intent.putExtra("username", contact_username);
                            intent.putExtra("Avatar", Avatar);
                            //intent.putExtra("my_username", Avatar);

                            mContext.startActivity(intent);
                        }

                    }
                });
        builder.show();
    }


    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView username;
        public ImageView profile_image;
        private TextView last_msg;
        private TextView last_time;
        private TextView unread_num;
        private RelativeLayout relativelayout;


        public ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
            last_msg = itemView.findViewById(R.id.last_msg);
            last_time = itemView.findViewById(R.id.last_time);
            unread_num = itemView.findViewById(R.id.unread_num);
            relativelayout = itemView.findViewById(R.id.relativelayout);

        }
    }

    public void filterList(List<User> filteredList) {
        mUsers = filteredList;
        notifyDataSetChanged();
    }



}
