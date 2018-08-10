package maxdistructo.discord.bots.droidbot

import maxdistructo.discord.bots.droidbot.background.BetaListener
import maxdistructo.discord.bots.droidbot.background.RunnableShutdown
import maxdistructo.discord.bots.droidbot.background.coreadditions.ICommandRegistry
import maxdistructo.discord.bots.droidbot.background.coreadditions.IDiscordBot
import maxdistructo.discord.bots.droidbot.commands.admin.Admin
import maxdistructo.discord.bots.droidbot.commands.admin.AdminListener
import maxdistructo.discord.bots.droidbot.commands.mafia.MafiaCommands
import maxdistructo.discord.bots.droidbot.commands.mafia.MafiaListener
import maxdistructo.discord.bots.droidbot.commands.mafia.handlers.GraveyardHandler
import maxdistructo.discord.bots.droidbot.commands.server.NateBattleCommands
import maxdistructo.discord.bots.droidbot.commands.server.NateBattleListener
import maxdistructo.discord.core.Client
import maxdistructo.discord.core.Config
import maxdistructo.discord.core.command.IBaseListener
import maxdistructo.discord.core.impl.Bot
import maxdistructo.discord.core.message.Message
import org.slf4j.Logger
import sx.blah.discord.api.IDiscordClient
import java.util.*

object BaseBot : IDiscordBot {
    override lateinit var client: IDiscordClient
    override lateinit var LOGGER : Logger
    override lateinit var bot : Bot
    lateinit var listener : IBaseListener
    override var registry = LinkedList<Pair<ICommandRegistry, IBaseListener>>()

    @JvmStatic
    fun main(args: Array<String>) {
        bot = Bot(Config.readToken())
        client = bot.client
        Client.client = bot.client
        LOGGER = bot.logger
        LOGGER.info("Client Created")
        addRegistryValues()
        for(value in registry){
            value.first.registerCommands(value.second)
            if(value.second.name == "Botfather.Base"){
                listener = value.second
            }
            bot.addListener(value.second)
        }
        bot.registerListeners()
        bot.client.dispatcher.registerListener(ReconnectListener()) //Listener that reconnects the bot and handles the status updating.
        Runtime.getRuntime().addShutdownHook(Thread(RunnableShutdown)) // Sends DM to Owner on Shutdown via Command Line or Bot command
    }

    private fun addRegistryValues(){
        this.registerCommandRegistry(Commands, BetaListener())
        this.registerCommandRegistry(MafiaCommands, MafiaListener())
        this.registerCommandRegistry(NateBattleCommands, NateBattleListener())
        this.registerCommandRegistry(Admin, AdminListener())
    }

}
