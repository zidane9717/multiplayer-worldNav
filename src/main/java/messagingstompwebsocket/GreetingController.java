package messagingstompwebsocket;

import game.playerSystem.PlayersManager;
import game.settings.Game;
import game.playerSystem.Player;
import game.settings.GameManager;
import game.settings.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class GreetingController {

    @Autowired
    private SimpMessagingTemplate webSocket;

    static GreetingController instance;

    GreetingController() {
        instance = this;
    }

    public static GreetingController getInstance() {
        return instance;
    }

    @MessageMapping("/hello/{number}/{username}")
    @SendTo("/topic/greetings/{number}/{username}")
    public Greeting greeting(HelloMessage message) throws Exception {

        String number = message.getNumber();
        String name = message.getName();
        String command = message.getContent();

        GameManager managerGames = GameManager.getInstance();
        if (managerGames.checkGame(number)) {
            if (managerGames.getGame(number).getGameStatus()) {
                String answer = managerGames.getGame(number).getPlayersManager().processCommand(name, command);
                return new Greeting("Game: " + answer);
            }
            return new Greeting("Game: waiting for host to start the game..");
        }
        return new Greeting("Game has finished");
    }

    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    @ResponseBody
    public String validate(@RequestParam("number") String number, @RequestParam("name") String name, @RequestParam("state") int state) throws Exception {

        GameManager manager = GameManager.getInstance();

        //Host game
        if (state == 1) {
            if (!manager.checkGame(number)) {
                manager.createGame(number);
                manager.getGame(number).getPlayersManager().addPlayer(name, manager.getGame(number).getMap());
                return "ANNOUNCEMENT: GAME CREATED";
            }
            throw new InvalidName("Host number already used");
        }
        //Start game by Host
        else if (state == 3) {
            manager.getGame(number).disableJoinable();
            manager.getGame(number).setGameStatus();
            manager.getGame(number).startTimer(number);
            broadcastPlayersList(number);
            sendToClients(number, "ANNOUNCEMENT: GAME STARTED");
            return "";
        }

        //Join game
        if (manager.checkGame(number)) { //Check game exist
            if (manager.getGame(number).getJoinable()) {  //Check game joinable
                if (!manager.getGame(number).getPlayersManager().checkPlayerName(name)) { //Check name not exist
                    manager.getGame(number).getPlayersManager().addPlayer(name, manager.getGame(number).getMap());
                    sendToClients(number, "ANNOUNCEMENT: " + name + " joined the game.");
                    return "ANNOUNCEMENT: " + name + " joined the game.";
                }
                throw new InvalidName("Name used");
            }
            throw new InvalidName("Game closed");
        }
        throw new InvalidName("Game does not exist");
    }

    public void sendToClients(String number, String message) throws Exception {
        webSocket.convertAndSend("/topic/greetings/" + number, "{\"content\":\"" + message + "\"}");
    }

    @RequestMapping(value = "/fight", method = RequestMethod.GET)
    @ResponseBody
    public void fight(@RequestParam("number") String number, @RequestParam("name") String
            name, @RequestParam("id") String id, @RequestParam("content") String attackMove) throws InterruptedException {
        GameManager.getInstance().getGame(number).getPlayersManager().processFight(Integer.parseInt(id), name, attackMove);
    }

    @RequestMapping(value = "/disconnect", method = RequestMethod.GET)
    @ResponseBody
    public void disconnect(@RequestParam("number") String number, @RequestParam("name") String name) throws InterruptedException {
       GameManager manger = GameManager.getInstance();
       PlayersManager playerManger = manger.getGame(number).getPlayersManager();
       playerManger.disconnect(name);
       broadcastPlayersList(number);
    }

    public void fightModeBroadCast(String number, Player playerOne, Player playerTwo, String message) {
        webSocket.convertAndSend("/topic/greetings/" + number + "/" + playerOne.getName(), "{\"content\":\"" + message + "\"}");
        webSocket.convertAndSend("/topic/greetings/" + number + "/" + playerTwo.getName(), "{\"content\":\"" + message + "\"}");
    }

    public void broadcastPlayersList(String number){
        GameManager manager = GameManager.getInstance();
        webSocket.convertAndSend("/topic/greetings/" + number, "{\"content\":\"" + "" + "!!! ==Updated list== !!!" + "\"}");
        for(String name : manager.getGame(number).getPlayersManager().players.keySet()){
            String message = "player : "+name;
            webSocket.convertAndSend("/topic/greetings/" + number, "{\"content\":\"" + message + "\"}");
        }
    }
}
