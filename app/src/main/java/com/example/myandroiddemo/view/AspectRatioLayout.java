package com.example.myandroiddemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.myandroiddemo.R;

public class AspectRatioLayout extends RelativeLayout {
    private static final String TAG = "AspectRatioLayout";
    private float aspectRatio = 1.2f;

    public AspectRatioLayout(Context context) {
        super(context);
    }

    public AspectRatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public void init(AttributeSet attrs) {
//        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.AspecRatio);
//        aspectRatio = typedArray.getFloat(R.styleable.AspecRatio_ratio,1f);
//        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "onMeasure: ================================================");
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int measureWidth = getMeasuredWidth();
        int measureHeight = getMeasuredHeight();
        int widthG = getWidth();
        int heightG = getHeight();
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int calculateHeight = (int) (measureWidth / aspectRatio);

        //1、确定大小
        //2、确定位置
        //3、自由发挥绘画天赋


        Log.i(TAG, "onMeasure: measureWidth: " + measureWidth + " measureHeight: " + measureHeight + " widthG : " + widthG+ " heightG : " + heightG);
        Log.i(TAG, "onMeasure: width: " + width + " height: " + height);
        setMeasuredDimension(measureWidth,calculateHeight);
        widthG = getWidth();
        heightG = getHeight();
         measureWidth = getMeasuredWidth();
         measureHeight = getMeasuredHeight();
        Log.i(TAG, "onMeasure: --------------------------------calculateHeight: " + calculateHeight);
        Log.i(TAG, "onMeasure: measureWidth: " + measureWidth + " measureHeight: " + measureHeight + " widthG : " + widthG + " heightG : " + heightG);

        Log.i(TAG, "onMeasure: ================================================");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
    }


    //    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            int padding = 100;
            childAt.layout(padding,padding,childAt.getWidth() / 2 + padding,childAt.getHeight() + padding);
        }
        Log.i(TAG, "onLayout: l: " + l + " t: " + t + " r: " + r + " b: " + b);
        Log.i(TAG, "onLayout: width: " + getWidth() + " height: " + getHeight());




    }


}
