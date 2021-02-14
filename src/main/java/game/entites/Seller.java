package game.entites;


import game.items.Item;
import game.settings.Gold;
import game.playerSystem.Player;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.List;

public class Seller implements Entity {

    private final HashMap<String, Item> sellerItems = new HashMap<>(); //Seller's items no one should get access to it.

    Seller(List items) {
        convertListToHashMap(items);
    }

    private void convertListToHashMap(List<Item> items) {
        for(Item item : items){
            sellerItems.put(item.getName(),item);
        }
    }

    public String confirmPayment(String item,Player player) {
        Gold itemPrice = sellerItems.get(item).getPrice();

        player.inventory.put(item, sellerItems.get(item));
        player.gold = player.gold.subtract(itemPrice.getValue());
        return "purchased " + item;
    }

    public String sell(Item item,Player player) {
        BigDecimal itemPrice = item.getPrice().getValue();

        player.inventory.remove(item.getName());
        player.gold = player.gold.add(itemPrice, new MathContext(4));
        return "<" + item.getName() + " sold>";
    }

    public String list() {
        String answer = "";
        for (String item : sellerItems.keySet()) {
            answer= answer + item + " key, price: " + sellerItems.get(item).getPrice().getValue().intValue()+" ";
        }
        return answer;
    }

    public boolean haveThisItem(String item) {
        return sellerItems.containsKey(item);
    }

    public boolean enoughMoneyToTrade(String item,BigDecimal playerGold) {

        Gold itemPrice = sellerItems.get(item).getPrice();
        return playerGold.intValue() >= itemPrice.getValue().intValue();
    }

    @Override
    public String look() {
        return " Seller ";
    }
}

