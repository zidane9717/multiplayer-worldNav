package game.settings;

import game.initiate.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class PlayersManagement {

    HashMap<String,Player> players = new HashMap<>();
    private final ArrayList<String> bookedRooms = new ArrayList();

    public void addPlayer(String name){
        Player player = new Player(name);
        generateCoordinates(player);
        players.put(name,player);
    }

    private void generateCoordinates(Player player) {
        while (true) {
            int x = ThreadLocalRandom.current().nextInt(0, 5 + 1);
            int y = ThreadLocalRandom.current().nextInt(0, 8 + 1);
            if(!bookedRooms.contains(String.valueOf(y+x))){
                player.setCoordinates(y,x);
                bookedRooms.add(String.valueOf(y+x));
                break;
            }
        }
    }

    public String managePlayerCommand(String name,String command){
       return Game.processCommand(players.get(name),command);
    }
}
