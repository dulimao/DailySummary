package com.gxa.car.splitscreen.utils;

import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * @author Administrator
 */
public class SplitScreenUtils {
    public static void switchWindowSize(Window win, boolean isFullScreen, Context context) {
        try {
            Class<Window> cls = Window.class;
            Method method = cls.getDeclaredMethod("setFullScreen", Context.class, boolean.class);
            method.invoke(win, context.getApplicationContext(), isFullScreen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isFullScreen(Context context, WindowManager.LayoutParams params) {
        if (params == null || context == null) {
            return false;
        }
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        int screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        boolean is2K = screenWidth > 1920 || screenHeight > 1080;
        return is2K ? params.height > 1200 : params.height > 900;
    }
}
