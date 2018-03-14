package maxdistructo.droidbot2;

import maxdistructo.droidbot2.background.*;
import maxdistructo.droidbot2.commands.mafia.MafiaListener;
import maxdistructo.droidbot2.core.Config;
import maxdistructo.droidbot2.core.Client;
import org.slf4j.Logger;
import sx.blah.discord.api.IDiscordClient;

public class BaseBot {
    public static IDiscordClient client;
    public static Logger LOGGER = Client.LOGGER;
    public final static Listener listener = new Listener();


    public static void main(String[] args){
        String token = Config.readToken();
        Client.createClient(token);
        client = Client.client; //To prevent causing the whole program from having to be re-written
        LOGGER.info("Client Created");
        //client.getDispatcher().registerListener(new AudioMain());
        //LOGGER.info("Registered Audio Commands ");
        //Help.registerHelp();
        client.getDispatcher().registerListener(listener);
        LOGGER.info("Registered Listener");
        client.getDispatcher().registerListener(new MafiaListener());
        LOGGER.info("Registered Mafia Listener. PLAY ON!");
    }

}
