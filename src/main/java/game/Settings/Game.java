package game.Settings;


import java.util.Scanner;

public class Game {

    Map map;
    Time time;
    Thread timer;
    SingletonPlayer player = SingletonPlayer.INSTANCE;

    public Game() {
        map = new Map();
        //    time = new Time();
       // timer = new Thread(time);
    }

    private void startGame() {
        System.out.print("=========================Game starts========================");

        Scanner guts = new Scanner(System.in);
        String command = guts.nextLine().toLowerCase();
        String[] words = command.toLowerCase().split(" ");

        // if command -> look, check, open, trade. while no lights by flashlight or room lights switch then print its dark.
        if (command.equals("look") || command.equals("check") ||
                command.equals("open") || command.equals("trade") ||
                (words.length > 1 && words[0].equals("use") && !words[1].equals("flashlight"))) {
            if (!player.isIlluminated()) {

            }
        }

        switch (command.toLowerCase()) {
            case "look" -> System.out.println(player.look());
            case "check" -> System.out.println(player.check());
            case "open" -> System.out.println(player.open());
            case "switchlights" -> System.out.println(player.switchLights());
            case "forward" -> System.out.println(player.move("forward"));
            case "backward" -> System.out.println(player.move("backward"));
            case "playerstatus" -> {
                time.time();
                player.playerStatus();
            }
            case "left" -> System.out.println(player.orientate("left"));
            case "right" -> System.out.println(player.orientate("right"));
            case "trade" -> System.out.println(player.tradeMode());
            default -> {
                if ((words.length == 3 && words[0].equals("use") && words[2].equals("key"))
                        || (words.length == 2 && words[0].equals("use") && words[1].equals("flashlight"))) {
                    System.out.println(player.use(words[1]));
                } else {
                    System.out.println("Invalid command please, write one of these commands -> [ look , check , open , use flashlight , use <name> key , switchlights , trade , quit , restart ]");
                }
            }
        }
    }

}