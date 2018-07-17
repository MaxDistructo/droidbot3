package maxdistructo.discord.bots.droidbot.background

import kotlinx.coroutines.experimental.launch
import maxdistructo.discord.bots.droidbot.BaseBot
import maxdistructo.discord.bots.droidbot.background.filter.SwearFilter
import maxdistructo.discord.core.Config
import maxdistructo.discord.core.Perms
import maxdistructo.discord.core.command.BaseCommand
import maxdistructo.discord.core.command.ICommandRegistry
import maxdistructo.discord.core.message.Message
import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent
import sx.blah.discord.handle.impl.events.shard.ShardReadyEvent
import sx.blah.discord.handle.obj.ActivityType
import sx.blah.discord.handle.obj.StatusType
import java.util.*

class BetaListener : BaseListener() {
    override var commandRegistries = LinkedList<ICommandRegistry>()
    override val name = "Botfather.Base"
    override var adminCommands = listOf<BaseCommand>()
    override var commandsArray = listOf<BaseCommand>()
    override var modCommands = listOf<BaseCommand>()

    @EventSubscriber
    override fun onMessageReceivedEvent(event: MessageReceivedEvent) {
        val message = event.message
        val prefix = Config.readPrefix()
        val messageContent = message.content.split(" ")
        if (Config.readServerConfig(message.guild).getBoolean("SwearFilter")) {
            SwearFilter.filter(message)
        }
        if (message.content.startsWith(prefix)) {
            launch {
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

    }
}