package com.ceocho.kakaotalk;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoViewActivity extends AppCompatActivity {

    public final static int URL = 1;
    public final static int SDCARD = 2;
    VideoView videoView;
    Button btnStart, btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);

        Intent getIntent = getIntent();
        String VIDEO_URL = getIntent.getStringExtra("url");

        /**
         * 영상을 출력하기 위한 비디오뷰
         * SurfaceView를 상속받아 만든 클래스
         * 웬만하면 VideoView는 그때 그때 생성해서 추가 후 사용
         * 화면 전환 시 여러 UI가 있을 때 화면에 제일 먼저 그려져서 보기에 좋지 않을 때가 있다
         * 예제에서 xml에 추가해서 해봄
         */
        //레이아웃 위젯 findViewById
        videoView = (VideoView) findViewById(R.id.view);

        //미디어컨트롤러 추가하는 부분
        MediaController controller = new MediaController(VideoViewActivity.this);
        videoView.setMediaController(controller);

        //비디오뷰 포커스를 요청함
        videoView.requestFocus();

        videoView.setVideoURI(Uri.parse(VIDEO_URL));
        videoView.start();


        //동영상이 재생준비가 완료되었을 때를 알 수 있는 리스너 (실제 웹에서 영상을 다운받아 출력할 때 많이 사용됨)
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Toast.makeText(VideoViewActivity.this,
                        "Video is ready", Toast.LENGTH_SHORT).show();
            }
        });

        //동영상 재생이 완료된 걸 알 수 있는 리스너
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //동영상 재생이 완료된 후 호출되는 메소드
                Toast.makeText(VideoViewActivity.this,
                        "video play complete.", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

    }

}


