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

import com.squareup.picasso.Picasso;

import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter {
    private final LayoutInflater inflater;
    private final List<Track> tracks;

    TrackAdapter(Context context, List<Track> tracks) {
        this.tracks = tracks;
        this.inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_track, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Track track = tracks.get(position);
        TrackAdapter.ViewHolder viewHolder = (TrackAdapter.ViewHolder) holder;
        if(track.getImgUrl()!="")
        {
            Picasso.get().load(track.getImgUrl()).into(viewHolder.flagView);
        }
        else
            {
            Picasso.get().load(R.drawable.music_icon).into(viewHolder.flagView);
        }
        viewHolder.nameView.setText(track.getName());
        viewHolder.musicianView.setText(track.getMusician());
        Integer minutes = track.getDuration()/60;
        Integer secs = track.getDuration()%60;
        viewHolder.durationView.setText(String.format("%d:%02d",minutes,secs));
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView flagView;
        final TextView nameView, musicianView,durationView;

        ViewHolder(View view){
            super(view);
            flagView = view.findViewById(R.id.track_logo);
            nameView = view.findViewById(R.id.track_name);
            musicianView = view.findViewById(R.id.track_musician);
            durationView = view.findViewById(R.id.duration_track);
        }
    }
}
