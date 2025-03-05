package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;

import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class TalkService extends GenericService{
	private MessageReceivedEvent event;
	private String apiKey;
	private int currentKey = 1;//we start by using the first key in the file
	
	public TalkService() {
		try {
			File f = new File("aimlapi_KEYS.txt");
			if(!f.exists()) {f.createNewFile();}
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			apiKey = br.readLine();
		} catch (Exception e) {
			System.err.println("ERROR LEYENDO KEYS PARA EL LLM, ASEGURATE DE PONER EL TOKEN EN EL FICHERO discord_KEY.txt");
		}		
		
	}
	
	@Override
	public boolean answer(String args, Event event) {
		
		MessageReceivedEvent messageEvent = (MessageReceivedEvent) event;
		try {
            String apiUrl = "https://api.aimlapi.com/v1/chat/completions";
           
            String jsonInputString = "{"
                    + "\"model\": \"mistralai/Mistral-7B-Instruct-v0.2\","
                    + "\"messages\": [{\"role\": \"user\", \"content\": \""+args+"\"}],"
                    + "\"temperature\": 0.7"
                    + "}";

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            
            // Leer la respuesta y guardarla en un String
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            // Convertir la respuesta en un JSONObject
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray choices = jsonResponse.getJSONArray("choices");
            String message = choices.getJSONObject(0).getJSONObject("message").getString("content");
        	System.out.println(message);
        	messageEvent.getChannel().sendMessage(message).queue();

        } catch (Exception e) {
        	System.out.println("failed key: "+apiKey);
        	changeKey();
        	System.out.println("new key: "+apiKey);
        	if(apiKey == null) {
        		return false;//All tokens are consumed
        	}
        	else {//we changed the key, now we re-ask the llm
        		answer(args,event);
        	}
        }
		
		return true;
	}
	
	
	
	public void changeKey() {
		currentKey++;
		try {
			File f = new File("aimlapi_KEYS.txt");
			if(!f.exists()) {f.createNewFile();}
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			for(int i = 0; currentKey!=i;i++) {
				apiKey = br.readLine();
			}
		} catch (Exception e) {
			System.err.println("ERROR LEYENDO KEYS PARA EL LLM, ASEGURATE DE PONER EL TOKEN EN EL FICHERO discord_KEY.txt");
		}	
		System.out.println("Key changed");
	}
	
	public MessageReceivedEvent getEvent() {
		return event;
	}

	public void setEvent(MessageReceivedEvent event) {
		this.event = event;
	}
	
}
