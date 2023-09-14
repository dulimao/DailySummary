package com.example.myandroiddemo.card;

import java.util.List;

public class CardBody {
    private int item_limit;
    private int no_limit;
    private CardStyle style;
    private List<Item> items;

    public int getItem_limit() {
        return item_limit;
    }

    public void setItem_limit(int item_limit) {
        this.item_limit = item_limit;
    }

    public int getNo_limit() {
        return no_limit;
    }

    public void setNo_limit(int no_limit) {
        this.no_limit = no_limit;
    }

    public CardStyle getStyle() {
        return style;
    }

    public void setStyle(CardStyle style) {
        this.style = style;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
