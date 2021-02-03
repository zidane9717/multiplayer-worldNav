package com.Entites;

 public class Wall extends Entity{

    public Wall(){
        setEntityType(EntityType.WALL);
    }

    @Override
    public String look() {
        return "<empty wall>";
    }
}
