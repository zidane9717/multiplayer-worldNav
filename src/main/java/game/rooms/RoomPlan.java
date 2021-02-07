package game.rooms;

import game.entites.Entity;

public interface RoomPlan {

    void setRoomName(String name);

    void setRoomLights(int state);

    void setNorthWall(Entity north);

    void setSouthWall(Entity south);

    void setEastWall(Entity east);

    void setWestWall(Entity west);

    Entity wallAt(String looking);
}
