package com.example.myandroiddemo.service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myandroiddemo.IClientInterface;
import com.example.myandroiddemo.IPlayInterface;
import com.example.myandroiddemo.R;
import com.example.myandroiddemo.view.Person;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener, PlayerService.PlayerCallback {

    private static final String TAG = "PlayerActivity";
    private Button buttonStart;
    private Button buttonStop;
    private Button buttonBind;
    private Button buttonUnBind;
    private Button buttonReset;
    private TextView textviewShow;
    private TextView textviewShow2;
    private Intent serviceIntent;
    private PlayerService playerService;
    private boolean isBind;
    private boolean isSameProcess = false;//服务是否在新进程中
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        buttonStart = findViewById(R.id.button_start);
        buttonStop = findViewById(R.id.button_stop);
        buttonBind = findViewById(R.id.button_bind);
        buttonUnBind = findViewById(R.id.button_unbind);
        buttonReset = findViewById(R.id.button_reset);
        textviewShow = findViewById(R.id.textview_show);
        textviewShow2 = findViewById(R.id.textview_show2);
        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
        buttonBind.setOnClickListener(this);
        buttonUnBind.setOnClickListener(this);
        buttonReset.setOnClickListener(this);
        serviceIntent = new Intent(this, PlayerService.class);
        IntentFilter intentFilter = new IntentFilter("com.play.service.action");
        registerReceiver(broadcastReceiver,intentFilter);

        Intent intent = new Intent();
        Person person = new Person("dulimao",33);
        intent.putExtra("person",person);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("person",Person.class);
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.play.service.action")) {
                int progress = intent.getIntExtra("progress",0);
                textviewShow2.setText("广播收到 当前进度: " + progress);
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_start:
                serviceIntent.putExtra("url","http://www.dulilmao.com");
                startService(serviceIntent);
                break;
            case R.id.button_stop:
                stopService(serviceIntent);
//                unregisterReceiver(broadcastReceiver);
                break;
            case R.id.button_bind:
                serviceIntent.putExtra("url","http://www.dulilmao.com");
                if (isSameProcess) {
                    bindService(serviceIntent,connection,BIND_AUTO_CREATE);
                } else {
                    bindService(serviceIntent,connectionProcess,BIND_AUTO_CREATE);
                }
                break;
            case R.id.button_unbind:
                if (isBind) {
                    if (isSameProcess) {
                        unbindService(connection);
                    } else {
                        unbindService(connectionProcess);
                    }
                }
                break;
                case R.id.button_reset:
                    if (isSameProcess) {
                        if (playerService!= null) {
                            playerService.resetNumber();
                        }
                    } else {
                        if (iPlayInterface != null) {
                            try {
                                iPlayInterface.resetProgress(0);
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    break;
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            playerService = ((PlayerService.PlayerBinder)iBinder).getService();
            playerService.setPlayerCallback(PlayerActivity.this);
            isBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            playerService = null;
            isBind = false;
        }
    };


    IPlayInterface iPlayInterface;
    private ServiceConnection connectionProcess = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iPlayInterface = IPlayInterface.Stub.asInterface(iBinder);
            try {
                iPlayInterface.setPlayCallback(iClientInterface);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            isBind = true;
            Log.i(TAG, "onServiceConnected isBind: " + isBind);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            iPlayInterface = null;
            isBind = false;
            Log.i(TAG, "onServiceDisconnected isBind: " + isBind);
        }
    };

    private IClientInterface.Stub iClientInterface = new IClientInterface.Stub() {
        @Override
        public void updateProgress(int progress) throws RemoteException {
            textviewShow.setText("远程接口回调 当前进度: " + progress);
        }
    };

    @Override
    public void updateProgress(int progress) {
        textviewShow.setText("接口回调 当前进度: " + progress);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
