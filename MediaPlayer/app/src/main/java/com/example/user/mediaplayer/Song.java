package com.example.user.mediaplayer;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import java.io.Serializable;

public class Song implements Serializable
{
    private String name;
    private int resId;


    public Song(String name, int resID) {
        this.name = name;
        this.resId=resID;
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
