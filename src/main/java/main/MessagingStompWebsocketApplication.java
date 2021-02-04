package main;

import game.Settings.Game;
import game.Settings.SingletonPlayer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MessagingStompWebsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessagingStompWebsocketApplication.class, args);
		SingletonPlayer player = SingletonPlayer.getInstance();
		player.setCoordinates(1,0);
		new Game();
	}

}
