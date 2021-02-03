package com.Items;

import com.Settings.Gold;

public class FlashLight extends Item {

    private int state;

    FlashLight(Gold price, String flashlight) {
        setState(0);                //Default state is OFF
        setName("flashlight");
        setPrice(price);
        setItemType(flashlight);
    }

    public int getState() {
        return state;
    }

    private void setState(int state) {
        this.state = state;
    }

    @Override
    public String use() {
        if (state == 0) {
            setState(1);
            return "<Flash light is ON>";
        }
        setState(0);
        return "<Flash light is OFF>";
    }

}
