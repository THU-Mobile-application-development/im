package com.ceocho.kakaotalk;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentTransaction;

import com.ceocho.kakaotalk.ATask.ListInsert;
import com.ceocho.kakaotalk.Utill.GpsTracker;
import com.ceocho.kakaotalk.Utill.OkhttpUtill;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SendEtcActivity extends AppCompatActivity {
    private static final String TAG = "main Sub1Insert";
    Button send_geo,open_camera,select_photo,open_video,select_video,open_audio,select_audiio,confirm,cancel;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    final int LOAD_IMAGE = 1001;
    final int CAMERA_REQUEST = 1000;
    final int REQUEST_VIDEO_CAPTURE = 1;
    long fileSize = 0;

    public String imageRealPath, imageDbPath;

    String Geo;
    int flag = 0;
    File file = null;
    java.text.SimpleDateFormat tmpDateFormat;
    ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        tmpDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");

        setContentView(R.layout.activity_send_etc);

        send_geo = findViewById(R.id.send_geo);
        open_camera = findViewById(R.id.open_camera);
        open_audio = findViewById(R.id.open_audio);
        open_video = findViewById(R.id.open_video);
        select_photo = findViewById(R.id.select_photo);
        select_audiio = findViewById(R.id.select_audio);
        select_video = findViewById(R.id.select_video);
        confirm = findViewById(R.id.confirm);
        cancel = findViewById(R.id.cancel);
        imageView = findViewById(R.id.imageView);



        send_geo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 0;
                GpsTracker gpsTracker = new GpsTracker(SendEtcActivity.this);
                double latitude = gpsTracker.getLatitude(); // 위도
                double longitude = gpsTracker.getLongitude(); //경도
                // 필요시
                String address = getCurrentAddress(latitude, longitude);
                System.out.println("주소");
                System.out.println(latitude);
                System.out.println(longitude);
                System.out.println(address);
                Geo = "latitude:" + Double.toString(latitude) +"/" +"longitude:" + Double.toString(longitude)+"/"+"address:"+address;
                    }
                });
        open_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try { //파일을 만들고 절대 경로를 logcat에 출력
                    file = createFile();
                    Log.d(TAG, "onClick: " + file.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                }


                //이미지뷰와 인텐트 설정
                imageView.setVisibility(View.VISIBLE);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // API24 이상 부터
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,
                            FileProvider.getUriForFile(getApplicationContext(),
                                    getApplicationContext().getPackageName() + ".fileprovider", file));
                    Log.d("sub1:appId", getApplicationContext().getPackageName());
                }else {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                }

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, CAMERA_REQUEST);
                }
            }
        });
        open_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try { //파일을 만들고 절대 경로를 logcat에 출력
                    file = createFile();
                    Log.d(TAG, "onClick: " + file.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                }


                //이미지뷰와 인텐트 설정
                imageView.setVisibility(View.VISIBLE);
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // API24 이상 부터
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,
                            FileProvider.getUriForFile(getApplicationContext(),
                                    getApplicationContext().getPackageName() + ".fileprovider", file));
                    Log.d("sub1:appId", getApplicationContext().getPackageName());
                }else {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                }

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);
                }
            }
        });

        select_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag =1;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), LOAD_IMAGE);
            }
        });

        select_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 2;
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select video"), LOAD_IMAGE);
            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();//startActivity()를 할것이 아니므로 그냥 빈 인텐트로 만듦
                if(flag == 0) {
                    intent.putExtra("data", Geo);
                    setResult(flag, intent);
                }
                if(flag==1||flag==2){
                    intent.putExtra("data",imageRealPath);
                    setResult(flag,intent);
                }

                finish();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setResult(RESULT_CANCELED);
                finish();

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == LOAD_IMAGE && resultCode == RESULT_OK) {
            try {
                String path = "";

                //데이타에서 Uri 얻기
                Uri selectImageUri = data.getData();
                if (selectImageUri != null) {
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



    //URI에서 실제 경로 추출하는 메서드
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getApplicationContext().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }


    private File createFile() throws IOException {
        String imageFileName = "My" + tmpDateFormat.format(new Date()) + ".jpg";
        File storageDir = Environment.getExternalStorageDirectory();
        File curFile = new File(storageDir, imageFileName);
        return curFile;
    }



    public String getCurrentAddress(double latitude, double longitude) {
        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 100);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            showDialogForLocationServiceSetting();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            showDialogForLocationServiceSetting();
            return "잘못된 GPS 좌표";
        }
        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            showDialogForLocationServiceSetting();
            return "주소 미발견";
        }
        Address address = addresses.get(0);
        return address.getAddressLine(0).toString() + "\n";
    } //여기부터는 GPS 활성화를 위한 메소드들

    private void showDialogForLocationServiceSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SendEtcActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    }
