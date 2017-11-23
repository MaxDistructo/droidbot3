package maxdistructo.droidbot2.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

public class Client {
        public static IDiscordClient client;
    public final static Logger LOGGER = LoggerFactory.getLogger(Client.class);
        public static void createClient(String token) { // Returns a new instance of the Discord client
        ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
        clientBuilder.withToken(token); // A to the builder
        clientBuilder.withRecommendedShardCount();
        try {
            client = clientBuilder.login();
        } catch (DiscordException e) {
            e.printStackTrace();
            System.exit(0);
        } 

    }
    public static void registerListener(Object listener){
            client.getDispatcher().registerListener(listener);
    }
}
