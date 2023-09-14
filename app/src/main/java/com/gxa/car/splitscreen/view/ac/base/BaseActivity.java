package com.gxa.car.splitscreen.view.ac.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.gxa.car.splitscreen.utils.SplitScreenUtils;

/**
 * @author Administrator
 */
public abstract class BaseActivity extends AppCompatActivity {
    public static final String PARENT_WINDOW_STATE_FLAG = "parent_window_is_full_screen";
    protected LayoutInflater mInflater;
    protected FrameLayout mParentLayout;

    private View mNormalView = null;
    private View mLargeView = null;

    private boolean mFullScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(
                getOrCreateParentView(),
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT)
        );
        mFullScreen = parentIsFullScreen(getIntent());
        changeWindowSizeIfNeed();
        notifyLayoutChanged();
    }

    private void changeWindowSizeIfNeed() {
        boolean isFullScreen = SplitScreenUtils.isFullScreen(this, getWindow().getAttributes());
        if (isFullScreen != mFullScreen) {
            SplitScreenUtils.switchWindowSize(getWindow(), mFullScreen, this);
        }
    }

    private ViewGroup getOrCreateParentView() {
        if (mParentLayout == null) {
            mParentLayout = new FrameLayout(this);
        }
        return mParentLayout;
    }

    private LayoutInflater getInflater() {
        if (mInflater == null) {
            mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        return mInflater;
    }

    /**
     * Get parameter from parent activity to calculate
     * whether it should display full screen
     *
     * @param intent Intent from parent
     * @return is full screen
     */
    private boolean parentIsFullScreen(Intent intent) {
        if (intent == null) {
            return false;
        }
        return intent.getBooleanExtra(PARENT_WINDOW_STATE_FLAG, false);
    }

    private View getContentView(boolean isFullScreen) {
        View view;
        if (isFullScreen) {
            if (mLargeView == null) {
                view = inflateViewIfNeed(getLargeLayoutId(), true);
            } else {
                view = mLargeView;
            }
        } else {
            if (mNormalView == null) {
                view = inflateViewIfNeed(getNormalLayoutId(), false);
            } else {
                view = mNormalView;
            }
        }
        Log.d("BaseActivity", "created view :" + view);
        return view;
    }

    private View inflateViewIfNeed(int layoutId, boolean isFullScreen) {
        View view = getInflater().inflate(layoutId, null, false);
        if (isFullScreen) {
            mLargeView = view;
        } else {
            mNormalView = view;
        }
        return view;
    }

    private void addViewToParentIfNeed(View view) {
        if (view == null) {
            return;
        }
        if (getOrCreateParentView().getChildCount() > 0) {
            getOrCreateParentView().removeAllViews();
        }
        getOrCreateParentView().addView(view);
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        super.onWindowAttributesChanged(params);
        boolean isFullScreen = SplitScreenUtils.isFullScreen(
                getApplicationContext(),
                params
        );
        if (isFullScreen != mFullScreen) {
            mFullScreen = isFullScreen;
            //此处根据界面是否全屏做布局适配，
            // 不建议整体布局替换(除非大屏和小屏控件位置变化很大)，
            // 尽量使用自适应布局
            // 注：如果不做布局缓存（减少inflate次数），可直接调用setContentView(isFullScreen ? getLargeLayoutId() ： getNormalLayoutId());
            notifyLayoutChanged();
        }
    }

    /**
     * 只是举个例子，不建议整体替换布局
     */
    private void notifyLayoutChanged() {
        addViewToParentIfNeed(getContentView(mFullScreen));
        onViewCreate(mFullScreen);
    }

    /**
     * get large layout id
     *
     * @return layout id such as : R.id.activity_main
     */
    public abstract int getLargeLayoutId();

    /**
     * get normal layout id
     *
     * @return layout id such as : R.id.activity_main
     */
    public abstract int getNormalLayoutId();

    /**
     * new view created
     *
     * @param isFullScreen full screen or not
     */
    public abstract void onViewCreate(boolean isFullScreen);
}
