package com.example.myandroiddemo;


import androidx.viewpager.widget.PagerAdapter;

/**
 * Created by cuiyan on 2018/7/9.
 */
public abstract class RealPagerAdapterImp extends PagerAdapter {
    public abstract int getRealCount();

    public abstract int getRealPosition(int pos);
}
