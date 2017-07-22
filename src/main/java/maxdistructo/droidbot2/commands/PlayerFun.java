package maxdistructo.droidbot2.commands;

import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

public class PlayerFun //Additions Ideas: Shoot,Stab,Fake mute
{
    public static String onSlapCommand(IMessage message, IUser mentioned){
        return mentioned + " you have been slapped by " + message.getAuthor() + ":clap:";
    }
    public static String onTntCommand(IMessage message, IUser mentioned){
        return mentioned + " you have been blown up by " + message.getAuthor() + " using TNT! :boom:";
    }
    public static String onMarryCommand(IMessage message, IUser mentioned){
        return "This command is WIP";
    }
    public static String onKissCommand(IMessage message, IUser mentioned){
        return mentioned + " you have been kissed by " + message.getAuthor() + ":lips:";
    }
    public static String onHugCommand(IMessage message, IUser mentioned){
        return mentioned + " you have been hugged by " + message.getAuthor() + ":hugging:";
    }
    public static String onPokeCommand(IMessage message, IUser mentioned){
        return mentioned + " you have been poked by " + message.getAuthor() + ":point_right: ";
    }
    public static String onPayRespects(IMessage message, IUser mentioned){ // /f command.
        return message.getAuthor() + " pays their respects. https://cdn.discordapp.com/emojis/294160585179004928.png";
    }
    public static String onBanHammer(IMessage message, IUser mentioned){
        return message.getAuthor() + " picks up the <:blobhammer:315285738302341121> and prepares to swing it at " + mentioned + "! It misses " + mentioned + " by a hair and they live to see another day!";
   }
   public static String onShootCommand(IMessage message, IUser mentioned){
        return message.getAuthor() + " picks up a gun and shoots " + mentioned + "! ";
   }
   public static String onStabCommand(IMessage message, IUser mentioned){
        return mentioned + " was attacked by " + message.getAuthor() + " using a knife!";
   }
   public static String onMuteCommand(IMessage message, IUser mentioned) {
       return mentioned + " was muted by " + message.getAuthor() + " for 1 second.";
   }
   public static String onLennyCommand(){
       return "( ͡° ͜ʖ ͡°)";
   }
   public static String onShrugCommand(){
        return "¯\\_(ツ)_/¯";
   }
   public static String onXpCommand(IUser mentioned){
       return mentioned + "\n ```Diff\n" +
               "- How the XP system works:\n" +
               "XP is gained every time you talk with a 2 minute cooldown. XP is randomized from 10-20. There is global and local XP, local XP is XP gained on that server and is represented in the user's server score. Global XP is global and cannot be modified using commands. \n" +
               "\n" +
               "+ How to get credits: \n" +
               "You get credits for Talking, Leveling, and Using t!dailies. Credits are global and cannot be modified by a command. \n" +
               "```";
   }
   public static String onPunchCommand(IMessage message, IUser mentioned){
       return mentioned + ", you have been punched by " + message.getAuthor() + "! :punch:";
   }
}
