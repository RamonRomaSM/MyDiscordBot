package DiscordJavaBot.DiscordJavaBot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import net.dv8tion.jda.api.events.Event;

public class BotEventManager {
	private Set<Response>responses;

	public BotEventManager() {
		
		responses=new HashSet<Response>();
	}
	
	public void addResp(Response resp) {
		
		if(responses.add(resp)) {
		System.out.println("Response to '"+resp.getId()+"' added successfully");
		}
		else {System.out.println("Tried to add '"+resp.getId()+"' but it was duplicated");}
	}
		
	
	
	public void removeResp(String id) {
		Response asked=new Response(id,"This is a Mock") {
			@Override
			public void resolve(Event event) {
			}
		};
		
		
		if(responses.remove(asked)) {System.out.println("Response to '"+id+"' removed successfully");}
		else {System.out.println("Tried to remove '"+id+"' but didnt exist");}
	}
	
	public String show() {
		
		return Arrays.toString(responses.toArray());
	}
	
	
}
