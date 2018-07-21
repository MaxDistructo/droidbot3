package maxdistructo.discord.bots.droidbot.background.coreadditions

import maxdistructo.discord.core.command.IBaseListener

interface ICommandRegistry {
    fun registerCommands(listener : IBaseListener)
}