package com.example.myandroiddemo.view.viewpager2;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.gala.video.component.widget.BlocksView;

public class MyBlocksView extends BlocksView {
    private static final String TAG = "MyBlocksView";
    public MyBlocksView(Context context) {
        super(context);
    }

    public MyBlocksView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(TAG, "dispatchTouchEvent: " + event.getAction());
        return super.dispatchTouchEvent(event);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: " + event.getAction());
        return super.onTouchEvent(event);
    }
}
