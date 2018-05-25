package maxdistructo.discord.bots.droidbot.commands.mafia.obj

import maxdistructo.discord.bots.droidbot.BaseBot
import maxdistructo.discord.bots.droidbot.commands.mafia.init.IGame
import org.json.JSONObject
import sx.blah.discord.handle.obj.IChannel

class Game(input: JSONObject) : IGame {
    private var info: JSONObject = input
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
    override val vampChannel: IChannel
        get() = BaseBot.client.getChannelByID(info.getLong("vamp_chat"))
    override val vamphunterChannel: IChannel
        get() = BaseBot.client.getChannelByID(info.getLong("vamphunter_chat"))
    override val dayNum: Int
        get() = info.getInt("daynum")
}