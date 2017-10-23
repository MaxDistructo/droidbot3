package maxdistructo.droidbot2.commands;

import maxdistructo.droidbot2.BaseBot;
import maxdistructo.droidbot2.background.Config;
import maxdistructo.droidbot2.background.Perms;
import maxdistructo.droidbot2.background.message.Message;
import sx.blah.discord.handle.obj.*;

public class Spam {
    public static String onSpamCommand(Object[] args, IMessage message, IUser mentioned){
        //Spams a user in their DMs Command: prefix + spam <@User> NumberOfTimes
        IUser author = message.getAuthor();
        IUser spamPlayer = mentioned;
        int spamNum;
        if(args.length == 3){
            spamNum = Config.converToInt(args[2]);
        }
        else{
            return "You did not enter enough arguments to run this command.";
        }
        if(mentioned == BaseBot.client.getOurUser() && !Perms.checkMod(message)){
            spamPlayer = author;
        }
        int i = 0;
        while(i < spamNum){
            try {
                Thread.sleep(1250L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message.sendDM(spamPlayer, "YOU GOT SPAMMED SON!");
            i++;
        }
        return "Player " + spamPlayer + " was successfully spammed " + spamNum + " times.";

    }
}
