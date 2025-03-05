package services;

import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class GenericService {
	public abstract boolean answer(String args, Event event);
}
