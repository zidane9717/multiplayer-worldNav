package com.Entites;


public class Door extends Entity implements CheckableEntity {

    Door(String name, int state ,EntityType entityType) {
        setName(name);
        setState(state);
        setEntityType(entityType);
    }

    @Override
    public String look() {
        return "<the " + getName() + " door>";
    }

    @Override
    public String check() {
        if (getState() == 0) {
            return "<Door is locked, " + getName() + " key is needed>";
        }
        return "<The " + getName() + " door is open>";
    }

}
