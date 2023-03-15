package com.example.muzilla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class PlayerActivity extends AppCompatActivity {

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        sp = getSharedPreferences("Account", Context.MODE_PRIVATE);

        FullScreen();
        LoadWallpaper();
    }



    public void FullScreen()
    {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View v = findViewById(R.id.cl2);
        v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public void LoadWallpaper()
    {
        try {
            if (sp.getString("wall_uri", "").length() != 0) {
                ImageView constraintLayout = findViewById(R.id.wall_back_player);
                try {
                    constraintLayout.setImageURI(Uri.parse(sp.getString("wall_uri", "")));
                }
                catch (Exception ex)
                {

                }
            }
        }
        catch (Exception ex)
        {

        }
    }
}