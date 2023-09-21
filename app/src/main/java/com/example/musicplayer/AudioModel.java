package com.example.musicplayer;

import java.io.Serializable;

public class AudioModel implements Serializable {
    String path;
    String data;
    String duration;

    public AudioModel(String path, String data, String duration) {
        this.path = path;
        this.data = data;
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
