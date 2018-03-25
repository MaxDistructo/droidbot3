package maxdistructo.droidbot2.commands.mafia

import maxdistructo.droidbot2.core.Client
import maxdistructo.droidbot2.core.Utils
import maxdistructo.droidbot2.core.message.Message
import org.json.JSONObject
import org.json.JSONTokener
import sx.blah.discord.handle.obj.IMessage
import java.io.File
import java.io.IOException
import java.nio.file.Paths

object Perms{
    fun checkAdmin(message : IMessage) : Boolean{
        //Checks if user is a Admin/Owner of the Server (Or Myself).
        val admins = getAdminArray("/config/mafia/"+ message.guild.longID + "_perms.txt")
        var i = 0
        while (i < admins.size) {
            if (message.author.longID == admins[i]) {
                return true
            }
            i++
        }
        return false
    }
    fun checkAdmin(message: IMessage, input : Long) : Boolean{
        //Checks if user is a Admin/Owner of the Server (Or Myself).
        val admins = getAdminArray("/config/mafia/"+ message.guild.longID + "_perms.txt")
        var i = 0
        while (i < admins.size) {
            if (input == admins[i]) {
                return true
            }
            i++
        }
        return false
    }
    fun checkMod(message : IMessage) : Boolean{
        val admins = getAdminArray("/config/mafia/"+ message.guild.longID + "_perms.txt")
        var i = 0
        while (i < admins.size) {
            if (message.author.longID == admins[i] || checkAdmin(message)) {
                return true
            }
            i++
        }
        return false
    }
    fun checkMod(message : IMessage, input : Long) : Boolean{
        val admins = getAdminArray("/config/mafia/"+ message.guild.longID + "_perms.txt")
        var i = 0
        while (i < admins.size) {
            if (input == admins[i] || checkAdmin(message, input)) {
                return true
            }
            i++
        }
        return false
    }
    fun getAdminArray(fileName : String) : LongArray{
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
        println("Converted JSON file to JSONObject")
        val array = root.getJSONArray("Admins")
        val longArray = LongArray(array.length())
        var i = 0
        while (i < array.length()) {
            longArray[i] = array.getLong(i)
            i++
        }
        return longArray
    }
    fun getModArray(fileName : String) : LongArray{
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
        println("Converted JSON file to JSONObject")
        val array = root.getJSONArray("Mods")
        val longArray = LongArray(array.length())
        var i = 0
        while (i < array.length()) {
            longArray[i] = array.getLong(i)
            i++
        }
        return longArray
    }
    fun checkMafiaChannels(message : IMessage) : Boolean{
        val root = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt")
        val channels = arrayListOf(root.getLong("admin_chat"), root.getLong("day_chat"), root.getLong("mafia_chat"), root.getLong("medium_chat"), root.getLong("spy_chat"), root.getLong("dead_chat"), root.getLong("jailor_chat"), root.getLong("jailed_chat"), 422457248724549632L)
        for(channel in channels){
            if(channel == message.channel.longID){
                return true
            }
        }
        return false

    }
}