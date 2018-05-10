package maxdistructo.discord.bots.droidbot.commands.mafia

import maxdistructo.discord.bots.droidbot.core.Roles
import maxdistructo.discord.bots.droidbot.core.Utils
import maxdistructo.discord.bots.droidbot.core.Utils.s
import maxdistructo.discord.bots.droidbot.core.message.Message
import org.apache.commons.lang3.ArrayUtils
import org.json.JSONArray
import org.json.JSONObject
import sx.blah.discord.handle.obj.IMessage
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Paths

object MafiaConfig {
    fun getPlayers(message: IMessage): LongArray {
        val root1 = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_players.txt")
        val jsonArray = root1.getJSONArray("players")
        val strArray = LongArray(jsonArray.length())
        for (i in 0 until jsonArray.length()) {
            strArray[i] = jsonArray.getLong(i)
        }
        return strArray
    }

    fun getPlayers(message: IMessage, role: String): LongArray {
        val usersList = message.guild.getUsersByRole(Roles.getRole(message, role))
        usersList.remove(message.client.ourUser)
        for (user in usersList) {
            if (Perms.checkMod(message, user.longID)) {
                usersList.remove(user)
            }
        }
        val players = LongArray(usersList.size)
        var i = 0
        for (user in usersList) {
            players[i] = user.longID
            i++
        }
        return players
    }

    fun getPlayerDetails(message: IMessage): Array<Any> {
        if (!Perms.checkMod(message)) {
            val root1 = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_playerdat.txt")
            val root = root1.getJSONObject("" + message.author.longID)
            val list = arrayListOf<Any>(root.getString("alignment"), root.getString("class"), root.getString("role"), message.author.getRolesForGuild(message.guild).contains(Roles.getRole(message, "Dead(Mafia)")), root.getInt("attack"), root.getInt("defense"))
            return list.toArray()
        } else {
            val list = arrayListOf<Any>("admin", "admin", "admin", false, 3, 3)
            return list.toArray()
        }
    }

    fun getPlayerDetails(message: IMessage, playerID: Long): Array<Any> {
        if (!Perms.checkMod(message, playerID)) {
            val root1 = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_playerdat.txt")
            val root = root1.getJSONObject("" + playerID)
            val list = arrayListOf<Any>(root.getString("alignment"), root.getString("class"), root.getString("role"), message.guild.getUserByID(playerID).getRolesForGuild(message.guild).contains(Roles.getRole(message, "Dead(Mafia)")), root.getInt("attack"), root.getInt("defense"))
            return list.toArray()
        } else {
            val list = arrayListOf<Any>("admin", "admin", "admin", false, 3, 3)
            return list.toArray()
        }
    }

    fun shuffleJSONArray(jsonArray: JSONArray): Array<String?> {
        val list = arrayOfNulls<String>(jsonArray.length())
        for (i in 0 until jsonArray.length()) {
            list[i] = jsonArray.getString(i)
        }
        ArrayUtils.shuffle(list)
        return list
    }

    fun writeGame(message: IMessage, `object`: JSONObject) {
        val currentRelativePath = Paths.get("")
        val s = currentRelativePath.toAbsolutePath().toString()
        val file = File(s + "/config/mafia/" + message.guild.longID + "_playerdat.txt")
        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: IOException) {
                Message.throwError(e, message)
            }
        }
        try {
            FileWriter(s + "/config/mafia/" + message.guild.longID + "_playerdat.txt").use { fileWriter ->
                fileWriter.write(`object`.toString())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun getJailed(message: IMessage): Long {
        val root = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt")
        return root.getLong("jailed")
    }

    fun writeGameDat(message: IMessage, `object`: JSONObject) {
        val currentRelativePath = Paths.get("")
        val s = currentRelativePath.toAbsolutePath().toString()
        val file = File(s + "/config/mafia/" + message.guild.longID + "_dat.txt")
        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: IOException) {
                Message.throwError(e, message)
            }
        }
        try {
            FileWriter(s + "/config/mafia/" + message.guild.longID + "_dat.txt").use { fileWriter ->
                fileWriter.write(`object`.toString())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun writeActions(message: IMessage, json: JSONObject) {
        val file = File(s + "/config/mafia/" + message.guild.longID + "_actions.txt")
        if (!file.exists()) {
            file.createNewFile()
        }
        FileWriter(file).use { fileWriter ->
            fileWriter.write(json.toString())
        }
    }

    fun investResults(message: IMessage): JSONObject {
        val json = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_actions.txt")
        return json.getJSONObject("invest_results")
    }
}