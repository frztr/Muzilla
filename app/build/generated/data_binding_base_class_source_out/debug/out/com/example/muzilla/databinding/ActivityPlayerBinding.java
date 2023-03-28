// Generated by view binder compiler. Do not edit!
package com.example.muzilla.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.muzilla.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityPlayerBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageButton addTrackButton;

  @NonNull
  public final CardView cardViewMusicIcon;

  @NonNull
  public final ConstraintLayout cl2;

  @NonNull
  public final TextView currentDuration;

  @NonNull
  public final ImageButton imageButtonPlayerNext;

  @NonNull
  public final ImageButton imageButtonPlayerPause;

  @NonNull
  public final ImageButton imageButtonPlayerPrev;

  @NonNull
  public final ImageButton loopButton;

  @NonNull
  public final LinearLayout playerButtons;

  @NonNull
  public final ConstraintLayout playerDurations;

  @NonNull
  public final ImageView playerMusicIcon;

  @NonNull
  public final LinearLayout playerSeekbar;

  @NonNull
  public final SeekBar playerSeekbarElement;

  @NonNull
  public final TextView playerTrackMusician;

  @NonNull
  public final TextView playerTrackName;

  @NonNull
  public final ImageButton shuffleButton;

  @NonNull
  public final TextView trackDuration;

  @NonNull
  public final ImageView wallBackPlayer;

  private ActivityPlayerBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageButton addTrackButton, @NonNull CardView cardViewMusicIcon,
      @NonNull ConstraintLayout cl2, @NonNull TextView currentDuration,
      @NonNull ImageButton imageButtonPlayerNext, @NonNull ImageButton imageButtonPlayerPause,
      @NonNull ImageButton imageButtonPlayerPrev, @NonNull ImageButton loopButton,
      @NonNull LinearLayout playerButtons, @NonNull ConstraintLayout playerDurations,
      @NonNull ImageView playerMusicIcon, @NonNull LinearLayout playerSeekbar,
      @NonNull SeekBar playerSeekbarElement, @NonNull TextView playerTrackMusician,
      @NonNull TextView playerTrackName, @NonNull ImageButton shuffleButton,
      @NonNull TextView trackDuration, @NonNull ImageView wallBackPlayer) {
    this.rootView = rootView;
    this.addTrackButton = addTrackButton;
    this.cardViewMusicIcon = cardViewMusicIcon;
    this.cl2 = cl2;
    this.currentDuration = currentDuration;
    this.imageButtonPlayerNext = imageButtonPlayerNext;
    this.imageButtonPlayerPause = imageButtonPlayerPause;
    this.imageButtonPlayerPrev = imageButtonPlayerPrev;
    this.loopButton = loopButton;
    this.playerButtons = playerButtons;
    this.playerDurations = playerDurations;
    this.playerMusicIcon = playerMusicIcon;
    this.playerSeekbar = playerSeekbar;
    this.playerSeekbarElement = playerSeekbarElement;
    this.playerTrackMusician = playerTrackMusician;
    this.playerTrackName = playerTrackName;
    this.shuffleButton = shuffleButton;
    this.trackDuration = trackDuration;
    this.wallBackPlayer = wallBackPlayer;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityPlayerBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityPlayerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_player, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityPlayerBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.add_track_button;
      ImageButton addTrackButton = ViewBindings.findChildViewById(rootView, id);
      if (addTrackButton == null) {
        break missingId;
      }

      id = R.id.cardView_music_icon;
      CardView cardViewMusicIcon = ViewBindings.findChildViewById(rootView, id);
      if (cardViewMusicIcon == null) {
        break missingId;
      }

      ConstraintLayout cl2 = (ConstraintLayout) rootView;

      id = R.id.current_duration;
      TextView currentDuration = ViewBindings.findChildViewById(rootView, id);
      if (currentDuration == null) {
        break missingId;
      }

      id = R.id.imageButtonPlayerNext;
      ImageButton imageButtonPlayerNext = ViewBindings.findChildViewById(rootView, id);
      if (imageButtonPlayerNext == null) {
        break missingId;
      }

      id = R.id.imageButtonPlayerPause;
      ImageButton imageButtonPlayerPause = ViewBindings.findChildViewById(rootView, id);
      if (imageButtonPlayerPause == null) {
        break missingId;
      }

      id = R.id.imageButtonPlayerPrev;
      ImageButton imageButtonPlayerPrev = ViewBindings.findChildViewById(rootView, id);
      if (imageButtonPlayerPrev == null) {
        break missingId;
      }

      id = R.id.loop_button;
      ImageButton loopButton = ViewBindings.findChildViewById(rootView, id);
      if (loopButton == null) {
        break missingId;
      }

      id = R.id.player_buttons;
      LinearLayout playerButtons = ViewBindings.findChildViewById(rootView, id);
      if (playerButtons == null) {
        break missingId;
      }

      id = R.id.player_durations;
      ConstraintLayout playerDurations = ViewBindings.findChildViewById(rootView, id);
      if (playerDurations == null) {
        break missingId;
      }

      id = R.id.player_music_icon;
      ImageView playerMusicIcon = ViewBindings.findChildViewById(rootView, id);
      if (playerMusicIcon == null) {
        break missingId;
      }

      id = R.id.player_seekbar;
      LinearLayout playerSeekbar = ViewBindings.findChildViewById(rootView, id);
      if (playerSeekbar == null) {
        break missingId;
      }

      id = R.id.player_seekbar_element;
      SeekBar playerSeekbarElement = ViewBindings.findChildViewById(rootView, id);
      if (playerSeekbarElement == null) {
        break missingId;
      }

      id = R.id.player_track_musician;
      TextView playerTrackMusician = ViewBindings.findChildViewById(rootView, id);
      if (playerTrackMusician == null) {
        break missingId;
      }

      id = R.id.player_track_name;
      TextView playerTrackName = ViewBindings.findChildViewById(rootView, id);
      if (playerTrackName == null) {
        break missingId;
      }

      id = R.id.shuffle_button;
      ImageButton shuffleButton = ViewBindings.findChildViewById(rootView, id);
      if (shuffleButton == null) {
        break missingId;
      }

      id = R.id.track_duration;
      TextView trackDuration = ViewBindings.findChildViewById(rootView, id);
      if (trackDuration == null) {
        break missingId;
      }

      id = R.id.wall_back_player;
      ImageView wallBackPlayer = ViewBindings.findChildViewById(rootView, id);
      if (wallBackPlayer == null) {
        break missingId;
      }

      return new ActivityPlayerBinding((ConstraintLayout) rootView, addTrackButton,
          cardViewMusicIcon, cl2, currentDuration, imageButtonPlayerNext, imageButtonPlayerPause,
          imageButtonPlayerPrev, loopButton, playerButtons, playerDurations, playerMusicIcon,
          playerSeekbar, playerSeekbarElement, playerTrackMusician, playerTrackName, shuffleButton,
          trackDuration, wallBackPlayer);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
