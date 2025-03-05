package DiscordJavaBot.DiscordJavaBot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import services.TalkService;

public class Main {

	public static void main(String[] args) {
		
		BotEventManager manager=new BotEventManager();
		manager.addResp(new Response("!!talk","Habla con el bot"),new TalkService());
		
		
		
		try {
			File f = new File("discord_KEY.txt");
			if(!f.exists()) {f.createNewFile();}
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String key = br.readLine();
			
			
			Bot bot=new Bot(manager,key);
		} catch (Exception e) {
			System.err.println("PUEDE QUE EL TOKEN SEA INVALIDO, ASEGURATE DE PONER EL TOKEN EN EL FICHERO discord_KEY.tx");
		}		
		
	}
}
