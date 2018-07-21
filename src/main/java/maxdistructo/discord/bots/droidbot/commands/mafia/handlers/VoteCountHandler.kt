package maxdistructo.discord.bots.droidbot.commands.mafia.handlers

import maxdistructo.discord.bots.droidbot.commands.mafia.init.IHandler
import sx.blah.discord.handle.obj.IMessage

object VoteCountHandler{

    class Handler : IHandler {
        override val name: String
            get() = "Botfather.Mafia.VoteCountUpdater"
        override fun update(message: IMessage) {
            VoteCountHandler.update(message)
        }
        override fun reset(message: IMessage) {
            VoteCountHandler.reset(message)
        }
    }

     fun update(message : IMessage){

    }
     fun reset(message : IMessage){

    }
}