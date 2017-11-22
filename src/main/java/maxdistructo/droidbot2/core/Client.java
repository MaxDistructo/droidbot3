package maxdistructo.droidbot2.core;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

public class Client {
        public static IDiscordClient createClient(String token) { // Returns a new instance of the Discord client
        ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
        clientBuilder.withToken(token); // A to the builder
        clientBuilder.withRecommendedShardCount();
        try {
            return clientBuilder.login();
        } catch (DiscordException e) {
            e.printStackTrace();
            return null;
        } 

    }
}
