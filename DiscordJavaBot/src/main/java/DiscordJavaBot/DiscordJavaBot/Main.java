package DiscordJavaBot.DiscordJavaBot;

public class Main {

	public static void main(String[] args) {
		BotEventManager manager=new BotEventManager();
		
		
		try {
			Bot bot=new Bot(manager);
		} catch (Exception e) {
			System.err.println("PUEDE QUE EL TOKEN SEA INVALIDO");
		}
	}
}
