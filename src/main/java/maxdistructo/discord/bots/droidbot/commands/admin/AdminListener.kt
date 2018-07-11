package maxdistructo.discord.bots.droidbot.commands.admin

import kotlinx.coroutines.experimental.launch
import maxdistructo.discord.bots.droidbot.background.BaseListener
import maxdistructo.discord.core.Config
import maxdistructo.discord.core.Perms
import maxdistructo.discord.core.command.BaseCommand
import maxdistructo.discord.core.command.ICommandRegistry
import maxdistructo.discord.core.message.Message
import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent
import java.util.*

class AdminListener : BaseListener() {

    override val name = "Botfather.Admin"
    override var commandRegistries = LinkedList<ICommandRegistry>()
    override var commandsArray: List<BaseCommand> = listOf()
    override var adminCommands: List<BaseCommand> = listOf()
    override var modCommands: List<BaseCommand> = listOf()
    var ownerCommands: List<AdminCommand> = listOf()
    var guildOwnerCommands: List<AdminCommand> = listOf()

    @EventSubscriber
    override fun onMessageReceivedEvent(event: MessageReceivedEvent) {
        val message = event.message
        val prefix = Config.readPrefix()
        val messageContent = message.content.split(" ")
        if (message.content.startsWith(prefix)) {
            launch {
                when {
                    Perms.checkOwner(message) -> for (command in ownerCommands) {
                        if (messageContent[0] == prefix + "@" + command.commandName) {
                            if (command.hasOutput) {
                                Message.sendMessage(message.channel, command.init(message, messageContent))
                                message.delete()
                            } else {
                                command.init(message, messageContent)
                                message.delete()
                            }
                        }
                    }
                    Perms.checkOwner_Guild(message) -> {
                        for (command in guildOwnerCommands) {
                            if (messageContent[0] == prefix + "@" + command.commandName) {
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
                    Perms.checkAdmin(message) -> for (command in adminCommands) {
                        if (messageContent[0] == prefix + "@" + command.commandName) {
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
                        if (messageContent[0] == prefix + "@" + command.commandName) {
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
                        if (messageContent[0] == prefix + "@" + command.commandName) {
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

        override fun registerCommand(vararg commands: BaseCommand) {
            for (commandIn in commands) {
                val command = commandIn as AdminCommand
                when {
                    command.requiresOwner -> ownerCommands += command
                    command.requiresGuildOwner -> {
                        ownerCommands += command
                        guildOwnerCommands += command
                    }
                    command.requiresAdmin -> {
                        ownerCommands += command
                        guildOwnerCommands += command
                        adminCommands += command
                    }
                    command.requiresMod -> {
                        ownerCommands += command
                        guildOwnerCommands += command
                        adminCommands += command
                        modCommands += command
                    }
                    else -> {
                        ownerCommands += command
                        guildOwnerCommands += command
                        adminCommands += command
                        modCommands += command
                        commandsArray += command
                    }
                }
            }
        }
    }
