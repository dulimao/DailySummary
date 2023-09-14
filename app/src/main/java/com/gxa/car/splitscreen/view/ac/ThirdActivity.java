package com.gxa.car.splitscreen.view.ac;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.myandroiddemo.R;
import com.gxa.car.splitscreen.view.ac.base.BaseActivity;

/**
 * @author Administrator
 */
public class ThirdActivity extends AppCompatActivity {
    private static final String TAG = "ActivityTest";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_large);

        Log.i(TAG, "C onCreate: taskId: " + getTaskId());
    }

    //    private Button mButton;
//    private TextView mTvContent;
//
//    @Override
//    public int getLargeLayoutId() {
//        return R.layout.activity_main_large;
//    }
//
//    @Override
//    public int getNormalLayoutId() {
//        return R.layout.activity_main_small;
//    }
//
//    @Override
//    public void onViewCreate(boolean isFullScreen) {
//        mButton = findViewById(R.id.btn_change);
//        mTvContent = findViewById(R.id.tv_content);
//        initView(isFullScreen);
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
//            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//            String imeiOrMeid = telephonyManager.getDeviceId();
//            Log.i(TAG, "onViewCreate: imei: " + imeiOrMeid);
//        }
//
//
//    }
//
//    private void initView(boolean isFullScreen) {
////        if (true) {
////            getWindow().getDecorView().getWindowInsetsController().hide(WindowInsets.Type.statusBars());
////        } else {
////            getWindow().getDecorView().getWindowInsetsController().show(WindowInsets.Type.statusBars());
////        }
//        mTvContent.setTextColor(
//                isFullScreen ? Color.parseColor("#FF0000")
//                        : Color.parseColor("#00FF00")
//        );
//        mButton.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, NewActivity.class);
//            //需要确认打开的界面是否需要全屏显示，
//            // 这里demo因为需要和MainActivity保持相同大小，
//            // 所以直接将MainActivity的全屏状态给到NewActivity
//            intent.putExtra(BaseActivity.PARENT_WINDOW_STATE_FLAG, isFullScreen);
//            startActivity(intent);
//        });
//    }


}