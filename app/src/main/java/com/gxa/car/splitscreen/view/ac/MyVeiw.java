package com.gxa.car.splitscreen.view.ac;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class MyVeiw extends TextView {
    private static final String TAG = "MyVeiw";
    public MyVeiw(Context context) {
        super(context);
    }

    public MyVeiw(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyVeiw(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: event: " + event.getAction());
        return super.onTouchEvent(event);
    }
}
