package com.Rooms;

import com.Entites.Decor;
import com.Entites.Entity;

public class RoomEngineer {

    private final RoomBuilder roomBuilder;


    public RoomEngineer(RoomBuilder roomBuilder){
        this.roomBuilder=roomBuilder;
    }

    public Room getRoom(){
        return this.roomBuilder.getRoom();
    }

    public void makeRoom(String name, int lights, Entity north, Entity south, Entity east, Entity west){

        this.roomBuilder.buildName(name);
        this.roomBuilder.buildLights(lights);
        this.roomBuilder.buildNorthWall(north);
        this.roomBuilder.buildSouthWall(south);
        this.roomBuilder.buildEastWall(east);
        this.roomBuilder.buildWestWall(west);

    }

    public void makeLootRoom(int type){
        if(type==1){
            this.roomBuilder.buildName("lootA");
            this.roomBuilder.buildLights(1);

            if(((int) (Math.random()*1)) == 1){
                Entity decor = Decor.getInstance(1);
                this.roomBuilder.buildNorthWall(decor);
            }

            this.roomBuilder.buildNorthWall(north);
            this.roomBuilder.buildSouthWall(south);
            this.roomBuilder.buildEastWall(east);
            this.roomBuilder.buildWestWall(west);
        }
        else if(type==2){

        }
    }
}
