package messagingstompwebsocket;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class GreetingController {

	@Autowired
	private static SimpMessagingTemplate simpMessagingTemplate;
    private final ArrayList<String> bookedNames = new ArrayList<>();

	@MessageMapping("/hello/{username}")
	@SendTo("/topic/greetings/{username}")
	public Greeting greeting(HelloMessage message) throws Exception {
		return new Greeting(message.getName()+" "+message.getContent());
	}

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greetingToAll(HelloMessage message) throws Exception {
		Thread.sleep(500);

		return new Greeting("ANNOUNCEMENT: "+message.getName()+": has joined the game.");
	}

	@RequestMapping(value = "/examples/echo-message", method = RequestMethod.GET)
	@ResponseBody
	public String sendPostMessage(@RequestParam("message") String message){
		if(bookedNames.contains(message)){
		throw new InvalidName("Invalid name");
		}

		bookedNames.add(message);
		return "Success";
	}


	public static void sendToClients(String message){
		simpMessagingTemplate.convertAndSend("/topic/greetings",message);
	}

}
