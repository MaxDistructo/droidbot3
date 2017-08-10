package maxdistructo.droidbot2.commands;

import maxdistructo.droidbot2.background.Config;
import maxdistructo.droidbot2.background.Perms;
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
            Config.CHIPS -= Config.converToInt(args[1]);
            int random = (int)(Math. random() * 50 + 1);
            if(random > 25){
                Config.CHIPS += Config.converToInt(args[1]);
                Config.CHIPS += Config.converToInt(args[1]);
                int wins = Config.converToInt(args[1]) + Config.converToInt(args[1]);
                Config.writeCasino(message);
                Casino.checkMembership(message);
                return "You have successfully won " + wins;
            }
            else{
                Config.writeCasino(message);
                Casino.checkMembership(message);
                return "Sorry! You were unlucky!";
            }

        }
        return "Fifty Fifty has errored. ";
    }
}
