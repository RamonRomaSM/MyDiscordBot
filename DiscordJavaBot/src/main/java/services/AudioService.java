package services;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class AudioService extends GenericService{
	@Override
	public boolean answer(String args, Event ev) {
		MessageReceivedEvent event = (MessageReceivedEvent) ev;
		/*
		MessageReceivedEvent ev = (MessageReceivedEvent) event;
	        try {
	            new URI(args);
	        } catch (URISyntaxException e) {
	        	args = "ytsearch:" + args;
	        }

	        PlayerManager playerManager = PlayerManager.get();
	        ev.getChannel().sendMessage("Playing").queue();
	        playerManager.play(ev.getGuild(), args);
	        */
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
	        	System.out.println(args);
	        	e.printStackTrace();
	        	System.out.println("mepasa");
	            args = "ytsearch:" + args;
	        }
	        
	        PlayerManager playerManager = PlayerManager.get();
	        event.getChannel().sendMessage("Playing").queue();
	        playerManager.play(event.getGuild(), args);
	        
	        AudioManager audioManager = event.getGuild().getAudioManager();
	        audioManager.setSendingHandler(new AudioSendHandler() {//TODO: quizas con implementar esto me valga
				
				@Override
				public ByteBuffer provide20MsAudio() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public boolean canProvide() {
					// TODO Auto-generated method stub
					return false;
				}
			});
	        
		return false;
	}
	
}
