package maxdistructo.discord.bots.droidbot.commands

import maxdistructo.droidbot2.core.message.Message
import sx.blah.discord.handle.obj.IMessage

object Ping {

    fun onPingCommand(message: IMessage) {
        Message.sendMessage(message.channel, "Pong! Time taken: " + message.client.ourUser.shard.responseTime + " milliseconds")
    }

}
