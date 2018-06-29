package maxdistructo.discord.bots.droidbot.commands

import maxdistructo.discord.bots.droidbot.background.PrivUtils
import maxdistructo.discord.bots.droidbot.background.constructor.BaseCommand
import maxdistructo.discord.core.Perms
import sx.blah.discord.handle.obj.IMessage

class Check : BaseCommand(){
    override val commandName: String
        get() = "check"
    override val helpMessage: String
        get() = "check - Checks permissions for yourself."

    override fun init(message: IMessage, args: List<String>): String {
        return onCheckCommand(PrivUtils.listToArray(args), message)
    }
    // @Command(aliases = {"/check"}, description = "Checks if you have perms for a role", usage = "/check")
    fun onCheckCommand(args: Array<String>, message: IMessage): String {
        return " Is Mod: " + Perms.checkMod(message) + "\n Is Admin: " + Perms.checkAdmin(message) + "\n Is Owner: " + Perms.checkOwner(message) + "\n Is Games Channel: " + Perms.checkGames(message) + "\n Mafia: \n Is Admin: " + maxdistructo.discord.bots.droidbot.commands.mafia.Perms.checkAdmin(message) + "\n Is Mod: " + maxdistructo.discord.bots.droidbot.commands.mafia.Perms.checkMod(message) + "\n  Is Mafia Channel: " + maxdistructo.discord.bots.droidbot.commands.mafia.Perms.checkMafiaChannels(message)
    }
}
