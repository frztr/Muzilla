package com.example.muzilla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.ImageViewCompat;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

public class PlayerActivity extends AppCompatActivity {

    SharedPreferences sp;
    SeekBar sb;
    TextView current_duration;
    Track last_track;
    ViewUpdater viewUpdater = new ViewUpdater()
    {
        @Override
        public void onPlayingStateChanged() {
            PlayButtonChangeIcon((ImageButton)findViewById(R.id.imageButtonPlayerPause));
        }

        @Override
        public void onTrackLoaded() {
            setTrackonPlayer();
            MyAudiosUpdate();
        }

        @Override
        public void onTrackCurrentDurationChanged() {
            PlayerCurrent_Pos();

        }

        @Override
        public void onLoopStateChange() {
            LoopChanged();
        }

        @Override
        public void onShuffleStateChanged() {
            ShuffleChanged();
        }

        @Override
        public void onAudiosUpdate() {
            MyAudiosUpdate();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        sp = getSharedPreferences("Account", Context.MODE_PRIVATE);
        sb = (SeekBar) findViewById(R.id.player_seekbar_element);
        current_duration = (TextView) findViewById(R.id.current_duration);

        PlayButtonChangeIcon((ImageButton)findViewById(R.id.imageButtonPlayerPause));
        setTrackonPlayer();
        PlayerCurrent_Pos();
        LoopChanged();
        ShuffleChanged();
        MyAudiosUpdate();
        FullScreen();
        LoadWallpaper();
    }

    private boolean Seek_bar_touched = false;

    public void PlayerCurrent_Pos()
    {
        int cur = AudioPlayer.getInstance().getCurrent_pos();
        if(!Seek_bar_touched) {
            sb.setProgress(cur);
        }
        Integer minutes = cur/60000;
        Integer secs = cur%60000/1000;

        runOnUiThread(()->
        {
            current_duration.setText(String.format("%d:%02d",minutes,secs));
        });
    }

    public void PlayButtonChangeIcon(ImageView img)
    {
        if(AudioPlayer.getInstance().isPlaying()==true) {
            img.setImageResource(R.drawable.ic_pause__1_);
        }
        else
        {
            img.setImageResource(R.drawable.ic_play__1_);
        }
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

    public void Next(View v)
    {
        AudioPlayer.getInstance().Next();
        if(AudioPlayer.getInstance().getCurrentTrack()==null && last_track != null) {

            this.finish();
        }
        else
        {
            last_track = AudioPlayer.getInstance().getCurrentTrack();
        }
    }

    @SuppressLint("ResourceType")
    public void Pause(View v)
    {
        if(AudioPlayer.getInstance().isPlaying())
        {
            AudioPlayer.getInstance().Pause();
        }
        else
        {
            AudioPlayer.getInstance().Play();
        }
    }

    public void Prev(View v)
    {
        AudioPlayer.getInstance().Prev();
    }

    public void setTrackonPlayer()
    {
        Track track = AudioPlayer.getInstance().getCurrentTrack();
        if(track!=null) {
            if (track.getImgUrl() != "") {
                Picasso.get().load(track.getImgUrl()).into((ImageView) findViewById(R.id.player_music_icon));
            } else {
                Picasso.get().load(R.drawable.music_icon).into((ImageView) findViewById(R.id.player_music_icon));
            }
            TextView tv = (TextView) findViewById(R.id.player_track_name);
            tv.setText(track.getName());
            TextView tv2 = (TextView) findViewById(R.id.player_track_musician);
            tv2.setText(track.getMusician());
            TextView duration_tv = (TextView) findViewById(R.id.track_duration);
            Integer minutes = track.getDuration()/60;
            Integer secs = track.getDuration()%60;
            duration_tv.setText(String.format("%d:%02d",minutes,secs));
            sb = (SeekBar) findViewById(R.id.player_seekbar_element);
            current_duration.setText("0:00");
            sb.setProgress(0);
            sb.setMax(track.getDuration()*1000);
            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    Seek_bar_touched = true;
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    Seek_bar_touched = false;
                    AudioPlayer.getInstance().setCurrent_pos(seekBar.getProgress());
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FullScreen();
        MyAudiosUpdate();
    }

    public void setLoop(View v)
    {
        AudioPlayer.getInstance().setLoop();
    }

    public void addTrack(View v)
    {
        if(AudioPlayer.getInstance().getCurrentTrack().getLocal_owner()!=null)
        {
            API.getInstance().deleteTrack(AudioPlayer.getInstance().getCurrentTrack());
        }
        else
        {
            API.getInstance().addTrack(AudioPlayer.getInstance().getCurrentTrack());
        }
    }

    public void setShuffle(View v)
    {
        AudioPlayer.getInstance().Shuffle();
    }

    private void ShuffleChanged()
    {
        ToggleButton(AudioPlayer.getInstance().getShuffle(),(ImageButton) findViewById(R.id.shuffle_button));
    }

    private void LoopChanged()
    {
        ToggleButton(AudioPlayer.getInstance().getLoopState(),(ImageButton) findViewById(R.id.loop_button));
    }

    private void ToggleButton(boolean condition,ImageButton button)
    {
        if(condition)
        {
            DrawableCompat.setTint(button.getDrawable(), ContextCompat.getColor(this,R.color.color2_100));
        }
        else
        {
            DrawableCompat.setTint(button.getDrawable(), ContextCompat.getColor(this,R.color.color2_40));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.slide_in_up,R.anim.stay);
        AudioPlayer.getInstance().addUpdater(viewUpdater);
        API.getInstance().addViewUpdater(viewUpdater);
    }

    private void MyAudiosUpdate()
    {
        ImageButton addtrackbutton = (ImageButton) findViewById(R.id.add_track_button);
        if(AudioPlayer.getInstance().getCurrentTrack().getLocal_owner()!=null)
        {
            addtrackbutton.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
            DrawableCompat.setTint(addtrackbutton.getDrawable(),ContextCompat.getColor(this,R.color.color2_100));
        }
        else
        {
            addtrackbutton.setImageDrawable(getResources().getDrawable(R.drawable.ic_plus));
            DrawableCompat.setTint(addtrackbutton.getDrawable(),ContextCompat.getColor(this,R.color.color2_40));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay,R.anim.slide_out_up);
    }
}