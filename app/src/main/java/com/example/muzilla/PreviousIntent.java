package com.example.muzilla;

import android.content.BroadcastReceiver;
import android.content.Context;

public class PreviousIntent extends BroadcastReceiver {
    public void onReceive(Context context, android.content.Intent intent) {
        ((App)context.getApplicationContext()).ap.Prev();
    }
}
