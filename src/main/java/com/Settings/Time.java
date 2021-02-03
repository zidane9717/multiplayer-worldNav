package com.Settings;


import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Time implements Runnable {

    static int time;

    Time(String jsonTime) {
        try {
            long jo = (long) new JSONParser().parse(new FileReader("src/main/resources/Time/" + jsonTime + ".json"));
            time=(int) jo;
        } catch (
                IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (time != 0) {
                time();
                time = time - 60000;
                Thread.sleep(60000);
            }
            System.out.println("================ **TIME IS UP!!** ================");
            Game.outcome(2);
        } catch (InterruptedException ignored) {
        }
    }

    public void time() {
        System.out.println();
        System.out.println("================ Time remaining: " + time / 60 / 1000 + " minutes left================");
    }

}
