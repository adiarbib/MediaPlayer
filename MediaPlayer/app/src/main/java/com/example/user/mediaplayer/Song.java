package com.example.user.mediaplayer;

import android.content.res.Resources;

import java.io.Serializable;

public class Song implements Serializable
{
    private final String ANDROID_RESOURCE_PATH="android.resource://com.example.user.mediaplayer/raw/";

    private String name;
    private int resId;
    private String uriString;


    public Song(String name, int resID) {
        this.name = name;
        this.resId=resID;
        this.uriString=ANDROID_RESOURCE_PATH+ Resources.getSystem().getResourceEntryName(resID);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
