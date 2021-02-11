package game.settings;


import messagingstompwebsocket.GreetingController;

public class Time implements Runnable {

    int time;
    String number;

    public Time(String number) {
        this.number = number;
        time = 1500000;
    }

    @Override
    public void run() {
        try {
            while (time != 0) {
                GreetingController.getInstance().sendToClients(number, time());
                time = time - 60000;
                Thread.sleep(60000);
            }
            System.out.println("================ **TIME IS UP!!** ================");
            //   Game.outcome(2);
        } catch (InterruptedException ignored) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String time() {
        return "================ Time remaining: " + time / 60 / 1000 + " minutes left================";
    }

}
