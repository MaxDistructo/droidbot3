package maxdistructo.droidbot2;

import java.util.logging.Level;
import java.util.logging.Logger;

import maxdistructo.droidbot2.background.*;
import maxdistructo.droidbot2.background.Config;
import maxdistructo.droidbot2.background.audio.AudioMain;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import sx.blah.discord.api.IDiscordClient;

import javax.security.auth.login.LoginException;

public class BaseBot {
    public static IDiscordClient client;
    public static JDA jda;
    public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public final static String version = "2.0.1";

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
        Listener listener = new Listener();
        client.getDispatcher().registerListener(listener);
        LOGGER.info("Registered Listener");
        do {
            try {
                Thread.sleep(86400000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try{
                ZIPBackup.startBackup();
            }
            catch(Exception e){

            }

        }
        while (client.isLoggedIn());






    }

}
