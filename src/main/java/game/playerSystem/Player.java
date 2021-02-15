package game.playerSystem;

import game.entites.CheckableEntity;
import game.entites.Door;
import game.entites.Entity;
import game.entites.Seller;
import game.items.FlashLight;
import game.items.Item;
import game.rooms.Room;
import mvc.controller.GameManager;
import game.settings.Map;
import game.settings.TextInterface;
import game.settings.Gold;


import java.math.BigDecimal;
import java.util.HashMap;

public class Player {

    private String name;
    public HashMap<String, Item> inventory = new HashMap<>();
    public BigDecimal gold = Gold.TWENTY.getValue();
    public PlayerNavigation nav;
    private String number;

    public Player(String name, String number) {
        this.number = number;
        this.name = name;
        nav = new PlayerNavigation(this, number);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String look() { //Writing to interface Entity
        return currentRoom().wallAt(nav.getLooking()).look();
    }

    public String check() {
        Entity entity = currentRoom().wallAt(nav.getLooking());
        if (entity instanceof CheckableEntity) {
            return ((CheckableEntity) entity).check(this);
        }
        return "it is " + entity.look() + ", not something i can check";
    }

    public String use(String item) {
        if (inventory.containsKey(item)) {
            return inventory.get(item).use(this);
        }
        return "i don't have such an item..";
    }

    public String open() {
        if (currentRoom().wallAt(nav.getLooking()) instanceof Door) {
            Door door = (Door) currentRoom(nav.getY(), nav.getX()).wallAt(nav.getLooking());
            if (door.getState() == 1) {
                return "door is open";
            } else {
                return door.getName() + " key is needed";
            }
        }
        return "it is not something i can open";
    }

    public String switchLights() {
        if (currentRoom().getIllumination() == 1) {
            currentRoom().setIllumination(0);
            return "Lights off";
        } else if (currentRoom().getIllumination() == 0) {
            currentRoom().setIllumination(1);
            return "Lights on";
        } else {
            return "Switch lights are jammed..";
        }
    }

    public String playerStatus() {
        String currentRoomName = currentRoom(nav.getY(), nav.getX()).getRoomName();
        return " Looking : " + nav.getLooking() + "\n" +
                "      Gold : " + gold + "\n" +
                "      Current room : " + currentRoomName + "\n" +
                "Keys & items: " + inventory.keySet().toString() + "\n" +
                "===============";
    }

    String validatePayment(String item) {
        if (currentRoom(nav.getY(), nav.getX()).wallAt(nav.getLooking()) instanceof Seller) {

            Seller seller = (Seller) currentRoom(nav.getY(), nav.getX()).wallAt(nav.getLooking());
            if (seller.haveThisItem(item)) {
                if (seller.enoughMoneyToTrade(item, gold)) {
                    return seller.confirmPayment(item, this);
                } else {
                    return "'get back when you have enough money'..said the seller";
                }
            } else {
                return "'i don't have whatever you're asking for'..said the seller";
            }
        }
        return "I'm not facing anyone that i can trade with..";
    }

    String validateSelling(String item) {
        if (currentRoom(nav.getY(), nav.getX()).wallAt(nav.getLooking()) instanceof Seller) {
            Seller seller = (Seller) currentRoom(nav.getY(), nav.getX()).wallAt(nav.getLooking());
            if (inventory.containsKey(item)) {
                return seller.sell(inventory.get(item), this);
            } else {
                return "i don't have such item to sell..";
            }
        }
        return "I'm not facing anyone that i can trade with..";
    }

    public String tradeMode() {
        if (currentRoom(nav.getY(), nav.getX()).wallAt(nav.getLooking()) instanceof Seller) {
            TextInterface.tradeModeMessage();
            return "you can : 'buy<Item>', 'sell<Item>', 'list' to see the sellers items";
        }
        return "I'm not facing anyone that i can trade with..";
    }

    public boolean checkRoomLightning() {

        //if lights switch in the room are off or jammed -> check if flashlight is owned and on
        if (this.currentRoom().getIllumination() == 0 || this.currentRoom().getIllumination() == 3) {
            if (this.inventory.containsKey("flashlight")) {
                FlashLight f = (FlashLight) this.inventory.get("flashlight");
                if (f.getState() == 0) {
                    System.out.println("i can't do anything, it's too dark..maybe i should use my flashlight");
                    return false;
                }
            } else {
                System.out.println("it's dark..i must switch the lights on or get a flashlight..");
                return false;
            }
        }
        return true;
    }

    public Room currentRoom(int y, int x) {
        GameManager managerGames = GameManager.getInstance();
        Map map = managerGames.getGame(number).getMap();
        return map.roomAt(y, x);
    }

    public Room currentRoom() {
        GameManager managerGames = GameManager.getInstance();
        Map map = managerGames.getGame(number).getMap();
        return map.roomAt(nav.getY(), nav.getX());
    }

    public String list() {
        if (currentRoom(nav.getY(), nav.getX()).wallAt(nav.getLooking()) instanceof Seller) {
            Seller seller = (Seller) currentRoom(nav.getY(), nav.getX()).wallAt(nav.getLooking());
            return seller.list();
        }
        return "you are not facing a seller";
    }
}

