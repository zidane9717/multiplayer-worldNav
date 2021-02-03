package com.Entites;

import com.Items.Item;

import java.util.HashMap;
import java.util.List;

/**
 * A door between 2 rooms must be the same the object.
 * That's why we have a Hashmap to store each door object with the key of it's name.
 */

public class EntityFactory {

    public static HashMap<String, Door> doors = new HashMap<>();

    public Entity makeEntity(String name, int state) {

        if (doors.containsKey(name)) {
            return doors.get(name);
        }

        Door door = new Door(name, state, Entity.EntityType.DOOR);
        doors.put(name, door);
        return door;

    }

    public Entity makeEntity(String name, int state, List items) {
        return new Chest(name, state, Entity.EntityType.CHEST, items);
    }

    public Entity makeEntity(Decor.DecorType name,Item item) {
        return new Decor(name,item);
    }

    public Entity makeEntity(String name, int available, HashMap items) {
        return new Seller(name, available, Entity.EntityType.SELLER, items);
    }

    public Entity makeEntity() {
        return new Wall();
    }
}
