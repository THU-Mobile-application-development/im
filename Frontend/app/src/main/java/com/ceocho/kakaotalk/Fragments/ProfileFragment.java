package com.ceocho.kakaotalk.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ceocho.kakaotalk.EditProfileActivity;
import com.ceocho.kakaotalk.LoginActivity;
import com.ceocho.kakaotalk.MainActivity;
import com.ceocho.kakaotalk.Model.User;
import com.ceocho.kakaotalk.R;
import com.ceocho.kakaotalk.ResetPasswordActivity;
import com.ceocho.kakaotalk.Utill.OkhttpUtill;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {

    CircleImageView image_profile;
    TextView username, nickname, phonenumber;
    Button edit_profile, upload_avatar, edit_password;

    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        image_profile = view.findViewById(R.id.profile_image);
        username = view.findViewById(R.id.username);
        nickname = view.findViewById(R.id.nickname);
        phonenumber = view.findViewById(R.id.phonenumber);
        edit_profile = view.findViewById(R.id.edit_profile_btn);
        upload_avatar = view.findViewById(R.id.upload_avatar_btn);
        edit_password = view.findViewById(R.id.edit_password_btn);

        Map result = OkhttpUtill.get("user/myinfo");

        username.setText(result.get("username").toString());
        nickname.setText(result.get("nickname").toString());
        phonenumber.setText(result.get("phonenumber").toString());
        //image_profile.setImageResource();
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
        return view;
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
                System.out.println("it is working?");


                refresh();

            }

        }
    }


    private void refresh() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();
    }


//    public void mOnPopupClick(View v){
//        //데이터 담아서 팝업(액티비티) 호출
//        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
//        intent.putExtra("data", "Test Popup");
//        startActivityForResult(intent, 1);
//    }


//        image_profile.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                openImage();
//            }
//
//        });
//
//        return view;
//    }
//
//    private void openImage() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, IMAGE_REQUEST);
//    }
//
//    private String getFileExtension(Uri uri) {
//        ContentResolver contentResolver = getContext().getContentResolver();
//        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
//        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
//    }
//
//    private void uploadImage() {
//        final ProgressDialog pd = new ProgressDialog(getContext());
//        pd.setMessage("Uploading");
//        pd.show();
//
//        if (imageUri != null) {
//            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
//                    +"."+getFileExtension(imageUri));
//
//            uploadTask = fileReference.putFile(imageUri);
//
//            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                @Override
//                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                    if (!task.isSuccessful()){
//                        throw task.getException();
//                    }
//
//                    return fileReference.getDownloadUrl();
//
//                }
//            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                    if (task.isSuccessful()){
//                        Uri downloadUri = task.getResult();
//                        String mUri = downloadUri.toString();
//
//                        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
//                        HashMap<String, Object> map = new HashMap<>();
//                        map.put("imageURL", mUri);
//                        reference.updateChildren(map);
//
//                        pd.dismiss();
//                    } else {
//                        Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
//                        pd.dismiss();
//                    }
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        } else {
//            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if ((requestCode == IMAGE_REQUEST) && (resultCode == RESULT_OK)
//                && (data != null) && (data.getData() != null)){
//            imageUri = data.getData();
//
//            if (uploadTask != null && uploadTask.isInProgress()){
//                Toast.makeText(getContext(),"Upload in progress", Toast.LENGTH_SHORT).show();
//            } else {
//                uploadImage();
//            }
//        }
//    }
}
