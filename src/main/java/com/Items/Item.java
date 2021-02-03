package com.Items;

import com.Settings.Gold;

public abstract class Item {

    private String name;
    private Gold price;
    private String itemType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gold getPrice() {
        return price;
    }

    public void setPrice(Gold price) {
        this.price = price;
    }

    public void setItemType(String type){this.itemType=type;}

    public abstract String use();

}
