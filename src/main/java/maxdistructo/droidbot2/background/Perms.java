package maxdistructo.droidbot2.background;

import java.util.List;

import sx.blah.discord.handle.obj.*;

public class Perms {
    public static boolean checkMod(IMessage message){
        IUser author = message.getAuthor();
        IGuild guild = message.getGuild();
        List<IRole> roleList = author.getRolesForGuild(guild);
        Object[] roleArray = roleList.toArray(new Object[0]);
        int i = 0;
        
        while(i < roleArray.length){
            if(roleArray[i].equals("Moderator") || roleArray[i].equals("Administrator") || roleArray[i].equals("Bot Moderator")){
                i = roleArray.length;
                return true;     
            }
            else{
                i++;
            }
        }
        return false;
          
        }
        
    public static boolean checkAdmin(IMessage message){
        IUser author = message.getAuthor();
        IGuild guild = message.getGuild();
        List<IRole> roleList = author.getRolesForGuild(guild);
        Object[] roleArray = roleList.toArray(new Object[0]);
        int i = 0;
        while(i < roleArray.length){
            if(roleArray[i] == "Administrator" || roleArray[i] == "Bot Moderator"){
                Config.ISMOD = true;
                i = roleArray.length;
                return true;     
            }
            else{
                Config.ISMOD = false;
                i++;
            }
        }
        return false;
}
    public static boolean checkOwner(IMessage message){
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
    
    public static boolean checkGames(IMessage message){
        IChannel channel = message.getChannel();
        String channelName = channel.getName();
        
        if(channelName == "casino"){
            return true;
        }
        else{
            return false;
        }
    }
}
