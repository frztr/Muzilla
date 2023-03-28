package com.example.muzilla;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter  {
    private final LayoutInflater inflater;
    private final List<Playlist> playlists;
    Context context;

    PlaylistAdapter(Context context, List<Playlist> playlists) {
        this.playlists = playlists;
        this.inflater = LayoutInflater.from(context);
        this.context= context;
    }
        @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Playlist playlist = playlists.get(position);
        PlaylistAdapter.ViewHolder viewHolder = (PlaylistAdapter.ViewHolder) holder;
        if(playlist.getImgUrl()!="")
        {
            Picasso.get().load(playlist.getImgUrl()).into(viewHolder.flagView);
        }
        else
        {
            Picasso.get().load(R.drawable.music_icon).into(viewHolder.flagView);
        }
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
        ConstraintLayout wall_item;


        ViewHolder(View view,PlaylistAdapter playlistAdapter){
            super(view);
            flagView = view.findViewById(R.id.playlist_logo);
            nameView = view.findViewById(R.id.playlist_name);
            musicianView = view.findViewById(R.id.playlist_musician);
            wall_item = view.findViewById(R.id.playlist_clicked_back);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animator animator = ViewAnimationUtils.createCircularReveal(wall_item, wall_item.getWidth()/2, wall_item.getHeight() / 2, 0f, (float) Math.hypot(wall_item.getWidth()/2, wall_item.getHeight() / 2));
                    animator.setDuration(300);
                    animator.setInterpolator(new AccelerateInterpolator());
                    animator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {

                            AlphaAnimation animation = new AlphaAnimation(1f,0f);
                            animation.setDuration(600);
                            animation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    wall_item.setVisibility(View.INVISIBLE);
                                    Intent intent = new Intent(playlistAdapter.context,PlaylistActivity.class);
                                    Playlist playlist = playlistAdapter.playlists.get(getAdapterPosition());
                                    intent.putExtra("owner_id",playlist.getOwner_id());
                                    intent.putExtra("playlist_id",playlist.getId());
                                    intent.putExtra("playlist_name",playlist.getName());
                                    intent.putExtra("playlist_musician",playlist.getMusician());
                                    intent.putExtra("playlist_img",playlist.getImgUrl());
                                    playlistAdapter.context.startActivity(intent);

                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            wall_item.startAnimation(animation);
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                    wall_item.setVisibility(View.VISIBLE);
                    animator.start();
                }
            });
        }
    }


}
