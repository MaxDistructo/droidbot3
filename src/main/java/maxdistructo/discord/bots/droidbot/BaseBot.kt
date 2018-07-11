package maxdistructo.discord.bots.droidbot

import maxdistructo.discord.bots.droidbot.background.BetaListener
import maxdistructo.discord.bots.droidbot.commands.admin.Admin
import maxdistructo.discord.bots.droidbot.commands.admin.AdminListener
import maxdistructo.discord.bots.droidbot.commands.mafia.MafiaCommands
import maxdistructo.discord.bots.droidbot.commands.mafia.MafiaListener
import maxdistructo.discord.bots.droidbot.commands.server.NateBattleCommands
import maxdistructo.discord.bots.droidbot.commands.server.NateBattleListener
import maxdistructo.discord.core.Client
import maxdistructo.discord.core.Config
import maxdistructo.discord.core.impl.Bot
import org.slf4j.Logger
import sx.blah.discord.api.IDiscordClient

object BaseBot {
    lateinit var client: IDiscordClient
    lateinit var LOGGER : Logger
    lateinit var bot : Bot
    val listener = BetaListener()
    val mafiaListener = MafiaListener()
    val nateListener = NateBattleListener()
    val adminListener = AdminListener()

    @JvmStatic
    fun main(args: Array<String>) {
        bot = Bot(Config.readToken())
        client = bot.client
        Client.client = bot.client
        LOGGER = bot.logger
        LOGGER.info("Client Created")
        listener.addCommandRegistry(Commands.CommandsRegistry())
        mafiaListener.addCommandRegistry(MafiaCommands.MafiaCommandRegistry())
        nateListener.addCommandRegistry(NateBattleCommands.NateBattleCommandRegistry())
        adminListener.addCommandRegistry(Admin.AdminCommandRegistry())
        bot.addListener(listener)
        bot.addListener(mafiaListener)
        bot.addListener(nateListener)
        bot.addListener(adminListener)
        bot.registerListeners()
    }

}
