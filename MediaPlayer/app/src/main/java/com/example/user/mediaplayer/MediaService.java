package com.example.user.mediaplayer;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;

public class MediaService extends Service {

    public MediaPlayer mediaPlayer;
    public final static String ACTION_PLAY = "ACTION_PLAY";
    public final static String ACTION_PAUSE = "ACTION_PAUSE";
    public final static String ACTION_STOP = "ACTION_STOP";
    public final static String ACTION_SKIP_PREV = "ACTION_SKIP_PREV";
    public final static String ACTION_RESUME = "ACTION_RESUME";
    public final static String ACTION_SKIP_NEXT = "ACTION_SKIP_NEXT";
    public final static String ACTION_CHECK_IF_PLAYING = "ACTION_CHECK_IF_PLAYING";
    public final static int NOTIFICATION_ID = 1;
    public static boolean isAlive = false;
    ArrayList<Song> songsList;
    private int position;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initNotification();
        String action = intent.getAction();
        position=intent.getIntExtra(MainActivity.POSITION_SONG,0);

            switch (action) {

                case ACTION_PLAY:
                    if (position<0||position>2) {
                        throw new RuntimeException();}
                    else
                    {
                        playSong(position);
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
                    if (position<0||position>2) {
                        throw new RuntimeException();}
                    else
                    {
                        position++;
                        if(position>2)
                            position = 0;
                        playSong(position);
                    }
                    break;

                case ACTION_SKIP_PREV:
                    if (position<0||position>2) {
                        throw new RuntimeException();}
                    else
                    {
                        position--;
                        if(position<0)
                            position = 2;
                        Log.e("entered",position+"");
                        playSong(position);
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
                .setSmallIcon(R.drawable.icon)
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
        //mediaPlayer = new MediaPlayer();
        songsList = new ArrayList<>();
        setSongsLists();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer=null;
    }

    void playSong(int position)
    {
        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
        }
        mediaPlayer = MediaPlayer.create(this, songsList.get(position).getResId());
        mediaPlayer.start();
        isAlive =true;
    }

    void stopSong()
    {
        if(mediaPlayer.isPlaying())
            mediaPlayer.stop();
        isAlive =false;
    }

    private void setSongsLists() {
        songsList.add(new Song("Falling away with you",R.raw.muse_falling_away_with_you));
        songsList.add(new Song("Madness",R.raw.muse_madness));
        songsList.add(new Song("Hysteria",R.raw.muse_hysteria));
    }



}
