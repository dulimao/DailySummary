package com.example.webview;

import android.content.Context;
import android.util.Log;

import com.example.common.IWebViewService;

public class WebViewServiceImpl implements IWebViewService {
    private static final String TAG = "WebViewServiceImpl";
    @Override
    public void startWebActivity(Context context, String title, String url) {
        Log.i(TAG, "startWebActivity: title: " + title + " url: " + url);
    }
}
