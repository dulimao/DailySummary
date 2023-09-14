package com.example.myandroiddemo;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gaozheyuan on 2018/7/24.
 */

public class SpeakerSharedPreferencesFactory {

    public static final String DEFAULT_SHAREPREFERENCE_NAME = "default_sharePreference";


    public final static String FOLLOW_SYSTEM = "follow_system";
    public final static String NIGHT_MODE_MANUAL_SETTING = "night_mode_manual_setting";

    public static void setNightModeFollowSystem(Context context, boolean follow) {
        if (context == null) {
            return;
        }
        SharedPreferences sp = context.getApplicationContext()
                .getSharedPreferences(DEFAULT_SHAREPREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(FOLLOW_SYSTEM, follow);
        editor.commit();
    }

    public static boolean getNightModeFollowSystem(Context context) {
        SharedPreferences sp = context.getApplicationContext()
                .getSharedPreferences(DEFAULT_SHAREPREFERENCE_NAME, MODE_PRIVATE);
        return sp.getBoolean(FOLLOW_SYSTEM, false);
    }

    //深色模式手动开关状态
    public static void setNightModeByManual(Context context, boolean isNight) {
        if (context == null) {
            return;
        }
        SharedPreferences sp = context.getApplicationContext()
                .getSharedPreferences(DEFAULT_SHAREPREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(NIGHT_MODE_MANUAL_SETTING, isNight);
        editor.commit();
    }

    public static boolean getNightModeByManual(Context context) {
        SharedPreferences sp = context.getApplicationContext()
                .getSharedPreferences(DEFAULT_SHAREPREFERENCE_NAME, MODE_PRIVATE);
        return sp.getBoolean(NIGHT_MODE_MANUAL_SETTING, false);
    }


}
