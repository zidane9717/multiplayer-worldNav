package com.Settings;

import com.Entites.Decor;
import com.Entites.EntityFactory;
import com.Items.Item;
import com.Items.ItemFactory;
import com.Items.Key;
import com.Rooms.OldRoomBuilder;
import com.Rooms.Room;
import com.Rooms.RoomEngineer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Map {

    private static Room[][] graph;
    static ArrayList<Room> rooms;

    public Map() {
        initiateRooms();
        createMap();
    }

    private void createMap() {

        int counter = 0;

        graph = new Room[3][3];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                if ((i == 2 && j == 1) || (i == 1 && j == 2) || (i == 1 && j == 1) || (i == 1 && j == 0) || (i == 0 && j == 1)) {
                    graph[i][j] = rooms.get(counter);
                    counter++;
                }
            }
        }
    }

    static Room roomAt(int y, int x) {
        if (x >= 3) {
            Game.outcome(1);
        }
        return graph[y][x];
    }

    private void initiateRooss() {

        RoomEngineer roomEngineer = new RoomEngineer(new OldRoomBuilder());
        ItemFactory itemFactory = new ItemFactory();
        EntityFactory entityFactory = new EntityFactory();

        Item flashLight = itemFactory.makeItem(Gold.HUNDRED);
        HashMap<String, Item> hashMap = new HashMap<>();
        hashMap.put(flashLight.getName(), flashLight);

        roomEngineer.makeRoom("stranger", 1,
                entityFactory.makeEntity("mirror father", 1, hashMap),
                entityFactory.makeEntity("stranger", 0),
                entityFactory.makeEntity(Decor.DecorType.MIRROR, 0, null),
                entityFactory.makeEntity(Decor.DecorType.MIRROR, 0, null)
        );

        rooms.add(roomEngineer.getRoom());

        roomEngineer = new RoomEngineer(new OldRoomBuilder());
        roomEngineer.makeRoom("beginning", 0,
                entityFactory.makeEntity("treasure", 0, Arrays.asList(itemFactory.makeItem("stranger", Key.KeyType.DOOR, Gold.FIFTY), Gold.FIFTY, itemFactory.makeItem("unknown", Key.KeyType.DOOR, Gold.FIFTY))),
                entityFactory.makeEntity(Decor.DecorType.MIRROR, 1, itemFactory.makeItem("treasure2", Key.KeyType.CHEST, Gold.FIFTY)),
                entityFactory.makeEntity("lobby", 1),
                entityFactory.makeEntity("beginning", 0)
        );


        rooms.add(roomEngineer.getRoom());
        roomEngineer = new RoomEngineer(new OldRoomBuilder());
        roomEngineer.makeRoom("lobby", 1,
                entityFactory.makeEntity("stranger", 0),
                entityFactory.makeEntity("livelihood", 1),
                entityFactory.makeEntity("darkness", 1),
                entityFactory.makeEntity("lobby", 1)
        );


        rooms.add(roomEngineer.getRoom());

        roomEngineer = new RoomEngineer(new OldRoomBuilder());
        roomEngineer.makeRoom("darkness", 3,
                entityFactory.makeEntity(),
                entityFactory.makeEntity(),
                entityFactory.makeEntity("unknown", 0),
                entityFactory.makeEntity("darkness", 1)
        );

        rooms.add(roomEngineer.getRoom());

        roomEngineer = new RoomEngineer(new OldRoomBuilder());
        roomEngineer.makeRoom("livelihood", 0,
                entityFactory.makeEntity("livelihood", 1),
                entityFactory.makeEntity(Decor.DecorType.PAINTING, 1, itemFactory.makeItem("treasure", Key.KeyType.CHEST, Gold.FIFTY)),
                entityFactory.makeEntity("treasure2", 0, Arrays.asList(Gold.TWENTY_FIVE, Gold.TWENTY_FIVE)),
                entityFactory.makeEntity()
        );

        rooms.add(roomEngineer.getRoom());
    }

    private void initiateRooms() {
        RoomEngineer roomEngineer = new RoomEngineer(new OldRoomBuilder());
        ItemFactory itemFactory = new ItemFactory();

        graph = new Room[6][9];

        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {

                if((i==0 && j==0) || (i==0 && j== 8) || (i== 5 && j==0) || (i== 5 && j==8)){
                      continue;
                }

               if(i==0 && j % 2==0){
                   roomEngineer.makeLootRoom(1,itemFactory.makeItem());
                   graph[i][j]=
               }


            }
        }
    }
}
