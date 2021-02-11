package game.settings;

import game.initiate.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class PlayersManagement {

    HashMap<String, Player> players = new HashMap<>();
    private final ArrayList<String> bookedRooms = new ArrayList();

    public void addPlayer(String name) {
        Player player = new Player(name);
        generateCoordinates(player);
        players.put(name, player);
    }

    private void generateCoordinates(Player player) {
        while (true) {
            int x = ThreadLocalRandom.current().nextInt(0, 8 + 1);
            int y = ThreadLocalRandom.current().nextInt(0, 5 + 1);

            if((y == 0 && x == 0) || (y == 0 && x == 8) || (y == 5 && x == 0) || (y == 5 && x == 8)){
                continue;
            }

            if (!bookedRooms.contains(String.valueOf(y + x))) {
                player.setCoordinates(y, x);
                bookedRooms.add(String.valueOf(y + x));
                break;
            }
        }
    }

    public  String processCommand(String name, String command) {
        Player player = players.get(name);
        String[] words = command.toLowerCase().split(" ");

        // if command -> look, check, open, trade. while no lights by flashlight or room lights switch then print its dark.
        if (command.equals("look") || command.equals("check") ||
                command.equals("open") || command.equals("trade") ||
                (words.length > 1 && words[0].equals("use") && !words[1].equals("flashlight"))) {
            if (!player.checkRoomLightning()) {
                return "dark use flashlight or switchlights on";
            }
        }

        switch (command.toLowerCase()) {
            case "look" -> {
                return player.look();
            }
            case "check" -> {
                return player.check();
            }
            case "open" -> {
                return player.open();
            }
            case "switchlights" -> {
                return player.switchLights();
            }
            case "forward" -> {
                return player.move("forward");
            }
            case "backward" -> {
                return player.move("backward");
            }
            case "playerstatus" -> {
               return player.playerStatus();
            }
            case "left" -> {
                return player.orientate("left");
            }
            case "right" -> {
                return player.orientate("right");
            }
            case "trade" -> {
                return player.tradeMode();
            }
            default -> {
                if ((words.length == 3 && words[0].equals("use") && words[2].equals("key"))
                        || (words.length == 2 && words[0].equals("use") && words[1].equals("flashlight"))) {
                    return player.use(words[1]);
                } else {
                    return "Invalid command";
                }
            }
        }
    }
}
