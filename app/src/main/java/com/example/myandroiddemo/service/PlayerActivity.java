package com.example.myandroiddemo.service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myandroiddemo.R;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener, PlayerService.PlayerCallback {

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
                unregisterReceiver(broadcastReceiver);
                break;
            case R.id.button_bind:
                serviceIntent.putExtra("url","http://www.dulilmao.com");
                bindService(serviceIntent,connection,BIND_AUTO_CREATE);
                break;
            case R.id.button_unbind:
                if (isBind) {
                    unbindService(connection);
                }
                break;
                case R.id.button_reset:
                if (playerService!= null) {
                    playerService.resetNumber();
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
