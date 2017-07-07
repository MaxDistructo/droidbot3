package maxdistructo.droidbot2.commands;

import java.util.List;
import java.util.Optional;

import maxdistructo.droidbot2.background.Config;
import maxdistructo.droidbot2.background.Perms;
import sx.blah.discord.handle.impl.obj.Message;
import sx.blah.discord.handle.obj.*;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

public class Info implements CommandExecutor{
   // @Command(aliases = {"/i", "/info"}, description = "Gets your info. Mods can get info on any user.", usage = "/i|info @User#0000")
    public static String onInfoCommand(Object[] args, IMessage message, IUser mentioned){
        IUser author = message.getAuthor();
        IGuild guild = message.getGuild();
        String guildName = guild.getName();
        String nick = author.getNicknameForGuild(guild);
        List<IRole> roles = author.getRolesForGuild(guild);
        Object[] rolesArray = roles.toArray();
        String roleNames = "";
        int i = 1;
        while(i < rolesArray.length)
        {
            if(i == rolesArray.length - 1){
                IRole roleCheck = (IRole)rolesArray[i];
                roleNames = roleNames + roleCheck.getName() + " ";
            }
            else {
                IRole roleCheck = (IRole) rolesArray[i];
                roleNames = roleNames + roleCheck.getName() + ", ";
            }
            i++;
        }
        if(mentioned == null){
            return "You are known as: " + nick + ". \nYou are in Discord Server: " + guildName + "\nYour roles in this server are: " + roleNames;
        }
        else if(Perms.checkMod(message)){
            IUser checked = mentioned;
            String nickChecked = checked.getNicknameForGuild(guild);
            List<IRole> rolesChecked = checked.getRolesForGuild(guild);
            Object[] rolesCheckedArray = rolesChecked.toArray();
            String roleNamesChecked = "";
            i = 1;
            while(i < rolesCheckedArray.length)
            {
                if(i == rolesCheckedArray.length - 1){
                    IRole roleCheck = (IRole)rolesCheckedArray[i];
                    roleNamesChecked = roleNamesChecked + roleCheck.getName() + " ";
                }
                else {
                    IRole roleCheck = (IRole) rolesCheckedArray[i];
                    roleNamesChecked = roleNamesChecked + roleCheck.getName() + ", ";
                }
                i++;
            }

            return "\nMember " + checked.getName() + " is also known as " + nickChecked + "\nThey have the roles " + roleNamesChecked + "\nIn Discord Server: " + guildName;

        }
        return "Command has errored. Please enter a valid command.";
    }
}
