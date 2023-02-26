package com.example.muzilla;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter {
    private final LayoutInflater inflater;
    private final List<Playlist> playlists;

    PlaylistAdapter(Context context, List<Playlist> playlists) {
        this.playlists = playlists;
        this.inflater = LayoutInflater.from(context);
    }
        @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Playlist playlist = playlists.get(position);
        PlaylistAdapter.ViewHolder viewHolder = (PlaylistAdapter.ViewHolder) holder;
        //viewHolder.flagView.setImageURI(Uri.parse(playlist.getImgUrl()));
        viewHolder.flagView.setImageResource(R.drawable.track1);
        viewHolder.nameView.setText(playlist.getName());
        viewHolder.musicianView.setText(playlist.getMusician());
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView flagView;
        final TextView nameView, musicianView;

        ViewHolder(View view){
            super(view);
            flagView = view.findViewById(R.id.playlist_logo);
            nameView = view.findViewById(R.id.playlist_name);
            musicianView = view.findViewById(R.id.playlist_musician);
        }
    }
}
