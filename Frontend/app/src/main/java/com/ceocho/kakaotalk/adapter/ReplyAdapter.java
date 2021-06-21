
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
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import com.bumptech.glide.Glide;
//import com.ceocho.kakaotalk.MessageActivity;
        import com.ceocho.kakaotalk.MessageActivity;
        import com.ceocho.kakaotalk.Model.Chat;
        import com.ceocho.kakaotalk.Model.Reply;
        import com.ceocho.kakaotalk.Model.Story;
        import com.ceocho.kakaotalk.Model.User;
        import com.ceocho.kakaotalk.R;
        import com.ceocho.kakaotalk.Utill.MaptoJsonUtill;
        import com.ceocho.kakaotalk.Utill.OkhttpUtill;

        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ViewHolder> {


    private List<Reply> mReply;
    public RecyclerView reply_cycle;
    private Context mContext;


    public ReplyAdapter(Context mContext, List<Reply> mReply) {
        this.mReply = mReply;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public ReplyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.reply_item, parent, false);
        return new ReplyAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ReplyAdapter.ViewHolder holder, int position) {
        Reply reply = mReply.get(position);

        holder.reply_username.setText(reply.getUsername());
        holder.reply_time.setText(reply.getReplytime());
        holder.reply_content.setText(reply.getContent());


    }

    @Override
    public int getItemCount() {
        return mReply.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView reply_username,reply_time,reply_content;




        public ViewHolder(View itemView) {
            super(itemView);
//이미지
//            story_content = itemView.findViewById(R.id.story_content);
//            avatar = itemView.findViewById(R.id.avatar);
            reply_username = itemView.findViewById(R.id.reply_username);
            reply_time = itemView.findViewById(R.id.reply_time);
            reply_content = itemView.findViewById(R.id.reply_content);

            reply_cycle = itemView.findViewById(R.id.like_list);




        }
    }

}
