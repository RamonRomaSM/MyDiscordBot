package DiscordJavaBot.DiscordJavaBot;

public class Main {

	public static void main(String[] args) {
		try {
			Bot bot=new Bot();
		} catch (Exception e) {
			System.err.println("PUEDE QUE EL TOKEN SEA INVALIDO");
		}
	}
}
