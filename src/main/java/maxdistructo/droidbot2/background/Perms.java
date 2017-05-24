package maxdistructo.droidbot2.background;

import java.util.List;

import sx.blah.discord.handle.impl.obj.Message;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;

public class Perms {
    public static boolean checkMod(Message message){
        IUser author = message.getAuthor();
        IGuild guild = message.getGuild();
        List<IRole> roleList = author.getRolesForGuild(guild);
        IRole[] roleArray = roleList.toArray(new IRole[0]);
        String [] roles = new String[roleArray.length];
        int i = 0;
        
        while(i < roles.length){
            if(roles[i] == "Moderator" || roles[i] == "Administrator" || roles[i] == "Bot Moderator"){
                i = roles.length;
                return true;     
            }
            else{
                i++;
            }
        }
        return false;
          
        }
        
    public static boolean checkAdmin(Message message){
        IUser author = message.getAuthor();
        IGuild guild = message.getGuild();
        List<IRole> roleList = author.getRolesForGuild(guild);
        IRole[] roleArray = roleList.toArray(new IRole[0]);
        String [] roles = new String[roleArray.length];
        int i = 0;
        while(i < roles.length){
            if(roles[i] == "Administrator" || roles[i] == "Bot Moderator"){
                Config.ISMOD = true;
                i = roles.length;
                return true;     
            }
            else{
                Config.ISMOD = false;
                i++;
            }
        }
        return false;
}
    public static boolean checkOwner(Message message){
        IUser author = message.getAuthor();
        IGuild guild = message.getGuild();
        List<IRole> roleList = author.getRolesForGuild(guild);
        IRole[] roleArray = roleList.toArray(new IRole[0]);
        String [] roles = new String[roleArray.length];
        int i = 0;
        while(i < roles.length){
            if(roles[i] == "Bot Moderator"){
                Config.ISMOD = true;
                i = roles.length;
                return true;     
            }
            else{
                Config.ISMOD = false;
                i++;
            }
        }
        return false;
    }
    
    public static boolean checkGames(Message message){
        IChannel channel = message.getChannel();
        String channelName = channel.getName();
        
        if(channelName == "games"){
            return true;
        }
        else{
            return false;
        }
    }
}
