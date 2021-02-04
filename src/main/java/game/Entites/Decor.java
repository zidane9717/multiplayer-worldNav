package game.Entites;

import game.Items.Item;
import game.Settings.SingletonPlayer;

public class Decor implements Entity {

    public enum DecorType {
        PAINTING, MIRROR;
    }

    private Item item;
    private DecorType type;

    private Decor() {
    }

    Decor(DecorType decorType, Item item) {
        type = decorType;
        this.item = item;
    }

    @Override
    public String look() {
        if (type == DecorType.PAINTING) {
            return "<Painting>";
        }
        return "<You See a silhouette of you>";
    }

    public String check() {
        if (item !=null) {
            SingletonPlayer player = SingletonPlayer.INSTANCE;
            player.inventory.put(item.getName(), item);
            String print = "<The '" + item.getName() + "' was acquired>";
            item = null;
            return print;
        }
        return "<nothing>";
    }

}
