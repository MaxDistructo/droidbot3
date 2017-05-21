package maxdistructo.droidbot2.commands;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.entities.permissions.Role;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

import java.util.Collection;

public class Debug implements CommandExecutor {
    @Command(aliases = {"/debug"}, description = "Shows debug info for making code for the bot.", usage = "/debug")
    public String onDebugCommand(String[] args, Message message) {
        if(args[1] != null){
            return "Too Many Arguments. Please just type /debug";
        }
        else{
            User author = message.getAuthor();
            Channel channel = message.getChannelReceiver();
            Server server = channel.getServer();
            Collection<Role> roles = author.getRoles(server);

            return "You are: " + author + ". \n" + "Your channel is: " + channel + ". \n" + "Your server is: " + server + ". \n";

        }
        }
}
