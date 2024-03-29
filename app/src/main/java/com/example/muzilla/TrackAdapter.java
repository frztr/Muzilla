package com.example.muzilla;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter {
    private final LayoutInflater inflater;
    private final ArrayList<Track> tracks;
    public Context context;

    TrackAdapter(Context context, ArrayList<Track> tracks) {
        this.tracks = tracks;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_track, parent, false);
        ViewHolder viewHolder = new ViewHolder(view,this);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

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
        viewHolder.track_content_id = track.getContentId();

        if(AudioPlayer.getInstance().getCurrentTrack()!=null){

            if (track.getContentId().equals(AudioPlayer.getInstance().getCurrentTrack().getContentId())) {
                viewHolder.track_item_wall.setVisibility(View.VISIBLE);
            } else {
                viewHolder.track_item_wall.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView flagView;
        final TextView nameView, musicianView,durationView;
        final CardView track_item;
        final ConstraintLayout track_item_wall;
        String track_content_id;
        TrackAdapter trackAdapter;

        public void Update(View view)
        {
            if(AudioPlayer.getInstance().getCurrentTrack()!=null)
            {
                if(view.isAttachedToWindow()) {
                    if (AudioPlayer.getInstance().getCurrentTrack().getContentId() == track_content_id) {
                        if(((IPlayerActivity) trackAdapter.context).getLastTrack() != null) {
                            if (((IPlayerActivity) trackAdapter.context).getLastTrack().getContentId() != track_content_id) {
                                Animator animator = ViewAnimationUtils.createCircularReveal(track_item_wall, 0, track_item_wall.getHeight() / 2, 0f, (float) Math.hypot(track_item_wall.getWidth(), track_item_wall.getHeight() / 2));
                                animator.setDuration(300);
                                animator.setInterpolator(new AccelerateInterpolator());
                                track_item_wall.setVisibility(View.VISIBLE);
                                animator.start();
                            }
                        }
                        else {
                            Animator animator = ViewAnimationUtils.createCircularReveal(track_item_wall, 0, track_item_wall.getHeight() / 2, 0f, (float) Math.hypot(track_item_wall.getWidth(), track_item_wall.getHeight() / 2));
                            animator.setDuration(300);
                            animator.setInterpolator(new AccelerateInterpolator());
                            track_item_wall.setVisibility(View.VISIBLE);
                            animator.start();
                        }
                    }
                    else
                    {
                        if (track_item_wall.getVisibility() == View.VISIBLE) {

                            Animator animator2 = ViewAnimationUtils.createCircularReveal(track_item_wall, 0, track_item_wall.getHeight() / 2, (float) Math.hypot(track_item_wall.getWidth(), track_item_wall.getHeight() / 2), 0f);
                            animator2.setDuration(300);
                            animator2.setInterpolator(new AccelerateInterpolator());
                            animator2.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    track_item_wall.setVisibility(View.INVISIBLE);
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            });
                            animator2.start();
                        }
                    }
                }
                else
                {
                    if (AudioPlayer.getInstance().getCurrentTrack().getContentId() == track_content_id)
                    {
                        track_item_wall.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        track_item_wall.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }


        ViewHolder(View view,TrackAdapter adapter){
            super(view);
            this.trackAdapter = adapter;
            flagView = view.findViewById(R.id.track_logo);
            nameView = view.findViewById(R.id.track_name);
            musicianView = view.findViewById(R.id.track_musician);
            durationView = view.findViewById(R.id.duration_track);
            track_item = view.findViewById(R.id.track_item);
            track_item_wall = view.findViewById(R.id.track_item_wall);

            ViewUpdater viewUpdater = new ViewUpdater()
            {
                @Override
                public void onTrackLoaded() {
                    Update(view);
                }

                @Override
                public void onCurrentTrackStateChanged() {
                    if(AudioPlayer.getInstance().getCurrentTrack()==null)
                    {
                        if (track_item_wall.getVisibility() == View.VISIBLE) {

                            if (view.isAttachedToWindow()) {
                                Animator animator2 = ViewAnimationUtils.createCircularReveal(track_item_wall, 0, track_item_wall.getHeight() / 2, (float) Math.hypot(track_item_wall.getWidth(), track_item_wall.getHeight() / 2), 0f);
                                animator2.setDuration(300);
                                animator2.setInterpolator(new AccelerateInterpolator());
                                animator2.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animator) {
                                        track_item_wall.setVisibility(View.INVISIBLE);
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animator) {

                                    }
                                });
                                animator2.start();
                            }
                            else
                            {
                                track_item_wall.setVisibility(View.INVISIBLE);
                            }

                        } else
                        {

                        }
                    }
                }
            };
            AudioPlayer.getInstance().addUpdater(viewUpdater);

            track_item.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {

                    if(AudioPlayer.getInstance().getCurrentTrack()!=null) {
                        if (AudioPlayer.getInstance().getCurrentTrack().getContentId().equals(trackAdapter.tracks.get(getAdapterPosition()).getContentId())) {
                            if (AudioPlayer.getInstance().isPlaying()) {
                                AudioPlayer.getInstance().Pause();
                            } else {
                                AudioPlayer.getInstance().Play();
                            }
                        } else {
                            if (!AudioPlayer.getInstance().getOriginalPlaylist().equals(trackAdapter.tracks))
                            {
                                AudioPlayer.getInstance().setPlaylist(trackAdapter.tracks);
                                AudioPlayer.getInstance().setShuffle(false);
                                AudioPlayer.getInstance().Play(getAdapterPosition());
                            } else {
                                AudioPlayer.getInstance().Play(getAdapterPosition());
                            }
                        }
                    }
                    else
                    {
                        AudioPlayer.getInstance().setPlaylist(trackAdapter.tracks);
                        AudioPlayer.getInstance().setShuffle(false);
                        AudioPlayer.getInstance().Play(getAdapterPosition());
                    }
                }
            });
        }
    }
}
