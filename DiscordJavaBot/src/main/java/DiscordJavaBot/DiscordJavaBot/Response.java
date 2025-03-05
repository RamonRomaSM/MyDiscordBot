package DiscordJavaBot.DiscordJavaBot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import services.GenericService;

public class Response {
	private String id;
	private String description;
	private GenericService service;
	
	public Response(String id,String description) {
		this.id=id;
		this.description=description;
	}
	
	public String getId() {
		return id;
	}
	public void resolve(MessageReceivedEvent event) {
		Message mensaje=event.getMessage();
		String req=mensaje.getContentRaw().split(" ")[0];//this is the command
		service.answer(event.getMessage().getContentRaw().substring(req.length()), event);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		try {
			//if we are comparing responses
			Response r=(Response) obj;
			if(r.getId().equals(this.id)) {return true;}
			return false;
			
		} catch (Exception e) {
			//if we are comparing an id
			String o=(String) obj;
			if(o.equals(this.id)) {return true;}
			return false;
		}
		
		
		
		
	}
	@Override
	public int hashCode() {
		
		return id.hashCode();
	}
	@Override
	public String toString() {		
		return id+": "+description;
	}
	
}
