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
        return message.getAuthor() + " pays their respects. https://cdn.discordapp.com/emojis/276807186800443402.png";
    }
    public static String onBanHammer(IMessage message, IUser mentioned){
        return message.getAuthor() + " picks up the <:blobhammer:315285738302341121> and prepares to swing it at " + mentioned + "! It misses " + mentioned + " by a hair and they live to see another day!";
   }

}
