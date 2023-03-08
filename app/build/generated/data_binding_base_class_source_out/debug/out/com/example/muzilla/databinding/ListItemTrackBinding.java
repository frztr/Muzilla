// Generated by view binder compiler. Do not edit!
package com.example.muzilla.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public final class ListItemTrackBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView durationTrack;

  @NonNull
  public final CardView trackItem;

  @NonNull
  public final ConstraintLayout trackItemWall;

  @NonNull
  public final ImageView trackLogo;

  @NonNull
  public final TextView trackMusician;

  @NonNull
  public final TextView trackName;

  private ListItemTrackBinding(@NonNull CardView rootView, @NonNull TextView durationTrack,
      @NonNull CardView trackItem, @NonNull ConstraintLayout trackItemWall,
      @NonNull ImageView trackLogo, @NonNull TextView trackMusician, @NonNull TextView trackName) {
    this.rootView = rootView;
    this.durationTrack = durationTrack;
    this.trackItem = trackItem;
    this.trackItemWall = trackItemWall;
    this.trackLogo = trackLogo;
    this.trackMusician = trackMusician;
    this.trackName = trackName;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ListItemTrackBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ListItemTrackBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.list_item_track, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ListItemTrackBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.duration_track;
      TextView durationTrack = ViewBindings.findChildViewById(rootView, id);
      if (durationTrack == null) {
        break missingId;
      }

      CardView trackItem = (CardView) rootView;

      id = R.id.track_item_wall;
      ConstraintLayout trackItemWall = ViewBindings.findChildViewById(rootView, id);
      if (trackItemWall == null) {
        break missingId;
      }

      id = R.id.track_logo;
      ImageView trackLogo = ViewBindings.findChildViewById(rootView, id);
      if (trackLogo == null) {
        break missingId;
      }

      id = R.id.track_musician;
      TextView trackMusician = ViewBindings.findChildViewById(rootView, id);
      if (trackMusician == null) {
        break missingId;
      }

      id = R.id.track_name;
      TextView trackName = ViewBindings.findChildViewById(rootView, id);
      if (trackName == null) {
        break missingId;
      }

      return new ListItemTrackBinding((CardView) rootView, durationTrack, trackItem, trackItemWall,
          trackLogo, trackMusician, trackName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
