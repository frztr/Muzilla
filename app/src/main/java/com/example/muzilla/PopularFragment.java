package com.example.muzilla;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class PopularFragment extends Fragment {


    private ArrayListExtended<Track> tracks;
    private TrackAdapter adapter;
    public PopularFragment() {
        tracks = new ArrayListExtended<Track>();
        API.getInstance().getPopularAudio(tracks,0);
        API.getInstance().addPlaylistListener(tracks);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new TrackAdapter(this.getContext(),tracks.arrayList);
        tracks.addListener(()->
        {
            adapter.notifyDataSetChanged();
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_popular, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LoadPopular();
    }

    private void LoadPopular()
    {
        RecyclerView rv = (RecyclerView) getView().findViewById(R.id.tracks_list_popular);
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(rv.canScrollVertically(1)==false)
                {
                    API.getInstance().getPopularAudio(tracks,rv.getAdapter().getItemCount());
                }
            }
        });
    }
}