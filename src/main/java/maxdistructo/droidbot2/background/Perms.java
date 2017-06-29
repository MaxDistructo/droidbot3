package maxdistructo.droidbot2.background;

import java.util.List;

import sx.blah.discord.handle.obj.*;

public class Perms {
    public static boolean checkMod(IMessage message){
        IUser author = message.getAuthor();
        //Checks if user is a Moderator of the server
        long[] moderators = Config.readServerModConfig(message.getGuild());
        int i = 0;
        while(i < moderators.length){
            if(author.getLongID() == moderators[i]){
                return true;
            }
            i++;
        }
        return false;
    }

    public static boolean checkAdmin(IMessage message){
        IUser author = message.getAuthor();
        IGuild guild = message.getGuild();
        //Checks if user is a Admin of Doggo (Or Myself).
        long[] admins = Config.readServerAdminConfig(message.getGuild());
        int i = 0;
        while(i < admins.length){
            if(author.getLongID() == admins[i]){
                return true;
            }
            i++;
        }
        return false;
    }
    public static boolean checkOwner(IMessage message){
       IUser author = message.getAuthor();
       if(author.getLongID() == 228111371965956099L){
           return true;
       }
       else{
           return false;
       }
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
}
