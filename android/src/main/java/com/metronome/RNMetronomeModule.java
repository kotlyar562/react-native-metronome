package com.metronome;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import android.media.AudioManager;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import android.os.Build;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.media.AudioAttributes;


public class RNMetronomeModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  int mSoundNumber = 1;
  int mBpm = 80;
  int soundID = -1;
  boolean isPlaying = false;
  boolean loaded = false;
  int interval = 1000;
  

  SoundPool soundPool;

  ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);

  ScheduledFuture scheduledFuture;

  public RNMetronomeModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  private static int toInterval(int bpm) {
    return (int) 60000 / bpm;
  }

  @ReactMethod
  public void nextSound() {
    mSoundNumber = (mSoundNumber + 1) % 6;
    if (soundPool != null ) {
      stop();
      int sound_id = this.reactContext.getResources().getIdentifier("sound"+mSoundNumber, "raw",
                                                      this.reactContext.getPackageName());
      soundID = soundPool.load(this.reactContext, sound_id, 1);
      play(mBpm);
    } else {
      play(mBpm);
    }
  }

  private void initializeSoundPool() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      soundPool = new SoundPool.Builder()
        .setMaxStreams(1)
        .setAudioAttributes(new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build())
        .build();
      
    } else soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
      @Override
      public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
          loaded = true;
      }
    });
    int sound_id = this.reactContext.getResources().getIdentifier("sound"+mSoundNumber, "raw",
                                                    this.reactContext.getPackageName());
    soundID = soundPool.load(this.reactContext, sound_id, 1);
  }

  Runnable tik = new Runnable() {
    @Override
    public void run() {
      soundPool.play(soundID, 1, 1, 1, 0, 1.0f);
    }
  };
  
  @ReactMethod
  public void play(int bpm) {
    if (soundPool == null) {
      initializeSoundPool();
    } else {
      stop();
    }
    if (bpm > 0) {
      mBpm = bpm;
      isPlaying = true;
      interval = toInterval(bpm);
      ex.setRemoveOnCancelPolicy(true);
      scheduledFuture = ex.scheduleAtFixedRate(tik, interval, interval, TimeUnit.MILLISECONDS);
    }
  }

  @ReactMethod
  public void stop() {
    if (soundPool != null) {
      isPlaying = false;
      scheduledFuture.cancel(false);
    }
  }

  @ReactMethod
  public void isPlay(Callback isPlayCallback) {
    isPlayCallback.invoke(isPlaying);
  }

  @Override
  public String getName() {
    return "RNMetronome";
  }
}