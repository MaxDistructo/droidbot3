package maxdistructo.droidbot2.commands;

import maxdistructo.droidbot2.BaseBot;
import maxdistructo.droidbot2.background.Listener;
import sx.blah.discord.handle.obj.IUser;


public class Help {

    public static String onHelpCommand(){
        IUser user = BaseBot.client.getOurUser();
        return "Commands List for " + user.mention(true) + "\n \n" + Listener.prefix + "50|fifty <bet>: A fifty fifty game for the casino. \n" + Listener.prefix + "allin <multiplier>: You bet all your chips hoping to mulitply your balance by the multiplier. \n"+ Listener.prefix + "casino <join|payday|balance>: Casino Commands\n"+ Listener.prefix + "check: Checks if user has any special perms.\n" + Listener.prefix +
                "fortune: Gets your fortune\n" + Listener.prefix + "info: Gets your info\n" + Listener.prefix + "insult <@user>: Insults another discord user \n";
    }
}
