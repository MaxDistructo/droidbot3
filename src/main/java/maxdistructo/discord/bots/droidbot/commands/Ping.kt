package maxdistructo.discord.bots.droidbot.commands

import maxdistructo.discord.bots.droidbot.background.constructor.BaseCommand
import sx.blah.discord.handle.obj.IMessage

class Ping : BaseCommand() {
    override val commandName: String
        get() = "ping"
    override val helpMessage: String
        get() = "ping - Gets latency between the bot and Discord"
    override val requiresAdmin: Boolean
        get() = false
    override val requiresMod: Boolean
        get() = false

    override fun init(message: IMessage, args: List<String>): String {
        return onPingCommand(message)
    }

    private fun onPingCommand(message: IMessage): String {
        return "Pong! Time taken: " + message.client.ourUser.shard.responseTime + " milliseconds"
    }

}
