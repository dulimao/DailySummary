package com.example.myandroiddemo.card;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Item {

    private JSONObject data;
    private String data_type;
    private CopyOnWriteArrayList<HashMap<String,String>> show;
    private ItemAction action;
    private ItemStype style;
    private int id;
    private int type;
    private String itemDataType;


    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public CopyOnWriteArrayList<HashMap<String, String>> getShow() {
        return show;
    }

    public void setShow(CopyOnWriteArrayList<HashMap<String, String>> show) {
        this.show = show;
    }


    public ItemAction getAction() {
        return action;
    }

    public void setAction(ItemAction action) {
        this.action = action;
    }

    public ItemStype getStyle() {
        return style;
    }

    public void setStyle(ItemStype style) {
        this.style = style;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getItemDataType() {
        return itemDataType;
    }

    public void setItemDataType(String itemDataType) {
        this.itemDataType = itemDataType;
    }
}
