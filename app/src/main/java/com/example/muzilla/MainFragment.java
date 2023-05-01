package com.example.muzilla;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    PlaylistAdapter adapter;
    ArrayListExtended<Playlist> playlists = new ArrayListExtended<Playlist>();
    TrackAdapter adapter2;
    ArrayListExtended<Track> tracks = new ArrayListExtended<Track>();
    Integer profileId;
    String old_text="";
    EditText edt;

    ViewUpdater viewUpdater = new ViewUpdater()
    {
        @Override
        public void onAudiosUpdate() {
            if(getView()!=null)
            {
                EditText edt = (EditText) getView().findViewById(R.id.myaudio_input);
                if (edt.getText().length() == 0) {
                    UpdateAudio(0);
                }
            }
        }
    };

    public MainFragment()
    {
        SharedPreferences sp =  App.getApplicationData();
        this.profileId = Integer.parseInt(sp.getString("profileId",""));
        API.getInstance().getMyPlaylists(playlists,this.profileId,0);
        API.getInstance().addPlaylistListener(tracks);
        API.getInstance().getMyAudio(tracks,0);
        API.getInstance().addViewUpdater(viewUpdater);
    }

    @Override
    public void onStart() {
        super.onStart();
        edt = (EditText) getView().findViewById(R.id.myaudio_input);
        setSearchControlsBehavior();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setPlaylistViewerBehavior();
        setTracklistViewerBehavior();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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

    private void UpdateAudio(int offset)
    {
        if(offset == 0) {
            tracks.arrayList.clear();
        }
        API.getInstance().getMyAudio(tracks,offset);
    }

    private void UpdateSearchedAudio(String s,int offset)
    {
        if(offset == 0) {
            tracks.arrayList.clear();
        }
        API.getInstance().searchMyAudio(tracks,s,offset,profileId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    private void setPlaylistViewerBehavior()
    {
        RecyclerView rv = (RecyclerView) getView().findViewById(R.id.playlists_list);
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(rv.canScrollHorizontally(1)==false)
                {
                    if(edt.getText().length()==0) {
                        API.getInstance().getMyPlaylists(playlists, profileId, rv.getAdapter().getItemCount());
                    }
                }
            }
        });
    }

    private void setTracklistViewerBehavior()
    {
        RecyclerView rv2 = (RecyclerView) getView().findViewById(R.id.tracks_list_main);
        rv2.setAdapter(adapter2);
        rv2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (rv2.canScrollVertically(1) == false) {

                    if(edt.getText().toString().length()==0) {
                        UpdateAudio(rv2.getAdapter().getItemCount());
                    }
                }
            }
        });
    }

    private void setSearchControlsBehavior()
    {
        CardView cv = (CardView) getView().findViewById(R.id.myaudio_input_layout);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.requestFocus();
            }
        });

        edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!edt.hasFocus()&& edt.getText().toString().length()==0)
                {
                    UpdateAudio(0);
                }
            }
        });

        edt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER && old_text != edt.getText().toString())
                {
                    if(edt.getText().length() != 0)
                    {
                        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(edt.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        RecyclerView rv = (RecyclerView) getView().findViewById(R.id.tracks_list_main);
                        UpdateSearchedAudio(edt.getText().toString(),0);
                        old_text = edt.getText().toString();
                        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                super.onScrollStateChanged(recyclerView, newState);
                                if (rv.canScrollVertically(1) == false) {
                                    if(edt.getText().length()!=0) {
                                        UpdateSearchedAudio(old_text,rv.getAdapter().getItemCount());
                                    }
                                }
                            }
                        });
                        return true;
                    }
                    else
                        {
                        UpdateAudio(0);
                        old_text = edt.getText().toString();
                        return false;
                    }

                }
                return false;
            }
        });
    }
}