package maxdistructo.droidbot2.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import maxdistructo.droidbot2.core.Utils;

public class Say {
    public static String onSayCommand(Object[] args, IMessage message, IChannel mentionedChannel) throws RateLimitException, DiscordException, MissingPermissionsException {
        boolean anotherChannel = false;
        List<IMessage.Attachment> attachments = null;
        attachments = message.getAttachments();
        if(mentionedChannel != null){
            anotherChannel = true;
        }
        
        if (anotherChannel) {
            if(attachments != null){
            return Utils.makeNewString(args, 2) + attachements.get(0).getUrl();
            }
            else{
                return Utils.makeNewString(args, 2);
            }
        }

        else{
            if(attachments != null){
                return Utils.makeNewString(args, 1) + attachments.get(0).getUrl();
            }
            else{ //This is technically not required though I want to make sure it does what it is suppost to do.
               return Utils.makeNewString(args, 1); 
            }
        }
    }
}
