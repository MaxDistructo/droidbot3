package maxdistructo.droidbot2.background;

import maxdistructo.droidbot2.commands.Check;
import maxdistructo.droidbot2.commands.Debug;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Listener {
    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event) { // This method is NOT called because it doesn't have the @EventSubscriber annotation
        event.getAuthor();
    }

    public void onReadyEvent(ReadyEvent event){

    }

}
