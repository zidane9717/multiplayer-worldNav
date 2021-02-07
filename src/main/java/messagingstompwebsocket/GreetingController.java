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
    private final ArrayList<String> bookedNames = new ArrayList<>();
    private final HashMap<Integer, Game> games = new HashMap<Integer, Game>();

	@MessageMapping("/hello/{number}/{username}")
	@SendTo("/topic/greetings/{number}/{username}")
	public Greeting greeting(HelloMessage message) throws Exception {
		Thread.sleep(500);
		return new Greeting("Game: yes");
	}

	@MessageMapping("/hello/{number}")
	@SendTo("/topic/greetings")
	public Greeting greetingToAll(HelloMessage message) throws Exception {
		return new Greeting("ANNOUNCEMENT: Game created");
	}

	@RequestMapping(value = "/examples/echo-message", method = RequestMethod.GET)
	@ResponseBody
	public String sendPostMessage(@RequestParam("message") String message){
		System.out.println("aaaa");

		if(bookedNames.contains(message)){
		throw new InvalidName("Invalid name");
		}
		bookedNames.add(message);
		return "Success";
	}

	@RequestMapping(value = "/validate/hostNumber", method = RequestMethod.GET)
	@ResponseBody
	public String validateHostNumber(@RequestParam("message") String message){
		if(games.containsKey(Integer.valueOf(message))){
			throw new InvalidName("Host number already used");
		}
		games.put(Integer.valueOf(message),new Game());
		return "Host created";
	}
	public static void sendToClients(String message){
		simpMessagingTemplate.convertAndSend("/topic/greetings",message);
	}

}
