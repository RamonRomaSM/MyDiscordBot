package DiscordJavaBot.DiscordJavaBot;

import net.dv8tion.jda.api.entities.Message; 
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

public class EventListener extends ListenerAdapter{

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String aut=event.getAuthor()+"";
		if(!aut.contains("MiBot")) {
			
			//AQUI ANALIZAR COMANDOS
			
			
			
			Message mensaje=event.getMessage();
			mensaje.reply("hola");
			String resp=mensaje.getContentRaw();
			
			event.getChannel().sendMessage(resp).queue();
		}
		
		
	}

	

}
