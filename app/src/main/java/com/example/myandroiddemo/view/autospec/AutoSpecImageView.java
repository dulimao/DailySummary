package com.example.myandroiddemo.view.autospec;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class AutoSpecImageView extends AppCompatImageView {


    private final AutoSpecHelper mAutoSpecHelper;

    public AutoSpecImageView(Context context) {
        this(context, null);
    }

    public AutoSpecImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoSpecImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mAutoSpecHelper = AutoSpecHelper.create(this, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mAutoSpecHelper.onMeasure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(mAutoSpecHelper.mWidthMeasureSpec, mAutoSpecHelper.mHeightMeasureSpec);
    }
}
