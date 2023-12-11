package com.example.common;

import java.util.Iterator;
import java.util.ServiceLoader;

public class AutoServiceTool {


    public static <T> T load(Class<T> clazz) {
        ServiceLoader<T> load = ServiceLoader.load(clazz);
        Iterator<T> iterator = ServiceLoader.load(clazz).iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }



}
