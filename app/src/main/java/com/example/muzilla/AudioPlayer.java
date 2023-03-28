package com.example.muzilla;
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class AudioPlayer {
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private ArrayList<Track> playlist = new ArrayList<Track>();
    private ArrayList<Track> shuffled_list = new ArrayList<Track>();
    private int current_pos = -1;
    private boolean Playing = false;

    private ArrayList<ViewUpdater> updaters = new ArrayList<ViewUpdater>();
    private Track current_track;
    private boolean Shuffle_On = false;
    private boolean isPrepared;

    private void PlayingUpdate()
    {
        for (ViewUpdater updater: updaters)
        {
            updater.onPlayingStateChanged();
        }

        if(Playing)
        {
           TimerOn();
        }
        else
            {
                TimerOff();
            }
    }

    private void LoopStateChange()
    {

        for (ViewUpdater updater: updaters)
        {
            updater.onLoopStateChange();
        }
    }


    private void OnTrackChanged()
    {

        for (ViewUpdater updater: updaters)
        {
            updater.onTrackLoaded();
        }
    }

    public void DurationUpdate()
    {

        for (ViewUpdater updater: updaters)
        {
            updater.onTrackCurrentDurationChanged();
        }
    }

    private void CurrentTrackStateChanged()
    {

        for (ViewUpdater updater: updaters)
        {
            updater.onCurrentTrackStateChanged();
        }
    }

    private  void ShuffleStateChanged()
    {

        for (ViewUpdater updater: updaters)
        {
             updater.onShuffleStateChanged();
        }
    }

    public void addUpdater(ViewUpdater viewUpdater)
    {
        updaters.add(viewUpdater);
    }

    public void removeUpdater(ViewUpdater viewUpdater)
    {
        updaters.remove(viewUpdater);
    }

    Timer timer = new Timer();

    private static AudioPlayer instance;

    public static AudioPlayer getInstance()
    {
        if(instance==null)
        {
            instance = new AudioPlayer();
        }
        return instance;
    }

    public static void setInstance(AudioPlayer audioPlayer)
    {
        instance = audioPlayer;
    }

    public AudioPlayer()
    {
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(!getLoopState()) {
                    Playing = false;
                    PlayingUpdate();
                    if(isPrepared) {
                        Next();
                    }
                    CurrentTrackStateChanged();
                }
            }
        });
        DurationUpdate();
    }

    private void TimerOn()
    {
        timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                if(mediaPlayer.isPlaying())
                {
                    DurationUpdate();
                }
            }
        },0,1000);
    }

    private void TimerOff()
    {
        timer.cancel();
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

    public void setPlaylist(ArrayList<Track> playlist) {
        this.playlist = playlist;
    }

    public void Play(int pos)
    {
        boolean loop = mediaPlayer.isLooping();
        mediaPlayer.stop();
        isPrepared = false;
        mediaPlayer.reset();
        mediaPlayer.setLooping(loop);
        try {
            current_pos = pos;
            Track track = getPlaylist().get(current_pos);
            current_track = track;
            OnTrackChanged();
            CurrentTrackStateChanged();
            if(current_track.getMusicUrl() == null) {
                current_track.addListener(() ->
                {
                    if(current_track.getMusicUrl()!=null)
                    {

                        ForcePlay();
                    }
                });
                API.getInstance().getTrackById(track);
            }
            else
            {
                ForcePlay();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ForcePlay()
    {
        try {
            mediaPlayer.setDataSource(current_track.getMusicUrl());
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer)
                {
                    isPrepared = true;
                    Play();
                }
            });
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Play()
    {
        Playing = true;
        PlayingUpdate();
        if(isPlaying()) {
            mediaPlayer.start();
        }
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
        if(current_pos-1 >= 0) {
            Play(current_pos - 1);
        }
        else
        {
                Play(current_pos);
        }
    }
    public void Next()
    {
        if(current_pos+1< getPlaylist().size())
        {
            Play(current_pos + 1);
        }
        else
        {
            Stop();
        }
    }

    public ArrayList<Track> getOriginalPlaylist()
    {
        return playlist;
    }

    public ArrayList<Track> getPlaylist()
    {
        if(Shuffle_On)
        {
            return shuffled_list;
        }
        else
        {
            return playlist;
        }
    }

    public Track getCurrentTrack()
    {
        return current_track;
    }

    public int getCurrent_pos()
    {
        return mediaPlayer.getCurrentPosition();
    }

    public void setCurrent_pos(int pos)
    {
        mediaPlayer.seekTo(pos);
        DurationUpdate();
    }

    public void setLoop()
    {
        if(!mediaPlayer.isLooping())
        {
            mediaPlayer.setLooping(true);
        }
        else
        {
            mediaPlayer.setLooping(false);
        }
        LoopStateChange();
    }

    public boolean getLoopState()
    {
        return mediaPlayer.isLooping();
    }

    public void Shuffle() {
       if(Shuffle_On)
       {
           for(int i = 0;i<getPlaylist().size();i++)
           {
               if(getCurrentTrack().getContentId().equals(playlist.get(i).getContentId()))
               {
                   current_pos = i;
               }
           }
           Shuffle_On = false;
       }
       else
       {
           shuffled_list = new ArrayList<Track>();
           shuffled_list.addAll(playlist);
           Collections.shuffle(shuffled_list);

           for(int i = 0;i<getPlaylist().size();i++)
           {
               if(getCurrentTrack()!=null) {
                   if (getCurrentTrack().getContentId().equals(shuffled_list.get(i).getContentId())) {
                       Track current = shuffled_list.get(i);
                       Track first = shuffled_list.get(0);
                       shuffled_list.set(0, current);
                       shuffled_list.set(i, first);
                       current_pos = 0;
                   }
               }
               else
               {

               }
           }
           Shuffle_On = true;
       }
       ShuffleStateChanged();
    }

    public boolean getShuffle() {
        return Shuffle_On;
    }

    public void setShuffle(boolean shuffle) {
         Shuffle_On = shuffle;
    }
}
