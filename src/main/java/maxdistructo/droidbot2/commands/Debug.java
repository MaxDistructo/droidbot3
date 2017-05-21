package maxdistructo.droidbot2.commands;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.*;

import java.util.Collection;

public class Debug implements CommandExecutor {
    @Command(aliases = {"/debug"}, description = "Shows debug info for making code for the bot.", usage = "/debug")
    public String onDebugCommand(String[] args, Message message) {
            User author = message.getAuthor();
            MessageChannel channel = message.getChannel();
            long server = channel.getIdLong();
            long authorIdLong = author.getIdLong();

            return "You are: " + author + ". \n" + "Your channel is: " + channel + ". \n" + "Your server is: " + server + ". \n" + "Your ID is: " + authorIdLong + ".";


        }
}
