package game.initiate;


import game.settings.PlayersManagement;
import game.settings.Time;
import messagingstompwebsocket.GreetingController;

public class Game {

    final Map map;
    static Time time;
    Thread timer;
    private boolean joinable;
    private boolean gameStatus;
    private final PlayersManagement manager = new PlayersManagement();
    static Game instance;


    public Game(){
        instance=this;
        gameStatus=false;
        joinable=true;
        map = new Map();
    }

    public static Game getInstance(){
        return instance;
    }

    public PlayersManagement getManager() {
        return manager;
    }

    public boolean getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus() {
        gameStatus = true;
    }

    public boolean getJoinable() {
        return joinable;
    }

    public void disableJoinable() {
        joinable = false;
    }

    public void startTimer(String number) {
        time = new Time(number);
        timer = new Thread(time);
        timer.start();
    }

    public String getTime() {
       return time.time();
    }
}