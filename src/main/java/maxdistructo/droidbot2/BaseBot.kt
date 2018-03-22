package maxdistructo.droidbot2

import maxdistructo.droidbot2.background.*
import maxdistructo.droidbot2.commands.mafia.MafiaListener
import maxdistructo.droidbot2.core.Config
import maxdistructo.droidbot2.core.Client
import org.slf4j.Logger
import sx.blah.discord.api.IDiscordClient

object BaseBot {
    lateinit var client: IDiscordClient
    var LOGGER = Client.LOGGER
    val listener = Listener()


    @JvmStatic
    fun main(args: Array<String>) {
        val token = Config.readToken()
        Client.createClient(token)
        client = Client.client!! //To prevent causing the whole program from having to be re-written
        LOGGER.info("Client Created")
        //client.getDispatcher().registerListener(new AudioMain());
        //LOGGER.info("Registered Audio Commands ");
        //Help.registerHelp();
        client.dispatcher.registerListener(listener)
        LOGGER.info("Registered Listener")
        client.dispatcher.registerListener(MafiaListener())
        LOGGER.info("Registered Mafia Listener. PLAY ON!")
    }

}
