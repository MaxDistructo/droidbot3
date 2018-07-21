package maxdistructo.discord.bots.droidbot.background.coreadditions

import maxdistructo.discord.core.command.IBaseListener
import maxdistructo.discord.core.impl.Bot
import org.slf4j.Logger
import sx.blah.discord.api.IDiscordClient
import java.util.*

interface IDiscordBot {
    var client : IDiscordClient
    var LOGGER : Logger
    var bot : Bot
    var registry : LinkedList<Pair<ICommandRegistry, IBaseListener>>
    fun registerCommandRegistry(commandRegistry : ICommandRegistry, listener : IBaseListener){
        registry.add(Pair(commandRegistry, listener))
    }
}