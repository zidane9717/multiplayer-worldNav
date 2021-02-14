package game.settings;


import game.playerSystem.PlayersManager;

public class Game {

    Map map;
    static Time time;
    Thread timer;
    private boolean joinable;
    private boolean gameStatus;
    private PlayersManager manager;

    public Game(String number){
        gameStatus=false;
        joinable=true;
        map = new Map(number);
        manager = new PlayersManager(map,number);
    }

    public PlayersManager getPlayersManager() {
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

    public Map getMap() {
        return this.map;
    }
}