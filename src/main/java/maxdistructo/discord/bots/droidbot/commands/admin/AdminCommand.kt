package maxdistructo.discord.bots.droidbot.commands.admin

import maxdistructo.discord.core.command.BaseCommand

open class AdminCommand : BaseCommand() {
    override val requiresGuildOwner = false
    override val requiresOwner = false
}