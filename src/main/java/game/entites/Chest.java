package game.entites;

import game.items.Item;
import game.settings.Gold;
import game.settings.Player;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

public class Chest implements Entity,CheckableEntity {

    private List items;
    private String name;
    private int state;
    Player player;

    public Chest(String name, int state, List items) {
        this.name=name;
        this.state=state;
        this.items = items;
    }

    @Override
    public String look() {
        return "<the " + name + " chest>";
    }

    @Override
    public String check(Player player) {
        if (state == 0) {
            return "<Chest closed '" + name + "' key is needed to unlock>";
        }

        if (items.isEmpty()) {
            return "<Chest is clear>";
        }

        System.out.print("<Items are looted: ");
        for (Object chestLoot : items) {
            if (chestLoot instanceof Gold) {

                BigDecimal chestLootVal = ((Gold) chestLoot).getValue();

                this.player.gold = this.player.gold.add(chestLootVal, new MathContext(4));
                System.out.print("[" + ((Gold) chestLoot).getValue() + " gold] ");
            } else if (chestLoot instanceof Item) {
                this.player.inventory.put(((Item) chestLoot).getName(), (Item) chestLoot);
                System.out.println("[The " + ((Item) chestLoot).getName() + "] ");
            }
        }
        List itemsEmpty = new ArrayList();

        items = itemsEmpty;
        return "";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public void setState(int state) {
        this.state= state;
    }
}
