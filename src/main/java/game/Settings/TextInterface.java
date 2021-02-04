package game.Settings;

public class TextInterface implements Runnable{

    public static void winMessage(){
        System.out.println("==========YOU DID IT!!=========");
        System.out.println("==========YOU HAVE ESCAPED!!==========");
        System.out.println("==========CONGRATULATIONS==========");
        System.out.println("why don't you try to play again.. !!");
    }

    public static void loseMessage(){
        System.out.println("==========TIME'S UP=========");
        System.out.println("==========YOU LOST==========");
        System.out.println("==========YOU GOT TRAPPED INSIDE==========");
    }

    public static void restartMessage(){
        System.out.println("The game restarted..");
    }

    public static void quitMessage(){
       System.out.println("Please come back later..GOODBYE!");
    }

    public static void frameMessage(){
        System.out.println("============================================");
    }

    public static void storyMessage() throws InterruptedException {
        System.out.println("......");
        System.out.println("yesterday when you went to sleep..");
        Thread.sleep(2000);
        System.out.println("you've been kidnapped and placed in this room");
        Thread.sleep(2000);
        System.out.println("you are in a building of 5 rooms..");
        Thread.sleep(2000);
        System.out.println("you've got so little time..");
        Thread.sleep(2000);
        System.out.println("but enough for you to escape..");
        Thread.sleep(3000);
        System.out.println("before the toxic gas leaks inside the building..");
        Thread.sleep(2000);
        System.out.println("ESCAPE FOR YOUR LIFE!");
        Thread.sleep(2000);
    }

    public static void gameRulesMessage(){
        frameMessage();
        System.out.println("*RULES*");
        System.out.println("The game needs your english writing skills to play.");
        System.out.println("By writing down one of the commands in the log shown below to communicate with your character inside the game..");
        frameMessage();
        System.out.println("Navigate through the map -> [forward, backward, left, right]");
        System.out.println("Player actions -> [look, check, open, playerstatus, use flashlight, use <name> key]");
        System.out.println("Game settings  -> [restart ,quit]");
        frameMessage();
    }

    public static void tradeModeMessage() {
        System.out.println("===========Seller store===========");
        System.out.println("'buy <item>'  -> to buy affordable item");
        System.out.println("'sell <item>' -> to sell item you own");
        System.out.println("'list'        -> to list sellers items");
        System.out.println("'finish'      -> to exit trade mode");
        System.out.println("==================================");

    }

    public static void mainMenuMessage() {
        frameMessage();
        System.out.println("==========Welcome to Trap the game==========");
        System.out.println("===================Menu====================");
        System.out.println("write '1' -> New game");
        System.out.println("write '2' -> Load checkpoint");
        System.out.println("write '3' -> Save game & Quit");
        frameMessage();
    }

    @Override
    public void run() {
        try {
            storyMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
