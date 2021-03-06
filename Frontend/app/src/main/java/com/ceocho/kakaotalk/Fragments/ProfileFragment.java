package com.ceocho.kakaotalk.Fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ceocho.kakaotalk.ATask.ListInsert;
import com.ceocho.kakaotalk.EditProfileActivity;
import com.ceocho.kakaotalk.R;
import com.ceocho.kakaotalk.ResetPasswordActivity;
import com.ceocho.kakaotalk.ATask.BitmapUtil;
import com.ceocho.kakaotalk.Utill.OkhttpUtill;


import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {

    CircleImageView image_profile;
    TextView username, nickname, phonenumber;
    Button edit_profile, upload_avatar, edit_password, select_photo,edit_username;
    final int LOAD_IMAGE = 1001;
    long fileSize = 0;

    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    public String imageRealPath, imageDbPath;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        image_profile = view.findViewById(R.id.profile_image);
        username = view.findViewById(R.id.username);
        nickname = view.findViewById(R.id.nickname);
        phonenumber = view.findViewById(R.id.phonenumber);
        edit_profile = view.findViewById(R.id.edit_profile_btn);
        select_photo = view.findViewById(R.id.select_photo_btn);
        edit_password = view.findViewById(R.id.edit_password_btn);
        upload_avatar = view.findViewById(R.id.upload_avatar_btn);
        edit_username = view.findViewById(R.id.edit_username_btn);

        Map result = OkhttpUtill.get("user/myinfo");

        username.setText(result.get("username").toString());
        nickname.setText(result.get("nickname").toString());
        phonenumber.setText(result.get("phonenumber").toString());

        try {
            Bitmap bitmap = new BitmapUtil(result.get("avatar").toString()).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String url = result.get("avatar").toString();
        url = url.replace("/home/uploads/", "");
        Glide.with(this)
                .load(OkhttpUtill.contentURL + url)
                .into(image_profile);

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                intent.putExtra("data", "edit_info");
                startActivityForResult(intent, 1);

            }

        });
        edit_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ResetPasswordActivity.class);
                intent.putExtra("data", "Test Popup");
                startActivityForResult(intent, 1);
            }
        });

        edit_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                intent.putExtra("data", "edit_username");
                startActivityForResult(intent, 1);
            }
        });


        select_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), LOAD_IMAGE);
            }
        });


        upload_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //??????????????? ??????????????? ???????????? ?????????
                if (OkhttpUtill.isNetworkConnected(getActivity().getApplicationContext()) == true) {
                    if (fileSize <= 30000000) { //?????? ????????? 30?????? ?????? ????????? ????????? ??? ??? ??????

                        System.out.println("it is working?");
                        new ListInsert(imageDbPath, imageRealPath, "user/avatar").execute();

                    } else {
                        // ????????? ??????
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
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
                reload();
            }

        });


        return view;
    }

    public void reload() {

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.detach(this).attach(this).commit();
    }

    // Bitmap bmImg;

//    private Bitmap getImageBitmap(String url) {
//        Bitmap bm = null;
//        URL myFileUrl =null;
//
//        try {
//            myFileUrl= new URL(url);
//
//            HttpURLConnection conn= (FileURLConnection)myFileUrl.openConnection();
//            conn.setDoInput(true);
//            conn.connect();
//            int length = conn.getContentLength();
//            InputStream is = conn.getInputStream();
//
//            bmImg = BitmapFactory.decodeStream(is);
//            image_profile.setImageBitmap(bmImg);
//        } catch (IOException e) {
//            System.out.println("Error");
//            //Log.e(TAG, "Error getting bitmap", e);
//        }
//        return bm;
//    }
//


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
            String sendText = data.getExtras().getString("result");
            if (sendText.equals("ok")) {
                System.out.println("it is working?");


                refresh();

            }

        }

        if (requestCode == LOAD_IMAGE && resultCode == RESULT_OK) {
            try {
                String path = "";

                //??????????????? Uri ??????
                Uri selectImageUri = data.getData();
                if (selectImageUri != null) {
                    //Uri?????? ?????? ??????
                    path = getPathFromURI(selectImageUri);
                }


                //????????? ?????? ??????
                imageRealPath = path;
                Log.d("Sub1Add", "imageFilePathA Path : " + imageRealPath);
                String uploadFileName = imageRealPath.split("/")[imageRealPath.split("/").length - 1];
                imageDbPath = OkhttpUtill.baseURL + "user/avatar/" + uploadFileName;

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
}
