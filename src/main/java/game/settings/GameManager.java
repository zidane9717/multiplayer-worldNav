package game.settings;

import game.playerSystem.Player;
import messagingstompwebsocket.Greeting;
import messagingstompwebsocket.GreetingController;

import java.util.HashMap;

public class GameManager {

    public static HashMap<String,Game> games = new HashMap<>();

    private static GameManager instance;

    private GameManager(){}

    public static GameManager getInstance(){
        if(instance == null){
           return instance = new GameManager();
        }
        return instance;
    }

    public  boolean checkGame(String number){
        return games.containsKey(number);
    }

    public  Game getGame(String number){
        return games.get(number);
    }

    public  void addGame(String number, Game game){
        games.put(number,game);
    }

    public  void removeGame(String number){
        games.remove(number);
    }

    public void createGame(String number) {
        games.put(number,new Game(number));
    }

    public void wonTheGame(Player player,String number){
        GreetingController greetingController = GreetingController.getInstance();
        try {
            greetingController.sendToClients(number,"CONGRATULATIONS to "+player.getName()+" HAS WON THE GAME");
        } catch (Exception e) {
            e.printStackTrace();
        }
        games.remove(number,games.get(number));
    }
}
