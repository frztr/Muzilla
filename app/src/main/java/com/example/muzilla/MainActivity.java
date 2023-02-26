package com.example.muzilla;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        View v = findViewById(R.id.cl1);
        v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getSupportFragmentManager().beginTransaction().replace(R.id.pageloader,new MainFragment()).commit();
    }

    public void MainPageLoad(View v)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.pageloader,new MainFragment()).commit();
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