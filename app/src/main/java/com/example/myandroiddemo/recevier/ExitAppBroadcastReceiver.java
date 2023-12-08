package com.example.myandroiddemo.recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;



public class ExitAppBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "ExitAppBroadcastReceive";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.i(TAG, "onReceive: action: " + action);
        if (!TextUtils.isEmpty(action) && action.equals("com.qiyi.video.iv.exit")) {
            System.exit(0);
        }
    }
}
