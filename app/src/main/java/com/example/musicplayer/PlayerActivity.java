package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PlayerActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer= MyMusicPlayer.getMediaPlayer();
    ArrayList<AudioModel> songList;
    AudioModel current_song;
    TextView titleTv,cur_timeTv, total_timeTv;
    SeekBar seekBar;
    ImageView big_music, playBtn, preBtn, nextBtn;
    int x=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        titleTv= findViewById(R.id.songLabel);
        cur_timeTv= findViewById(R.id.current_time);
        total_timeTv= findViewById(R.id.total_time);
        seekBar= findViewById(R.id.seek_bar);
        big_music= findViewById(R.id.big_music);
        playBtn= findViewById(R.id.pause);
        preBtn= findViewById(R.id.previous);
        nextBtn= findViewById(R.id.next);

        titleTv.setSelected(true);
        songList= (ArrayList<AudioModel>) getIntent().getSerializableExtra("LIST");

        setResourcesWithMusic();

        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer!=null){
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    cur_timeTv.setText(convertMMSS(mediaPlayer.getCurrentPosition()+""));

                    if (mediaPlayer.isPlaying()){
                        playBtn.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                        big_music.setRotation(x++);
                    } else {
                        playBtn.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
                        big_music.setRotation(0);
                    }
                }
                new Handler().postDelayed(this,100);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer!=null && fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        playBtn.setOnClickListener(v ->pausePlay() );
        preBtn.setOnClickListener(v ->prePlayMusic() );
        nextBtn.setOnClickListener(v ->nextPlayMusic() );

        PlayMusic();

    }

    public void setResourcesWithMusic(){
        current_song= songList.get(MyMusicPlayer.currentIndex);

        titleTv.setText(current_song.getData());
        total_timeTv.setText(convertMMSS(current_song.getDuration()));
    }

    public void PlayMusic(){
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(current_song.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void prePlayMusic(){
        if (MyMusicPlayer.currentIndex==0)
            return;
        MyMusicPlayer.currentIndex -=1;
        setResourcesWithMusic();
        PlayMusic();

    }
    public void nextPlayMusic(){
        if (MyMusicPlayer.currentIndex==songList.size()-1)
            return;
        MyMusicPlayer.currentIndex +=1;
        setResourcesWithMusic();
        PlayMusic();

    }
    public void pausePlay(){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }
    }

    public static String convertMMSS(String duration){
        long millis= Long.parseLong(duration);
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }
}