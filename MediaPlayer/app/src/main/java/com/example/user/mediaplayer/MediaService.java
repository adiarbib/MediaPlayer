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

    private MediaPlayer mediaPlayer;

    public final String ACTION_PLAY = "ACTION_PLAY";
    public final String ACTION_PAUSE="ACTION_PAUSE";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        int resID= intent.getIntExtra("songID",0);
        if(resID==0)
        {
            throw new RuntimeException();
        }
        else {
            switch (action) {
                case ACTION_PLAY:
                    mediaPlayer = MediaPlayer.create(this, resID);
                    mediaPlayer.start();
                break;

                case
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
