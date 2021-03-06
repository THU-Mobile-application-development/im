package com.ceocho.kakaotalk.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ceocho.kakaotalk.ATask.ListInsert;
import com.ceocho.kakaotalk.EditProfileActivity;
import com.ceocho.kakaotalk.MainActivity;
import com.ceocho.kakaotalk.MessageActivity;
import com.ceocho.kakaotalk.Model.Reply;
import com.ceocho.kakaotalk.Model.Story;
import com.ceocho.kakaotalk.Model.User;
import com.ceocho.kakaotalk.R;
import com.ceocho.kakaotalk.UserAddActivity;
import com.ceocho.kakaotalk.Utill.MaptoJsonUtill;
import com.ceocho.kakaotalk.Utill.OkhttpUtill;
import com.ceocho.kakaotalk.adapter.StoryAdapter;
import com.ceocho.kakaotalk.adapter.UserAdapter;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class StoryFragment extends Fragment {

    private RecyclerView recyclerView;

    private StoryAdapter storyAdapter;


    private List<Story> mStorys;

    Button publish_story,select_photo,select_video;


    final int LOAD_IMAGE = 1001;
    long fileSize = 0;

    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    public String imageRealPath, imageDbPath;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_story, container, false);
        publish_story = view.findViewById(R.id.publish_story_btn);
        select_photo = view.findViewById(R.id.select_image_btn);
        select_video = view.findViewById(R.id.select_video_btn);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        mStorys = new ArrayList<>();

        loadStorys();

        publish_story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OkhttpUtill.isNetworkConnected(getActivity().getApplicationContext()) == true) {
                    if(fileSize <= 30000000) { //?????? ????????? 30?????? ?????? ????????? ????????? ??? ??? ??????
                        System.out.println(imageRealPath);
                        new ListInsert(imageDbPath, imageRealPath,"story/publish").execute();

                    } else {
                        // ????????? ??????
                        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity().getApplicationContext());
                        builder.setTitle("??????");
                        builder.setMessage("?????? ????????? 30MB???????????? ????????? ???????????? ???????????? ????????????.\n30MB?????? ????????? ????????? ????????????!!!");
                        builder.setPositiveButton("???", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "???????????? ???????????? ?????? ????????????.", Toast.LENGTH_SHORT).show();
                }

                //?????? ?????? ??? ???????????????.
//                Story story = new Story();
//                List<Reply> reply = new ArrayList<>();
//                List<String> likeuser = new ArrayList<>();
//                Map my_result = OkhttpUtill.get("user/myinfo");
//                String username = my_result.get("username").toString();
//                String avatar = my_result.get("avatar").toString();
//
//                SimpleDateFormat sDate2 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
//
//                story.setReply(reply);
//                story.setLikeUsername(likeuser);
//                story.setLikesNum(0);
//                story.setUsername(username);
//                story.setPublishTime(sDate2.format(new Date()));
//                story.setAvatar(avatar);
////                story.setType();
//////??????????????? ???????????? ????????? ?????????
////                //????????? ??????????????? ?????? ????????? ??????????????? ??????
////                story.setContent();
//                mStorys.add(story);
//                storyAdapter.notifyDataSetChanged();


            }
        });


        select_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), LOAD_IMAGE);
            }
        });


        select_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), LOAD_IMAGE);
            }
        });




        return view;
    }










    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
            String sendText = data.getExtras().getString("result");
            if (sendText.equals("ok")) {


                refresh();

            }

        }

        if(requestCode == LOAD_IMAGE && resultCode == RESULT_OK) {
            try {
                String path = "";

                //??????????????? Uri ??????
                Uri selectImageUri = data.getData();
                if(selectImageUri != null) {
                    //Uri?????? ?????? ??????
                    path = getPathFromURI(selectImageUri);
                }



                //????????? ?????? ??????
                imageRealPath = path;
                Log.d("Sub1Add", "imageFilePathA Path : " + imageRealPath);
                String uploadFileName = imageRealPath.split("/")[imageRealPath.split("/").length - 1];
                imageDbPath = OkhttpUtill.baseURL + "story/publish/" + uploadFileName;

                fileSize = imageRealPath.length();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }




    }


    private void refresh() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();
    }



    //URI?????? ?????? ?????? ???????????? ?????????
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }




    private void loadStorys() {
        Map result = OkhttpUtill.get("story/list");


        List<String> storyAvatar = MaptoJsonUtill.jsonlisttolist((JSONArray) result.get("storyList"), "avatar");
        List<String> storyContent = MaptoJsonUtill.jsonlisttolist((JSONArray) result.get("storyList"), "content");
        List<String> storyPublishtime =  MaptoJsonUtill.jsonlisttolist((JSONArray) result.get("storyList"), "publishTime");
        List<String> storyId =  MaptoJsonUtill.jsonlisttolist((JSONArray) result.get("storyList"), "storyId");
        List<String> storyType =  MaptoJsonUtill.jsonlisttolist((JSONArray) result.get("storyList"), "type");
        List<String> storyUsername =  MaptoJsonUtill.jsonlisttolist((JSONArray) result.get("storyList"), "username");
        System.out.println("working?");
        //????????? string?????? ????????? ????????? ??????
        List<String> storyLikenum =  MaptoJsonUtill.jsonlisttolist((JSONArray) result.get("storyList"), "likesNum");
        List<String> storyReplynum =  MaptoJsonUtill.jsonlisttolist((JSONArray) result.get("storyList"), "replyNum");


        //arraylist ?????? arraylist ??????????????? ?????? ?????????
        List<String> storylikeUsername = MaptoJsonUtill.jsonlisttolist((JSONArray) result.get("storyList"), "likeUsername");
        List<String> storyReply = MaptoJsonUtill.jsonlisttolist((JSONArray) result.get("storyList"), "reply");
            mStorys.clear();
            for (int i = 0; i < storyId.size(); i++) {
                String avatar = storyAvatar.get(i);
                String id = storyId.get(i);
                String content = storyContent.get(i);
                String publishtime = storyPublishtime.get(i);
                String type = storyType.get(i);
                String username = storyUsername.get(i);
                String likenum = storyLikenum.get(i);
                String replynum = storyReplynum.get(i);
                List<String> reply_content = MaptoJsonUtill.getreplycontent(storyReply.get(i),Integer.parseInt(replynum));
                List<String> reply_time = MaptoJsonUtill.getreplytime(storyReply.get(i),Integer.parseInt(replynum));
                List<String> reply_username = MaptoJsonUtill.getreplyusername(storyReply.get(i),Integer.parseInt(replynum));
                List<Reply> Replylist = new ArrayList<>();
                for(int j = 0; j<Integer.parseInt(replynum);j++){
                    Reply reply = new Reply();
                    reply.setUsername(reply_username.get(j));
                    reply.setContent(reply_content.get(j));
                    reply.setReplytime(reply_time.get(j));
                    Replylist.add(reply);
                }
                List<String> userlike_list = new ArrayList<>();
                userlike_list = MaptoJsonUtill.getlikeuser(storylikeUsername.get(i),Integer.parseInt(likenum));
                System.out.println("???????????????!!");
                System.out.println(userlike_list);
                Story story = new Story();
                story.setReply(Replylist);
                story.setAvatar(avatar);
                story.setStoryId(id);
                story.setContent(content);
                story.setPublishTime(publishtime);
                story.setType(type);
                story.setUsername(username);
                story.setLikesNum(Integer.parseInt(likenum));
                story.setLikeUsername(userlike_list);
                mStorys.add(story);

            }
            storyAdapter = new StoryAdapter(getContext(), mStorys);
            recyclerView.setAdapter(storyAdapter);



    }

}
