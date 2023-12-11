package com.example.myandroiddemo.java.proxy;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ViewUtils {
    private static final String TAG = "ViewUtils";

    public static void injectEvent(Activity activity) {
        if (activity == null) {
            return;
        }

        try {
            Class<? extends Activity> activityClass = activity.getClass();
            Method[] activityMethods = activityClass.getDeclaredMethods();
            for (Method activityMethod : activityMethods) {
                if (activityMethod.isAnnotationPresent(OnClick.class)) {
                    OnClick onClickAnnotation = activityMethod.getAnnotation(OnClick.class);
                    int[] viewIds = onClickAnnotation.value();
                    EventType annotation = onClickAnnotation.annotationType().getAnnotation(EventType.class);
                    Log.i(TAG, "injectEvent: annotationType: " + onClickAnnotation.annotationType());
                    Class listenerClass = annotation.listenerType();
                    String listenerSetter = annotation.listenerSetter();
                    String methodName = annotation.methodName();
                    ProxyHandler proxyHandler = new ProxyHandler(activity);
                    Object listener = Proxy.newProxyInstance(listenerClass.getClassLoader(), new Class[]{listenerClass}, proxyHandler);
                    proxyHandler.mapMethod(methodName,activityMethod);

                    for (int viewId : viewIds) {
                        Method findViewByIdMethod = activityClass.getMethod("findViewById", int.class);
                        findViewByIdMethod.setAccessible(true);
                        Object view = findViewByIdMethod.invoke(activity, viewId);
                        Method setClickListenerMethod = view.getClass().getMethod(listenerSetter,listenerClass);
                        setClickListenerMethod.invoke(view,listener);
                    }

                    //代理对象：activity，方法：onClickView
                    //被代理对象：View，被代理方法：onClick()
    //                view.setonclicklistner{
    //                    onclick() {
    //
    //                    }
    //                }
                }
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


    public static void injectView(Activity activity) {
        if (activity == null) return;
        try {
            Class<? extends Activity> activityClass = activity.getClass();
            Field[] fields = activityClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(InjectView.class)) {
                    InjectView injectView = field.getAnnotation(InjectView.class);
                    int viewId = injectView.value();
                    Method findViewByIdMethod = activityClass.getDeclaredMethod("findViewById", int.class);
                    findViewByIdMethod.setAccessible(true);
                    View view = (View) findViewByIdMethod.invoke(activity, viewId);
                    field.setAccessible(true);
                    field.set(activity,view);
                    field.setAccessible(false);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
