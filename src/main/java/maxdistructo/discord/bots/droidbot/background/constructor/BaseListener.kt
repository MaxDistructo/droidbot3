package maxdistructo.discord.bots.droidbot.background.constructor

import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent

open class BaseListener {
    open fun registerCommand(vararg commands : BaseCommand){

    }

    @EventSubscriber
    open fun onMessageRecievedEvent(event : MessageReceivedEvent){

    }
    open var commandsArray = listOf<BaseCommand>()
    open var adminCommands = listOf<BaseCommand>()
    open var modCommands = listOf<BaseCommand>()
}