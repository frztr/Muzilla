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


public class RecommendationFragment extends Fragment {

    private API api;
    private ArrayListExtended<Track> tracks;
    private TrackAdapter adapter;

    public RecommendationFragment(API api) {
        this.api = api;
        tracks = new ArrayListExtended<Track>();
        api.getMyRecommendations(tracks,0);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recommendation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rv = (RecyclerView) getView().findViewById(R.id.tracks_list_recommendations);
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(rv.canScrollVertically(1)==false)
                {
                    api.getMyRecommendations(tracks,rv.getAdapter().getItemCount());
                }
            }
        });
    }
}