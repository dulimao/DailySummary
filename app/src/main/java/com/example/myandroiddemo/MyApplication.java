package com.example.myandroiddemo;

import android.app.Application;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
        boolean isFollowSystem = SpeakerSharedPreferencesFactory.getNightModeFollowSystem(this);
        if (isFollowSystem) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }
}
