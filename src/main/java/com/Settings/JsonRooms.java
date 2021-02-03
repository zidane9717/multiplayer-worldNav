package com.Settings;

import com.Entites.Decor;
import com.Entites.Entity;
import com.Entites.EntityFactory;
import com.Items.Item;
import com.Items.ItemFactory;
import com.Items.Key;
import com.Rooms.OldRoomBuilder;
import com.Rooms.Room;
import com.Rooms.RoomEngineer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonRooms {

    static ArrayList<Room> getRooms(String whereToRead) {

        ArrayList<Room> rooms = new ArrayList<>();
        RoomEngineer roomEngineer;

        try {
            JSONObject jo = (JSONObject) new JSONParser().parse(new FileReader("src/main/resources/Rooms/" + whereToRead + ".json"));
            JSONArray roomsList = (JSONArray) jo.get("rooms");

            for (JSONObject room : (Iterable<JSONObject>) roomsList) {
                String roomName = (String) room.get("name");
                int illumination = ((int) ((long) room.get("illumination")));

                Entity north = readEntityFromJSON((JSONObject) room.get("northWall"));
                Entity south = readEntityFromJSON((JSONObject) room.get("southWall"));
                Entity east = readEntityFromJSON((JSONObject) room.get("eastWall"));
                Entity west = readEntityFromJSON((JSONObject) room.get("westWall"));

                roomEngineer = new RoomEngineer(new OldRoomBuilder());
                roomEngineer.makeRoom(roomName, illumination, north, south, east, west);

                rooms.add(roomEngineer.getRoom());
            }
        } catch (
                ParseException | IOException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    private static Entity readEntityFromJSON(JSONObject wall) {

        EntityFactory entityFactory = new EntityFactory();
        Entity entity;

        String entityName = String.valueOf(wall.get("name"));
        long stateLong = (long) wall.get("state");
        int state = (int) stateLong;

        if ("CHEST".equals(wall.get("entityType"))) {
            entity = entityFactory.makeEntity(entityName, state, getListOfItems(wall));
        } else if ("DOOR".equals(wall.get("entityType"))) {
            entity = entityFactory.makeEntity(entityName, state);
        } else if ("DECOR".equals(wall.get("entityType"))) {
            Item item = null;
            if ((long) wall.get("state") != 0) {
                item = readItemFromJSON((JSONObject) wall.get("item"));
            }
            entity = entityFactory.makeEntity(Decor.DecorType.valueOf(entityName), state, item);
        } else if ("SELLER".equals(wall.get("entityType"))) {
            entity = entityFactory.makeEntity(entityName, state, getMapOfItems((JSONObject) wall.get("sellerItems")));
        } else {
            entity = entityFactory.makeEntity();
        }

        return entity;
    }

    private static List getListOfItems(JSONObject jsonItems) {

        ArrayList items = new ArrayList<>();
        JSONArray itemsList = (JSONArray) jsonItems.get("items");

        for (Object item : (Iterable<JSONObject>) itemsList) {
            if (item instanceof String) {
                items.add(Gold.valueOf((String) item));
            } else {
                items.add(readItemFromJSON((JSONObject) item));
            }
        }
        return items;
    }

    private static HashMap<String, Item> getMapOfItems(JSONObject sellerItems) {

        HashMap<String, Item> hashMap = new HashMap<>();

        for (Object jsonItem : sellerItems.keySet()) {

            Item item = readItemFromJSON((JSONObject) sellerItems.get(jsonItem));
            hashMap.put(item.getName(), item);
        }

        return hashMap;
    }

    private static Item readItemFromJSON(JSONObject jsonItem) {

        ItemFactory itemFactory = new ItemFactory();

        switch (String.valueOf(jsonItem.get("itemType"))) {
            case "key" -> {
                Item key = itemFactory.makeItem(
                        String.valueOf(jsonItem.get("name")),
                        Key.KeyType.valueOf(String.valueOf(jsonItem.get("keyType"))),
                        Gold.valueOf(String.valueOf(jsonItem.get("price"))));
                return key;
            }
            case "flashlight" -> {
                Item flash = itemFactory.makeItem(Gold.valueOf(String.valueOf(jsonItem.get("price"))));
                return flash;
            }
        }
        return null;
    }

}
