package maxdistructo.discord.bots.droidbot.commands.mafia.init

import sx.blah.discord.handle.obj.IMessage

interface IHandler  {
    val name : String
    fun update(message : IMessage)
    fun reset(message : IMessage)
}