package com.example.user.mediaplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<Song> songsAdapter;
    private MediaPlayer mediaPlayer;
    private ListView songsList;
    private ArrayList<Song> songArrayList;
    ImageButton playOrPause;
    ImageButton forawrd;
    ImageButton backward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songsList=(ListView) findViewById(R.id.songsList);
        songArrayList=new ArrayList<>();
        songsAdapter= new ArrayAdapter<Song>(this, android.R.layout.simple_list_item_1, songArrayList);
        songsList.setAdapter(songsAdapter);
        setSongsLists();

        songsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Song song=songsAdapter.getItem(i);
                Intent intent = new Intent(MainActivity.this, MediaService.class);
                intent.setAction("ACTION_PLAY");
                intent.putExtra("songID",song.getResId());
                startService(intent);
                playOrPause.setImageResource(R.drawable.pause);
            }
        });

        playOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(playOrPause.)
            }
        });


    }

    private void setSongsLists() {
        songsAdapter.add(new Song("Falling away with you",R.raw.muse_falling_away_with_you));
        songsAdapter.add(new Song("Madness",R.raw.muse_madness));
        songsAdapter.add(new Song("Hysteria",R.raw.muse_hysteria));
    }


}


