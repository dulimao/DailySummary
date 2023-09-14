package com.gxa.car.splitscreen.view.ac;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class MyListView extends RecyclerView {
    private static final String TAG = "MyListView";
    public MyListView(@NonNull Context context) {
        super(context);
    }

    public MyListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        Log.i(TAG, "onTouchEvent: e: "+ e.getAction());
        return super.onTouchEvent(e);
    }


    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        return false;
    }
}
