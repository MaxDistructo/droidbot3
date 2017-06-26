package maxdistructo.droidbot2.commands;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

import java.util.List;

import maxdistructo.droidbot2.background.Perms;
import sx.blah.discord.handle.obj.*;

public class Debug implements CommandExecutor {
    //@Command(aliases = {"/debug"}, description = "Shows debug info for making code for the bot.", usage = "/debug")
    public static String onDebugCommand(String[] args, IMessage message) {
        IUser author = message.getAuthor();
        IChannel channel = message.getChannel();
        IGuild guild = message.getGuild();
        IUser owner = guild.getOwner();
        List<IRole> roles = guild.getRoles();


        if(Perms.checkMod(message)) {
            return "You are: " + author + ". \n" + "Your channel is: " + channel + ". \n" + "Your Owner is: " + owner + ". \n" + "Your server's roles are: " + roles;
        }
        else{
            return "You have insufficient perms to use this command";
        }

    }
}
