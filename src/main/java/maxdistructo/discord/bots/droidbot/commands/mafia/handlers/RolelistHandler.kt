package maxdistructo.discord.bots.droidbot.commands.mafia.handlers

import maxdistructo.discord.bots.droidbot.commands.mafia.init.IHandler
import maxdistructo.discord.bots.droidbot.commands.mafia.obj.Player
import maxdistructo.discord.core.Utils
import org.json.JSONArray
import sx.blah.discord.handle.obj.IMessage

object RolelistHandler {
    class Handler(private val update: IMessage) : IHandler{
        override val name: String
            get() = "Botfather.Mafia.RolelistUpdater"
        override fun update(message: IMessage) {
            RolelistHandler.update(message, update)
        }
        override fun reset(message: IMessage) {
            RolelistHandler.reset(message, update)
        }
    }
    fun update(message : IMessage, update : IMessage){
        val builder = StringBuilder()
        builder.append("RoleList: \n")
        for(user in RolelistHandler.getRolelist(message)){
            val player = Player(message, user as Long)
            builder.append(player.user.getDisplayName(message.guild))
            builder.append("- ")
            builder.append(player.roleEnum.name)
            builder.append("\n")
        }
        update.edit(builder.toString())
        update.channel.pin(update)
    }
    fun reset(message : IMessage, update : IMessage){
        update.delete()
    }
    fun addRole(message : IMessage, role : String){
        val dat = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt")
        val list = dat.getJSONArray("rolelist")
        list.put(role)
        dat.remove("rolelist")
        dat.append("rolelist", list)
        Utils.writeJSONToFile("/config/mafia/" + message.guild.longID + "_dat.txt", dat)
    }
    fun getRolelist(message : IMessage) : JSONArray {
        val dat = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt")
        return dat.getJSONArray("rolelist")
    }
    fun resetRolelist(message : IMessage){
        val dat = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt")
        dat.remove("rolelist")
        Utils.writeJSONToFile("/config/mafia/" + message.guild.longID + "_dat.txt", dat)
    }
}