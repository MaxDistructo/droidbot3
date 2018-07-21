package maxdistructo.discord.bots.droidbot.commands.mafia.handlers

import maxdistructo.discord.bots.droidbot.commands.mafia.init.IHandler
import maxdistructo.discord.bots.droidbot.commands.mafia.obj.Roles
import sx.blah.discord.handle.obj.IMessage

object RolelistHandler {
    class Handler : IHandler{
        override val name: String
            get() = "Botfather.Mafia.RolelistUpdater"
        override fun update(message: IMessage) {
            RolelistHandler.update(message)
        }
        override fun reset(message: IMessage) {
            RolelistHandler.reset(message)
        }
    }

    fun update(message : IMessage){

    }
    fun reset(message : IMessage){

    }
    fun addRole(message : IMessage, role : Enum<Roles>){

    }
    fun getRolelist(message : IMessage){

    }
}