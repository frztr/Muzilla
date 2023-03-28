package com.example.muzilla;

import android.content.BroadcastReceiver;
import android.content.Context;

public class StopServiceIntent extends BroadcastReceiver
{
    public void onReceive(Context context, android.content.Intent intent) {
        AudioPlayer.getInstance().Stop();
    }
}
