package game.Settings;

import game.Entites.CheckableEntity;
import game.Entites.Door;
import game.Entites.Entity;
import game.Entites.Seller;
import game.Items.FlashLight;
import game.Items.Item;
import game.Rooms.Room;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Scanner;

public class SingletonPlayer {

    static SingletonPlayer instance;
    public HashMap<String, Item> inventory = new HashMap<>();
    public BigDecimal gold = Gold.ZERO.getValue();
    public String looking = "east";
    int y;
    int x;

    private SingletonPlayer(){}

    public static SingletonPlayer getInstance(){
        if(instance==null){
            return instance = new SingletonPlayer();
        }
        return instance;
    }

    public void setCoordinates(int x , int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public String look() { //Writing to interface Entity
        return currentRoom().wallAt(looking).look();
    }

    public String check() {
        Entity entity = currentRoom().wallAt(looking);
        if (entity instanceof CheckableEntity) {
            return ((CheckableEntity) entity).check();
        }
        return "it is " + entity.look() + ", not something i can check";
    }

    public String use(String item) {
        if (inventory.containsKey(item)) {
            return inventory.get(item).use();
        }
        return "i don't have such an item..";
    }

    public String open() {

        if (currentRoom().wallAt(looking) instanceof Door) {
            Door door = (Door) currentRoom().wallAt(looking);
            if (door.getState() == 1) {
                return "<door is open>";
            } else {
                return "<" + door.getName() + " key is needed>";
            }
        }
        return "it is not something i can open";
    }

    public String switchLights() {
        if (currentRoom().getIllumination() == 1) {
            currentRoom().setIllumination(0);
            return "<Lights off>";
        } else if (currentRoom().getIllumination() == 0) {
            currentRoom().setIllumination(1);
            return "<Lights on>";
        } else {
            return "<Switch lights are jammed..>";
        }
    }

    public String orientate(String direction) {
        if (direction.equals("left")) {
            switch (looking) {
                case "north" -> looking = "west";
                case "west" -> looking = "south";
                case "south" -> looking = "east";
                case "east" -> looking = "north";
            }
        } else if (direction.equals("right")) {
            switch (looking) {
                case "north" -> looking = "east";
                case "east" -> looking = "south";
                case "south" -> looking = "west";
                case "west" -> looking = "north";
            }
        }

        return "<You are looking " + looking + " now>";
    }

    public String move(String direction) {
        Entity entity = currentRoom().wallAt(looking);
        String wayToGo = looking;

        if (entity instanceof Door) { //Check if it is a door

            Door door = (Door) entity;

            if (direction.equals("backward")) {
                wayToGo = viceDirection();
            }

            if (door.getState() == 1) { // Check if it is open
                assert wayToGo != null;
                doMove(wayToGo);
                return "<Entered the " + currentRoom().getRoomName() + " room>";
            }
        }
        return "no place to move.";
    }

    private void doMove(String direction) {
        switch (direction) {
            case "east" -> x++;
            case "west" -> x--;
            case "north" -> y--;
            case "south" -> y++;
        }
    }

    public String playerStatus() {
       String currentRoomName = currentRoom().getRoomName();
       return "Looking : " + looking +"\n"+
               "      Gold : " + gold +"\n"+
               "      Current room : " + currentRoomName +"\n"+
               "Keys & items: " + inventory.keySet().toString()+"\n"+
               "===============";
    }

    public Room currentRoom() {
        return Map.roomAt(y, x);
    }

    private String validatePayment(String item) {
        Seller seller = (Seller) currentRoom().wallAt(looking);

        if (seller.haveThisItem(item)) {
            if (seller.enoughMoneyToTrade(item)) {
                return seller.confirmPayment(item);
            } else {
                return "'get back when you have enough money'..said the seller";
            }
        } else {
            return "'i don't have whatever you're asking for'..said the seller";
        }
    }

    private String validateSelling(String item) {

        Seller seller = (Seller) currentRoom().wallAt(looking);
        if (inventory.containsKey(item)) {
            return seller.sell(inventory.get(item));
        } else {
            return "i don't have such item to sell..";
        }
    }

    private String viceDirection() {
        switch (looking) {
            case "east" -> {
                return "west";
            }
            case "west" -> {
                return "east";
            }
            case "north" -> {
                return "south";
            }
            case "south" -> {
                return "north";
            }
        }
        return null;
    }

    public String tradeMode() {

        if (currentRoom().wallAt(looking) instanceof Seller) {
            TextInterface.tradeModeMessage();

            Scanner guts = new Scanner(System.in);
            int counter = 0;

            while (true) {
                String[] words = guts.nextLine().split(" ");
                if (words.length == 2 && words[0].equals("buy")) {
                    System.out.println(validatePayment(words[1]));
                } else if (words.length == 3 && words[0].equals("sell")) {
                    System.out.println(validateSelling(words[1]));
                } else if (words[0].equals("list")) {
                    Seller seller = (Seller) currentRoom().wallAt(looking);
                    seller.list();
                } else if (words[0].equals("finish")) {
                    return "<trading finished>";
                } else if (words[0].equals("playerstatus")) {
                    playerStatus();
                } else {
                    System.out.println("'what do you want'..said the seller");

                    counter++;
                    if (counter > 4) {
                        System.out.println("<Remember you still in the trading mode..write 'finish' if you would like to close trading mode>");
                        counter = 0;
                    }
                }
            }
        }
        return "I'm not facing anyone that i can trade with..";
    }

    boolean checkRoomLightning() {
        SingletonPlayer player = SingletonPlayer.getInstance();

        //if lights switch in the room are off or jammed -> check if flashlight is owned and on
        if (player.currentRoom().getIllumination() == 0 || player.currentRoom().getIllumination() == 3) {
            if (player.inventory.containsKey("flashlight")) {
                FlashLight f = (FlashLight) player.inventory.get("flashlight");
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
}

