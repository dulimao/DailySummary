package com.example.myandroiddemo.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.myandroiddemo.IClientInterface;
import com.example.myandroiddemo.IPlayInterface;
import com.example.myandroiddemo.R;
import com.gxa.car.splitscreen.view.ac.NewActivity;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerService extends Service {
    private static final String TAG = "PlayerService";
    public static final String CHANNEL_ID = "exampleServiceChannel";

    private Notification notification;
    //无论是同一进程还是不同进程，都可以使用广播和接口回调的方式实现双向通信，
    // 只不过同一进程中，是Java接口，不同进程中是AIDL接口
    private PlayerCallback playerCallback;//同一进程使用的回调接口
    private IClientInterface iClientInterface;//独立进程用的回调接口
    private AtomicInteger atomicInteger;

    private boolean isRunning;
    private PlayerBinder playerBinder = new PlayerBinder();
    private boolean isSameProcess = false;//服务是否在新进程中
    private Person mPerson;

    private Handler playerHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int progress = msg.arg1;
            if (playerCallback != null) {
                playerCallback.updateProgress(progress);
            }
            if (iClientInterface != null) {
                try {
                    mPerson.setAge(mPerson.getAge() + 10);
                    iClientInterface.updateProgress(progress,mPerson);
                    Log.i(TAG, "handleMessage: mPerson: " + mPerson.toString());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
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

        //普通后台服务置于后台一段时间就被系统杀死了，为了一直保活将次服务设置为前台服务
        Intent intent1 = new Intent(this, NewActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent1,PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("前台服务通知")
                .setContentText(url)
                .setSmallIcon(R.drawable.aa)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1,notification);

//        stopForeground(true);//从前台服务切换到后台服务

        isRunning = true;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    atomicInteger.getAndIncrement();
                    Message message = Message.obtain();
                    message.arg1 = atomicInteger.get();
                    playerHandler.sendMessage(message);

                    Intent intent = new Intent("com.play.service.action");
                    intent.putExtra("progress",atomicInteger.get());
                    sendBroadcast(intent);
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

    private final IPlayInterface.Stub binder = new IPlayInterface.Stub() {
        @Override
        public void resetProgress(int progress) throws RemoteException {
            Log.i(TAG, "resetProgress: progress: " + progress);
            resetNumber();
        }

        @Override
        public void setPlayCallback(IClientInterface clientCnterface, Person person) throws RemoteException {
            mPerson = person;
            Log.i(TAG, "setPlayCallback: person: " + person.toString());
            person.setAge(30);
            iClientInterface = clientCnterface;
        }

        @Override
        public void setPersons(List<Person> persons) throws RemoteException {
            for (int i = 0; i < persons.size(); i++) {
                Log.i(TAG, "setPersons: person " + persons.get(i).toString());
            }
            persons.clear();//如果是inout的话，这里clear了，客户端的也就清空了
            Log.i(TAG, "setPersons: threadName: " + Thread.currentThread().getName());
        }

        @Override
        public void setBundle(Bundle bundle) throws RemoteException {
            String name = bundle.getString("name");
            Log.i(TAG, "setBundle: name: " + name);
            Log.i(TAG, "setBundle: threadName: "  + Thread.currentThread().getName());
//            binder:10381_2 // Binder线程，由Binder驱动管理
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //当有绑定者的时候，stop是停止不了的
        //startService和bindService可以都执行，不分先后顺序
        String url = intent.getStringExtra("url");
        Log.i(TAG, "onBind: url: " + url + " threadName: " + Thread.currentThread().getName());
        return isSameProcess ? playerBinder : binder;
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
        //需要注意的是，如果同一个服务同时被start和bind，必须既调用 stopService()方法又调用 unbindService()方法才能真正地停止服务。
        super.onDestroy();
        isRunning = false;
        atomicInteger.set(0);
        playerHandler.removeCallbacksAndMessages(0);
        Log.i(TAG, "onDestroy: ");
    }

    public void resetNumber() {
        Log.i(TAG, "resetNumber: thread: " + Thread.currentThread().getName());
        atomicInteger.set(0);
    }


    public void setPlayerCallback(PlayerCallback playerCallback) {
        this.playerCallback = playerCallback;
    }

    interface PlayerCallback {
        void updateProgress(int progress);
    }
}
