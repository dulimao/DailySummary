package com.example.autoservicemoduleb;


import com.example.autoserviceinterfacemodule.IDisplay;
import com.google.auto.service.AutoService;

@AutoService(IDisplay.class)
public class DisplayB implements IDisplay {
    private static final String TAG = "DisplayB";
    @Override
    public String display() {
        return "display B";
    }

    //必须要有公共的无参构造函数
//    private DisplayB() {
//
//    }
}
