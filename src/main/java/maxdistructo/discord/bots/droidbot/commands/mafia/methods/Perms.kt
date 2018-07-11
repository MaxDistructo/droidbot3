package maxdistructo.discord.bots.droidbot.commands.mafia.methods

import maxdistructo.discord.core.Client
import maxdistructo.discord.core.Utils
import maxdistructo.discord.core.message.Message
import org.json.JSONObject
import org.json.JSONTokener
import sx.blah.discord.handle.obj.IMessage
import java.io.File
import java.io.IOException
import java.nio.file.Paths

object Perms {
    fun checkAdmin(message: IMessage): Boolean {
        val admins = getAdminArray("/config/mafia/" + message.guild.longID + "_perms.txt")
        return admins.contains(message.author.longID)
    }

    fun checkAdmin(message: IMessage, input: Long): Boolean {
        //Checks if user is a Admin/Owner of the Server (Or Myself).
        val admins = getAdminArray("/config/mafia/" + message.guild.longID + "_perms.txt")
        return admins.contains(input)
    }

    fun checkMod(message: IMessage): Boolean {
        val admins = getModArray("/config/mafia/" + message.guild.longID + "_perms.txt")
        return admins.contains(message.author.longID) || checkAdmin(message)
    }

    fun checkMod(message: IMessage, input: Long): Boolean {
        val admins = getModArray("/config/mafia/" + message.guild.longID + "_perms.txt")
        return admins.contains(input) || checkAdmin(message, input)
    }

    fun getAdminArray(fileName: String): LongArray {
        val currentRelativePath = Paths.get("")
        val s = currentRelativePath.toAbsolutePath().toString()
        val file = File(s + fileName)
        val uri = file.toURI()
        var tokener: JSONTokener? = null
        try {
            tokener = JSONTokener(uri.toURL().openStream())
        } catch (e: IOException) {
            Message.sendDM(Client.client!!.applicationOwner, e.toString())
            e.printStackTrace()
        }

        val root = JSONObject(tokener!!)
        val array = root.getJSONArray("Admins")
        val longArray = LongArray(array.length())
        var i = 0
        while (i < array.length()) {
            longArray[i] = array.getLong(i)
            i++
        }
        return longArray
    }

    fun getModArray(fileName: String): LongArray {
        val currentRelativePath = Paths.get("")
        val s = currentRelativePath.toAbsolutePath().toString()
        val file = File(s + fileName)
        val uri = file.toURI()
        var tokener: JSONTokener? = null
        try {
            tokener = JSONTokener(uri.toURL().openStream())
        } catch (e: IOException) {
            Message.sendDM(Client.client!!.applicationOwner, e.toString())
            e.printStackTrace()
        }

        val root = JSONObject(tokener!!)
        val array = root.getJSONArray("Mods")
        val longArray = LongArray(array.length())
        var i = 0
        while (i < array.length()) {
            longArray[i] = array.getLong(i)
            i++
        }
        return longArray
    }

    fun checkMafiaChannels(message: IMessage): Boolean {
        val root = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt")
        val channels = arrayListOf(root.getLong("admin_chat"), root.getLong("day_chat"), root.getLong("mafia_chat"), root.getLong("medium_chat"), root.getLong("spy_chat"), root.getLong("dead_chat"), root.getLong("jailor_chat"), root.getLong("jailed_chat"), 422457248724549632L, 450415346671812608L, 294551461772394497L)
        return channels.contains(message.channel.longID)
    }

    fun checkSpectator(message: IMessage) : Boolean{
        return message.author.hasRole(message.guild.getRolesByName("Mafia(Spectator)")[0])
    }
}