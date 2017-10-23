package maxdistructo.droidbot2.background;


import maxdistructo.droidbot2.BaseBot;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.PermissionUtils;

public class Perms {
    public static boolean checkMod(IMessage message){
        IUser author = message.getAuthor();
        //Checks if user is a Moderator of the server
        long[] moderators = Config.readServerModConfig(message.getGuild());
        int i = 0;
        while(i < moderators.length){
            if(author.getLongID() == moderators[i] || author == message.getGuild().getOwner() || author == BaseBot.client.getApplicationOwner() || checkAdmin(message)){
                return true;
            }
            i++;
        }
        return false;
    }

    public static boolean checkAdmin(IMessage message){
        IUser author = message.getAuthor();
        //Checks if user is a Admin/Owner of the Server (Or Myself).
        long[] admins = Config.readServerAdminConfig(message.getGuild());
        int i = 0;
        while(i < admins.length){
            if(author.getLongID() == admins[i] || author == message.getGuild().getOwner() || author == BaseBot.client.getApplicationOwner()){
                return true;
            }
            i++;
        }
        return false;
    }

    public static boolean checkOwner_Guild(IMessage message){
        IUser author = message.getAuthor();

        return author.getLongID() == BaseBot.client.getApplicationOwner().getLongID() || author.getLongID() == message.getGuild().getOwnerLongID();
    }
    public static boolean checkOwner(IMessage message){
       IUser author = message.getAuthor();
        return author.getLongID() == BaseBot.client.getApplicationOwner().getLongID();
    }

    public static boolean checkGames(IMessage message){
        IChannel channel = message.getChannel();
        String channelName = channel.getName();

        String[] channels = Config.readServerGamesConfig(message.getGuild());
        int i = 0;
        while(i < channels.length){
            if(channelName.equals(channels[i])){
                return true;
            }
            i++;
        }
        return false;
    }
    public static boolean checkForPermission(IMessage message, Permissions permission){
        return PermissionUtils.hasPermissions(message.getGuild(), message.getAuthor(), permission);
    }
}
