package services;

import java.net.URI;
import java.net.URISyntaxException;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import utils.YouTubeAudioHandler;

public class AudioService extends GenericService{
	@Override
	public boolean answer(String args, Event ev) {
		MessageReceivedEvent event = (MessageReceivedEvent) ev;
		 	Member member = event.getMember();
	        GuildVoiceState memberVoiceState = member.getVoiceState();

	        if(!memberVoiceState.inAudioChannel()) {
	            event.getChannel().sendMessage("You need to be in a voice channel").queue();
	            return false;
	        }

	        Member self = event.getGuild().getSelfMember();
	        GuildVoiceState selfVoiceState = self.getVoiceState();

	        if(!selfVoiceState.inAudioChannel()) {
	            event.getGuild().getAudioManager().openAudioConnection(memberVoiceState.getChannel());
	        } else {
	            if(selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
	                event.getChannel().sendMessage("You need to be in the same channel as me").queue();
	                return false;
	            }
	        }
	       
	        try {
	            URI uri =new URI(args.trim());    
	        } catch (URISyntaxException e) {
	        	e.printStackTrace();
	            args = "ytsearch:" + args;
	        }

	        event.getChannel().sendMessage("Playing").queue();
	        AudioManager audioManager = event.getGuild().getAudioManager();
	        try {
				audioManager.setSendingHandler(new YouTubeAudioHandler(args));
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	        
		return false;
	}
	
}
