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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;


public class RNMetronomeModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  MediaPlayer mMediaPlayer;
  int mSoundNumber = 0;
  int mBpm = 80;

  ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);

  ScheduledFuture scheduledFuture;

  public RNMetronomeModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  private void initializeMediaPlayer() {
    if (mMediaPlayer == null) {
      mMediaPlayer = new MediaPlayer();
    }
  }

  @ReactMethod
  public void loadMedia(int mSoundNumber) {
    if (mMediaPlayer != null) {
      mMediaPlayer.reset();
    }
    initializeMediaPlayer();

    mSoundNumber = mSoundNumber % 6; 

    try {
      Uri uri = Uri.parse("android.resource://" + getReactApplicationContext().getPackageName() + "/raw/sound"+mSoundNumber);
      mMediaPlayer.setDataSource(getCurrentActivity(), uri);
      mMediaPlayer.prepare();
    } catch (IOException e) {
      Log.e("RNMetronome", "Exception", e);
    }
  }

  @ReactMethod
  public void nextSound() {
    stop();
    mSoundNumber = mSoundNumber + 1;
    play(mBpm);
  }

  @ReactMethod
  public void release() {
    if (mMediaPlayer != null) {
      mMediaPlayer.release();
      mMediaPlayer = null;
    }
  }

  Runnable tik = new Runnable() {
    @Override
    public void run() {
      mMediaPlayer.start();
    }
  };
  
  @ReactMethod
  public void play(int bpm) {
    mBpm = bpm;
    int result = (int)Math.round(60000/bpm);
    ex.setRemoveOnCancelPolicy(true);
    stop();
    loadMedia(mSoundNumber);
    scheduledFuture = ex.scheduleAtFixedRate(tik, result, result, TimeUnit.MILLISECONDS);
  }

  @ReactMethod
  public void stop() {
    if (mMediaPlayer != null) {
      mMediaPlayer.release();
      mMediaPlayer = null;
      scheduledFuture.cancel(false);
    }
  }

  @Override
  public String getName() {
    return "RNMetronome";
  }
}