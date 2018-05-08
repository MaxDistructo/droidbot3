package maxdistructo.discord.bots.droidbot

import maxdistructo.droidbot2.background.*
import maxdistructo.droidbot2.commands.mafia.MafiaListener
import maxdistructo.droidbot2.core.Config
import maxdistructo.droidbot2.core.impl.Bot
import org.slf4j.Logger
import sx.blah.discord.api.IDiscordClient

object BaseBot {
    lateinit var client: IDiscordClient
    lateinit var LOGGER : Logger
    lateinit var bot : Bot

    @JvmStatic
    fun main(args: Array<String>) {
        bot = Bot(Config.readToken())
        client = bot.client
        LOGGER = bot.logger
        LOGGER.info("Client Created")
        client.dispatcher.registerListener(Listener())
        LOGGER.info("Registered Listener")
        client.dispatcher.registerListener(MafiaListener())
        LOGGER.info("Registered Mafia Listener. PLAY ON!")
    }

}
