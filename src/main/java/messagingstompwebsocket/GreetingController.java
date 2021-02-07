package messagingstompwebsocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

@Controller
public class GreetingController {

	@Autowired
	private static SimpMessagingTemplate simpMessagingTemplate;
    private ArrayList<String> bookedNames = new ArrayList<>();

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMessage message) throws Exception {
		Thread.sleep(1000); // simulated delay
		return new Greeting(message.getName()+" "+message.getContent());
	}

	public static void sendToClients(String message){
		simpMessagingTemplate.convertAndSend("/topic/greetings",message);
	}

}
