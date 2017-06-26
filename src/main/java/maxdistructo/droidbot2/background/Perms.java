package maxdistructo.droidbot2.background;

import java.util.List;

import sx.blah.discord.handle.obj.*;

public class Perms {
    public static boolean checkMod(IMessage message){
        IUser author = message.getAuthor();
        IGuild guild = message.getGuild();
        //Checks if user is a Moderator of Doggo.
        if(author.getLongID() == 261700221606690816L || author.getLongID() == 228111371965956099L || author.getLongID() == 256964987073855488L || author.getLongID() == 265173874738593792L || author.getLongID() == 194943753490792449L && guild.getLongID() == 314569556809220118L){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean checkAdmin(IMessage message){
        IUser author = message.getAuthor();
        IGuild guild = message.getGuild();
        //Checks if user is a Admin of Doggo (Or Myself).
        if(author.getLongID() == 261700221606690816L || author.getLongID() == 228111371965956099L && guild.getLongID() == 314569556809220118L){
            return true;
        }
        else{
            return false;
        }
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

        if(channelName.equals("games") || channelName.equals("bot") || channelName.equals("botmod")){
            return true;
        }
        else{
            return false;
        }
    }
}
