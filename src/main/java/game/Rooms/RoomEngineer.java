package game.Rooms;

import game.Entites.Decor;
import game.Entites.Door;
import game.Entites.Entity;
import game.Entites.EntityFactory;
import game.Items.Item;
import game.Items.ItemFactory;
import game.Items.Key;
import game.Settings.Gold;

import java.util.HashMap;

public class RoomEngineer {

    private RoomBuilder roomBuilder;

    private HashMap<String, Room> rooms = new HashMap<>();

    public Room getRoom() {
        return this.roomBuilder.getRoom();
    }

    public void makeCustomRoom(String name, int lights, Entity north, Entity south, Entity east, Entity west) {

        roomBuilder = new OldRoomBuilder();
        this.roomBuilder.buildName(name);
        this.roomBuilder.buildLights(lights);
        this.roomBuilder.buildNorthWall(north);
        this.roomBuilder.buildSouthWall(south);
        this.roomBuilder.buildEastWall(east);
        this.roomBuilder.buildWestWall(west);

    }

    /*
     * Type '1' = Decor type PAINTING & Flashlight Behind it & Door South
     * Type '2' = Decor type MIRROR & Chest Key Behind it & Door East
     */
    public void makeDecorRoom(int type) {

        roomBuilder = new OldRoomBuilder();
        EntityFactory entityFactory = new EntityFactory();
        ItemFactory itemFactory = new ItemFactory();

        this.roomBuilder.buildLights(1);
        this.roomBuilder.buildName("Loot");

        if (type == 1) {
            this.roomBuilder.buildNorthWall(entityFactory.makeEntity(Decor.DecorType.PAINTING, itemFactory.makeItem("flashlight", Gold.TWO)));
            this.roomBuilder.buildSouthWall(entityFactory.makeEntity("loot", 1));
            this.roomBuilder.buildEastWall(entityFactory.makeEntity());
            this.roomBuilder.buildWestWall(entityFactory.makeEntity());
        } else if (type == 2) {
            this.roomBuilder.buildNorthWall(entityFactory.makeEntity(Decor.DecorType.MIRROR, itemFactory.makeItem("chest", Key.KeyType.CHEST, Gold.TEN)));
            this.roomBuilder.buildSouthWall(entityFactory.makeEntity("loot", 1));
            this.roomBuilder.buildEastWall(entityFactory.makeEntity());
            this.roomBuilder.buildWestWall(entityFactory.makeEntity());
        } else if (type == 3) {
            this.roomBuilder.buildNorthWall(entityFactory.makeEntity(Decor.DecorType.PAINTING, itemFactory.makeItem("chest", Key.KeyType.CHEST, Gold.TEN)));
            this.roomBuilder.buildSouthWall(entityFactory.makeEntity());
            this.roomBuilder.buildEastWall(entityFactory.makeEntity("loot", 1));
            this.roomBuilder.buildWestWall(entityFactory.makeEntity());
        } else if (type == 4) {
            this.roomBuilder.buildNorthWall(entityFactory.makeEntity(Decor.DecorType.MIRROR, itemFactory.makeItem("chest", Key.KeyType.CHEST, Gold.TEN)));
            this.roomBuilder.buildSouthWall(entityFactory.makeEntity());
            this.roomBuilder.buildEastWall(entityFactory.makeEntity());
            this.roomBuilder.buildWestWall(entityFactory.makeEntity("loot", 1));
        }
    }

    public void makeWinnerRoom() {
        roomBuilder = new OldRoomBuilder();


    }

    public void makeChestRoom() {
        roomBuilder = new OldRoomBuilder();

    }

    public void makeSellerRoom() {
        roomBuilder = new OldRoomBuilder();

    }
}
