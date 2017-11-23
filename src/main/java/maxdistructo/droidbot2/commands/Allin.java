package maxdistructo.droidbot2.commands;

import maxdistructo.droidbot2.commands.casino.CasinoConfig;
import maxdistructo.droidbot2.commands.casino.Casino;
import maxdistructo.droidbot2.core.Perms;
import maxdistructo.droidbot2.core.message.Message;
import sx.blah.discord.handle.obj.IMessage;

public class Allin {
    public static String onAllinCommand(Object[] args, IMessage message){
        CasinoConfig.readCasino(message);
        if (args.length != 2) {
            return "Please specify the amount to multiply your balance by if you win.";
        }
        if(Perms.checkGames(message)) {
            Message.sendMessage(message.getChannel(), "You deposit all of your chips into the machine and pull the lever...");
            int multipy = Integer.valueOf((String) args[1]);
            int random = (int) (Math.random() * multipy + 1);
            int random2 = (int) (Math.random() * multipy + 1);

            if (random == random2) {
                CasinoConfig.CHIPS = CasinoConfig.CHIPS * multipy;
                CasinoConfig.writeCasino(message);
                Casino.checkMembership(message);
                return "You win and have multiplied your chips by " + multipy;
            } else {
                CasinoConfig.CHIPS = 0;
                CasinoConfig.writeCasino(message);
                Casino.checkMembership(message);
                return "You lose and have lost all your chips.";
            }
        }
        return "Please run this command in one of your bot channels.";
    }
}
