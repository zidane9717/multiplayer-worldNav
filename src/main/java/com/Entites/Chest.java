package com.Entites;

import com.Items.Item;
import com.Settings.Gold;
import com.Settings.SingletonPlayer;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

public class Chest extends Entity implements CheckableEntity {

    private List items;

    public Chest(String name, int state, EntityType entityType, List items) {
        setName(name);
        setState(state);
        this.items = items;
        setEntityType(entityType);
    }

    @Override
    public String look() {
        return "<the " + getName() + " chest>";
    }

    @Override
    public String check() {
        if (getState() == 0) {
            return "<Chest closed '" + getName() + "' key is needed to unlock>";
        }

        if (items.isEmpty()) {
            return "<Chest is clear>";
        }

        SingletonPlayer player = SingletonPlayer.INSTANCE;
        System.out.print("<Items are looted: ");
        for (Object chestLoot : items) {
            if (chestLoot instanceof Gold) {

                BigDecimal chestLootVal = ((Gold) chestLoot).getValue();

                player.gold = player.gold.add(chestLootVal, new MathContext(4));
                System.out.print("[" + ((Gold) chestLoot).getValue() + " gold] ");
            } else if (chestLoot instanceof Item) {
                player.inventory.put(((Item) chestLoot).getName(), (Item) chestLoot);
                System.out.println("[The " + ((Item) chestLoot).getName() + "] ");
            }
        }
        List itemsEmpty = new ArrayList();

        items = itemsEmpty;
        return "";
    }

}
