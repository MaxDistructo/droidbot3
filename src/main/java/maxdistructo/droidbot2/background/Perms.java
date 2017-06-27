package maxdistructo.droidbot2.background;

import java.util.List;

import sx.blah.discord.handle.obj.*;

public class Perms {
    public static boolean checkMod(IMessage message){
        IUser author = message.getAuthor();
        IGuild guild = message.getGuild();
        //Checks if user is a Moderator of Doggo.
        if(author.getID().equals("261700221606690816") || author.getID() .equals("228111371965956099") || author.getID().equals("256964987073855488") || author.getID().equals("265173874738593792") || author.getID().equals("194943753490792449") && guild.getID().equals("314569556809220118")){
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
        if(author.getID().equals("261700221606690816") || author.getID().equals("228111371965956099") && guild.getID().equals("314569556809220118")){
            return true;
        }
        else{
            return false;
        }
    }
    public static boolean checkOwner(IMessage message){
       IUser author = message.getAuthor();
       if(author.getID().equals("228111371965956099")){
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
