package com.example.muzilla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public API API;
    SharedPreferences sp;
    ViewPager2 vp2;
    int startpos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = getSharedPreferences("Account",Context.MODE_PRIVATE);
        if(sp.getString("access_token","")!="")
        {
            API = new API(sp.getString("access_token", ""));
            API.getMyProfile(sp);
            startpos = 0;

        }
        else
        {
            startpos =4;
        }

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        View v = findViewById(R.id.cl1);
        v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        vp2 = findViewById(R.id.pageloader);

        ArrayList<Fragment> arrayList = new ArrayList<Fragment>();
        MainFragment mf = new MainFragment();
        mf.setSharedPreferences(sp);
        arrayList.add(mf);
        arrayList.add(new PopularFragment());
        arrayList.add(new RecommendationFragment());
        arrayList.add(new SearchFragment());
        SettingsFragment sf = new SettingsFragment();
        sf.setSharedPreferences(sp);
        arrayList.add(sf);

        SliderAdapter sliderAdapter = new SliderAdapter(this);
        sliderAdapter.setFragments(arrayList);
        vp2.setAdapter(sliderAdapter);
        vp2.setCurrentItem(startpos);
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

}