package com.Items;

import com.Entites.CheckableEntity;
import com.Entites.Entity;
import com.Settings.Gold;
import com.Settings.SingletonPlayer;


public class Key extends Item {

    public enum KeyType {
        DOOR, CHEST;
    }

    private KeyType keyType;

    Key(String keyName, KeyType type, Gold price, String key) {
        keyType = type;
        setName(keyName);
        setPrice(price);
        setItemType(key);
    }

    @Override
    public String use() {
        SingletonPlayer player = SingletonPlayer.INSTANCE;
        Entity entity = player.currentRoom().wallAt(player.looking);
        if (entity instanceof CheckableEntity) {

            if (entity.getName().equals(getName().toLowerCase())) { //if entity name = key name
                if (entity.getState() == 1) {
                    entity.setState(0);
                    return entity.getName() + " " + keyType + " locked";
                }
                entity.setState(1);
                return entity.getName() + " " + keyType + " unlocked";
            }
        }
        return "i can only use keys for doors or chests..";
    }

}
