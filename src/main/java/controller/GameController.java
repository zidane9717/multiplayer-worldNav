package controller;

import game.Settings.Game;
import game.Settings.SingletonPlayer;
import model.Greeting;
import model.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.HashMap;

@Controller
public class GameController {

	@Autowired
	private static SimpMessagingTemplate simpMessagingTemplate;

    private HashMap<String, SingletonPlayer> players = new HashMap<>();
    private int counterX = 1;
    private int counterY = 0;

	@MessageMapping("/hello/{username}")
	@SendTo("/topic/greetings/{username}")
	public Greeting greeting(HelloMessage message) throws Exception {
		Thread.sleep(500); // simulated delay

		return new Greeting(Game.processGame(players.get(message.getName()),message.getCommand()));
	}

	public static void sendToClients(String message){
		simpMessagingTemplate.convertAndSend("/topic/greetings",message);
	}

	public void playerJoin(String name){
		SingletonPlayer player = SingletonPlayer.getInstance();
		player.setCoordinates(counterX,counterY);
		players.put(name,player);
		counterX++;
	}
}
