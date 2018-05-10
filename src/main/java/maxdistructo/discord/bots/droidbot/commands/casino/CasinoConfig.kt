package maxdistructo.discord.bots.droidbot.commands.casino

import maxdistructo.discord.bots.droidbot.BaseBot
import maxdistructo.droidbot2.core.message.Message
import org.json.JSONObject
import org.json.JSONTokener
import sx.blah.discord.handle.obj.IGuild
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IRole
import sx.blah.discord.handle.obj.IUser
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Paths

object CasinoConfig {

    var PLAYER = "null"
    var CHIPS: Int = 0
    var MEMBERSHIP: String = ""
    var trivia = arrayOfNulls<String>(2)

    fun newCasino(message: IMessage) {
        val currentRelativePath = Paths.get("")
        val s = currentRelativePath.toAbsolutePath().toString()
        val user = message.author
        val stringUser = user.name
        val f = File(s + "/droidbot/config/casino/" + user.longID + ".txt")
        f.parentFile.mkdirs() //Create Directories for File
        try {
            f.createNewFile() //Create file
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val newUser = JSONObject()
        newUser.put("User", stringUser)
        newUser.put("Chips", 100)
        newUser.put("Membership", "null")

        try {
            FileWriter(s + "/droidbot/config/casino/" + user.longID + ".txt").use { file ->
                file.write(newUser.toString())
                println("Successfully Copied JSON Object to File...")
                println("\nJSON Object: $newUser")
            }
        } catch (e: IOException) {
            BaseBot.LOGGER.warn("Config.newCasino Error.")
            Message.sendDM(BaseBot.client.applicationOwner, e.toString())
            e.printStackTrace()
        }

    }

    fun newCasino(user: IUser, guild: IGuild) {
        val currentRelativePath = Paths.get("")
        val s = currentRelativePath.toAbsolutePath().toString()
        val stringUser = user.name
        val f = File(s + "/droidbot/config/casino/" + user.longID + ".txt")
        f.parentFile.mkdirs() //Create Directories for File
        try {
            f.createNewFile() //Create file
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val newUser = JSONObject()
        newUser.put("User", stringUser)
        newUser.put("Chips", 100)
        newUser.put("Membership", "null")

        try {
            FileWriter(s + "/droidbot/config/casino/" + user.longID + ".txt").use { file ->
                file.write(newUser.toString())
                println("Successfully Copied JSON Object to File...")
                println("\nJSON Object: $newUser")
            }
        } catch (e: IOException) {
            BaseBot.LOGGER.warn("Config.newCasino Error.")
            Message.sendDM(BaseBot.client.applicationOwner, e.toString())
            e.printStackTrace()
        }

    }

    fun readCasino(message: IMessage): Array<Any>? {
        val currentRelativePath = Paths.get("")
        val s = currentRelativePath.toAbsolutePath().toString()
        val user = message.author
        val stringUser = user.name

        val file = File(s + "/droidbot/config/casino/" + user.longID + ".txt")
        if (file.exists()) {
            val uri = file.toURI()
            var tokener: JSONTokener? = null
            try {
                tokener = JSONTokener(uri.toURL().openStream())
                println("Successfully read file $stringUser.txt")
            } catch (e: IOException) {
                Message.sendDM(BaseBot.client.applicationOwner, e.toString())
                e.printStackTrace()
            }

            val root = JSONObject(tokener!!)
            println("Converted JSON file to JSONObject")
            val casinoValues = arrayOf(root.getString("User"), root.getInt("Chips"), root.getString("Membership"))
            println("Successfully read values from file.")
            CHIPS = root.getInt("Chips")
            MEMBERSHIP = root.getString("Membership")
            PLAYER = root.getString("User")
            return casinoValues
        } else {
            readLegacyCasino(message)
        }
        return null
    }

    private fun readLegacyCasino(message: IMessage) { //This is to allow for a new command to move a person's legacy casino to the new system.
        val currentRelativePath = Paths.get("")
        val s = currentRelativePath.toAbsolutePath().toString()
        val user = message.author
        val stringUser = user.name

        val file = File(s + "/droidbot/config/" + message.guild.longID + "/casino/" + user.longID + ".txt")
        if (file.exists()) {
            val uri = file.toURI()
            var tokener: JSONTokener? = null
            try {
                tokener = JSONTokener(uri.toURL().openStream())
                println("Successfully read file $stringUser.txt")
            } catch (e: IOException) {
                Message.sendDM(BaseBot.client.applicationOwner, e.toString())
                e.printStackTrace()
            }

            if (tokener != null) {
                val root = JSONObject(tokener)
                println("Converted JSON file to JSONObject")
                val casinoValues = arrayOf(root.getString("User"), root.getInt("Chips"), root.getString("Membership"))
                println("Successfully read values from file.")
                CHIPS = casinoValues[1] as Int
                MEMBERSHIP = casinoValues[2] as String
                PLAYER = casinoValues[0] as String
                writeCasino(message)
            }
        } else {
            newCasino(message)
        }
    }

    fun readCasino(user: IUser, guild: IGuild) {
        val currentRelativePath = Paths.get("")
        val s = currentRelativePath.toAbsolutePath().toString()
        val stringUser = user.name

        val file = File(s + "/droidbot/config/casino/" + user.longID + ".txt")
        if (file.exists()) {
            val uri = file.toURI()
            var tokener: JSONTokener? = null
            try {
                tokener = JSONTokener(uri.toURL().openStream())
                println("Successfully read file $stringUser.txt")
            } catch (e: IOException) {
                Message.sendDM(BaseBot.client.applicationOwner, e.toString())
                e.printStackTrace()
            }

            val root = JSONObject(tokener!!)
            println("Converted JSON file to JSONObject")
            PLAYER = root.getString("User")
            CHIPS = root.getInt("Chips")
            MEMBERSHIP = root.getString("Membership")
            println("Successfully read values from file.")
        } else {
            newCasino(user, guild)
            // Casino.onCasinoCommand(new Object[] {Listener.prefix + "casino", "join"}, message,null);
        }

    }

    fun writeCasino(message: IMessage) {
        val currentRelativePath = Paths.get("")
        val s = currentRelativePath.toAbsolutePath().toString()
        val user = message.author
        val stringUser = user.name

        val file = File(s + "/droidbot/config/casino/" + user.longID + ".txt")
        if (!file.exists()) {
            try {
                file.mkdirs()
                file.createNewFile()
            } catch (e: IOException) {
                Message.throwError(e, message)
            }

        }
        val uri = file.toURI()
        var tokener: JSONTokener? = null
        try {
            tokener = JSONTokener(uri.toURL().openStream())
            println("Successfully read file $stringUser.txt")
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val newUser = JSONObject(tokener!!)
        newUser.put("User", PLAYER)
        newUser.put("Chips", CHIPS)
        newUser.put("Membership", MEMBERSHIP)

        try {
            FileWriter(s + "/droidbot/config/casino/" + user.longID + ".txt").use { fileWriter ->
                fileWriter.write(newUser.toString())
                println("Successfully Copied JSON Object to File...")
                println("\nJSON Object: $newUser")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun writeCasino(user: IUser, guild: IGuild) {
        val currentRelativePath = Paths.get("")
        val s = currentRelativePath.toAbsolutePath().toString()
        val stringUser = user.name

        val file = File(s + "/droidbot/config/casino/" + user.longID + ".txt")
        if (!file.exists()) {
            try {
                file.mkdirs()
                file.createNewFile()
            } catch (e: IOException) {
                Message.throwError(e)
            }

        }
        val uri = file.toURI()
        var tokener: JSONTokener? = null
        try {
            tokener = JSONTokener(uri.toURL().openStream())
            println("Successfully read file $stringUser.txt")
        } catch (e: IOException) {
            Message.sendDM(BaseBot.client.applicationOwner, e.toString())
            e.printStackTrace()
        }

        val newUser = JSONObject(tokener!!)
        newUser.put("User", PLAYER)
        newUser.put("Chips", CHIPS)
        newUser.put("Membership", MEMBERSHIP)

        try {
            FileWriter(s + "/droidbot/config/casino/" + user.longID + ".txt").use { fileWriter ->
                fileWriter.write(newUser.toString())
                println("Successfully Copied JSON Object to File...")
                println("\nJSON Object: $newUser")
            }
        } catch (e: IOException) {
            Message.sendDM(BaseBot.client.applicationOwner, e.toString())
            e.printStackTrace()
        }

    }

    fun resetBJ(message: IMessage) {
        val currentRelativePath = Paths.get("")
        val s = currentRelativePath.toAbsolutePath().toString()
        val file = File(s + "/droidbot/config/blackjack/" + message.author.longID)
        file.delete()
    }

    fun writeBlackjackFields(playerScore: Int, playerHand: String, dealerScore: Int, dealerHand: String, bet: Int, message: IMessage) {
        val currentRelativePath = Paths.get("")
        val s = currentRelativePath.toAbsolutePath().toString()
        val root = JSONObject()
        root.put("BJ_playerScore", playerScore)
        root.put("BJ_playerHand", playerHand)
        root.put("BJ_dealerScore", dealerScore)
        root.put("BJ_dealerHand", dealerHand)
        root.put("BJ_bet", bet)
        try {
            FileWriter(s + "/droidbot/config/blackjack/" + message.author.longID + ".txt").use { fileWriter ->
                fileWriter.write(root.toString())
                println("Successfully Copied JSON Object to File...")
                println("\nJSON Object: $root")
            }
        } catch (e: IOException) {
            Message.sendDM(BaseBot.client.applicationOwner, e.toString())
            e.printStackTrace()
        }

    }

    fun readBJFields(message: IMessage): JSONObject {
        val currentRelativePath = Paths.get("")
        val s = currentRelativePath.toAbsolutePath().toString()
        val user = message.author
        val stringUser = user.name

        val file = File(s + "/droidbot/config/blackjack/" + message.author.longID + ".txt")
        val uri = file.toURI()
        var tokener: JSONTokener? = null
        try {
            tokener = JSONTokener(uri.toURL().openStream())
            println("Successfully read file $stringUser.txt")
        } catch (e: IOException) {
            Message.sendDM(BaseBot.client.applicationOwner, e.toString())
            e.printStackTrace()
        }

        return JSONObject(tokener!!)
    }

    fun doPaydayReset(guild: IGuild) {
        val rolesList = guild.getRolesByName("Payday")
        var paydayRole: IRole? = null

        try {
            paydayRole = rolesList[0]
        } catch (e: Exception) {
            println("Could not find Payday role for server - " + guild.name)
        }

        if (paydayRole != null) {
            val paydayUsers = guild.getUsersByRole(paydayRole)
            val paydayArray = paydayUsers.toTypedArray()

            var ii = 0
            while (ii < paydayArray.size) {
                val user = paydayArray[ii] as IUser
                user.removeRole(paydayRole)
                ii++
            }
        }

    }

}
