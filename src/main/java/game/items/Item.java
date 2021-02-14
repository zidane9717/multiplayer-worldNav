package game.items;

import game.settings.Gold;
import game.playerSystem.Player;

public abstract class Item {

    private String name;
    private Gold price;

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

    public abstract String use(Player player);

}
