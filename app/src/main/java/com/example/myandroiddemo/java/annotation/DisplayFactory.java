package com.example.myandroiddemo.java.annotation;

import com.example.autoserviceinterfacemodule.IDisplay;

import java.util.Iterator;
import java.util.ServiceLoader;

public class DisplayFactory {
    private static DisplayFactory displayFactory;
    private final Iterator<IDisplay> mIterator;

    private DisplayFactory() {
        ServiceLoader<IDisplay> loader = ServiceLoader.load(IDisplay.class);
        mIterator = loader.iterator();

    }

    public static DisplayFactory getDisplayFactory() {
        if (displayFactory == null) {
            synchronized (DisplayFactory.class) {
                if (displayFactory == null) {
                    displayFactory = new DisplayFactory();
                }
            }
        }
        return displayFactory;
    }

    public IDisplay getDisplay() {
        return mIterator.next();
    }

    public boolean hasNextDisplay() {
        return mIterator.hasNext();
    }
}
