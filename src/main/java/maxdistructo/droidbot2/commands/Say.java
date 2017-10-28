package maxdistructo.droidbot2.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.List;

import maxdistructo.droidbot2.background.Utils;

public class Say {
    public static String onSayCommand(Object[] args, IMessage message, IChannel mentionedChannel) throws RateLimitException, DiscordException, MissingPermissionsException {
        boolean anotherChannel = false;
        if(mentionedChannel != null){
            anotherChannel = true;
        }
        
        if (anotherChannel) {
            return Utils.makeNewString(args, 2);
        }

        else{
            return Utils.makeNewString(args, 1);
        }
    }
}
