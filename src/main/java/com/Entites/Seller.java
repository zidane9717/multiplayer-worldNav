package com.Entites;


import com.Items.Item;
import com.Settings.Gold;
import com.Settings.SingletonPlayer;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;

public class Seller extends Entity {

    private HashMap<String, Item> sellerItems; //Seller's items no one should get access to it.

    Seller(String name, int available,EntityType entityType, HashMap items) {
        setName(name);
        setState(available);
        setEntityType(entityType);
        sellerItems = items;
    }

    public String confirmPayment(String item) {
        Gold itemPrice = sellerItems.get(item).getPrice();
        SingletonPlayer player = SingletonPlayer.INSTANCE;

        player.inventory.put(item, sellerItems.get(item));
        player.gold = player.gold.subtract(itemPrice.getValue());
        return "<purchased " + item + ">";
    }

    public String sell(Item item) {
        BigDecimal itemPrice = item.getPrice().getValue();
        SingletonPlayer player = SingletonPlayer.INSTANCE;

        player.inventory.remove(item.getName());
        player.gold = player.gold.add(itemPrice, new MathContext(4));
        return "<" + item.getName() + " sold>";
    }

    public void list() {
        System.out.println("=====Seller's store======");
        System.out.println("Item:             Price:");
        for (String item : sellerItems.keySet()) {
            System.out.println(item + "         " + sellerItems.get(item).getPrice().getValue().intValue());
        }
        System.out.println("========================");
    }

    public boolean haveThisItem(String item) {
        return sellerItems.containsKey(item);
    }

    public boolean enoughMoneyToTrade(String item) {
        SingletonPlayer player = SingletonPlayer.INSTANCE;

        Gold itemPrice = sellerItems.get(item).getPrice();
        return player.gold.intValue() >= itemPrice.getValue().intValue();
    }

    @Override
    public String look() {
        return "a strange man looks like a seller...he calls him self " + getName();
    }
}

