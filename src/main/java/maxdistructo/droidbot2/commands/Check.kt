package maxdistructo.droidbot2.commands

import maxdistructo.droidbot2.core.Perms
import sx.blah.discord.handle.obj.IMessage

object Check {
    // @Command(aliases = {"/check"}, description = "Checks if you have perms for a role", usage = "/check")
    fun onCheckCommand(args: Array<String>, message: IMessage): String {
        return " Is Mod: " + Perms.checkMod(message) + "\n Is Admin: " + Perms.checkAdmin(message) + "\n Is Owner: " + Perms.checkOwner(message) + "\n Is Games Channel: " + Perms.checkGames(message)
    }
}
