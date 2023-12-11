package com.example.myandroiddemo.java.proxy;

import android.app.Activity;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

public class ProxyHandler implements InvocationHandler {
    private static final String TAG = "ProxyHandler";
    private WeakReference<Activity> mHandlerRef;
    private HashMap<String, Method> mMethodHashMap = new HashMap<>();

    public ProxyHandler(Activity activity) {
        this.mHandlerRef = new WeakReference<>(activity);
    }

    public void mapMethod(String name, Method method) {
        mMethodHashMap.put(name, method);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.i(TAG, "invoke: method: " + method.getName() + " args = " + Arrays.toString(args));
        Activity activity = mHandlerRef.get();
        if (activity == null) return null;
        Method realMethod = mMethodHashMap.get(method.getName());
        if (realMethod != null) {
            return realMethod.invoke(activity,args);
        }
        return null;
    }
}
