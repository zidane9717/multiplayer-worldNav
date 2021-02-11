package messagingstompwebsocket;

import game.initiate.Game;
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

    GreetingController(){
        instance=this;
    }

   public static GreetingController getInstance(){
        return instance;
    }

    private final HashMap<Game, ArrayList<String>> bookedNames = new HashMap<>();
    private final HashMap<String, Game> games = new HashMap<>();

    @MessageMapping("/hello/{number}/{username}")
    @SendTo("/topic/greetings/{number}/{username}")
    public Greeting greeting(HelloMessage message) throws Exception {

        String number = message.getNumber();
        String name = message.getName();
        String command = message.getContent();

        if (games.get(number).getGameStatus()) {
            String answer = games.get(number).getManager().processCommand(name, command);
            return new Greeting("Game: " + answer);
        }
        return new Greeting("Game: waiting for host to start the game..");
    }

    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    @ResponseBody
    public String validate(@RequestParam("number") String number, @RequestParam("name") String name, @RequestParam("state") int state) {

        //Host game
        if (state == 1) {
            if (!games.containsKey(number)) {
                Game game = new Game();
                games.put(number, game);
                bookedNames.put(game, new ArrayList<>());
                bookedNames.get(game).add(name);
                game.getManager().addPlayer(name);
                return "Game created. Use the host number to let other players join you";
            }
            throw new InvalidName("Host number already used");
        }
        //Start game by Host
        else if(state == 3){
            games.get(number).disableJoinable();
            games.get(number).setGameStatus();
            games.get(number).startTimer(number);
            return  "ANNOUNCEMENT: Game started ";
        }

        //Join game
        Game game = games.get(number);
        if (games.containsValue(game)) {
            if (!bookedNames.get(game).contains(name)) {
                if (game.getJoinable()) {
                    bookedNames.get(game).add(name);
                    game.getManager().addPlayer(name);
                    return "ANNOUNCEMENT: "+name+" joined the game.";
                }
                throw new InvalidName("Game closed");
            }
            throw new InvalidName("Name is already used");
        }
        throw new InvalidName("Game does not exit");
    }

    public void sendToClients(String number,String message) throws Exception {
        webSocket.convertAndSend("/topic/greetings/"+number, "{\"content\":\""+message+"\"}");
    }
}
