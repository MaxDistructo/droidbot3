package maxdistructo.discord.bots.droidbot

import maxdistructo.discord.bots.droidbot.background.BetaListener
import maxdistructo.discord.bots.droidbot.background.constructor.BaseListener
import maxdistructo.discord.bots.droidbot.commands.mafia.MafiaCommands
import maxdistructo.discord.bots.droidbot.commands.mafia.MafiaListener
import maxdistructo.discord.core.Client
import maxdistructo.discord.core.Config
import maxdistructo.discord.core.impl.Bot
import org.slf4j.Logger
import sx.blah.discord.api.IDiscordClient

object BaseBot {
    lateinit var client: IDiscordClient
    lateinit var LOGGER : Logger
    lateinit var bot : Bot
    lateinit var listener: BaseListener
    lateinit var mafiaListener: MafiaListener

    @JvmStatic
    fun main(args: Array<String>) {
        bot = Bot(Config.readToken())
        client = bot.client
        Client.client = bot.client
        LOGGER = bot.logger
        LOGGER.info("Client Created")
        listener = BetaListener()
        LOGGER.info("Instantiated Listener")
        Commands.init(listener)
        LOGGER.info("Registered Commands")
        client.dispatcher.registerListener(listener)
        LOGGER.info("Registered Listener")
        mafiaListener = MafiaListener()
        LOGGER.info("Instantiated Mafia Listener")
        MafiaCommands.init(mafiaListener)
        LOGGER.info("Registered Mafia Commands")
        client.dispatcher.registerListener(mafiaListener)
        LOGGER.info("Registered Mafia Listener. PLAY ON!")
    }

}
