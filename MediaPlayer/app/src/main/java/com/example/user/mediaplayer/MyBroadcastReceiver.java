package com.example.user.mediaplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by User on 19/11/2016.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {

    public boolean isPlaying;

    @Override
    public void onReceive(Context context, Intent intent) {

        String action=intent.getAction();

        switch (action)
        {
            case MediaService.ACTION_CHECK_IF_PLAYING:
                isPlaying=intent.getBooleanExtra("isPlaying",isPlaying);
                break;
        }

    }
}
