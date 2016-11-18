package com.example.user.mediaplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<Song> songsAdapter;
    private ListView songsList;
    private ArrayList<Song> songArrayList;
    ImageButton playOrPause;
    ImageButton forawrd;
    ImageButton backward;
    int currentSongPosition=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songsList=(ListView) findViewById(R.id.songsList);
        playOrPause=(ImageButton)findViewById(R.id.playOrPause);
        forawrd=(ImageButton)findViewById(R.id.forward);
        backward=(ImageButton)findViewById(R.id.backward);

        songArrayList=new ArrayList<>();
        songsAdapter= new ArrayAdapter<Song>(this, android.R.layout.simple_list_item_1, songArrayList);
        songsList.setAdapter(songsAdapter);
        setSongsLists();

        songsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                playSong(position);
            }
        });

        playOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseSong();
            }
        });

        forawrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(playOrPause.getTag().toString())==R.drawable.play || currentSongPosition<0 || currentSongPosition>=songsList.getCount()-1)
                {
                    playSong(0);
                }
                else
                {
                    currentSongPosition++;
                    playSong(currentSongPosition);
                }
            }
        });

        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("current song position",currentSongPosition+"");
                if(Integer.parseInt(playOrPause.getTag().toString())==R.drawable.play || currentSongPosition<0 || currentSongPosition>songsList.getCount()-1)
                {
                    playSong(0);
                }
                else if(currentSongPosition==0)
                {
                    playSong(songsList.getCount()-1);
                }

                else
                {
                    currentSongPosition--;
                    playSong(currentSongPosition);
                }
            }
        });
    }

    private void pauseSong() {
        Intent intent = new Intent(MainActivity.this, MediaService.class);
        intent.setAction("ACTION_PAUSE");
        startService(intent);
        playOrPause.setImageResource(R.drawable.play);
        playOrPause.setTag(R.drawable.play);
    }

    private void playSong(int position) {
        currentSongPosition=position;
        Song song=songsAdapter.getItem(position);
        Intent intent = new Intent(MainActivity.this, MediaService.class);
        intent.setAction("ACTION_PLAY");
        intent.putExtra("songID",song.getResId());
        startService(intent);
        playOrPause.setImageResource(R.drawable.pause);
        playOrPause.setTag(R.drawable.pause);
    }

    private void setSongsLists() {
        songsAdapter.add(new Song("Falling away with you",R.raw.muse_falling_away_with_you));
        songsAdapter.add(new Song("Madness",R.raw.muse_madness));
        songsAdapter.add(new Song("Hysteria",R.raw.muse_hysteria));
    }

}


