package com.Items;

import com.Settings.Gold;

public class ItemFactory {

     public Item makeItem(Gold price){
         return new FlashLight(price,"flashlight");
     }

     public Item makeItem(String keyName, Key.KeyType keyType, Gold price){
         return new Key(keyName,keyType,price,"key");
     }

}
