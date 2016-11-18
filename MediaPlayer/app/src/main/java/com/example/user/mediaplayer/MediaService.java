package com.example.user.mediaplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by User on 17/11/2016.
 */
public class MediaService extends Service {

    public MediaPlayer mediaPlayer;
    public final String ACTION_PLAY = "ACTION_PLAY";
    public final String ACTION_PAUSE = "ACTION_PAUSE";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        int resID = intent.getIntExtra("songID", 0);
            switch (action) {
                case ACTION_PLAY:
                    if (resID == 0) {
                        throw new RuntimeException();}
                    else
                    {
                        PlaySong(resID);
                    }
                    break;

                case ACTION_PAUSE:
                    if(mediaPlayer.isPlaying())
                        mediaPlayer.pause();
                    break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer=new MediaPlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer=null;
    }

    void PlaySong (int resID)
    {
        if(mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mediaPlayer = MediaPlayer.create(this, resID);
        mediaPlayer.start();
    }
}
