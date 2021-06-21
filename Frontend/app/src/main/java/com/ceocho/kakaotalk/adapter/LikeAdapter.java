

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
        import com.ceocho.kakaotalk.Model.Story;
        import com.ceocho.kakaotalk.Model.User;
        import com.ceocho.kakaotalk.R;
        import com.ceocho.kakaotalk.Utill.MaptoJsonUtill;
        import com.ceocho.kakaotalk.Utill.OkhttpUtill;

        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.ViewHolder> {


    private List<String> mLikeuser;
    public RecyclerView like_recycle;
    private Context mContext;


    public LikeAdapter(Context mContext, List<String> mLikeuser) {
        this.mLikeuser = mLikeuser;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public LikeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.like_item, parent, false);
        return new LikeAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull LikeAdapter.ViewHolder holder, int position) {
        String like = mLikeuser.get(position);
        System.out.println(like);
        holder.likeuser.setText(like);


    }

    @Override
    public int getItemCount() {
        return mLikeuser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView likeuser;




        public ViewHolder(View itemView) {
            super(itemView);
//이미지
//            story_content = itemView.findViewById(R.id.story_content);
//            avatar = itemView.findViewById(R.id.avatar);
            likeuser = itemView.findViewById(R.id.like_user);
            like_recycle = itemView.findViewById(R.id.like_list);




        }
    }

}
