package game.entites;

import game.items.Item;
import game.items.Key;
import game.playerSystem.Player;

public class Decor implements Entity, CheckableEntity {

    public enum DecorType {
        PAINTING, MIRROR;
    }

    private Item item;
    private DecorType type;
    private int state;

    private Decor() {
    }

    Decor(DecorType decorType, Item item) {
        type = decorType;
        this.item = item;
        setState(1);
    }

    @Override
    public String look() {
        if (type == DecorType.PAINTING) {
            return "Painting";
        }
        return "You See a silhouette of you";
    }

    @Override
    public String check(Player player) {
        if (item != null) {
            player.inventory.put(item.getName(), item);
            String print="";
            if (item instanceof Key) {
                 print = "The '" + item.getName() + "' key was acquired";
            } else {
                 print = "The '" + item.getName() + "' was acquired";
            }
            item = null;
            setState(0);
            return print;
        }
        return "nothing";
    }

    @Override
    public String getName() {
        return String.valueOf(type);
    }

    @Override
    public int getState() {

        return state;
    }

    @Override
    public void setState(int state) {
        this.state = state;
    }

}
