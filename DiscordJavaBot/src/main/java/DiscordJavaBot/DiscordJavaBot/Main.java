package DiscordJavaBot.DiscordJavaBot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Main {
	/*
	 * TODO:	unirse, salirse, dime la hora, help, lo de las buenas noches, lo de añadir contestaciones
	 * */
	public static void main(String[] args) {
		BotEventManager manager=new BotEventManager();
		manager.addResp(new Response("/saluda","Saluda al que lo invoca") {
			
			@Override
			public void resolve(MessageReceivedEvent event) {
				event.getChannel().sendMessage("Hola "+event.getAuthor().getName()+" :wave:").queue();
				
			}
		});
		
		try {
			Bot bot=new Bot(manager);
		} catch (Exception e) {
			System.err.println("PUEDE QUE EL TOKEN SEA INVALIDO");
		}
	}
}
