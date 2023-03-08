package com.example.muzilla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public API API;
    SharedPreferences sp;
    ViewPager2 vp2;
    int startpos;
    public AudioPlayer AudioPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = getSharedPreferences("Account",Context.MODE_PRIVATE);
        ArrayList<Fragment> arrayList = new ArrayList<Fragment>();
        if(sp.getString("access_token","").length()!=0)
        {
            API = new API(sp.getString("access_token", ""));
            AudioPlayer = new AudioPlayer(API);
            API.getMyProfile(sp);
            startpos = 0;
            MainFragment mf = new MainFragment(API,Integer.parseInt(sp.getString("profileId","")));
            arrayList.add(mf);
            arrayList.add(new PopularFragment(API));
            arrayList.add(new RecommendationFragment(API));
            arrayList.add(new SearchFragment(API));
        }
        else
        {

        }

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        View v = findViewById(R.id.cl1);
        v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

            try {
                if (sp.getString("wall_uri", "").length() != 0) {
                    ImageView constraintLayout = findViewById(R.id.constraintLayout);
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

        vp2 = findViewById(R.id.pageloader);
        SettingsFragment sf = new SettingsFragment();
        sf.setSharedPreferences(sp);
        arrayList.add(sf);
        SliderAdapter sliderAdapter = new SliderAdapter(this);
        sliderAdapter.setFragments(arrayList);
        vp2.setAdapter(sliderAdapter);
        vp2.setCurrentItem(startpos);
        ImageButton img = (ImageButton)findViewById(R.id.imageButton2);
        AudioPlayer.addPlayingListener(()->
        {
            if(AudioPlayer.isPlaying()==true) {
                img.setImageResource(R.drawable.ic_pause__1_);
            }
            else
                {
                    img.setImageResource(R.drawable.ic_play__1_);
                }
        });

        AudioPlayer.addOnTrackLoadedListener(()->
        {
            Track track = AudioPlayer.getCurrentTrack();
            if(track!=null) {
                if (track.getImgUrl() != "") {
                    Picasso.get().load(track.getImgUrl()).into((ImageView) findViewById(R.id.imageView2));
                } else {
                    Picasso.get().load(R.drawable.music_icon).into((ImageView) findViewById(R.id.imageView2));
                }
                TextView tv = (TextView) findViewById(R.id.textView);
                tv.setText(track.getName());
                TextView tv2 = (TextView) findViewById(R.id.textView2);
                tv2.setText(track.getMusician());
            }
        });

        AudioPlayer.addOnCurrentTrackExistsListener(()->
        {
            LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayout2);
            if(AudioPlayer.getCurrentTrack()==null)
            {
                ll.animate().scaleY(0f);
            }
            else
            {
                ll.animate().scaleY(1f);
            }
        });

    }


    public void MainPageLoad(View v)
    {
          vp2.setCurrentItem(0,true);
    }
    public void PopularPageLoad(View v)
    {
        vp2.setCurrentItem(1,true);
    }
    public void SearchPageLoad(View v)
    {
        vp2.setCurrentItem(3,true);
    }
    public void SettingsPageLoad(View v)
    {
        vp2.setCurrentItem(4,true);
    }
    public void Next(View v)
    {
        AudioPlayer.Next();
    }

    @SuppressLint("ResourceType")
    public void Pause(View v)
    {
        if(AudioPlayer.isPlaying())
        {
            AudioPlayer.Pause();
        }
        else
            {
                AudioPlayer.Play();
            }
    }

    public void Prev(View v)
    {
        AudioPlayer.Prev();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_main);
        View v = findViewById(R.id.cl1);
        v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}