package com.example.musicplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class musicListAdapter extends RecyclerView.Adapter<musicListAdapter.ViewHolder>{

  ArrayList<AudioModel> songList;
  Context context;

    public musicListAdapter(ArrayList<AudioModel> songList, Context context) {
        this.songList = songList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_items,parent,false);
        return new musicListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AudioModel songData= songList.get(position);
        holder.textViewTitle.setText(songData.getData());

        if (MyMusicPlayer.currentIndex==position){
            holder.textViewTitle.setTextColor(Color.parseColor("#E91E63"));
        } else {
            holder.textViewTitle.setTextColor(Color.parseColor("#FFFFFF"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyMusicPlayer.getMediaPlayer().reset();
                MyMusicPlayer.currentIndex= position;

                Intent intent = new Intent(context,PlayerActivity.class);
                intent.putExtra("LIST", songList);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle;
        ImageView music;
        public ViewHolder( View itemView) {
            super(itemView);

            textViewTitle= itemView.findViewById(R.id.textSong);
            music= itemView.findViewById(R.id.musicImg);
        }
    }
}
