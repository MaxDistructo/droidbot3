package maxdistructo.droidbot2.commands;

import com.sun.javaws.jnl.LibraryDesc;
import maxdistructo.droidbot2.BaseBot;
import maxdistructo.droidbot2.background.Listener;
import sx.blah.discord.handle.obj.IUser;

import javax.management.ListenerNotFoundException;


public class Help {

    public static String onHelpCommand(){
        IUser user = BaseBot.client.getOurUser();
        return "Commands List for " + user.mention(true) + "\n \n" +
                Listener.prefix + "50|fifty <bet>: A fifty fifty game for the casino. \n" +
                Listener.prefix + "allin <multiplier>: You bet all your chips hoping to mulitply your balance by the multiplier. \n"+
                Listener.prefix + "banhammer <@user>: Hits another user with the banhammer maybe\n" +
                Listener.prefix + "casino <join|payday|balance>: Casino Commands\n"+
                Listener.prefix + "check: Checks if user has any special perms.\n" +
                Listener.prefix + "fortune: Gets your fortune\n" +
                Listener.prefix + "hug<@user>: Hugs another user\n"+
                Listener.prefix + "info: Gets your info\n" +
                Listener.prefix + "insult <@user>: Insults another discord user \n"+
                Listener.prefix + "kiss <@user>: Kiss another user.\n"+
                Listener.prefix + "mute <@user>: Mutes another user maybe\n" +
                Listener.prefix + "poke <@user>: Pokes another user\n"+
                Listener.prefix + "respect | /f: Pays Respects \n" +
                Listener.prefix + "say [channel]: Says the following message in this channel or another specified channel\n"+
                Listener.prefix + "shoot <@user>: Shoots a user with a gun\n"+
                Listener.prefix + "slap <@user>: Slaps another user.\n"+
                Listener.prefix + "spam <@user>: Spams the other user in DMs.\n"+
                Listener.prefix + "stab <@user>: Stabs a user\n"+
                Listener.prefix + "tnt <@user>: Blow up another user.";
    }
}
