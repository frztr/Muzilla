package com.example.muzilla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
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
    public AudioPlayer AudioPlayer;
    Track last_track = null;
    ArrayList<Fragment> pages = new ArrayList<Fragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        InitializeApp();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FullScreen();
        LoadWallpaper();
        SliderAdapter sliderAdapter = new SliderAdapter(this);
        sliderAdapter.setFragments(pages);
        vp2 = findViewById(R.id.pageloader);
        vp2.setAdapter(sliderAdapter);



        MainActivity t = this;
        LinearLayout ll = findViewById(R.id.linearLayout2);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(t,PlayerActivity.class);
                t.startActivity(intent);

                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
            }
        });

    }

    public boolean bound;

    public void StartAudioService()
    {
        Intent intent = new Intent(this,AudioPlayer.class);

        ServiceConnection srv = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.d("Console", "MainActivity onServiceConnected");
                AudioPlayer = ((AudioPlayer.AudioBinder) iBinder).getService(API);

                ((App) getApplicationContext()).ap = AudioPlayer;

                AudioPlayer.addPlayingListener(()->
                {
                    PlayButtonChangeIcon((ImageButton)findViewById(R.id.imageButton2));
                });

                AudioPlayer.addOnTrackLoadedListener(()->
                {
                    setTrackonPlayer();
                });

                setTrackonPlayer();

                AudioPlayer.addOnCurrentTrackStateChanged(()->
                {
                    setPlayerBehavior();
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.d("Console", "MainActivity onServiceDisconnected");
            }
        };
        bindService(intent,srv,0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("TestChannel","TestNameChannel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
            getApplicationContext().startForegroundService(intent);
        }
    }

    public void InitializeApp()
    {
        sp = getSharedPreferences("Account",Context.MODE_PRIVATE);
        pages = new ArrayList<Fragment>();
        if(sp.getString("access_token","").length()!=0)
        {
            API = new API(sp.getString("access_token", ""));
            StartAudioService();
            API.getMyProfile(sp);
            MainFragment mf = new MainFragment(API,Integer.parseInt(sp.getString("profileId","")));
            pages.add(mf);
            pages.add(new PopularFragment(API));
            pages.add(new RecommendationFragment(API));
            pages.add(new SearchFragment(API));
        }
        SettingsFragment sf = new SettingsFragment();
        sf.setSharedPreferences(sp);
        pages.add(sf);
    }

    public void PlayButtonChangeIcon(ImageView img)
    {
        if(AudioPlayer.isPlaying()==true) {
            img.setImageResource(R.drawable.ic_pause__1_);
        }
        else
        {
            img.setImageResource(R.drawable.ic_play__1_);
        }
    }

    public void LoadWallpaper()
    {
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
    }

    public void FullScreen()
    {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View v = findViewById(R.id.cl1);
        v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public void setTrackonPlayer()
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
    }

    public void setPlayerBehavior()
    {
        LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayout2);
        if(AudioPlayer.getCurrentTrack()==null && last_track != null)
        {
            for(int i = 0;i<ll.getChildCount();i++)
            {
                ll.getChildAt(i).setVisibility(View.GONE);
            }

            ValueAnimator anim = ValueAnimator.ofInt(78, 0);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = ll.getLayoutParams();
                    layoutParams.height = Math.round(val * getResources().getDisplayMetrics().density);
                    ll.setLayoutParams(layoutParams);
                }
            });
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {

                    ll.setVisibility(View.GONE);

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            anim.setDuration(300);
            anim.start();
        }
        if(AudioPlayer.getCurrentTrack()!=null && last_track == null)
        {
            for(int i = 0;i<ll.getChildCount();i++)
            {
                ll.getChildAt(i).setVisibility(View.GONE);
            }
            ll.setVisibility(View.VISIBLE);
            ValueAnimator anim = ValueAnimator.ofInt(0, 78);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = ll.getLayoutParams();
                    layoutParams.height = Math.round(val * getResources().getDisplayMetrics().density);
                    ll.setLayoutParams(layoutParams);
                }
            });
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    for(int i = 0;i<ll.getChildCount();i++)
                    {
                        ll.getChildAt(i).setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            anim.setDuration(300);
            anim.start();

        }
        last_track = AudioPlayer.getCurrentTrack();
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
        FullScreen();
    }
}