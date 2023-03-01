package com.example.muzilla;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    public API API;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = getSharedPreferences("Account",Context.MODE_PRIVATE);
        if(sp.getString("access_token","")!="")
        {
            API = new API(sp.getString("access_token", ""));
            if(sp.getString("profileId","")==""||sp.getString("profileId","")==null)
            {
                API.getMyProfile(sp);

            }
        }

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        View v = findViewById(R.id.cl1);
        v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        MainFragment mf = new MainFragment();
        mf.setOwner_id(Integer.parseInt(sp.getString("profileId","")));
        getSupportFragmentManager().beginTransaction().replace(R.id.pageloader,mf).commit();
    }

    public void MainPageLoad(View v)
    {
        MainFragment mf = new MainFragment();
        mf.setOwner_id(Integer.parseInt(sp.getString("profileId","")));
        getSupportFragmentManager().beginTransaction().replace(R.id.pageloader,mf).commit();
    }
    public void PopularPageLoad(View v)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.pageloader,new PopularFragment()).commit();
    }
    public void SearchPageLoad(View v)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.pageloader,new SearchFragment()).commit();
    }
    public void SettingsPageLoad(View v)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.pageloader,new SettingsFragment()).commit();
    }
}