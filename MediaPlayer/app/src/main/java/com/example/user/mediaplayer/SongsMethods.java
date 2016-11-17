package com.example.user.mediaplayer;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.provider.MediaStore;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 06/11/2016.
 */
public class SongsMethods
{
    private Context context;
    public SongsMethods(Context context) {
        this.context=context;
    }

    public ArrayList<String> getSongNames(/*boolean usesRaw*/) throws IllegalAccessException {
        /*if(usesRaw)
        {

        }*/
        return getRawNameFiles();

    }

    public ArrayList<String> getRawNameFiles()  {
        ArrayList<String> rawFilesNames = new ArrayList<>();
        Field[] fields=R.raw.class.getFields();
        for(int count=0; count < fields.length; count++){
            if(fields[count].getName().endsWith("mp3"))
                rawFilesNames.add(fields[count].getName().toString());
        }
        return rawFilesNames;
    }

    public void PlaySongByResourceID(MediaPlayer mediaPlayer,int id)
    {
        if(!mediaPlayer.isPlaying())
        {
            mediaPlayer=MediaPlayer.create(context,R.raw.+name);
        }
    }




}
