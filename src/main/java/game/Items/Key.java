package game.Items;

import game.Entites.CheckableEntity;
import game.Entites.Entity;
import game.Settings.Gold;
import game.Settings.SingletonPlayer;


public class Key extends Item {

    public enum KeyType {
        DOOR, CHEST;
    }

    private KeyType keyType;

    Key(String keyName, KeyType type, Gold price, String key) {
        keyType = type;
        setName(keyName);
        setPrice(price);
    }

    @Override
    public String use() {
        SingletonPlayer player = SingletonPlayer.getInstance();
        Entity entity = player.currentRoom().wallAt(player.looking);
        if (entity instanceof CheckableEntity) {
             CheckableEntity entity1 = (CheckableEntity) entity;
            if (entity1.getName().equals(getName().toLowerCase())) { //if entity name = key name
                if (entity1.getState() == 1) {
                    entity1.setState(0);
                    return entity1.getName() + " " + keyType + " locked";
                }
                entity1.setState(1);
                return entity1.getName() + " " + keyType + " unlocked";
            }
        }
        return "i can only use keys for doors or chests..";
    }

}
