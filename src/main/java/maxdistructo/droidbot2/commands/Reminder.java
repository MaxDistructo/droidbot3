package maxdistructo.droidbot2.commands;

import maxdistructo.droidbot2.background.message.Message;
import sx.blah.discord.handle.obj.IMessage;

public class Reminder {

    public static void onReminderCommand(Object[] args, IMessage message){  //!remindme (time) "Message"
        double time = (int) args[0] * 60000;
        String reminder = null;
        int i = 1;
        while(i < args.length){
            if(i == args.length - 1){
                reminder = reminder + args[i];
                }
                else{
                    reminder = reminder + " " + args[i];
                }
                i++;
        }
        Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(),"Reminder","Your reminder has been set to go off in " + args[0] + " minutes.", message);
        Thread.sleep(time);
        Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(),"Reminder", reminder, message));
        
    }
}
