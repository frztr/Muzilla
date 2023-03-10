package com.example.muzilla;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.ArrayList;


public class SearchFragment extends Fragment {

    private API api;

    public SearchFragment(API api) {
        this.api = api;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        EditText edt = (EditText) getView().findViewById(R.id.search_input);
        edt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent)
            {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER)
                {
                    EditText editText = (EditText) view;
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText.getWindowToken(),0);
                    RecyclerView rv = (RecyclerView) getView().findViewById(R.id.tracks_list_search);
                    ArrayListExtended<Track> tracks = new ArrayListExtended<Track>();
                    TrackAdapter adapter = new TrackAdapter(getView().getContext(),tracks.arrayList);
                    tracks.addListener(()->
                    {
                        adapter.notifyDataSetChanged();
                    });
                    rv.setAdapter(adapter);
                    api.searchAudio(tracks,editText.getText().toString(),0);
                    rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            if(rv.canScrollVertically(1)==false)
                            {
                                api.searchAudio(tracks,editText.getText().toString(),rv.getAdapter().getItemCount());
                            }
                        }
                    });
                    return true;
                }
                return false;
            }
        });
    }
}