package DiscordJavaBot.DiscordJavaBot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;

import net.dv8tion.jda.api.entities.ClientType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.utils.TimeFormat;
import net.dv8tion.jda.internal.requests.WebSocketClient;
import services.TalkService;

public class Main {

	public static void main(String[] args) {
		TalkService srv = new TalkService();
		
		
		BotEventManager manager=new BotEventManager();
		manager.addResp(new Response("!!saluda","Saluda al que lo invoca") {
			
			@Override
			public void resolve(MessageReceivedEvent event) {
				event.getChannel().sendMessage("Hola "+event.getAuthor().getName()+" :wave:").queue();
				
			}
		});
		manager.addResp(new Response("!!join","Se une al chat del que lo invoca") {
			
			@Override
			public void resolve(MessageReceivedEvent event) {
				AudioManager audioManager = event.getGuild().getAudioManager();
			    audioManager.openAudioConnection(event.getMember().getVoiceState().getChannel());    	
			}
		});
		manager.addResp(new Response("!!leave","Se va del chat de voz") {
			
			@Override
			public void resolve(MessageReceivedEvent event) {
				AudioManager audioManager = event.getGuild().getAudioManager();
			    audioManager.closeAudioConnection();    	
				
			}
		});
		manager.addResp(new Response("!!hora","Dice la hora actual") {
			
			@Override
			public void resolve(MessageReceivedEvent event) {
				Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("Canada/Pacific"));
				
				String canada=cal.get(Calendar.HOUR)+":"+((cal.get(Calendar.MINUTE)<=9)?"0"+cal.get(Calendar.MINUTE):cal.get(Calendar.MINUTE))+" "+(((cal.get(Calendar.AM_PM)==0)?"AM":"PM")+" Canada/Pacific");
				
				
				String resp=LocalTime.now().toString().split(":")[0]+":"+LocalTime.now().toString().split(":")[1]+"   "+Locale.getDefault().toString()+"\r"+canada;
				event.getChannel().sendMessage(resp).queue();
			}
		});
		manager.addResp(new Response("!!comandos","Muestra la lista de comandos y sus descripciones") {
			
			@Override
			public void resolve(MessageReceivedEvent event) {
				event.getChannel().sendMessage(manager.show()).queue();
				
			}
		});
		
		manager.addResp(new Response("!!talk","Habla con el bot") {
			
			@Override
			public void resolve(MessageReceivedEvent event) {
				String question =event.getMessage().getContentRaw().substring(5);
				if(!srv.answer(question,event)) {
					event.getChannel().sendMessage("Error al recibir el mensaje de la API,  puede que se hayan acabado los tokens por hoy :(").queue();
				}
				
			}
		});
		
		
		
		
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
