package maxdistructo.discord.bots.droidbot.commands.mafia.handlers

import maxdistructo.discord.bots.droidbot.commands.mafia.init.IHandler
import maxdistructo.discord.bots.droidbot.commands.mafia.obj.Player
import maxdistructo.discord.core.Utils
import org.json.JSONArray
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser

object GraveyardHandler {
    class Handler(private val updateMessage : IMessage) : IHandler {
        override val name: String
            get() = "Botfather.Mafia.GraveyardUpdater"
        override fun update(message: IMessage) {
            update(message, updateMessage)
        }
        override fun reset(message: IMessage) {
            reset(message, updateMessage)
        }
    }
    fun update(message : IMessage, update : IMessage){
        val builder = StringBuilder()
        for(user in getGraveyard(message)){
            val player = Player(message, user as Long)
            builder.append(player.user.getDisplayName(message.guild))
            builder.append("- ")
            builder.append(player.roleEnum.name)
        }
        update.edit(builder.toString())
    }
    fun reset(message : IMessage, update : IMessage){
        update.delete()
        resetGraveyard(message)


    }
    fun getGraveyard(message : IMessage) : JSONArray{
        val dat = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt")
        return dat.getJSONArray("graveyard")
    }
    fun addToGraveyard(message : IMessage, user : IUser){
        val dat = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt")
        val json = dat.getJSONArray("graveyard")
        json.put(user.longID.toString())
        dat.remove("graveyard")
        dat.put("graveyard", json)
        Utils.writeJSONToFile("/config/mafia/" + message.guild.longID + "_dat.txt", dat)
    }
    fun removeFromGraveyard(message : IMessage, user : IUser){
        val dat = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt")
        val json = dat.getJSONArray("graveyard")
        val json2 = JSONArray()
        if(json.contains(user.longID.toString())){
            for(value in json){
                if(value != user.longID.toString()){
                    json2.put(value)
                }
            }
        }
        dat.remove("graveyard")
        dat.put("graveyard", json2)
        Utils.writeJSONToFile("/config/mafia/" + message.guild.longID + "_dat.txt", dat)
    }
    fun resetGraveyard(message : IMessage){
        val dat = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt")
        dat.remove("graveyard")
        Utils.writeJSONToFile("/config/mafia/" + message.guild.longID + "_dat.txt", dat)
    }
}