package com.ceocho.kakaotalk.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ceocho.kakaotalk.ATask.ListInsert;
import com.ceocho.kakaotalk.EditProfileActivity;
import com.ceocho.kakaotalk.LoginActivity;
import com.ceocho.kakaotalk.MainActivity;
import com.ceocho.kakaotalk.Model.User;
import com.ceocho.kakaotalk.R;
import com.ceocho.kakaotalk.ResetPasswordActivity;
import com.ceocho.kakaotalk.Utill.BitmapUtil;
import com.ceocho.kakaotalk.Utill.OkhttpUtill;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;


import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {

    CircleImageView image_profile;
    TextView username, nickname, phonenumber;
    Button edit_profile, upload_avatar, edit_password,select_photo;
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

        Map result = OkhttpUtill.get("user/myinfo");

        username.setText(result.get("username").toString());
        nickname.setText(result.get("nickname").toString());
        phonenumber.setText(result.get("phonenumber").toString());

//        File sourceimage = new File("c:\\new_logo.gif");
//        image1 = ImageIO.read(sourceimage);
//
//        InputStream is = new BufferedInputStream(
//                new FileInputStream("c:\\new_logo.gif"));
//        image2 = ImageIO.read(is);
//

//        Bitmap bm = getImageBitmap("file://"+result.get("avatar").toString());
//        image_profile.setImageBitmap(bm);
//        Glide.with(this)
//                .load(bm)
//                .into(image_profile);
//
  //  Bitmap bitmap = getImageBitmap("file://"+result.get("avatar").toString());
       Uri myUri = Uri.parse("file:///usr/local/share/avatar/2021/06/14/9eab4881-80d8-42d9-9042-47db296a118d_IMG_20210614_033500.jpg");
//        Bitmap bitmap = null;
//        try {
//            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), myUri);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        Bitmap  mBitmap = null;
//        try {
//            mBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), myUri);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // image_profile.setImageResource(mBitmap);
//        Glide.with(this).load(mBitmap).into(image_profile);

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                intent.putExtra("data", "Test Popup");
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
                //네트워크가 정상적으로 연결되어 있으면
                if(OkhttpUtill.isNetworkConnected(getActivity().getApplicationContext()) == true) {
                    if(fileSize <= 30000000) { //파일 크기가 30메가 보다 작아야 업로드 할 수 있음

                        System.out.println("it is working?");
                        new ListInsert(imageDbPath, imageRealPath).execute();

                    } else {
                        // 알림창 띄움
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
                        builder.setTitle("알림");
                        builder.setMessage("파일 크기가 30MB초과하는 파일은 업로드가 제한되어 있습니다.\n30MB이하 파일로 선택해 주십시요!!!");
                        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "인터넷이 연결되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });












        return view;
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

        if(requestCode == LOAD_IMAGE && resultCode == RESULT_OK) {
            try {
                String path = "";

                //데이타에서 Uri 얻기
                Uri selectImageUri = data.getData();
                if(selectImageUri != null) {
                    //Uri에서 경로 얻기
                    path = getPathFromURI(selectImageUri);
                }



                //이미지 경로 설정
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



    //URI에서 실제 경로 추출하는 메서드
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
