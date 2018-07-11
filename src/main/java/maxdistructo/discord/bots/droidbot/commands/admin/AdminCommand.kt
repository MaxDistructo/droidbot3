package maxdistructo.discord.bots.droidbot.commands.admin

import maxdistructo.discord.core.command.BaseCommand

open class AdminCommand : BaseCommand() {
    open val requiresGuildOwner = false
    open val requiresOwner = false
}