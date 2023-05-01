package com.example.muzilla;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class App extends Application
{
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        sp = getSharedPreferences("Account", Context.MODE_PRIVATE);

        if(!sp.getString("access_token", "").equals("")) {
            API.getInstance().setToken(sp.getString("access_token", ""));
            API.getInstance().setProfileId(Integer.parseInt(sp.getString("profileId", "")));
        }

        IntentFilter commandFilter = new IntentFilter();
        commandFilter.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(AudioManager.ACTION_AUDIO_BECOMING_NOISY))
                {
                    if(AudioPlayer.getInstance().getCurrentTrack()!=null)
                    {
                        AudioPlayer.getInstance().Pause();
                    }
                }
            }
        };

        registerReceiver(broadcastReceiver,commandFilter);

        BroadcastReceiver reciever = new PhoneStateBroadcastReceiver();
        IntentFilter filter= new IntentFilter();
        filter.addAction("android.intent.action.PHONE_STATE");
        filter.addAction("android.intent.action.NEW_OUTGOING_CALL");
        registerReceiver(reciever, filter);



    }

    private static SharedPreferences sp;

    public static SharedPreferences getApplicationData()
    {
            return sp;
    }

    public static Context getAppContext()
    {
        return context;
    }
}
