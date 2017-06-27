package maxdistructo.droidbot2.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.List;

public class Say {
    public static String onSayCommand(Object[] args, IMessage message, IChannel mentionedChannel) throws RateLimitException, DiscordException, MissingPermissionsException {
        int i = 1;

        boolean anotherChannel = false;
        List<IChannel> channelList = message.getGuild().getChannels();
        Object[] channelArray = channelList.toArray();

        while(i < channelArray.length){
            if(channelArray[i] == mentionedChannel){
                anotherChannel = true;
                System.out.println("Found Mentioned Channel - Channel");
                i = channelArray.length;
            }
            else{
                anotherChannel = false;
                System.out.println("Mentioned Channel was not found.");
            }
            i++;
        }
        i = 1;
        if (anotherChannel) {
            i = 2;
            String makeNewString = "";
            System.out.println("Begin making new string.");
            while (i < args.length) {
                makeNewString = makeNewString + " " + args[i];
                i++;
            }
            System.out.println("End making new string.");
            message.delete();
            System.out.println("Sent new string to be sent.");
            return makeNewString;
        }

        else{
            String makeNewString = "";
            System.out.println("Begin making new string.");
            while (i < args.length) {
                makeNewString = makeNewString + " " + args[i];
                i++;
            }
            System.out.println("End making new string.");
            message.delete();
            System.out.println("Sent new string to be sent.");
            return makeNewString;
        }
    }
}
