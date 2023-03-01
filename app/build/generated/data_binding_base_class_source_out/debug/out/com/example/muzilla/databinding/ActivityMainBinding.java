// Generated by view binder compiler. Do not edit!
package com.example.muzilla.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.muzilla.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ConstraintLayout cl1;

  @NonNull
  public final ConstraintLayout constraintLayout;

  @NonNull
  public final ImageButton imageButton;

  @NonNull
  public final ImageButton imageButton2;

  @NonNull
  public final ImageButton imageButton3;

  @NonNull
  public final ImageButton imageButton5;

  @NonNull
  public final ImageButton imageButton6;

  @NonNull
  public final ImageButton imageButton7;

  @NonNull
  public final ImageButton imageButton8;

  @NonNull
  public final ImageView imageView2;

  @NonNull
  public final LinearLayout linearLayout;

  @NonNull
  public final LinearLayout linearLayout2;

  @NonNull
  public final FrameLayout pageloader;

  @NonNull
  public final TextView textView;

  @NonNull
  public final TextView textView2;

  private ActivityMainBinding(@NonNull ConstraintLayout rootView, @NonNull ConstraintLayout cl1,
      @NonNull ConstraintLayout constraintLayout, @NonNull ImageButton imageButton,
      @NonNull ImageButton imageButton2, @NonNull ImageButton imageButton3,
      @NonNull ImageButton imageButton5, @NonNull ImageButton imageButton6,
      @NonNull ImageButton imageButton7, @NonNull ImageButton imageButton8,
      @NonNull ImageView imageView2, @NonNull LinearLayout linearLayout,
      @NonNull LinearLayout linearLayout2, @NonNull FrameLayout pageloader,
      @NonNull TextView textView, @NonNull TextView textView2) {
    this.rootView = rootView;
    this.cl1 = cl1;
    this.constraintLayout = constraintLayout;
    this.imageButton = imageButton;
    this.imageButton2 = imageButton2;
    this.imageButton3 = imageButton3;
    this.imageButton5 = imageButton5;
    this.imageButton6 = imageButton6;
    this.imageButton7 = imageButton7;
    this.imageButton8 = imageButton8;
    this.imageView2 = imageView2;
    this.linearLayout = linearLayout;
    this.linearLayout2 = linearLayout2;
    this.pageloader = pageloader;
    this.textView = textView;
    this.textView2 = textView2;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      ConstraintLayout cl1 = (ConstraintLayout) rootView;

      id = R.id.constraintLayout;
      ConstraintLayout constraintLayout = ViewBindings.findChildViewById(rootView, id);
      if (constraintLayout == null) {
        break missingId;
      }

      id = R.id.imageButton;
      ImageButton imageButton = ViewBindings.findChildViewById(rootView, id);
      if (imageButton == null) {
        break missingId;
      }

      id = R.id.imageButton2;
      ImageButton imageButton2 = ViewBindings.findChildViewById(rootView, id);
      if (imageButton2 == null) {
        break missingId;
      }

      id = R.id.imageButton3;
      ImageButton imageButton3 = ViewBindings.findChildViewById(rootView, id);
      if (imageButton3 == null) {
        break missingId;
      }

      id = R.id.imageButton5;
      ImageButton imageButton5 = ViewBindings.findChildViewById(rootView, id);
      if (imageButton5 == null) {
        break missingId;
      }

      id = R.id.imageButton6;
      ImageButton imageButton6 = ViewBindings.findChildViewById(rootView, id);
      if (imageButton6 == null) {
        break missingId;
      }

      id = R.id.imageButton7;
      ImageButton imageButton7 = ViewBindings.findChildViewById(rootView, id);
      if (imageButton7 == null) {
        break missingId;
      }

      id = R.id.imageButton8;
      ImageButton imageButton8 = ViewBindings.findChildViewById(rootView, id);
      if (imageButton8 == null) {
        break missingId;
      }

      id = R.id.imageView2;
      ImageView imageView2 = ViewBindings.findChildViewById(rootView, id);
      if (imageView2 == null) {
        break missingId;
      }

      id = R.id.linearLayout;
      LinearLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      id = R.id.linearLayout2;
      LinearLayout linearLayout2 = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout2 == null) {
        break missingId;
      }

      id = R.id.pageloader;
      FrameLayout pageloader = ViewBindings.findChildViewById(rootView, id);
      if (pageloader == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      id = R.id.textView2;
      TextView textView2 = ViewBindings.findChildViewById(rootView, id);
      if (textView2 == null) {
        break missingId;
      }

      return new ActivityMainBinding((ConstraintLayout) rootView, cl1, constraintLayout,
          imageButton, imageButton2, imageButton3, imageButton5, imageButton6, imageButton7,
          imageButton8, imageView2, linearLayout, linearLayout2, pageloader, textView, textView2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
