package com.example.muzilla;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneStateBroadcastReceiver extends BroadcastReceiver {
    private final String TAG = getClass().getName();
    private static String number = null;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                if(AudioPlayer.getInstance().getCurrentTrack()!=null & AudioPlayer.getInstance().isPlaying())
                {
                    AudioPlayer.getInstance().Pause();
                }
            }

            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                if(AudioPlayer.getInstance().getCurrentTrack()!=null & !AudioPlayer.getInstance().isPlaying())
                {
                    AudioPlayer.getInstance().Play();
                }
            }

            if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                if(AudioPlayer.getInstance().getCurrentTrack()!=null & AudioPlayer.getInstance().isPlaying())
                {
                    AudioPlayer.getInstance().Pause();
                }
            }
        }

    }

}
