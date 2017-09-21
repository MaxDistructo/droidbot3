package maxdistructo.droidbot2.commands;

import maxdistructo.droidbot2.background.Config;
import maxdistructo.droidbot2.background.message.Message;
import sx.blah.discord.handle.obj.IMessage;

public class Reminder {

    public static void onReminderCommand(Object[] args, IMessage message){  //!remindme (time) "Message"
        long time = Config.convertToLong(args[0]) * 60000;
        String reminder = null;
        int i = 2;
        while(i < args.length){
            if(i == args.length - 1){
                reminder = reminder + args[i];
                }
                else{
                    reminder = reminder + " " + args[i];
                }
                i++;
        }
        Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(),"Reminder","Your reminder has been set to go off in " + args[0] + " minutes.", message));
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(),"Reminder", reminder, message));
        
    }
}
