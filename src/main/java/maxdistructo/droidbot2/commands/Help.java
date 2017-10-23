package maxdistructo.droidbot2.commands;

import maxdistructo.droidbot2.BaseBot;
import maxdistructo.droidbot2.background.Listener;
import maxdistructo.droidbot2.background.message.Message;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;



public class Help {

    public static String onHelpCommand(){
        IUser user = BaseBot.client.getOurUser();
        return "Commands List for " + user.mention(true) + "\n \n" +
                Listener.prefix + "50|fifty <bet>: A fifty fifty game for the casino. \n" +
                Listener.prefix + "allin <multiplier>: You bet all your chips hoping to mulitply your balance by the multiplier. \n"+
                Listener.prefix + "banhammer <@user>: Hits another user with the banhammer maybe\n" +
                Listener.prefix + "bj <bet>: Modified Blackjack Game\n"+
                Listener.prefix + "casino <join|payday|balance>: Casino Commands\n"+
                Listener.prefix + "check: Checks if user has any special perms.\n" +
                Listener.prefix + "emote <request|emote name>: Will display the emote or create a request to add it \n"+
                Listener.prefix + "fortune: Gets your fortune\n" +
                Listener.prefix + "hug <@user>: Hugs another user\n"+
                Listener.prefix + "info: Gets your info\n" +
                Listener.prefix + "insult <@user>: Insults another discord user \n"+
                Listener.prefix + "kiss <@user>: Kiss another user.\n"+
                Listener.prefix + "mute <@user>: Mutes another user maybe\n" +
                Listener.prefix + "poke <@user>: Pokes another user\n"+
                Listener.prefix + "punch <@user>: Punches another user\n"+
                Listener.prefix + "respect | /f: Pays Respects \n" +
                Listener.prefix + "say [channel]: Says the following message in this channel or another specified channel\n"+
                Listener.prefix + "shoot <@user>: Shoots a user with a gun\n"+
                Listener.prefix + "slap <@user>: Slaps another user.\n"+
                Listener.prefix + "spam <@user>: Spams the other user in DMs. MUST BE MOD+ TO USE ON OTHER USERS!\n"+
                Listener.prefix + "stab <@user>: Stabs a user\n"+
                Listener.prefix + "tnt <@user>: Blow up another user." +
                Listener.prefix + "xp [@user]: Shows the rules on Tatsu XP system. Can have a user mention to show another user.\n";
    }
    public static void onAdminHelpCommand(IMessage message){
        Message.sendDM(message.getAuthor(),onHelpCommand());
        String sendMessage = "Admin Commands List for " + BaseBot.client.getOurUser().mention(true) + "\n \n"+
                Listener.prefix + "@admin addMod <@User>: Adds a user as a Moderator" +
                Listener.prefix + "@admin addAdmin <@User>: Adds a user as a Administrator"+
                Listener.prefix + "@casino balance add|remove|set <@User> <balanceChange: Changes a Users Casino balance."+
                Listener.prefix + "@admin botAbuse <@User>: Marks a user as a bot abuser and denies them access to bot commands." +
                Listener.prefix + "@admin perms "
                ;
        Message.sendDM(message.getAuthor(),sendMessage);

    }
}