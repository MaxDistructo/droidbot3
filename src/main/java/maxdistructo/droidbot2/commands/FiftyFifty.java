package maxdistructo.droidbot2.commands;

import maxdistructo.droidbot2.commands.casino.CasinoConfig;
import maxdistructo.droidbot2.commands.casino.Casino;
import maxdistructo.droidbot2.core.Perms;
import maxdistructo.droidbot2.core.Utils;
import sx.blah.discord.handle.obj.*;

public class FiftyFifty{
    //@Command(aliases = {"/50","/fifty"}, description = "Fifty Fifty chance of doubling your money imputed", usage = "/50|fifty <bet>")
    public static String onFiftyCommand(Object[] args, IMessage message){
        IUser author = message.getAuthor();
        Casino.checkMembership(message);

        if(args.length == 1){
            return "You have not entered a bet. Please enter a bet by using the command /50|fifty <bet>";
        }
        else if(args.length == 2 && Integer.valueOf((String)args[1]) > 10 && Integer.valueOf((String)args[1]) < 500000 && Perms.checkGames(message)){
            CasinoConfig.CHIPS -= Utils.convertToInt(args[1]);
            int random = (int)(Math. random() * 50 + 1);
            if(random > 25){
                CasinoConfig.CHIPS += Utils.convertToInt(args[1]);
                CasinoConfig.CHIPS += Utils.convertToInt(args[1]);
                int wins = Utils.convertToInt(args[1]) + Utils.convertToInt(args[1]);
                CasinoConfig.writeCasino(message);
                Casino.checkMembership(message);
                return "You have successfully won " + wins;
            }
            else{
                CasinoConfig.writeCasino(message);
                Casino.checkMembership(message);
                return "Sorry! You were unlucky!";
            }

        }
        return "Fifty Fifty has errored. ";
    }
}
