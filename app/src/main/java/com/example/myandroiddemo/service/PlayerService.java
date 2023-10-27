package com.example.myandroiddemo.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.concurrent.atomic.AtomicInteger;

public class PlayerService extends Service {
    private static final String TAG = "PlayerService";

    private PlayerCallback playerCallback;
    private AtomicInteger atomicInteger;

    private boolean isRunning;
    private PlayerBinder playerBinder = new PlayerBinder();

    private Handler playerHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (playerCallback != null) {
                int progress = msg.arg1;
                playerCallback.updateProgress(progress);
            }
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        //初始化 多次启动或绑定只执行一次，service是单例的
        Log.i(TAG, "onCreate: ");
        atomicInteger = new AtomicInteger(0);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //执行任务 多次执行会走多次
        String url = intent.getStringExtra("url");
        Log.i(TAG, "onStartCommand: url: " + url);
        isRunning = true;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    atomicInteger.getAndIncrement();
                    Message message = Message.obtain();
                    message.arg1 = atomicInteger.get();
                    playerHandler.sendMessage(message);
                    Log.i(TAG, "thread_id: " + Thread.currentThread().getId() +" run: value: " + atomicInteger.get());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();
        return START_STICKY;
    }


    class PlayerBinder extends Binder {
        public PlayerService getService() {
            return PlayerService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //当有绑定者的时候，stop是停止不了的
        //startService和bindService可以都执行，不分先后顺序
        String url = intent.getStringExtra("url");
        Log.i(TAG, "onBind: url: " + url);
        return playerBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i(TAG, "onRebind: ");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        //销毁 stopService()或stopSelf() 或所有绑定者都解绑
        //startService和bindService都执行的话，必须stop和unbind也都执行才能销毁
        super.onDestroy();
        isRunning = false;
        atomicInteger.set(0);
        playerHandler.removeCallbacksAndMessages(0);
        Log.i(TAG, "onDestroy: ");
    }

    public void resetNumber() {
        atomicInteger.set(0);
    }


    public void setPlayerCallback(PlayerCallback playerCallback) {
        this.playerCallback = playerCallback;
    }

    interface PlayerCallback {
        void updateProgress(int progress);
    }
}
