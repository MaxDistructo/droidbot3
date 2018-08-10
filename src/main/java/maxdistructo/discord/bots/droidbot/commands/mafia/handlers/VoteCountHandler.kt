package maxdistructo.discord.bots.droidbot.commands.mafia.handlers

import maxdistructo.discord.bots.droidbot.commands.mafia.init.IHandler
import maxdistructo.discord.core.Utils
import sx.blah.discord.handle.obj.IMessage

object VoteCountHandler{

    class Handler(private val update : IMessage) : IHandler {
        override val name: String
            get() = "Botfather.Mafia.VoteCountUpdater"
        override fun update(message: IMessage) {
            VoteCountHandler.update(message, update)
        }
        override fun reset(message: IMessage) {
            VoteCountHandler.reset(message, update)
        }
    }

     fun update(message : IMessage, update : IMessage){
        update.edit("Votes: " + getVoteCount(message))
         update.channel.pin(update)
    }
     fun reset(message : IMessage, update : IMessage){
        update.delete()
         val dat = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt")
         dat.remove("votecount")
         dat.put("votecount", 0)
         Utils.writeJSONToFile("/config/mafia/" + message.guild.longID + "_dat.txt", dat)
    }
    fun addVote(message : IMessage, isMayor : Boolean){
        val dat = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt")
        var json = dat.getString("votecount").split(":")
        if(isMayor){
            //json += 3
        }
        else{
            //json++
        }
        dat.remove("votecount")
        dat.put("votecount", json)
        Utils.writeJSONToFile("/config/mafia/" + message.guild.longID + "_dat.txt", dat)
    }
    fun getVoteCount(message: IMessage) : Int{
        val dat = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt")
        return dat.getInt("votecount")
    }
}