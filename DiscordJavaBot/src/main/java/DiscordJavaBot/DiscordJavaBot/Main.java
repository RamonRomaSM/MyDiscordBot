package DiscordJavaBot.DiscordJavaBot;

import java.time.LocalTime;
import java.util.Locale;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class Main {
	/*
	 * TODO:	unirse, salirse, dime la hora, help, lo de las buenas noches, lo de añadir contestaciones
	 * */
	public static void main(String[] args) {
		BotEventManager manager=new BotEventManager();
		manager.addResp(new Response("!!saluda","Saluda al que lo invoca") {
			
			@Override
			public void resolve(MessageReceivedEvent event) {
				event.getChannel().sendMessage("Hola "+event.getAuthor().getName()+" :wave:").queue();
				
			}
		});
		manager.addResp(new Response("!!join","Se une al chat del que lo invoca") {
			
			@Override
			public void resolve(MessageReceivedEvent event) {
				AudioManager audioManager = event.getGuild().getAudioManager();
			    audioManager.openAudioConnection(event.getMember().getVoiceState().getChannel());    	
			}
		});
		manager.addResp(new Response("!!leave","Se va del chat de voz") {
			
			@Override
			public void resolve(MessageReceivedEvent event) {
				AudioManager audioManager = event.getGuild().getAudioManager();
			    audioManager.closeAudioConnection();    	
				
			}
		});
		manager.addResp(new Response("!!hora","Dice la hora actual") {
			
			@Override
			public void resolve(MessageReceivedEvent event) {
				String resp=LocalTime.now().toString().split(":")[0]+":"+LocalTime.now().toString().split(":")[1]+"   "+Locale.getDefault().toString();
				event.getChannel().sendMessage(resp).queue();
			}
		});
		manager.addResp(new Response("!!comandos","Muestra la lista de comandos y sus descripciones") {
			
			@Override
			public void resolve(MessageReceivedEvent event) {
				event.getChannel().sendMessage(manager.show()).queue();
				
			}
		});
		
		
		
		
		
		
		
		try {
			
			Bot bot=new Bot(manager,"MTE0MzExNzAzNDE2MzU1MjMwNg.GjpDR3._hOCVev9QObhQ9XgmXtiGr-KCnBt1pjFrRoSRI");
		} catch (Exception e) {
			System.err.println("PUEDE QUE EL TOKEN SEA INVALIDO");
		}
	}
}
