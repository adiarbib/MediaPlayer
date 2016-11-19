package com.example.user.mediaplayer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

/**
 * Created by User on 17/11/2016.
 */
public class MediaService extends Service {

    public MediaPlayer mediaPlayer;
    public final static String ACTION_PLAY = "ACTION_PLAY";
    public final static String ACTION_PAUSE = "ACTION_PAUSE";
    public final static String ACTION_STOP="ACTION_STOP";
    public final static String ACTION_SKIP_PREV="ACTION_SKIP_PREV";
    public final static String ACTION_RESUME="ACTION_RESUME";
    public final static String ACTION_SKIP_NEXT="ACTION_SKIP_NEXT";
    public final static int NOTIFICATION_ID=1;
    public static boolean isPlaying=false;

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
                        playSong(resID);
                    }
                    break;

                case ACTION_PAUSE:
                    if(mediaPlayer.isPlaying())
                        mediaPlayer.pause();
                    break;

                case ACTION_STOP:
                    stopSong();
                    break;

                case ACTION_RESUME:
                    mediaPlayer.start();
                    break;

                case ACTION_SKIP_NEXT:
                    if (resID == 0) {
                        throw new RuntimeException();}
                    else
                    {
                        playSong(resID);
                    }
                    break;

                case ACTION_SKIP_PREV:
                    if (resID == 0) {
                        throw new RuntimeException();}
                    else
                    {
                        playSong(resID);
                    }
                    break;

        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void initNotification() {

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Intent previousIntent = new Intent(this, MediaService.class);
        previousIntent.setAction(ACTION_SKIP_PREV);
        PendingIntent pendingPreviousIntent = PendingIntent.getService(this, 0, previousIntent, 0);

        Intent playIntent = new Intent(this, MediaService.class);
        playIntent.setAction(ACTION_RESUME);
        PendingIntent pendingPlayIntent = PendingIntent.getService(this, 0, playIntent, 0);

        Intent nextIntent = new Intent(this, MediaService.class);
        nextIntent.setAction(ACTION_SKIP_NEXT);
        PendingIntent pendingNextIntent = PendingIntent.getService(this, 0, nextIntent, 0);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("Music Player")
                .setContentText("Music")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                .setContentIntent(pendingNotificationIntent)
                .setOngoing(true)
                .addAction(R.drawable.back, "Previous", pendingPreviousIntent)
                .addAction(R.drawable.play, "Play", pendingPlayIntent)
                .addAction(R.drawable.forward, "Next", pendingNextIntent)
                .build();

        startForeground(NOTIFICATION_ID, notification);
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
        initNotification();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer=null;
    }

    void playSong(int resID)
    {
        if(mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mediaPlayer = MediaPlayer.create(this, resID);
        mediaPlayer.start();
        isPlaying=true;
    }

    void stopSong()
    {
        if(mediaPlayer.isPlaying())
            mediaPlayer.stop();
        isPlaying=false;
    }



}
