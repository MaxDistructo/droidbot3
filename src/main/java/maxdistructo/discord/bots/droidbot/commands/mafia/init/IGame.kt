package maxdistructo.discord.bots.droidbot.commands.mafia.init

import sx.blah.discord.handle.obj.IChannel

interface IGame {
    val adminChannel: IChannel
    val mediumChannel: IChannel
    val deadChannel: IChannel
    val mafiaChannel: IChannel
    val day: Boolean
    val spyChannel: IChannel
    val jailorChannel: IChannel
    val jailedChannel: IChannel
    val dayChannel: IChannel
    val vampChannel : IChannel
    val vamphunterChannel : IChannel
    val dayNum : Int
}