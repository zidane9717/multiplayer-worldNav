package com.Entites;

import com.Items.Item;
import com.Settings.SingletonPlayer;

import javax.management.InstanceAlreadyExistsException;
import javax.print.DocFlavor;

public class Decor extends Entity implements CheckableEntity {

    static Decor instanceA;
    static Decor instanceB;
    private Item item;

    public enum DecorType {
        PAINTING, MIRROR;
    }

    private Decor(){}

    private Decor(DecorType decorType){
        setName(String.valueOf(decorType));
    }

    Decor(DecorType decorType, Item item) {
        setName(String.valueOf(decorType));
        this.item = item;
    }

    public static Entity getInstance(int i) {
        if(i==1){
            if(instanceA==null){
                instanceA = new Decor(DecorType.MIRROR);
            }
            return instanceA;
        }
        if (instanceB == null) {
            instanceB = new Decor(DecorType.PAINTING);
        }
        return instanceB;
    }

    public void setItem(Item item){
        this.item = item;
    }

    @Override
    public String look() {
        switch (getName()) {
            case "PAINTING" -> {
                return "<Painting>";
            }

            case "MIRROR" -> {
                return "<You See a silhouette of you>";
            }
        }
        return "nothing";
    }

    @Override
    public String check() {
        if (getState() == 1) {
            SingletonPlayer player = SingletonPlayer.INSTANCE;
            player.inventory.put(item.getName(), item);
            String print = "it seems like there is something behind the " + getName() + ".. <The '" + item.getName() + "'key was acquired>";
            item = null;
            setState(0);
            return print;
        }
        return "nothing behind it..";
    }

}
