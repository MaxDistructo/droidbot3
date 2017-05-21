package maxdistructo.droidbot2.commands;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Casino implements CommandExecutor {
    @Command(aliases = {"/casino" }, description = "Shows some information about the bot.", usage = "!info [author|time]")
    public String onCasinoCommand(String[] args) {
        if (args.length > 1) { // more than 1 argument
            return "Too many arguments!";
        }
        if (args.length == 0) { // !info
            return  " Doggo Casino\n" +
                    "- Current Status: Online\n" +
                    "- Programer; @MaxDistructo#3927";
        }
        if (args.length == 1) { // 1 argument
            if (args[0].equals("register")) { // !info author
                return "- **Owner:** @MaxDistructo#3927\n" +
                        "- **Age:** 16\n" +
                        "- **Website:** https://maxdistructo.github.io";
            }
            if (args[0].equals("time")) { // !info time
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                Date currentDate = new Date(System.currentTimeMillis());
                return "It's" + format.format(currentDate);
            }
        }
        return "Unknown argument!";
    }

}
