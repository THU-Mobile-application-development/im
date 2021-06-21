package com.ceocho.kakaotalk.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
//import com.ceocho.kakaotalk.MessageActivity;
import com.ceocho.kakaotalk.Fragments.StoryFragment;
import com.ceocho.kakaotalk.MessageActivity;
import com.ceocho.kakaotalk.Model.Chat;
import com.ceocho.kakaotalk.Model.Story;
import com.ceocho.kakaotalk.Model.User;
import com.ceocho.kakaotalk.R;
import com.ceocho.kakaotalk.Utill.MaptoJsonUtill;
import com.ceocho.kakaotalk.Utill.OkhttpUtill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {


    private Context mContext;
    private List<Story> mStory;
    public LikeAdapter likeAdapter;
    public ReplyAdapter replyAdapter;
    public RecyclerView reply_recycle,like_recycle;



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
        replyAdapter = new ReplyAdapter(mContext,story.getReply());;
        reply_recycle.setAdapter(replyAdapter);
        likeAdapter = new LikeAdapter(mContext,story.getLikeUsername());
        like_recycle.setAdapter(likeAdapter);

        holder.like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map map = new HashMap();
                map.put("storyId", story.getStoryId());
                String input = MaptoJsonUtill.getJson(map);
                Map result = OkhttpUtill.post("story/like", input);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mStory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView story_username;
        public ImageView story_content,avatar;
        public TextView publish_time;
        public Button like_btn,reply_btn;




        public ViewHolder(View itemView) {
            super(itemView);
//이미지
//            story_content = itemView.findViewById(R.id.story_content);
//            avatar = itemView.findViewById(R.id.avatar);
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
