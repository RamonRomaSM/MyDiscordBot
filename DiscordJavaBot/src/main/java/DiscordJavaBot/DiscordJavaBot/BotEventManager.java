package DiscordJavaBot.DiscordJavaBot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import net.dv8tion.jda.api.events.Event;

public class BotEventManager {
	private Set<Response>responses;
	private String prefix;
	
	/*TODO: el prefix tiene que ser no alfanumerico, la id de las responses solo puede ser alfanumerico
	*
	*		hacer esta comprobacion desde el event listener:	validatePrefix
	*															validateResponse
	*
	*/
	
	public BotEventManager(String pref) {
		prefix=pref;
		responses=new HashSet<Response>();
	}
	
	public void addResp(Response resp) {
		//TODO: cuidado, lo que me den tiene que empezar por el prefix
		if(responses.add(resp)) {
		System.out.println("Response to '"+resp.getId()+"' added successfully");
		}
		else {System.out.println("Tried to add '"+resp.getId()+"' but it was duplicated");}
	}
		
	
	
	public void removeResp(String id) {
		Response asked=new Response(id,"This is a Mock") {
			
			@Override
			public void resolve(Event event) {
				// TODO Auto-generated method stub
				
			}
		};
		
		
		if(responses.remove(asked)) {System.out.println("Response to '"+id+"' removed successfully");}
		else {System.out.println("Tried to remove '"+id+"' but didnt exist");}
	}
	
	public String show() {
		
		return Arrays.toString(responses.toArray());
	}
	
	
	public static void main(String[] args) {
		BotEventManager b=new BotEventManager("/");
		b.addResp(new Response("hello","hace algo") {
			
			@Override
			public void resolve(Event event) {
				// TODO Auto-generated method stub
				
			}
		});
		b.addResp(new Response("play","hace algo") {
			
			@Override
			public void resolve(Event event) {
				// TODO Auto-generated method stub
				
			}
		});
		
		System.out.println(b.show());
		b.removeResp("hello");
		System.out.println(b.show());
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
