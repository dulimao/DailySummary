package com.example.myandroiddemo.view.autospec;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.myandroiddemo.R;


class AutoSpecHelper {
    private View mView;
    private float mSpec;
    private int mMaxWidth;
    private int mMaxHeight;

    int mWidthMeasureSpec;
    int mHeightMeasureSpec;
    private int mPaddingWidth;
    private int mPaddingHeight;

    private AutoSpecHelper() {

    }

    public static AutoSpecHelper create(View view, AttributeSet attributeSet) {
        TypedArray a = attributeSet == null ? null : view.getContext().obtainStyledAttributes(attributeSet, R.styleable.AutoSpec);
        float w = a == null ? 0 : a.getDimensionPixelSize(R.styleable.AutoSpec_auto_design_width, 0);
        float h = a == null ? 0 : a.getDimensionPixelSize(R.styleable.AutoSpec_auto_design_height, 0);
        float maxW = a == null ? 0 : a.getDimensionPixelSize(R.styleable.AutoSpec_auto_max_width, -1);
        float maxH = a == null ? 0 : a.getDimensionPixelSize(R.styleable.AutoSpec_auto_max_height, -1);
        float paddingWidth = a == null ? 0 : a.getDimensionPixelSize(R.styleable.AutoSpec_auto_padding_width, 0);
        float paddingHeight = a == null ? 0 : a.getDimensionPixelSize(R.styleable.AutoSpec_auto_padding_height, 0);
        if (a != null) {
            a.recycle();
        }
        float spec = w == 0 || h == 0 ? 0 : w / h;
        AutoSpecHelper helper = new AutoSpecHelper();
        helper.mSpec = spec;
        helper.mView = view;
        helper.mMaxWidth = (int) maxW;
        helper.mMaxHeight = (int) maxH;
        helper.mPaddingWidth = (int) paddingWidth;
        helper.mPaddingHeight = (int) paddingHeight;
        return helper;
    }

    void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidthMeasureSpec = widthMeasureSpec;
        mHeightMeasureSpec = heightMeasureSpec;
        ViewGroup.LayoutParams layoutParams = mView.getLayoutParams();
        if (mSpec <= 0 || layoutParams == null) {
            return;
        }
        int widthPadding = mView.getPaddingLeft() + mView.getPaddingRight();
        int heightPadding = mView.getPaddingTop() + mView.getPaddingBottom();
        if (shouldAdjust(layoutParams.height)) {
            int widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec);
            if (mMaxWidth > 0 && widthSpecSize > mMaxWidth) {
                widthSpecSize = mMaxWidth;
                mWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(widthSpecSize, View.MeasureSpec.getMode(widthMeasureSpec));
            }
            int desiredHeight = (int) ((widthSpecSize - widthPadding - mPaddingWidth) / mSpec + heightPadding + mPaddingHeight);
            int resolvedHeight = View.resolveSize(desiredHeight, heightMeasureSpec);
            mHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(resolvedHeight, View.MeasureSpec.EXACTLY);
        } else if (shouldAdjust(layoutParams.width)) {
            int heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec);
            if (mMaxHeight > 0 && heightSpecSize > mMaxHeight) {
                heightSpecSize = mMaxHeight;
                mHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(heightSpecSize, View.MeasureSpec.getMode(heightMeasureSpec));
            }
            int desiredWidth = (int) ((heightSpecSize - heightPadding - mPaddingHeight) * mSpec + widthPadding + mPaddingWidth);
            int resolvedWidth = View.resolveSize(desiredWidth, widthMeasureSpec);
            mWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(resolvedWidth, View.MeasureSpec.EXACTLY);
        }
    }

    private static boolean shouldAdjust(int layoutDimension) {
        // Note: wrap_content is supported for backwards compatibility, but should not be used.
        return layoutDimension == 0 || layoutDimension == ViewGroup.LayoutParams.WRAP_CONTENT;
    }

}
