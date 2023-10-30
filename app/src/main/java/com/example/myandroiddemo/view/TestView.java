package com.example.myandroiddemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TestView extends View {
    private static final String TAG = "TestView";

    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.i(TAG, "onMeasure: widthSize: " + widthSize + "  heightSize: " + heightSize);


        int dWidthSize,dHeightSize;
        if (widthMode == MeasureSpec.EXACTLY) {
            Log.i(TAG, "onMeasure: widthMode: EXACTLY");
            dWidthSize = widthSize;
        }  else if (heightMode == MeasureSpec.AT_MOST) {
            Log.i(TAG, "onMeasure: widthMode: AT_MOST ");
            dWidthSize = 100;
        } else {
            Log.i(TAG, "onMeasure: widthMode: ELSE");
            dWidthSize = 200;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            Log.i(TAG, "onMeasure: heightMode: EXACTLY");
            dHeightSize = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            Log.i(TAG, "onMeasure: heightMode: AT_MOST");
            dHeightSize = 300;
        } else {
            Log.i(TAG, "onMeasure: heightMode: ELSE");
            dHeightSize = 100;
        }

        setMeasuredDimension(dWidthSize,dHeightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
    }
}
