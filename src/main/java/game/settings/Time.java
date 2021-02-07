package game.settings;


public class Time implements Runnable {

    static int time;

    public Time() {
     time=500000;
    }

    @Override
    public void run() {
        try {
            while (time != 0) {
           //     GameController.sendToClients(time());
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
