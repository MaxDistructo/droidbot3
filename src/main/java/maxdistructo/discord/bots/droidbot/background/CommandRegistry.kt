package maxdistructo.discord.bots.droidbot.background

import maxdistructo.discord.core.command.BaseCommand
import maxdistructo.discord.core.command.IBaseListener
import maxdistructo.discord.core.command.ICommandRegistry
import java.util.*

open class CommandRegistry : ICommandRegistry {
    override var commandHolder =  LinkedList<BaseCommand>()

    override fun init(listener: IBaseListener) {
        for(value in commandHolder){
            listener.registerCommand(value)
        }
    }
}