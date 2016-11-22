package com.example.user.mediaplayer;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;



public class OpenSongActivity extends AppCompatActivity {

    private TextView songName;
    ImageButton playOrPause;
    ImageButton forawrd;
    ImageButton backward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_song);

        songName=(TextView)findViewById(R.id.songName);

        if (Intent.ACTION_VIEW.equals(getIntent().getAction()))
        {
            File file = new File(getIntent().getData().getPath());
            songName.setText(file.getName().toString());

            Log.e("hi","hi");
            // do what you want with the file...
        }
    }
}
