package com.example.user.mediaplayer;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class MediaService extends Service {


    public final static String ACTION_PLAY = "ACTION_PLAY";
    public final static String ACTION_PAUSE = "ACTION_PAUSE";
    public final static String ACTION_STOP = "ACTION_STOP";
    public final static String ACTION_SKIP_PREV = "ACTION_SKIP_PREV";
    public final static String ACTION_RESUME = "ACTION_RESUME";
    public final static String ACTION_SKIP_NEXT = "ACTION_SKIP_NEXT";

    public final static String ACTION_PLAY_FILE="ACTION_PLAY_FILE";
    public final static String ACTION_PAUSE_FILE="ACTION_PAUSE_FILE";

    public final static int NOTIFICATION_ID = 1;

    public MediaPlayer mediaPlayer;
    public static boolean isAlive = false;
    ArrayList<Song> songsList;
    private int position;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if(action==ACTION_PLAY_FILE||action==ACTION_PAUSE_FILE) {
            Uri uri = intent.getParcelableExtra("uri");

            switch (action){

                case ACTION_PLAY_FILE:
                    playFileSong(uri);
                    break;

                case ACTION_PAUSE_FILE:
                    if(mediaPlayer.isPlaying())
                        mediaPlayer.pause();
                    break;
            }

        }

        else{
            position=intent.getIntExtra(MainActivity.POSITION_SONG,position);

            switch (action) {

                case ACTION_PLAY:
                    if (position<0||position>2) {
                        throw new RuntimeException();}
                    else
                    {
                        initNotification();
                        playSong(position);
                    }
                    break;

                case ACTION_PAUSE:
                    initNotification();
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
                        initNotification();
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
                        playSong(position);
                        initNotification();
                    }
                    break;
            }

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
        if(mediaPlayer.isPlaying())
            playIntent.setAction(ACTION_PAUSE);
        else
            playIntent.setAction(ACTION_RESUME);
        PendingIntent pendingPlayIntent = PendingIntent.getService(this, 0, playIntent, 0);

        Intent nextIntent = new Intent(this, MediaService.class);
        nextIntent.setAction(ACTION_SKIP_NEXT);
        PendingIntent pendingNextIntent = PendingIntent.getService(this, 0, nextIntent, 0);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(songsList.get(position).getName())
                .setContentText("Music")
                .setSmallIcon(R.drawable.icon)
                .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                .setContentIntent(pendingNotificationIntent)
                .setOngoing(true)
                .addAction(R.drawable.back, "", pendingPreviousIntent)
                .addAction(R.drawable.ic_play_pause_black_24dp, "", pendingPlayIntent)
                .addAction(R.drawable.forward, "", pendingNextIntent)
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
        songsList = new ArrayList<>();
        mediaPlayer=new MediaPlayer();
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

    void playFileSong(Uri uri)
    {
        if(mediaPlayer == null)
            mediaPlayer = MediaPlayer.create(this, uri);
        else{
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(this, uri);
        }
        mediaPlayer.start();
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
