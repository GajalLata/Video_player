package com.example.vgvideoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    VideoView videoView;
    ArrayList<File> Songs;
    String text;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Intent intent = getIntent();
        Bundle bundle=intent.getExtras();
        Songs=(ArrayList) bundle.getParcelableArrayList("songList");
        text=intent.getStringExtra("currentSong");
        position=intent.getIntExtra("position",0);

        Toast.makeText(this, Songs.get(position).toString(), Toast.LENGTH_SHORT).show();
        Uri uri=Uri.parse(Songs.get(position).toString());

        videoView=findViewById(R.id.videoView);
        videoView.setVideoURI(uri);
        MediaController mediaController=new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();


    }
}