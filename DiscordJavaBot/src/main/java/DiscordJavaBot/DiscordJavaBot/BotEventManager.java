package DiscordJavaBot.DiscordJavaBot;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import services.GenericService;

public class BotEventManager {
	private HashMap<Response, GenericService>responses;
	private ArrayList<String>defaultCommands = new ArrayList<String>(Arrays.asList("!!comandos","!!hora","!!leave","!!join","!!saluda"));
	public BotEventManager() {
		//TODO: meterle los genericos	
		responses=new HashMap<Response, GenericService>();
		addResp(new Response("!!saluda","Saluda al que lo invoca"),null);
		addResp(new Response("!!join","Se une al chat del que lo invoca"),null);
		addResp(new Response("!!leave","Se va del chat de voz"),null);
		addResp(new Response("!!hora","Dice la hora actual"),null);
		addResp(new Response("!!comandos","Muestra la lista de comandos y sus descripciones"),null);
	}
	
	public void addResp(Response resp, GenericService service) {	
		responses.put(resp, service);
		System.out.println("Response to '"+resp.getId()+"' added successfully");
	}
		
	
	
	public void removeResp(String id) {
		Response asked=new Response(id,"This is a Mock") {
			@Override
			public void resolve(MessageReceivedEvent event) {
			}
		};
		responses.remove(asked);
		System.out.println("Response to '"+id+"' removed successfully");
		
	}
	
	
	public void answer(MessageReceivedEvent event) {
		Message mensaje=event.getMessage();
		String req=mensaje.getContentRaw().split(" ")[0];//this is the command
		
		if(!defaultCommands.contains(req)) {
			Response asked=new Response(req,"This is a Mock");
			if(responses.get(asked)==null) {
				event.getChannel().sendMessage("Comando no definido, !!comandos para ver el listado").queue();
			}
			else{
				responses.get(asked).answer(event.getMessage().getContentRaw().substring(req.length()), event);
			}
		}
		else {
			answerDefaultCommands(req,event);
		}
	}
	
	public String show() {
		String resp="COMANDOS:\r";
		
		Iterator<Response> it= responses.keySet().iterator();
		while(it.hasNext()) {
			resp=resp+it.next().toString()+"\r";
		}
		return resp;
	}
	
	public void answerDefaultCommands(String req,MessageReceivedEvent event) {
		AudioManager audioManager = event.getGuild().getAudioManager();
		switch (req) {
		case "!!comandos":
			event.getChannel().sendMessage(show()).queue();
			break;
		case "!!hora":
			String resp=LocalTime.now().toString().split(":")[0]+":"+LocalTime.now().toString().split(":")[1]+"   "+Locale.getDefault().toString()+"\r";
			event.getChannel().sendMessage(resp).queue();		
			break;			
		case "!!leave":			
		    audioManager.closeAudioConnection();    
			break;
		case "!!join":			
		    audioManager.openAudioConnection(event.getMember().getVoiceState().getChannel());    
			break;
		case "!!saluda":
			event.getChannel().sendMessage("Hola "+event.getAuthor().getName()+" :wave:").queue();
			break;
		default:
			break;
		}
		
	}
	
	
}
