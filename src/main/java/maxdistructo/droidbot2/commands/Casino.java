package maxdistructo.droidbot2.commands;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import maxdistructo.droidbot2.background.Config;
import maxdistructo.droidbot2.background.Perms;
import sx.blah.discord.handle.obj.*;


public class Casino implements CommandExecutor {
    @Command(aliases = {"/casino" }, description = "Casino Commands.", usage = "/casino [payday|balance]")
    public String onCasinoCommand(Object[] args, IMessage message) {
        IUser author = message.getAuthor();

        if(Config.PLAYER.equals("null") && args.length == 1 && args[0].equals("join")){
            Config.newCasino(author);
            return "You have been registered to join Doggo Casino";
        }
        else if(args.length == 1){
            if(args[0].equals("payday")){
                checkMembership(author);
                if(Config.MEMBERSHIP.equals("null")){
                    Config.CHIPS = Config.CHIPS + 1000;
                    Config.writeCasino(author);
                    return "You have collected your 1,000 Doge chips";
                }
                else if (Config.MEMBERSHIP.equals("A")){
                    Config.CHIPS = Config.CHIPS + 1500;
                    Config.writeCasino(author);
                    return "You have collected your 1,500 Doge chips";
                }
                else if (Config.MEMBERSHIP.equals("B")){
                    Config.CHIPS = Config.CHIPS + 1500;
                    Config.writeCasino(author);
                    return "You have collected your 1,500 Doge chips";
                }
                else if (Config.MEMBERSHIP.equals("C")){
                    Config.CHIPS = Config.CHIPS + 1800;
                    Config.writeCasino(author);
                    return "You have collected your 1,800 Doge chips";
                }
                else if (Config.MEMBERSHIP.equals("D")){
                    Config.CHIPS = Config.CHIPS + 2000;
                    Config.writeCasino(author);
                    return "You have collected your 2,000 Doge chips";
                }
                else if (Config.MEMBERSHIP.equals("E")){
                    Config.CHIPS = Config.CHIPS + 3000;
                    Config.writeCasino(author);
                    return "You have collected your 3,000 Doge chips";
                }
                else if (Config.MEMBERSHIP.equals("F")){
                    Config.CHIPS = Config.CHIPS + 4500;
                    Config.writeCasino(author);
                    return "You have collected your 4,500 Doge chips";
                }
                else if (Config.MEMBERSHIP.equals("G")){
                    Config.CHIPS = Config.CHIPS + 6000;
                    Config.writeCasino(author);
                    return "You have collected your 6,000 Doge chips";
                }
                else if (Config.MEMBERSHIP.equals("H")){
                    Config.CHIPS = Config.CHIPS + 6000;
                    Config.writeCasino(author);
                    return "You have collected your 6,000 Doge chips";
                }
                else if(Config.MEMBERSHIP.equals("I")){
                    Config.CHIPS =  Config.CHIPS + 7500;
                    Config.writeCasino(author);
                    return "You have collected your 7,500 Doge chips";
                }
                else{
                    return "Command \"/casino payday\" has errored. Your balance has not been affected.";
                }
            }
            else if(args[0].equals("balance")){
                return "You have " + Config.CHIPS + "Doge chips";
            }
            else if(args[0].equals("set") && args[1].equals("balance") && Perms.checkMod(message)){
                IUser modify = (IUser)args[2];
                int chips = (int)args[3];
                Config.readCasino(modify);
                Config.CHIPS = chips;
                checkMembership(modify);
                Config.writeCasino(modify);
                return author.mention() + "Balance of " + chips + " was successfully set for " + modify.mention();
            }
            else if(args[0].equals("add") && args[1].equals("balance") && Perms.checkMod(message)){
                IUser modify = (IUser)args[2];
                int chips = (int)args[3];
                Config.readCasino(modify);
                Config.CHIPS = Config.CHIPS + chips;
                checkMembership(modify);
                Config.writeCasino(modify);
                return author.mention() + "Balance of " + Config.CHIPS + " was successfully set for " + modify.mention();
            }
        }

        return "Error in command: Casino. Possible causes: Triggered by bot user, unknown argument, or User has attemped to run a command that they shouldn't.";
    }
    public static void checkMembership(IUser user){
        Config.readCasino(user);
        if(Config.CHIPS < 10000){
            Config.MEMBERSHIP = "null";
        }
        else if(Config.CHIPS > 10000 && Config.CHIPS < 25000){
            Config.MEMBERSHIP = "A";
        }
        else if(Config.CHIPS > 25000 && Config.CHIPS < 60000){
            Config.MEMBERSHIP = "B";
        }
        else if(Config.CHIPS > 60000 && Config.CHIPS < 100000){
            Config.MEMBERSHIP = "C";
        }
        else if(Config.CHIPS > 100000 && Config.CHIPS < 200000){
            Config.MEMBERSHIP = "D";
        }
        else if(Config.CHIPS > 200000 && Config.CHIPS < 350000){
            Config.MEMBERSHIP = "E";
        }
        else if(Config.CHIPS > 350000 && Config.CHIPS < 500000){
            Config.MEMBERSHIP = "F";
        }
        else if(Config.CHIPS > 500000 && Config.CHIPS < 800000){
            Config.MEMBERSHIP = "G";
        }
        else if(Config.CHIPS > 800000 && Config.CHIPS < 1000000){
            Config.MEMBERSHIP = "H";
        }
        else{
            Config.MEMBERSHIP = "I";
        }
    }

}
