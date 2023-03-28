package com.example.muzilla;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class App extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        sp = getSharedPreferences("Account", Context.MODE_PRIVATE);
        API.getInstance().setToken(sp.getString("access_token", ""));
        API.getInstance().setProfileId(Integer.parseInt(sp.getString("profileId","")));
    }

    private static SharedPreferences sp;

    public static SharedPreferences getApplicationData()
    {
            return sp;
    }
}
