package game.initiate;


import game.settings.PlayersManagement;
import game.settings.Time;

public class Game {

    final Map map;
    static Time time;
    Thread timer;
    private boolean joinable;
    private boolean gameStatus;
    private final PlayersManagement manager = new PlayersManagement();

    public Game(){
        gameStatus=false;
        joinable=true;
        map = new Map();
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

}