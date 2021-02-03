package controller;

import model.Greeting;
import model.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GameController {

	@Autowired
	private static SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/hello/{username}")
	@SendTo("/topic/greetings/{username}")
	public Greeting greeting(HelloMessage message) throws Exception {
		Thread.sleep(1000); // simulated delay
		return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
	}

	public static void sendToClients(String message){
		simpMessagingTemplate.convertAndSend("/topic/greetings",message);
	}

}
