package utils;

import java.nio.ByteBuffer; 

import net.dv8tion.jda.api.audio.AudioSendHandler;

public class AudioPlayerSendHandler implements AudioSendHandler {

	@Override
	public boolean canProvide() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ByteBuffer provide20MsAudio() {
		// TODO Auto-generated method stub
		return null;
	}
	  
	  
	}
