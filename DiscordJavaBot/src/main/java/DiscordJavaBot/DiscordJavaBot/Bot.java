package DiscordJavaBot.DiscordJavaBot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import okhttp3.EventListener;
import okhttp3.internal.http2.Http2Connection.Listener;

public class Bot {
	private final ShardManager shardmanager;
	
	
	public Bot(BotEventManager m,String token) {	
		
		DefaultShardManagerBuilder builder=DefaultShardManagerBuilder.createDefault(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS,GatewayIntent.GUILD_VOICE_STATES);
		builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
		builder.setActivity(Activity.listening("Linkin Park"));
		builder.addEventListeners(new DiscordJavaBot.DiscordJavaBot.EventListener(m));
		
		shardmanager=builder.build();
		
	}
	
	public ShardManager getShardmanager() {
		return shardmanager;
	}
	
	
}
