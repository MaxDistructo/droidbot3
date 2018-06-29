package maxdistructo.discord.bots.droidbot.background

import kotlinx.coroutines.experimental.launch
import maxdistructo.discord.bots.droidbot.background.constructor.BaseCommand
import maxdistructo.discord.bots.droidbot.background.constructor.BaseListener
import maxdistructo.discord.core.Config
import maxdistructo.discord.core.Perms
import maxdistructo.discord.core.message.Message
import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent

class BetaListener : BaseListener() {

    override var commandsArray = listOf<BaseCommand>()
    override var adminCommands = listOf<BaseCommand>()
    override var modCommands = listOf<BaseCommand>()

    override fun registerCommand(vararg commands : BaseCommand){
        for(command in commands) {
            when {
                command.requiresAdmin -> adminCommands += command
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
    override fun onMessageRecievedEvent(event : MessageReceivedEvent){
        val message = event.message
        val prefix = Config.readPrefix()
        val messageContent = message.content.split(" ")

        if(message.content.startsWith(prefix)) {
            launch {
                when {
                    Perms.checkAdmin(message) -> for (command in adminCommands) {
                        if (messageContent[0] == prefix + command.commandName) {
                            if(command.hasOutput) {
                                Message.sendMessage(message.channel, command.init(message, messageContent))
                                message.delete()
                            }
                        }
                    }
                    Perms.checkMod(message) -> for (command in modCommands) {
                        if (messageContent[0] == prefix + command.commandName) {
                            if(command.hasOutput) {
                                Message.sendMessage(message.channel, command.init(message, messageContent))
                                message.delete()
                            }
                        }
                    }
                    else -> for (command in commandsArray) {
                        if (messageContent[0] == prefix + command.commandName) {
                            if(command.hasOutput) {
                                Message.sendMessage(message.channel, command.init(message, messageContent))
                                message.delete()
                            }
                        }
                    }
                }
            }
        }

    }
}