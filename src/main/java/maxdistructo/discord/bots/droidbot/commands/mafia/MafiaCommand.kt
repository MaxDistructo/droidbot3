package maxdistructo.discord.bots.droidbot.commands.mafia

import maxdistructo.discord.bots.droidbot.background.constructor.BaseCommand
import sx.blah.discord.handle.obj.IMessage

open class MafiaCommand : BaseCommand() {
    override val hasOutput: Boolean
        get() = false
    override val requiresAdmin: Boolean
        get() = false
    override val requiresMod: Boolean
        get() = false
    override val commandName: String
        get() = "subcommandName"
    override val helpMessage: String
        get() = "subcommandName <parms> - Description of command"
    val roleRestriction : String
        get() = "none"

    override fun init(message: IMessage, args: List<String>): String {
       return "Mafia Command Error"
    }
}