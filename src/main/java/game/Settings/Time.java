package game.Settings;




import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

public class Time implements Runnable {

    static int time;

    Time( ) {
     time=500000;
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
         //   Game.outcome(2);
        } catch (InterruptedException ignored) {
        }
    }

    public String time() {
        return "================ Time remaining: " + time / 60 / 1000 + " minutes left================";
    }

}
