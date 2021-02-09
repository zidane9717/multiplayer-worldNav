package game.initiate;


import game.settings.Player;
import game.settings.Time;

public class Game {

    private boolean joinable;
    Map map;
    static Time time;
    Thread timer;

    public Game() {
        joinable=true;
        map = new Map();
        time = new Time();
        timer = new Thread(time);
    }

    public boolean getJoinable() {
        return joinable;
    }

    public void disableJoinable() {
        joinable = false;
    }

    public static String processCommand(Player player, String command) {
        System.out.print("=========================Game starts========================");

        String[] words = command.toLowerCase().split(" ");

        // if command -> look, check, open, trade. while no lights by flashlight or room lights switch then print its dark.
        if (command.equals("look") || command.equals("check") ||
                command.equals("open") || command.equals("trade") ||
                (words.length > 1 && words[0].equals("use") && !words[1].equals("flashlight"))) {
            if (!player.checkRoomLightning()) {
                   return "<dark> use flashlight or switchlights on";
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
                return time.time() + player.playerStatus();
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