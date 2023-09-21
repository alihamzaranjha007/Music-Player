package com.example.musicplayer;

import android.media.MediaPlayer;

public class MyMusicPlayer {
   private static MediaPlayer instance;

    private MyMusicPlayer () {
        instance = new MediaPlayer();
    }

    public static MediaPlayer getMediaPlayer () {
        if (instance == null) {
            new MyMusicPlayer();
        }
        return instance;
    }
    public static int currentIndex=-1;
}