package maxdistructo.droidbot2.commands;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import maxdistructo.droidbot2.BaseBot;
import maxdistructo.droidbot2.background.Perms;
import maxdistructo.droidbot2.background.message.Message;
import org.apache.commons.io.FileUtils;
import sx.blah.discord.handle.obj.IMessage;

public class Emote {
    
    public static void onEmoteCommand(IMessage message, Object[] args){ //!emote <EmoteName>

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        File file = new File(s + "/droidbot/config/emotes/" + args[1] + ".png");
        if(file.exists()){
            Message.sendMessage(message.getChannel(),file);
        }
        else{
            Message.sendMessage(message.getChannel(),"Emote not found.");
        }


    }
    public static void addEmoteCommand(IMessage message, Object[] args) { //!emote add <EmoteName> <URL>
        if (Perms.checkOwner(message)) {
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            File file = new File(s + "/droidbot/config/emotes/" + args[2] + ".png");
            URL url = null;
            try {
                url = new URL((String)args[3]);
            } catch (MalformedURLException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();

            }
            try {
                FileUtils.copyURLToFile(url, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            Message.sendMessage(message.getChannel(), "You do not have perms to add Emotes.");
        }
    }
    public static void requestEmoteCommand(IMessage message, Object[] args){ //!emote request <EmoteName> <URL>
        Message.sendDM(BaseBot.client.getApplicationOwner(),"User: " + message.getAuthor().getName() + message.getAuthor().getDiscriminator() + " would like you to add the emote " + args[2] + " which is found at " + args[3]);
    }
}
