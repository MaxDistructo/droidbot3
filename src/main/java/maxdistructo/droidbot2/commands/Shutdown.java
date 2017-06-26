package maxdistructo.droidbot2.commands;

import maxdistructo.droidbot2.background.Perms;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IMessage;

public class Shutdown {

    public static String onShutdownCommand(IMessage message){
        if(Perms.checkOwner(message)){
            message.reply("Shutting Down");
            System.exit(0);
            return "";
        }
        return "You do not have perms to shutdown this bot.";
    }
}
