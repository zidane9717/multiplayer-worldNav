package game.Settings;

import game.Rooms.Room;
import game.Rooms.RoomEngineer;


public class Map {

    private static Room[][] graph;

    public Map() {
        initiateRoomsAndMap();
    }

    static Room roomAt(int y, int x) {
        if (x >= 3) {
            //  Game.outcome(1);
        }
        return graph[y][x];
    }

    private void initiateRoomsAndMap() {

        RoomEngineer roomEngineer = new RoomEngineer();

        graph = new Room[6][9];

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 9; j++) {

                if ((i == 0 && j == 0) || (i == 0 && j == 8) || (i == 5 && j == 0) || (i == 5 && j == 8)) {
                    continue;
                } else if (i == 0 && j % 2 == 1) {
                    roomEngineer.makeDecorRoom(1);
                    graph[i][j] = roomEngineer.getRoom();
                } else if (i == 0 && j % 2 == 0) {
                    roomEngineer.makeDecorRoom(2);
                    graph[i][j] = roomEngineer.getRoom();
                } else if ((j == 0 && i % 2 == 1) || (j == 8 && i % 2 == 0)) {
                    roomEngineer.makeDecorRoom(3);
                    graph[i][j] = roomEngineer.getRoom();
                } else if ((j == 0 && i % 2 == 0) || (j == 8 && i % 2 == 1)) {
                    roomEngineer.makeDecorRoom(4);
                    graph[i][j] = roomEngineer.getRoom();
                } else if ((i == 2 && j == 2) || (i == 2 && j == 6)) {
                    roomEngineer.makeChestRoom(1);
                    graph[i][j] = roomEngineer.getRoom();
                } else if (i == 3 && j == 4) {
                    roomEngineer.makeChestRoom(2);
                    graph[i][j] = roomEngineer.getRoom();
                } else if ((i == 3 && j == 2) || (i == 3 && j == 6)) {
                    roomEngineer.makeSellerRoom(1);
                    graph[i][j] = roomEngineer.getRoom();
                } else if (i == 2 && j == 4) {
                    roomEngineer.makeSellerRoom(2);
                    graph[i][j] = roomEngineer.getRoom();
                } else if (i == 5) {
                    roomEngineer.makeWinnerRoom();
                    graph[i][j] = roomEngineer.getRoom();
                } else {
                    roomEngineer.makeLobbyRoom();
                    graph[i][j] = roomEngineer.getRoom();
                }

            }
        }
    }
}
