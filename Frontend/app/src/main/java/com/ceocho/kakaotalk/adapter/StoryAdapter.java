package com.ceocho.kakaotalk.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
//import com.ceocho.kakaotalk.MessageActivity;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ceocho.kakaotalk.Fragments.StoryFragment;
import com.ceocho.kakaotalk.MessageActivity;
import com.ceocho.kakaotalk.Model.Chat;
import com.ceocho.kakaotalk.Model.Reply;
import com.ceocho.kakaotalk.Model.Story;
import com.ceocho.kakaotalk.Model.User;
import com.ceocho.kakaotalk.R;
import com.ceocho.kakaotalk.Utill.MaptoJsonUtill;
import com.ceocho.kakaotalk.Utill.OkhttpUtill;
import com.ceocho.kakaotalk.VideoViewActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {


    private Context mContext;
    private List<Story> mStory;
    public LikeAdapter likeAdapter;
    public ReplyAdapter replyAdapter;
    public RecyclerView reply_recycle, like_recycle;


    public StoryAdapter(Context mContext, List<Story> mStory) {
        this.mStory = mStory;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public StoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.story_item, parent, false);
        return new StoryAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull StoryAdapter.ViewHolder holder, int position) {
        Story story = mStory.get(position);

        holder.story_username.setText(story.getUsername());
        holder.publish_time.setText(story.getPublishTime());
        String url = story.getAvatar();

        url = url.replace("/home/uploads/", "");
        Glide.with(mContext)
                .load(OkhttpUtill.contentURL + url)
                .into(holder.avatar);
        String url_content = story.getContent();

        //여기서 영상인지 사진인지 판단 필요 type으로
        if (story.getType().equals("0")) {
            url_content = url_content.replace("/home/uploads/", "");
            Glide.with(mContext)
                    .load(OkhttpUtill.contentURL + url_content)
                    .into(holder.story_content);
        } else {
            url_content = url_content.replace("/home/uploads/", "");
            Glide.with(mContext)
                    .asBitmap()
                    .load(OkhttpUtill.contentURL + url_content)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(holder.story_content);

        }

        replyAdapter = new ReplyAdapter(mContext, story.getReply());
        ;
        reply_recycle.setAdapter(replyAdapter);
        likeAdapter = new LikeAdapter(mContext, story.getLikeUsername());
        like_recycle.setAdapter(likeAdapter);

        String finalUrl_content = url_content;
        holder.story_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (story.getType().equals("1")) {
                    Intent intent = new Intent(mContext, VideoViewActivity.class);
                    intent.putExtra("url", OkhttpUtill.contentURL + finalUrl_content);
                    mContext.startActivity(intent);



                }
            }
        });


        holder.like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map map = new HashMap();
                map.put("storyId", story.getStoryId());
                String input = MaptoJsonUtill.getJson(map);
                Map result = OkhttpUtill.post("story/like", input);
                Map my_result = OkhttpUtill.get("user/myinfo");
                String username = my_result.get("username").toString();
                story.getLikeUsername().add(username);
                likeAdapter.notifyDataSetChanged();

            }
        });

        holder.reply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(mContext);
                AlertDialog.Builder dlg = new AlertDialog.Builder(mContext);
                dlg.setTitle("Reply");
                dlg.setView(editText);
                dlg.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Map map = new HashMap();
                        map.put("storyId", story.getStoryId());
                        map.put("content", editText.getText().toString());
                        String input = MaptoJsonUtill.getJson(map);
                        Map result = OkhttpUtill.post("story/reply", input);
                        Map my_result = OkhttpUtill.get("user/myinfo");
                        String username = my_result.get("username").toString();
                        Reply reply = new Reply();
                        reply.setContent(editText.getText().toString());
                        reply.setUsername(username);
                        SimpleDateFormat sDate2 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                        reply.setReplytime(sDate2.format(new Date()));
                        story.getReply().add(reply);
                        replyAdapter.notifyDataSetChanged();

                    }
                });
                dlg.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dlg.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mStory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView story_username;
        public ImageView story_content, avatar;
        public TextView publish_time;
        public Button like_btn, reply_btn;


        public ViewHolder(View itemView) {
            super(itemView);
            story_content = itemView.findViewById(R.id.story_content);
            avatar = itemView.findViewById(R.id.avatar);
            story_username = itemView.findViewById(R.id.story_username);
            publish_time = itemView.findViewById(R.id.publish_time);
            reply_btn = itemView.findViewById(R.id.reply_btb);
            like_btn = itemView.findViewById(R.id.like_btn);
            reply_recycle = itemView.findViewById(R.id.reply_list);
            like_recycle = itemView.findViewById(R.id.like_list);
            reply_recycle.setHasFixedSize(true);
            reply_recycle.setLayoutManager(new LinearLayoutManager(mContext));
            like_recycle.setHasFixedSize(true);
            like_recycle.setLayoutManager(new LinearLayoutManager(mContext));


        }
    }


}
