package com.example.user.mediaplayer;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;



public class OpenSongActivity extends AppCompatActivity {

    private TextView songName;
    ImageButton play;
    ImageButton pause;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_song);

        songName=(TextView)findViewById(R.id.songName);
        play=(ImageButton)findViewById(R.id.play_file);
        pause=(ImageButton)findViewById(R.id.pause_file);

        if(getIntent().getData()!=null)
        {
            playSong();
            File file=new File(getIntent().getData().getPath());
            songName.setText(file.getName());
        }

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSong();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseSong();
            }
        });


    }

    private void playSong() {
        Intent intent = new Intent(OpenSongActivity.this, MediaService.class);
        intent.setAction(MediaService.ACTION_PLAY_FILE);
        intent.putExtra("uri",getIntent().getData());
        startService(intent);
    }


    private void pauseSong(){
        Intent intent = new Intent(OpenSongActivity.this, MediaService.class);
        intent.setAction(MediaService.ACTION_PAUSE_FILE);
        startService(intent);
    }
}
