package maxdistructo.discord.bots.droidbot.background

import maxdistructo.discord.core.command.BaseCommand
import maxdistructo.discord.core.command.IBaseListener
import maxdistructo.discord.core.command.ICommand
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent
import java.util.*

open class BaseListener : IBaseListener {
    override var commandRegistry: LinkedList<ICommand> = LinkedList()

    override fun addCommand(command: ICommand) {
        commandRegistry.add(command)
    }

    override var adminCommands: List<BaseCommand> = listOf()
    override var commandsArray: List<BaseCommand> = listOf()
    override var modCommands: List<BaseCommand>  = listOf()
    override val name: String = "Botfather.Base"


    override fun createCommands() {
    }

    override fun onMessageReceivedEvent(event: MessageReceivedEvent){} //Will not be used ever
}