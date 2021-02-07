package game.entites;


import game.settings.Player;

public class Door implements Entity,CheckableEntity {

    private final String name;
    private int state;

    Door(String name, int state) {
        this.name=name;
        this.state=state;
    }

    @Override
    public String look() {
        return "<the " + name + " door>";
    }

    @Override
    public String check(Player player) {
        if (state == 0) {
            return "<Door is locked, " + name + " key is needed>";
        }
        return "<The " + name+ " door is open>";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public void setState(int state) {
        this.state= state;
    }
}
