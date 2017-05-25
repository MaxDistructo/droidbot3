package maxdistructo.droidbot2;

import java.nio.file.Path;
import java.nio.file.Paths;

import de.btobastian.sdcf4j.CommandHandler;
import de.btobastian.sdcf4j.handler.Discord4JHandler;
import maxdistructo.droidbot2.background.Client;
import maxdistructo.droidbot2.background.Config;
import maxdistructo.droidbot2.commands.*;
import sx.blah.discord.api.IDiscordClient;

public class BaseBot {
    public static String PREFIX;
    public static IDiscordClient client;
    public static void main(String[] args){
        String token = "MzE1MzEzOTY3NzU5MDk3ODU3.DAexvg.3N7thF8tQwu7zkWcIyMGWxJa070";
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        Config.reader(s + "/droidbot/config/config.txt", 1, token);
        client = Client.createClient(token);
        CommandHandler cmdHandler = new Discord4JHandler(client);
        cmdHandler.registerCommand(new Debug());
        cmdHandler.registerCommand(new Casino());
        cmdHandler.registerCommand(new Info());



    }

}
