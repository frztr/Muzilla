package com.example.muzilla;

import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class AudioPlayer {
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private ArrayList<Track> playlist = new ArrayList<Track>();
    private API API;
    private int current_pos = -1;
    private boolean Playing = false;
    private ArrayList<Runnable> PlayingListeners = new ArrayList<Runnable>();
    private ArrayList<Runnable> onTrackLoadedListeners = new ArrayList<Runnable>();
    private ArrayList<Runnable> onCurrentTrackStateChangedListeners =  new ArrayList<Runnable>();
    private Track current_track;

    private void PlayingUpdate()
    {
        for (Runnable r: PlayingListeners)
        {
         r.run();
        }
    }

    private void OnTrackChanged()
    {
        for (Runnable r:onTrackLoadedListeners) {
            r.run();
        }
    }

    public void addPlayingListener(Runnable r)
    {
        PlayingListeners.add(r);
        PlayingUpdate();
    }

    public void removePlayingListener(Runnable r)
    {
        PlayingListeners.remove(r);
        PlayingUpdate();
    }

    public void addOnTrackLoadedListener(Runnable r)
    {
        onTrackLoadedListeners.add(r);
    }

    public void removeOnTrackLoadedListener(Runnable r)
    {
        onTrackLoadedListeners.remove(r);
    }

    public void addOnCurrentTrackExistsListener(Runnable r)
    {
        onCurrentTrackStateChangedListeners.add(r);
        CurrentTrackStateChanged();
    }

    public void removeOnCurrentTrackExistsListener(Runnable r)
    {
        onCurrentTrackStateChangedListeners.remove(r);
        CurrentTrackStateChanged();
    }

    public void CurrentTrackStateChanged()
    {
        for (Runnable r:onCurrentTrackStateChangedListeners) {
            r.run();
        }
    }

    public AudioPlayer(API api)
    {
        this.API = api;
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Playing = false;
                Next();
                CurrentTrackStateChanged();
                PlayingUpdate();
            }
        });
    }

    public boolean isPlaying()
    {
        return Playing;
    }

    public void Pause()
    {
        if(current_pos != -1) {
            Playing = false;
            PlayingUpdate();
            mediaPlayer.pause();
        }
    }

    public void setDuration()
    {

    }

    public int getDuration()
    {
        return 0;
    }

    public void setPlaylist(ArrayList<Track> playlist) {
        this.playlist = playlist;
    }

    public void Play(int pos)
    {
        mediaPlayer.stop();
        mediaPlayer.reset();

        try {
            current_pos = pos;
            Track track = playlist.get(current_pos);
            current_track = track;
            OnTrackChanged();
            CurrentTrackStateChanged();
            if(current_track.getMusicUrl() == null) {
                current_track.addListener(() ->
                {
                    if(current_track.getMusicUrl()!=null)
                    {
                        try {
                            mediaPlayer.setDataSource(current_track.getMusicUrl());
                            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mediaPlayer) {
                                    Play();
                                }
                            });
                            mediaPlayer.prepareAsync();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                API.getTrackById(track);
            }
            else
                {
                    try {
                        mediaPlayer.setDataSource(track.getMusicUrl());
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                Play();
                            }
                        });
                        mediaPlayer.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Play()
    {
        Playing = true;
        PlayingUpdate();
        mediaPlayer.start();
    }

    public void Stop()
    {
        Playing = false;
        current_track = null;
        CurrentTrackStateChanged();
        mediaPlayer.stop();

    }

    public void Prev()
    {
        Play(current_pos-1);
    }
    public void Next()
    {
        Play(current_pos+1);
    }

    public ArrayList<Track> getPlaylist()
    {
        return  playlist;
    }

    public Track getCurrentTrack()
    {
        if (current_track!=null)
        {
            return  current_track;
        }
        else
        {
            return null;
        }
    }

}
