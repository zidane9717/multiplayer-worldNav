package messagingstompwebsocket;

import game.initiate.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
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
    public Greeting greeting(@PathParam("number") String number, HelloMessage message) throws Exception {
        System.out.println(number);
        return new Greeting("Game: yes");
    }

    public static void sendToClients(String game,String message) {
        simpMessagingTemplate.convertAndSend("/topic/greetings/"+game, message);
    }

    @MessageMapping("/hello/{number}")
    @SendTo("/topic/greetings/{number}")
    public Greeting greetingToAll(HelloMessage message) throws Exception {
        if(message.getContent().equals("firstTime")){
            return new Greeting("ANNOUNCEMENT: "+message.getName()+" joined the game.");
        }
        else{
            games.get(message.getContent()).disableJoinable();
            return new Greeting("ANNOUNCEMENT:Game started");
        }
    }

    @RequestMapping(value = "/validate/joinNumber", method = RequestMethod.GET)
    @ResponseBody
    public String validateJoinNumber(@RequestParam("message") String message) {

        if (games.containsKey(message)) {
            if(games.get(message).getJoinable()){
                return "joined";
            }
            throw new InvalidName("Game closed");
        }
        throw new InvalidName("Game does not exit");
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


}
