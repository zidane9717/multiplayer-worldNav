package com.Rooms;

import com.Entites.Entity;

public interface RoomBuilder {

    void buildLights(int state);

    void buildName(String name);

    void buildNorthWall(Entity entity);

    void buildSouthWall(Entity entity);

    void buildEastWall(Entity entity);

    void buildWestWall(Entity entity);

    Room getRoom();

}
