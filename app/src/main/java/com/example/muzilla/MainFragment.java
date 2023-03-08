package com.example.muzilla;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    PlaylistAdapter adapter;
    ArrayListExtended<Playlist> playlists;
    TrackAdapter adapter2;
    ArrayListExtended<Track> tracks;
    private API api;
    private Integer profileId;
    public MainFragment(API api, Integer profileId) {
        this.api = api;
        this.profileId = profileId;
        tracks = new ArrayListExtended<Track>();
        playlists = new ArrayListExtended<Playlist>();




        api.getMyPlaylists(playlists,this.profileId,0);
        api.getMyAudio(tracks,0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new PlaylistAdapter(this.getContext(),playlists.arrayList);
        adapter2 = new TrackAdapter(this.getContext(),tracks.arrayList);

        playlists.addListener(()->{
            adapter.notifyDataSetChanged();
        });
        tracks.addListener(()->
        {
            adapter2.notifyDataSetChanged();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        RecyclerView rv = (RecyclerView) getView().findViewById(R.id.playlists_list);
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(rv.canScrollHorizontally(1)==false)
                {
                    api.getMyPlaylists(playlists,profileId,rv.getAdapter().getItemCount());
                }
            }
        });

        RecyclerView rv2 = (RecyclerView) getView().findViewById(R.id.tracks_list_main);
        rv2.setAdapter(adapter2);
        rv2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(rv2.canScrollVertically(1)==false)
                {
                    api.getMyAudio(tracks,rv2.getAdapter().getItemCount());
                }
            }
        });

        tracks.addListener(()->
        {

          //  ((MainActivity)getActivity()).AudioPlayer.setPlaylist(tracks.arrayList);
          //  ((MainActivity)getActivity()).AudioPlayer.Play(0);
        });


    }
}