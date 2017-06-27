package maxdistructo.droidbot2;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.btobastian.sdcf4j.CommandHandler;
import de.btobastian.sdcf4j.handler.Discord4JHandler;
import maxdistructo.droidbot2.background.Client;
import maxdistructo.droidbot2.background.Listener;
import maxdistructo.droidbot2.commands.*;
import maxdistructo.droidbot2.background.audio.AudioMain;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.Status;

import javax.security.auth.login.LoginException;

public class BaseBot {
    public static String PREFIX = "/";
    public static IDiscordClient client;
    public static JDA jda;
    public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static void main(String[] args){
        LOGGER.setLevel(Level.INFO);
        String token = "";
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
       // token = Config.reader(s + "/droidbot/config/config.txt", 1);j
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
