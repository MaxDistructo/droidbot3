package maxdistructo.droidbot2.commands;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import maxdistructo.droidbot2.background.Config;
import maxdistructo.droidbot2.background.Perms;
import sx.blah.discord.handle.impl.obj.Message;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

public class Check implements CommandExecutor {

    @Command(aliases = {"/check"}, description = "Checks if you have perms for a role", usage = "/check")
    public String onCheckCommand(String[] args, Message message) {
        return "Is Mod: " + Perms.checkMod(message) + "\n Is Admin: " + Perms.checkAdmin(message) + "\n Is Owner: " + Perms.checkOwner(message) + "\n Is Games Channel: " + Perms.checkGames(message);
    }
}
