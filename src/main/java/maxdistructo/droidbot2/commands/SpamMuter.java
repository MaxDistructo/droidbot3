package maxdistructo.droidbot2.commands;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;

public class SpamMuter {
    public static int spamMessageCounter;
    public static IUser spammer;
    @EventSubscriber
    public static void onMessageRecieveEvent(MessageReceivedEvent event){
        IUser author = event.getMessage().getAuthor();
        if(spammer == author){

        }
    }
}
