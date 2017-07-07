package maxdistructo.droidbot2.background;

import maxdistructo.droidbot2.background.message.Message;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;

import java.util.List;

import static maxdistructo.droidbot2.BaseBot.jda;

public class Roles {
    public static boolean checkForBotAbuse(IMessage message){
        IUser user = message.getAuthor();
        List<IRole> rolesList = user.getRolesForGuild(message.getGuild());
        IRole[] roles = rolesList.toArray(new IRole[rolesList.size()]);
        int i = 0;
        while(i < roles.length){
            if(roles[i].getLongID() == 330346559093080067L){
                return true;
            }
            i++;
        }
        return false;
    }
    public static boolean checkForPayday(IMessage message){
        IUser user = message.getAuthor();
        List<IRole> rolesList = user.getRolesForGuild(message.getGuild());
        IRole[] roles = rolesList.toArray(new IRole[rolesList.size()]);
        int i = 0;
        while(i < roles.length){
            if(roles[i].getLongID() == 330353751116480512L){
                i = roles.length;
                return true;
            }
            i++;
        }
        return false;
    }
    public static void applyBotAbuser(IMessage message, IUser mentioned){
        IRole role = message.getGuild().getRoleByID(330346559093080067L);
        mentioned.addRole(role);
    }
    public static void applyPayday(IMessage message, IUser mentioned){
        IRole role = message.getGuild().getRoleByID(330353751116480512L);
        mentioned.addRole(role);
    }
    public static void removeBotAbuser(IMessage message, IUser mentioned){
        IRole role = message.getGuild().getRoleByID(330346559093080067L);
        mentioned.removeRole(role);
    }
    public static void removePayday(IMessage message, IUser mentioned) {
        IRole role = message.getGuild().getRoleByID(330353751116480512L);
        mentioned.removeRole(role);
    }
    public static void applyRole(IMessage message, IUser mentioned, String role){
        List<IRole> roleList = message.getGuild().getRolesByName(role);
        if(roleList == null){
            Message.sendMessage(message.getChannel(), "The role "+ role + " was not found.");
            Thread.interrupted();
        }
        mentioned.addRole(roleList.get(0));
    }
    public static void applyRole(IMessage message, IUser mentioned, long roleL){
        IRole role = message.getGuild().getRoleByID(roleL);
        if(role == null){
            Message.sendMessage(message.getChannel(), "The role "+ roleL + " was not found.");
            Thread.interrupted();
        }
        mentioned.addRole(role);
    }

}
