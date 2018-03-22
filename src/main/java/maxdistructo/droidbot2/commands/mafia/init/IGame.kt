package maxdistructo.droidbot2.commands.mafia.init

import sx.blah.discord.handle.obj.IChannel

interface IGame{
    val adminChannel : IChannel
    val mediumChannel : IChannel
    val deadChannel : IChannel
    val mafiaChannel : IChannel
    val day : Boolean
    val spyChannel : IChannel
    val jailorChat : IChannel
    val jailedChat : IChannel
    val dayChannel : IChannel
}