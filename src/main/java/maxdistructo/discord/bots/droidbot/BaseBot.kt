package maxdistructo.discord.bots.droidbot

import maxdistructo.discord.bots.droidbot.background.Listener
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

    @JvmStatic
    fun main(args: Array<String>) {
        bot = Bot(Config.readToken())
        client = bot.client
        Client.client = bot.client
        LOGGER = bot.logger
        LOGGER.info("Client Created")
        client.dispatcher.registerListener(Listener())
        LOGGER.info("Registered Listener")
        client.dispatcher.registerListener(MafiaListener())
        LOGGER.info("Registered Mafia Listener. PLAY ON!")
    }

}
