package maxdistructo.droidbot2.commands;

import sx.blah.discord.handle.obj.IMessage;

public class Say {
    public static String onSayCommand(Object[] args, IMessage message){
        int i = 1;
        String makeNewString = "";
        while(i < args.length){
            makeNewString = makeNewString + " " + args[i];
            i++;
        }
        return makeNewString;
    }
}
