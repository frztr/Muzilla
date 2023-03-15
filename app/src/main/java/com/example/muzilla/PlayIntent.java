package com.example.muzilla;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class PlayIntent extends BroadcastReceiver {
    public void onReceive(Context context, android.content.Intent intent) {

        AudioPlayer ap = ((App)context.getApplicationContext()).ap;
        if(ap.isPlaying()) {
            ap.Pause();
        }
        else
            {
                ap.Play();
            }
    }
}
