package com.gxa.car.splitscreen.view.ac;

import android.content.Context;
import android.database.Observable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

public class CategoryTagListView extends HorizontalScrollView {
    private static final String TAG = "CategoryTagListView";
    private FlowRadioGroup mTagContainerLayout;
    public Adapter mAdapter;
    public CategoryTagListView(Context context) {
        super(context);
        init();
    }

    public CategoryTagListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CategoryTagListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
        mAdapter.registerAdapterDataObserver(new AdapterDataObserver() {
            @Override
            public void onChanged() {
                notifyDataSetChanged();
            }
        });
    }

    private void init() {
        setHorizontalScrollBarEnabled(false);
        mTagContainerLayout = new FlowRadioGroup(getContext());
        mTagContainerLayout.setLayoutParams(new RadioGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT));
        mTagContainerLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(mTagContainerLayout);
    }

    public void notifyDataSetChanged() {
        mTagContainerLayout.removeAllViews();
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            RadioButton view = mAdapter.getItemView(mTagContainerLayout,i);
            mTagContainerLayout.addView(view);
            mAdapter.bindData(view,i);
        }
        mTagContainerLayout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.i(TAG, "onCheckedChanged: i: "+ i);
                mAdapter.onCheckChanged(i - 1);
            }
        });
    }

    static class AdapterDataObservable extends Observable<AdapterDataObserver> {
        public void notifyChanged() {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onChanged();
            }
        }
    }

    public abstract static class AdapterDataObserver {
        public abstract void onChanged();
    }

    public static abstract class Adapter<T> {
        private final AdapterDataObservable mObservable = new AdapterDataObservable();
        public CheckChangedListener<T> checkChangedListener;
        public abstract RadioButton getItemView(View parent,int position);
        public abstract void bindData(RadioButton itemView,int position);
        public abstract int getItemCount();
        public abstract void onCheckChanged(int position);
        public final void notifyDataSetChanged() {
            mObservable.notifyChanged();
        }

        public void registerAdapterDataObserver(@NonNull AdapterDataObserver observer) {
            mObservable.registerObserver(observer);
        }

        public void setCheckChangedListener(CheckChangedListener<T> checkChangedListener) {
            this.checkChangedListener = checkChangedListener;
        }

        interface CheckChangedListener<T> {
            void onCheckChanged(T t);
        }
    }
}
