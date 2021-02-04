package game.Entites;


public class Door implements Entity,CheckableEntity {

    private String name;
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
    public String check() {
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
