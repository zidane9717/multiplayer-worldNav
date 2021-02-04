package game.Rooms;

import game.Entites.Decor;
import game.Entites.Door;
import game.Entites.Entity;
import game.Entites.EntityFactory;
import game.Items.Item;
import game.Items.ItemFactory;
import game.Items.Key;
import game.Settings.Gold;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class RoomEngineer {

    private RoomBuilder roomBuilder;
    EntityFactory entityFactory = new EntityFactory();
    ItemFactory itemFactory = new ItemFactory();

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

    public void makeDecorRoom(int type) {

        roomBuilder = new OldRoomBuilder();

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

    public void makeChestRoom(int type) {
        roomBuilder = new OldRoomBuilder();
        this.roomBuilder.buildName("chest");
        this.roomBuilder.buildLights(3);
        this.roomBuilder.buildEastWall(entityFactory.makeEntity("treasure", 1));
        this.roomBuilder.buildWestWall(entityFactory.makeEntity("treasure", 1));

        if (type == 1) {
            this.roomBuilder.buildNorthWall(entityFactory.makeEntity("treasure", 1));
            this.roomBuilder.buildSouthWall(entityFactory.makeEntity("chest", 0, Arrays.asList(Gold.TWENTY_FIVE, Gold.TWENTY_FIVE)));
        } else {
            this.roomBuilder.buildNorthWall(entityFactory.makeEntity("chest", 0, Arrays.asList(Gold.TWENTY_FIVE, Gold.TWENTY_FIVE)));
            this.roomBuilder.buildSouthWall(entityFactory.makeEntity("treasure", 1));
        }
    }

    public void makeSellerRoom(int type) {

        roomBuilder = new OldRoomBuilder();

        this.roomBuilder.buildName("seller");
        this.roomBuilder.buildLights(1);
        this.roomBuilder.buildEastWall(entityFactory.makeEntity("trader", 1));
        this.roomBuilder.buildWestWall(entityFactory.makeEntity("trader", 1));

        if (type == 1) {
            this.roomBuilder.buildNorthWall(entityFactory.makeEntity(Collections.singletonList(itemFactory.makeItem("winner", Key.KeyType.DOOR, Gold.HUNDRED))));
            this.roomBuilder.buildSouthWall(entityFactory.makeEntity("trader",1));
        } else {
            this.roomBuilder.buildNorthWall(entityFactory.makeEntity("trader",1));
            this.roomBuilder.buildSouthWall(entityFactory.makeEntity(Collections.singletonList(itemFactory.makeItem("winner", Key.KeyType.DOOR, Gold.HUNDRED))));
        }
    }

    public void makeWinnerRoom() {
        roomBuilder = new OldRoomBuilder();
        this.roomBuilder.buildName("winner");
        this.roomBuilder.buildLights(1);
        this.roomBuilder.buildNorthWall(entityFactory.makeEntity("victory",1));
        this.roomBuilder.buildSouthWall(entityFactory.makeEntity("winner",0));
        this.roomBuilder.buildEastWall(entityFactory.makeEntity());
        this.roomBuilder.buildWestWall(entityFactory.makeEntity());
    }

    public void makeLobbyRoom() {
        roomBuilder = new OldRoomBuilder();
        this.roomBuilder.buildName("lobby");
        this.roomBuilder.buildLights(1);
        this.roomBuilder.buildNorthWall(entityFactory.makeEntity("lobby",1));
        this.roomBuilder.buildNorthWall(entityFactory.makeEntity("lobby",1));
        this.roomBuilder.buildNorthWall(entityFactory.makeEntity("lobby",1));
        this.roomBuilder.buildNorthWall(entityFactory.makeEntity("lobby",1));
    }

}
