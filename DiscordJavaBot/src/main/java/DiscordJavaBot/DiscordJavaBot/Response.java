package DiscordJavaBot.DiscordJavaBot;

import net.dv8tion.jda.api.events.Event;

public abstract class Response {
	private String id;
	public Response(String id) {
		this.id=id;
	}
	
	
	public abstract void resolve(Event event);
	
	@Override
	public boolean equals(Object obj) {
		String o=(String)obj;
		if(this.id.equals(o)) {
			return true;
		}
		return false;	
	}
	
	
}
