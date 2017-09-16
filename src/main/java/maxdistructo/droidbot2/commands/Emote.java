package maxdistructo.droidbot2.commands;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import maxdistructo.droidbot2.background.message.Message;
import sx.blah.discord.handle.obj.IMessage;

public class Emote {
    
    public static void onEmoteCommand(IMessage message, Object[] args){ //!emote <EmoteName>
    try{
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        Message.sendMessage(message.getChannel(), new File(s + "/droidbot2/config/emotes/" + args[1] + ".png"));
    }
    catch(Exception e){
        Message.sendMessage(message.getChannel(), "Command Error \n Common Causes \n - Emote Not Found (You entered an Invalid Emote Name) \n - Bot Does Not Have perms to upload files.");
    }

    }
}
