package com.example.muzilla;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class AudioNotificationService extends Service {

    AudioNotificationService.AudioBinder audioBinder = new AudioBinder();

    MediaSessionCompat cmpt;

    ViewUpdater viewUpdater = new ViewUpdater()
    {
        @Override
        public void onPlayingStateChanged() {
            super.onPlayingStateChanged();
            createNotification();
        }

        @Override
        public void onCurrentTrackStateChanged() {
            super.onCurrentTrackStateChanged();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && AudioPlayer.getInstance().getCurrentTrack()==null) {
            stopForeground(STOP_FOREGROUND_REMOVE);
            }
            else
                {
                    createNotification();
                }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        cmpt = new MediaSessionCompat(getApplicationContext(),"Muzilla");
        return audioBinder;
    }

    class AudioBinder extends Binder
    {
        AudioNotificationService getService()
        {
            return AudioNotificationService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notifintent = new Intent(this,AudioNotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notifintent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "TestChannel")
                .setContentTitle(getText(R.string.app_name))
                .setContentText(getText(R.string.app_name))
                .setSmallIcon(R.drawable.music_icon)
                .setContentIntent(pendingIntent);
        Notification notification = builder.build();

        AudioPlayer.getInstance().addUpdater(viewUpdater);
        startForeground(1,notification);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_REMOVE);
        }
        return START_NOT_STICKY;
    }

    private void createNotification()
    {
        Intent notifintent = new Intent(getApplicationContext(),AudioNotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notifintent, 0);
        Notification notification =
                null;
        final Bitmap[] bmp = {null};
        Track current_track = AudioPlayer.getInstance().getCurrentTrack();
        if(current_track!=null) {
            if (current_track.getImgUrl() != "") {

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
            } else {
                bmp[0] = BitmapFactory.decodeResource(getResources(), R.drawable.music_icon);
            }


            Intent playingIntent = new Intent(getApplicationContext(), PlayIntent.class);
            PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0, playingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Intent playingIntent2 = new Intent(getApplicationContext(), PreviousIntent.class);
            PendingIntent actionIntent2 = PendingIntent.getBroadcast(this, 0, playingIntent2, PendingIntent.FLAG_UPDATE_CURRENT);
            Intent playingIntent3 = new Intent(getApplicationContext(), NextIntent.class);
            PendingIntent actionIntent3 = PendingIntent.getBroadcast(this, 0, playingIntent3, PendingIntent.FLAG_UPDATE_CURRENT);
            Intent playingIntent4 = new Intent(getApplicationContext(), StopServiceIntent.class);
            PendingIntent actionIntent4 = PendingIntent.getBroadcast(this, 0, playingIntent4, PendingIntent.FLAG_UPDATE_CURRENT);
            int playing_ico = -1;
            if (AudioPlayer.getInstance().isPlaying()) {
                playing_ico = R.drawable.ic_pause__1_;
            } else {
                playing_ico = R.drawable.ic_play__1_;
            }

            notification = new NotificationCompat.Builder(getApplicationContext(), "TestChannel")
                    .setContentTitle(current_track.getName())
                    .setContentText(current_track.getMusician())
                    .setSmallIcon(R.drawable.ic_music_circle)
                    .setLargeIcon(bmp[0])
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(cmpt.getSessionToken()))
                    .setOnlyAlertOnce(true)
                    .addAction(R.drawable.ic_skip_previous, "Previous", actionIntent2)
                    .addAction(playing_ico, "Play", actionIntent)
                    .addAction(R.drawable.ic_skip_next, "Next", actionIntent3)
                    .addAction(R.drawable.ic_close, "Stop", actionIntent4)
                    .setContentIntent(pendingIntent)
                    .build();
        }
        else
            {
                notification = new NotificationCompat.Builder(getApplicationContext(), "TestChannel")
                        .setContentTitle(current_track.getName())
                        .setContentText(current_track.getMusician())
                        .setContentIntent(pendingIntent)
                        .build();
            }
        startForeground(1,notification);
    }
}
