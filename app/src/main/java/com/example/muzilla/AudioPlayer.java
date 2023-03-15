package com.example.muzilla;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.ArrayList;

public class AudioPlayer extends Service {
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

    public void addOnCurrentTrackStateChanged(Runnable r)
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

    public AudioPlayer()
    {
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
            createNotification();
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
                        ForcePlay();
                    }
                });
                API.getTrackById(track);
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
                    Play();


                }
            });
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createNotification()
    {
        Intent notifintent = new Intent(getApplicationContext(),AudioPlayer.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notifintent, 0);

        final Bitmap[] bmp = {null};
        if(current_track.getImgUrl()!="")
        {

               Picasso.get().load(current_track.getImgUrl()).into(new Target() {
                   @Override
                   public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                       bmp[0] = bitmap;
                   }

                   @Override
                   public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                   }

                   @Override
                   public void onPrepareLoad(Drawable placeHolderDrawable) {

                   }
               });
        }
        else
        {
                bmp[0] = BitmapFactory.decodeResource(getResources(),R.drawable.music_icon);
        }

        Intent playingIntent = new Intent(getApplicationContext(),PlayIntent.class);
        PendingIntent actionIntent = PendingIntent.getBroadcast(this,0,playingIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        Intent playingIntent2 = new Intent(getApplicationContext(),PreviousIntent.class);
        PendingIntent actionIntent2 = PendingIntent.getBroadcast(this,0,playingIntent2,PendingIntent.FLAG_UPDATE_CURRENT);
        Intent playingIntent3 = new Intent(getApplicationContext(),NextIntent.class);
        PendingIntent actionIntent3 = PendingIntent.getBroadcast(this,0,playingIntent3,PendingIntent.FLAG_UPDATE_CURRENT);
        int playing_ico = -1;
        if(isPlaying())
        {
            playing_ico = R.drawable.ic_pause__1_;
        }
        else
        {
                playing_ico = R.drawable.ic_play__1_;
        }
        Notification notification =
                null;
            notification = new NotificationCompat.Builder(getApplicationContext(), "TestChannel")
                    .setContentTitle(current_track.getName())
                    .setContentText(current_track.getMusician())
                    .setSmallIcon(R.drawable.ic_music_circle)
                    .setLargeIcon(bmp[0])
                    .setStyle( new androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(cmpt.getSessionToken()))
                    .setOnlyAlertOnce(true)
                    .addAction(R.drawable.ic_skip_previous,"Previous",actionIntent2)
                    .addAction(playing_ico,"Play",actionIntent)
                    .addAction(R.drawable.ic_skip_next,"Next",actionIntent3)
                    .setContentIntent(pendingIntent)
                    .build();

        startForeground(1,notification);
    }

    public void Play()
    {
        Playing = true;
        PlayingUpdate();
        mediaPlayer.start();
        createNotification();
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
        if(current_pos+1< playlist.size())
        {
            Play(current_pos + 1);
        }
        else
        {
            Stop();
        }
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

    AudioBinder audioBinder = new AudioBinder();

    MediaSessionCompat cmpt;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        cmpt = new MediaSessionCompat(getApplicationContext(),"Muzilla");
        return audioBinder;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notifintent = new Intent(this,AudioPlayer.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notifintent, 0);

        Notification notification =
                new NotificationCompat.Builder(this, "TestChannel")
                        .setContentTitle(getText(R.string.app_name))
                        .setContentText(getText(R.string.app_name))
                        .setSmallIcon(R.drawable.music_icon)
                        .setContentIntent(pendingIntent)
                        .build();

        startForeground(1,notification);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_REMOVE);
        }
        return START_NOT_STICKY;
    }

    class AudioBinder extends Binder
    {
        AudioPlayer getService(API api)
        {
            AudioPlayer.this.API = api;
            return AudioPlayer.this;
        }
    }

}
