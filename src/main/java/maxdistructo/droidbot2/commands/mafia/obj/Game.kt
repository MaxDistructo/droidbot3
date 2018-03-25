package maxdistructo.droidbot2.commands.mafia.obj

import maxdistructo.droidbot2.BaseBot
import maxdistructo.droidbot2.commands.mafia.init.IGame
import org.json.JSONObject
import sx.blah.discord.handle.obj.IChannel

class Game(input: JSONObject) : IGame{
    private var info : JSONObject = input
    override val adminChannel: IChannel
        get() = BaseBot.client.getChannelByID(info.getLong("admin_chat"))
    override val day: Boolean
        get() = info.getBoolean("day")
    override val deadChannel: IChannel
        get() = BaseBot.client.getChannelByID(info.getLong("dead_chat"))
    override val jailedChannel: IChannel
        get() = BaseBot.client.getChannelByID(info.getLong("jailed_chat"))
    override val jailorChannel: IChannel
        get() = BaseBot.client.getChannelByID(info.getLong("jailor_chat"))
    override val mafiaChannel: IChannel
        get() = BaseBot.client.getChannelByID(info.getLong("mafia_chat"))
    override val mediumChannel: IChannel
        get() = BaseBot.client.getChannelByID(info.getLong("medium_chat"))
    override val spyChannel: IChannel
        get() = BaseBot.client.getChannelByID(info.getLong("spy_chat"))
    override val dayChannel: IChannel
        get() = BaseBot.client.getChannelByID(info.getLong("day_chat"))
}