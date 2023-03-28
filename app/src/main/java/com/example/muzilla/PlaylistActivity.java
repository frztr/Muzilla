package com.example.muzilla;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PlaylistActivity extends AppCompatActivity implements IPlayerActivity {

    private ArrayListExtended<Track> tracks = new ArrayListExtended<Track>();
    private TrackAdapter adapter ;
    Track last_track = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        API.getInstance().addPlaylistListener(tracks);
        API.getInstance().getPlaylistAudio(getIntent().getStringExtra("owner_id"),getIntent().getStringExtra("playlist_id"), tracks,0);
        adapter = new TrackAdapter(this,tracks.arrayList);
        tracks.addListener(()->
        {
            adapter.notifyDataSetChanged();
        });
        FullScreen();
        LoadWallpaper();
    }

    public ViewUpdater viewUpdater = new ViewUpdater()
    {
        @Override
        public void onCurrentTrackStateChanged() {
            super.onCurrentTrackStateChanged();
            UpdatePlaylistPlaying();
            UpdateShuffleButton();
        }

        @Override
        public void onPlayingStateChanged() {
            super.onPlayingStateChanged();
            UpdatePlaylistPlaying();
        }

        @Override
        public void onShuffleStateChanged() {
            super.onShuffleStateChanged();
            UpdateShuffleButton();
        }
    };

    private void UpdateShuffleButton()
    {
        if (tracks.arrayList.contains(AudioPlayer.getInstance().getCurrentTrack()))
        {
            ToggleButton(AudioPlayer.getInstance().getShuffle(),findViewById(R.id.shuffle_playlist));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    private void LoadWallpaper()
    {
        try {
            if (App.getApplicationData().getString("wall_uri", "").length() != 0) {
                ImageView constraintLayout = findViewById(R.id.wall_back_playlist);
                try {
                    constraintLayout.setImageURI(Uri.parse(App.getApplicationData().getString("wall_uri", "")));
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

    private void FullScreen()
    {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View v = findViewById(R.id.cl3);
        v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.slide_in_up,R.anim.stay);
        if (getIntent().getStringExtra("playlist_img")  != "") {
            Picasso.get().load(getIntent().getStringExtra("playlist_img")).into((ImageView) findViewById(R.id.playlist_img_iv));
        } else {
            Picasso.get().load(R.drawable.music_icon).into((ImageView) findViewById(R.id.playlist_img_iv));
        }
        ((TextView) findViewById(R.id.playlist_name_tv)).setText(getIntent().getStringExtra("playlist_name"));
        ((TextView) findViewById(R.id.playlist_musician_tv)).setText(getIntent().getStringExtra("playlist_musician"));
        last_track = AudioPlayer.getInstance().getCurrentTrack();




        ImageButton addtrackbutton = (ImageButton) findViewById(R.id.add_playlist);
        if(App.getApplicationData().getString("profileId","").equals(getIntent().getStringExtra("owner_id")))
        {
            addtrackbutton.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
            DrawableCompat.setTint(addtrackbutton.getDrawable(), ContextCompat.getColor(this,R.color.color2_100));
        }
        else
        {
            addtrackbutton.setImageDrawable(getResources().getDrawable(R.drawable.ic_plus));
            DrawableCompat.setTint(addtrackbutton.getDrawable(),ContextCompat.getColor(this,R.color.color2_40));
        }

        if (tracks.arrayList.contains(AudioPlayer.getInstance().getCurrentTrack()))
        {
            ToggleButton(AudioPlayer.getInstance().getShuffle(),findViewById(R.id.shuffle_playlist));
        }

        AudioPlayer.getInstance().addUpdater(viewUpdater);
        LoadPlaylist();
    }

    public void Close(View view)
    {
        finish();
        overridePendingTransition(R.anim.stay,R.anim.slide_out_up);
    }

    private void LoadPlaylist()
    {
        RecyclerView rv = (RecyclerView) findViewById(R.id.playlist_rv);
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(rv.canScrollVertically(1)==false)
                {
                    API.getInstance().getPlaylistAudio(getIntent().getStringExtra("owner_id"),getIntent().getStringExtra("playlist_id"),tracks,rv.getAdapter().getItemCount());
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        FullScreen();
        UpdatePlaylistPlaying();
    }

    private void UpdatePlaylistPlaying()
    {
        PlayButtonChangeIcon((ImageButton) findViewById(R.id.imageButtonPlaylistPlay));
    }

    @Override
    public Track getLastTrack() {
        return last_track;
    }

    private void PlayButtonChangeIcon(ImageButton img)
    {
            if (tracks.arrayList.contains(AudioPlayer.getInstance().getCurrentTrack())){
                if (AudioPlayer.getInstance().isPlaying() == true) {
                img.setImageResource(R.drawable.ic_pause__1_);
                } else {
                img.setImageResource(R.drawable.ic_play__1_);
                }
            }
    }

    public void Pause(View v)
    {
        if(tracks.arrayList.equals(AudioPlayer.getInstance().getOriginalPlaylist()))
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
        else
        {
            AudioPlayer.getInstance().setPlaylist(tracks.arrayList);
            AudioPlayer.getInstance().Play(0);
        }
    }

    public void ShufflePlaylist(View view)
    {
        if(tracks.arrayList.equals(AudioPlayer.getInstance().getOriginalPlaylist()))
        {
            AudioPlayer.getInstance().Shuffle();
        }
        else
        {
            AudioPlayer.getInstance().setPlaylist(tracks.arrayList);
            if(AudioPlayer.getInstance().getShuffle())
            {
                AudioPlayer.getInstance().setShuffle(false);
            }
            AudioPlayer.getInstance().Shuffle();
            AudioPlayer.getInstance().Play(0);
        }

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
}