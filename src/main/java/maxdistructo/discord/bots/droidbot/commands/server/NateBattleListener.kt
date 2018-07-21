package maxdistructo.discord.bots.droidbot.commands.server

import maxdistructo.discord.bots.droidbot.background.BaseListener
import maxdistructo.discord.core.Config
import maxdistructo.discord.core.Perms
import maxdistructo.discord.core.command.BaseCommand
import maxdistructo.discord.core.command.ICommand
import maxdistructo.discord.core.message.Message
import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent
import java.util.*

class NateBattleListener : BaseListener() {
    override val name = "NateBattle.Base"
    override var commandRegistry = LinkedList<ICommand>()

    @EventSubscriber
    override fun onMessageReceivedEvent(event : MessageReceivedEvent) {
        val message = event.message
        val prefix = Config.readPrefix()
        val messageContent = message.content.split(" ")

        if (message.content.startsWith(prefix) && message.guild.longID == 462635426218246144L) { //This listener is locked to this server and will not be added if the server is not a part of the Shard
            when {
                Perms.checkAdmin(message) -> for (command in adminCommands) {
                    if (messageContent[0] == prefix + command.commandName) {
                        println()
                        if (command.hasOutput) {
                            Message.sendMessage(message.channel, command.init(message, messageContent))
                            message.delete()
                        } else {
                            command.init(message, messageContent)
                            message.delete()
                        }
                    }
                }
                Perms.checkMod(message) -> for (command in modCommands) {
                    if (messageContent[0] == prefix + command.commandName) {
                        if (command.hasOutput) {
                            Message.sendMessage(message.channel, command.init(message, messageContent))
                            message.delete()
                        } else {
                            command.init(message, messageContent)
                            message.delete()
                        }
                    }
                }
                else -> for (command in commandsArray) {
                    if (messageContent[0] == prefix + command.commandName) {
                        if (command.hasOutput) {
                            Message.sendMessage(message.channel, command.init(message, messageContent))
                            message.delete()
                        } else {
                            command.init(message, messageContent)
                            message.delete()

                        }
                    }
                }
            }
        }
    }
    override var commandsArray = listOf<BaseCommand>()
    override var adminCommands = listOf<BaseCommand>()
    override var modCommands = listOf<BaseCommand>()
}