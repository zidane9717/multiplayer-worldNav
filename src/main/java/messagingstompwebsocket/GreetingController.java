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
    private static SimpMessagingTemplate simpMessagingTemplate;

    private final HashMap<Game, ArrayList<String>> bookedNames = new HashMap<>();
    private final HashMap<String, Game> games = new HashMap<>();

    @MessageMapping("/hello/{number}/{username}")
    @SendTo("/topic/greetings/{number}/{username}")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(500);
        return new Greeting("Game: yes");
    }

    @MessageMapping("/hello/{number}")
    @SendTo("/topic/greetings/{number}")
    public Greeting greetingToAll(HelloMessage message) throws Exception {
        return new Greeting("ANNOUNCEMENT: Game created");
    }

    @RequestMapping(value = "/validate/hostNumber", method = RequestMethod.GET)
    @ResponseBody
    public String validateHostNumber(@RequestParam("message") String message) {

        if (games.containsKey(message)) {
            throw new InvalidName("Host number already used");
        }
        Game game = new Game();
        games.put(message, game);
        bookedNames.put(game,new ArrayList<>());
        return "Host created. Use the host number to let other players join you";
    }

    @RequestMapping(value = "/validate/name", method = RequestMethod.GET)
    @ResponseBody
    public String validateName(@RequestParam("message") String message) {
        String []input = message.split(" ");

        if(bookedNames.get(games.get(input[1])).contains(input[0])){
            throw new InvalidName("Name is already used");
        }
        bookedNames.get(games.get(input[1])).add(input[0]);
        return "Success";
    }

    public static void sendToClients(String message) {
        simpMessagingTemplate.convertAndSend("/topic/greetings", message);
    }

}
