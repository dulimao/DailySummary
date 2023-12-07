package com.example.autoservicemodule;

import android.util.Log;

import com.example.autoserviceinterfacemodule.IDisplay;
import com.google.auto.service.AutoService;

@AutoService(IDisplay.class)
public class DisplayA implements IDisplay {
    private static final String TAG = "DisplayA";
    
    @Override
    public String display() {
        return "display: A";
    }
}
