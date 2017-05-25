package maxdistructo.droidbot2.commands;

import java.util.List;
import java.util.Optional;

import maxdistructo.droidbot2.background.Perms;
import sx.blah.discord.handle.impl.obj.Message;
import sx.blah.discord.handle.obj.*;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

public class Info implements CommandExecutor{
    @Command(aliases = {"/i", "/info"}, description = "Gets your info. Mods can get info on any user.", usage = "/i|info @User#0000")
    public String onInfoCommand(Object[] args, IMessage message){
        IUser author = message.getAuthor();
        IGuild guild = message.getGuild();
        String guildName = guild.getName();
        Optional<String> nick = author.getNicknameForGuild(guild);
        List<IRole> roles = author.getRolesForGuild(guild);
        
        if(args.length == 0){
            return author.mention() + "``` You are known as: " + nick + ". \n You are in Discord Server: " + guildName + "\n Your roles in this server are: " + roles + " \n ```";
        }
        else if(Perms.checkMod(message)){
            IUser checked = (IUser)args[1];
            Optional<String> nickChecked = checked.getNicknameForGuild(guild);
            List<IRole> rolesChecked = checked.getRolesForGuild(guild);
            
            return "```Member " + checked.mention() + "is also known as " + nickChecked + "\n They have the roles " + rolesChecked + " here in Discord Server " + guildName + "```";
            
        }
        return "Command has errored. Please enter a valid command.";   
    }
}
