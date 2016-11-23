package com.example.user.mediaplayer;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
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
    public static String POSITION_SONG ="positionSong";
    TelephonyManager tManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songsList = (ListView) findViewById(R.id.songsList);
        playOrPause = (ImageButton) findViewById(R.id.playOrPause);
        forawrd = (ImageButton) findViewById(R.id.forward);
        backward = (ImageButton) findViewById(R.id.backward);

        songArrayList = new ArrayList<>();
        songsAdapter = new ArrayAdapter<Song>(this, android.R.layout.simple_list_item_1, songArrayList);
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
                if (Integer.parseInt(playOrPause.getTag().toString()) == R.drawable.pause)
                    pauseSong();
                else
                    resumeSong();
            }
        });

        playOrPause.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                stopSong();
                return true;
            }
        });

        forawrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playNext();
            }
        });

        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPrevious();
            }
        });

        tManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        tManager.listen(new CustomPhoneStateListener(this),
                PhoneStateListener.LISTEN_CALL_STATE);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void pauseSong() {
        Intent intent = new Intent(MainActivity.this, MediaService.class);
        intent.setAction(MediaService.ACTION_PAUSE);
        startService(intent);
        playOrPause.setImageResource(R.drawable.play);
        playOrPause.setTag(R.drawable.play);
    }

    private void playSong(int position) {
        Intent intent = new Intent(MainActivity.this, MediaService.class);
        intent.setAction(MediaService.ACTION_PLAY);
        intent.putExtra(POSITION_SONG,position);
        startService(intent);
        playOrPause.setImageResource(R.drawable.pause);
        playOrPause.setTag(R.drawable.pause);
    }

    private void playPrevious() {
        Intent intent = new Intent(MainActivity.this, MediaService.class);
        intent.setAction(MediaService.ACTION_SKIP_PREV);
        startService(intent);
        playOrPause.setImageResource(R.drawable.pause);
        playOrPause.setTag(R.drawable.pause);
    }

    private void playNext() {
        Intent intent = new Intent(MainActivity.this, MediaService.class);
        intent.setAction(MediaService.ACTION_SKIP_NEXT);
        startService(intent);
        playOrPause.setImageResource(R.drawable.pause);
        playOrPause.setTag(R.drawable.pause);
    }


    private void setSongsLists() {
        songsAdapter.add(new Song("Falling away with you",R.raw.muse_falling_away_with_you));
        songsAdapter.add(new Song("Madness",R.raw.muse_madness));
        songsAdapter.add(new Song("Hysteria",R.raw.muse_hysteria));
    }

    private void stopSong() {
        Intent intent = new Intent(MainActivity.this, MediaService.class);
        intent.setAction(MediaService.ACTION_STOP);
        startService(intent);
        playOrPause.setImageResource(R.drawable.play);
        playOrPause.setTag(R.drawable.play);
    }

    private void resumeSong() {
        Intent intent = new Intent(MainActivity.this, MediaService.class);
        intent.setAction(MediaService.ACTION_RESUME);
        startService(intent);
        playOrPause.setImageResource(R.drawable.pause);
        playOrPause.setTag(R.drawable.pause);

    }

    //TODO: change list to strings only.




}


