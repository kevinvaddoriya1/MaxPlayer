package com.example.videoplayer.player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media3.ui.PlayerView;
import com.example.videoplayer.activities.PlayerActivity;

public class CustomPlayerView extends PlayerView implements GestureDetector.OnGestureListener, ScaleGestureDetector.OnScaleGestureListener{

    public CustomPlayerView(Context context) {
        super(context);
    }

    public CustomPlayerView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomPlayerView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean tap() {
//        if (PlayerActivity.locked) {
//            Utilities.showText(this, "", MESSAGE_TIMEOUT_LONG);
//            setIconLock(true);
//            return true;
//        }
//
//        if (!PlayerActivity.controllerVisibleFully) {
//            showController();
//            return true;
//        } else if (PlayerActivity.haveMedia && PlayerActivity.exoPlayer != null && PlayerActivity.exoPlayer.isPlaying()) {
//            hideController();
//            return true;
//        }
        return false;
    }


    @Override
    public boolean onDown(@NonNull MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(@Nullable MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(@Nullable MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public boolean onScale(@NonNull ScaleGestureDetector scaleGestureDetector) {
        return false;
    }

    @Override
    public boolean onScaleBegin(@NonNull ScaleGestureDetector scaleGestureDetector) {
        return false;
    }

    @Override
    public void onScaleEnd(@NonNull ScaleGestureDetector scaleGestureDetector) {

    }
}
