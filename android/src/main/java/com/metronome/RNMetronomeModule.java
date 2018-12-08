
package com.metronome;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import android.media.MediaPlayer;
import android.media.AudioManager;
import android.util.Log;
import android.net.Uri;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.io.Exception;

public class RNMetronomeModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  MediaPlayer mMediaPlayer;
  int mResourceId;

  ScheduledThreadPoolExecutor mSheduler;

  public RNMetronomeModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @ReactMethod
  private void initializeMediaPlayer() {
    if (mMediaPlayer == null) {
      mMediaPlayer = new MediaPlayer();
    }
    if (mSheduler == null) {
      mSheduler = new ScheduledThreadPoolExecutor(1);
    }
  }

  @ReactMethod
  public void loadMedia(int resourceId) {

    mResourceId = resourceId;

    if (mMediaPlayer != null) {
      mMediaPlayer.reset();
    }

    initializeMediaPlayer();

    try {
      Uri uri = Uri.parse("android.resource://" + getReactApplicationContext().getPackageName() + "/raw/sounds/1");
      mMediaPlayer.setDataSource(getCurrentActivity(), uri);
      mMediaPlayer.prepare();
    } catch (Exception e) {
      Log.e("RNMetronome", "Exception", e);
    }
  }

  @ReactMethod
  public void release() {
    if (mMediaPlayer != null) {
      mMediaPlayer.release();
      mMediaPlayer = null;
    }
  }

  @ReactMethod
  public void play(int bpm) {
    // Handler handler = new Handler();
    // ler.postDelayed(new Runnable() {
    // verride
    // public void run() {
    // mMediaPlayer.start();
    // play();
    // }
    // }, 300);
    mSheduler.scheduleAtFixedRate(new Runnable() {
      @Override
      public void run() {
        mMediaPlayer.start();
      }
    }, bpm, bpm, TimeUnit.MILLISECONDS);
  }

  @ReactMethod
  public void stop() {
    if (mMediaPlayer != null) {
      mMediaPlayer.stop();
    }
  }

  @Override
  public String getName() {
    return "RNMetronome";
  }
}