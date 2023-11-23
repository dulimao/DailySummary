package com.example.myandroiddemo.view.autospec;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AutoSpecFrameLayout extends FrameLayout {

    private final AutoSpecHelper mAutoSpecHelper;

    public AutoSpecFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public AutoSpecFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoSpecFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mAutoSpecHelper = AutoSpecHelper.create(this, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mAutoSpecHelper.onMeasure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(mAutoSpecHelper.mWidthMeasureSpec, mAutoSpecHelper.mHeightMeasureSpec);
    }
}
