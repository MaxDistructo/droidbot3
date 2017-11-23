package maxdistructo.droidbot2.commands;

import java.io.IOException;

import maxdistructo.droidbot2.core.Perms;
import maxdistructo.droidbot2.core.message.Message;
import sx.blah.discord.handle.obj.IMessage;

public class Restart {
    public static void run(IMessage message){
        if(Perms.checkAdmin(message)){
         try {
            Process ps = Runtime.getRuntime().exec(new String[] {"java", "-jar", "droidbot2.jar"});
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         Message.sendMessage(message.getChannel(),"Reboot in progress.");
         System.exit(0);
        }
    }
}
