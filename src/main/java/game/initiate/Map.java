package game.initiate;

import game.rooms.Room;
import game.rooms.RoomEngineer;


public class Map {

    private static Room[][] graph;

    public Map() {
        initiateRoomsAndMap();
    }

    public static Room roomAt(int y, int x) {
        if (x >= 3) {
            //  Game.outcome(1);
        }
        return graph[y][x];
    }

    private void initiateRoomsAndMap() {

        RoomEngineer roomEngineer = new RoomEngineer();

        graph = new Room[6][9];

        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 9; x++) {

                if ((y == 0 && x == 0) || (y == 0 && x == 8) || (y == 5 && x == 0) || (y == 5 && x == 8)) {
                    continue;
                } else if (y == 0 && x % 2 == 1) {
                    roomEngineer.makeDecorRoom(1);
                    graph[y][x] = roomEngineer.getRoom();
                } else if (y == 0 && x % 2 == 0) {
                    roomEngineer.makeDecorRoom(2);
                    graph[y][x] = roomEngineer.getRoom();
                } else if ((x == 0 && y % 2 == 1) || (x == 8 && y % 2 == 0)) {
                    roomEngineer.makeDecorRoom(3);
                    graph[y][x] = roomEngineer.getRoom();
                } else if ((x == 0 && y % 2 == 0) || (x == 8 && y % 2 == 1)) {
                    roomEngineer.makeDecorRoom(4);
                    graph[y][x] = roomEngineer.getRoom();
                } else if ((y == 2 && x == 2) || (y == 2 && x == 6)) {
                    roomEngineer.makeChestRoom(1);
                    graph[y][x] = roomEngineer.getRoom();
                } else if (y == 3 && x == 4) {
                    roomEngineer.makeChestRoom(2);
                    graph[y][x] = roomEngineer.getRoom();
                } else if ((y == 3 && x == 2) || (y == 3 && x == 6)) {
                    roomEngineer.makeSellerRoom(1);
                    graph[y][x] = roomEngineer.getRoom();
                } else if (y == 2 && x == 4) {
                    roomEngineer.makeSellerRoom(2);
                    graph[y][x] = roomEngineer.getRoom();
                }else if((y==5 && x==1) || (y==5 && x==7)){
                    roomEngineer.makeDecorRoom(1);
                    graph[y][x] = roomEngineer.getRoom();
                } else if (y == 5) {
                    roomEngineer.makeWinnerRoom();
                    graph[y][x] = roomEngineer.getRoom();
                } else {
                    roomEngineer.makeLobbyRoom();
                    graph[y][x] = roomEngineer.getRoom();
                }
               System.out.print(y+" "+x+" "+graph[y][x].getRoomName()+" ");
            }
            System.out.println();
        }

    }
}
