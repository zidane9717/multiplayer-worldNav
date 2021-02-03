package com.Settings;

import com.Rooms.Room;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class SaveFile {

    public static void save() {
        saveTime();
        saveMap();
        saveStatus();
    }

    private static void saveTime() {
        String path = "src/main/resources/Time/loadgameTime.json";
        try (FileWriter file = new FileWriter(path)) {
            Gson gson = new Gson();
            file.write(gson.toJson(Time.time));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveMap() {
        String path = "src/main/resources/Rooms/loadgameRooms.json";
        try (FileWriter file = new FileWriter(path)) {
            JSONObject roomss = new JSONObject();
            JSONArray roomsContain = new JSONArray();

            for (Room room : Map.rooms) {
                Gson gson = new Gson();
                roomsContain.add(gson.toJsonTree(room));
            }

            roomss.put("rooms", roomsContain);
            file.write(String.valueOf(roomss));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveStatus() {
        SingletonPlayer player = SingletonPlayer.INSTANCE;

        String path = "src/main/resources/Player/loadgamePlayer.json";
        try (FileWriter file = new FileWriter(path)) {
            Gson gson = new Gson();
            JSONArray jsonContainer = new JSONArray();
            JSONObject jsonInventory = new JSONObject();
            JSONObject jsonGold = new JSONObject();
            jsonInventory.put("inventory", player.inventory);
            jsonGold.put("gold", player.gold);

            jsonContainer.add(gson.toJsonTree(player.inventory));
            jsonContainer.add(jsonGold);

            file.write(String.valueOf(jsonContainer));

            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
