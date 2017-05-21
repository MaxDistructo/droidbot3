package maxdistructo.droidbot2.commands;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import maxdistructo.droidbot2.background.Config;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;


public class Casino implements CommandExecutor {
    @Command(aliases = {"/casino" }, description = "Shows some information about the bot.", usage = "!info [author|time]")
    public String onCasinoCommand(String[] args, Message message) {
        User author = message.getAuthor();
        long authorIdLong = author.getIdLong();
        Config.readCasino(authorIdLong);

        if(Config.PLAYER != 0 && args.length == 0){
            return "You have already registered to Doggo Casino";
        }
        else if(Config.PLAYER == 0 && args.length == 0){
            Config.newCasino(authorIdLong);
            return "You have been registered to join Doggo Casino";
        }
        else if(args.length == 1){
            if(args[0].equals("payday")){
                if(Config.MEMBERSHIP.equals(null)){
                    Config.CHIPS = Config.CHIPS + 100;
                    return "You have collected your 100 Doge chips";
                }
                else if (Config.MEMBERSHIP.equals("Member")){
                    Config.CHIPS = Config.CHIPS + 750;
                    return "You have collected your 750 Doge chips";
                }
                else if (Config.MEMBERSHIP.equals("Contributor")){
                    Config.CHIPS = Config.CHIPS + 1500;
                    return "You have collected your 1500 Doge chips";
                }
                else if (Config.MEMBERSHIP.equals("Admin")){
                    Config.CHIPS = Config.CHIPS + 20000;
                    return "You have collected your 20000 Doge chips";
                }
                else if (Config.MEMBERSHIP.equals("Mod")){
                    Config.CHIPS = Config.CHIPS + 12000;
                    return "You have collected your 12000 Doge chips";
                }
                else{
                    Config.CHIPS =  Config.CHIPS + 3000;
                    return "You have collected your 3000 Doge chips";
                }
            }
            else if(args[0].equals("balance")){
                return "You have " + Config.CHIPS + "Doge chips";
            }
        }

        return "Unknown argument!";
    }

}
