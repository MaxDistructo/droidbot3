package maxdistructo.discord.bots.droidbot.background.constructor

import maxdistructo.discord.bots.droidbot.BaseBot
import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent

open class BaseListener {
    open fun registerCommand(vararg commands : BaseCommand){
        for(command in commands) {
            when {
                command.requiresAdmin -> {
                    adminCommands += command
                }
                command.requiresMod -> {
                    adminCommands += command
                    modCommands += command
                }
                else -> {
                    adminCommands += command
                    modCommands += command
                    commandsArray += command
                }
            }
        }
    }
    @EventSubscriber
    open fun onMessageReceivedEvent(event : MessageReceivedEvent){
    }
    open var commandsArray = listOf<BaseCommand>()
    open var adminCommands = listOf<BaseCommand>()
    open var modCommands = listOf<BaseCommand>()
}