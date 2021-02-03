package com.Entites;

public abstract class Entity {

    public enum EntityType {
        DOOR, CHEST, DECOR, SELLER, WALL
    }

    private String name;
    private int state;
    private EntityType entityType;

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String look();

}
