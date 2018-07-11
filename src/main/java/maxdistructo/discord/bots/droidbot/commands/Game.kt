package maxdistructo.discord.bots.droidbot.commands

import maxdistructo.discord.core.command.BaseCommand
import sx.blah.discord.handle.obj.IMessage

class Game : BaseCommand() {

    override val commandName: String
        get() = "Game"
    override val requiresMod: Boolean
        get() = false
    override val requiresAdmin: Boolean
        get() = false
    override val helpMessage : String
        get() = ""

    override fun init(message : IMessage, args : List<String>) : String {
        return "Command Error: $commandName"
    }
}