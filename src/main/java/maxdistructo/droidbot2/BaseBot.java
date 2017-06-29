package maxdistructo.droidbot2;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import maxdistructo.droidbot2.background.Client;
import maxdistructo.droidbot2.background.Config;
import maxdistructo.droidbot2.background.Listener;
import maxdistructo.droidbot2.background.audio.AudioMain;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import sx.blah.discord.api.IDiscordClient;

import javax.security.auth.login.LoginException;

public class BaseBot {
    public static String PREFIX = "/";
    public static IDiscordClient client;
    public static JDA jda;
    public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static void main(String[] args){
        LOGGER.setLevel(Level.INFO);
        String token = Config.readToken();
        try {
            jda = new JDABuilder(AccountType.BOT).setToken(token).buildBlocking();
        } catch (LoginException | InterruptedException | RateLimitedException e) {
            e.printStackTrace();
        }
        client = Client.createClient(token);
        LOGGER.info("Client Created");
        client.getDispatcher().registerListener(new AudioMain());
        LOGGER.info("Registered Audio Commands ");
        client.getDispatcher().registerListener(new Listener());
        LOGGER.info("Registered Listener");






    }

}
