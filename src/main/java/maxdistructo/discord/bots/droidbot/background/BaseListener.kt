package maxdistructo.discord.bots.droidbot.background

import maxdistructo.discord.core.command.BaseCommand
import maxdistructo.discord.core.command.IBaseListener
import maxdistructo.discord.core.command.ICommandRegistry
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent
import java.util.*

open class BaseListener : IBaseListener {
    override var adminCommands: List<BaseCommand> = listOf()
    override var commandRegistries = LinkedList<ICommandRegistry>()
    override var commandsArray: List<BaseCommand> = listOf()
    override var modCommands: List<BaseCommand>  = listOf()
    override val name: String = "Botfather.Base"

    override fun addCommandRegistry(registry: ICommandRegistry) {
        commandRegistries.add(registry)
    }

    override fun createCommands() {
        for(value in commandRegistries){
            for(command in value.commandHolder){
                this.registerCommand(command)
            }
        }
    }

    override fun onMessageReceivedEvent(event: MessageReceivedEvent){} //Will not be used ever
}