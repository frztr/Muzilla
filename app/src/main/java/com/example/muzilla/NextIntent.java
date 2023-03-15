package com.example.muzilla;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.util.Log;

public class NextIntent extends BroadcastReceiver {
    public void onReceive(Context context, android.content.Intent intent) {
        Log.e("Console", "Next" );
        ((App)context.getApplicationContext()).ap.Next();
    }
}
