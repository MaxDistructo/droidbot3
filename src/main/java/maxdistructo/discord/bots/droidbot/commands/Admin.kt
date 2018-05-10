package maxdistructo.discord.bots.droidbot.commands


import maxdistructo.discord.bots.droidbot.BaseBot.client
import maxdistructo.discord.bots.droidbot.background.Conf
import maxdistructo.discord.bots.droidbot.commands.casino.CasinoConfig
import maxdistructo.discord.core.Config
import maxdistructo.discord.core.Perms
import maxdistructo.discord.core.Roles
import maxdistructo.discord.core.Utils
import maxdistructo.discord.core.Utils.s
import maxdistructo.discord.core.message.Message
import org.json.JSONArray
import sx.blah.discord.handle.obj.*
import sx.blah.discord.util.Image
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Paths
import java.text.NumberFormat
import java.time.Instant
import java.util.*

object Admin {
    fun addMod(message: IMessage, mentioned: IUser): String {
        if (Perms.checkOwner_Guild(message)) {
            val root = Config.readServerConfig(message.guild)
            val list = root.getJSONArray("Moderators")
            list.put(mentioned.longID)
            root.put("Moderators", list)
            val currentRelativePath = Paths.get("")
            val s = currentRelativePath.toAbsolutePath().toString()
            try {
                FileWriter(s + "/droidbot/config/" + message.guild.longID + ".txt").use { file ->
                    file.write(root.toString())
                    println("Successfully Copied JSON Object to File...")
                    println("\nJSON Object: $root")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return "Sucessfully added " + mentioned.getDisplayName(message.guild) + " to the Moderator list."
        }
        return "Command Error"
    }

    fun addAdmin(message: IMessage, mentioned: IUser): String {
        if (Perms.checkOwner_Guild(message)) {
            val root = Config.readServerConfig(message.guild)
            val list = JSONArray()
            list.put(mentioned.longID)
            root.append("Admins", list)
            val currentRelativePath = Paths.get("")
            val s = currentRelativePath.toAbsolutePath().toString()
            try {
                FileWriter(s + "/droidbot/config/" + message.guild.longID + ".txt").use { file ->
                    file.write(root.toString())
                    println("Successfully Copied JSON Object to File...")
                    println("\nJSON Object: $root")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return "Sucessfully added " + mentioned.getDisplayName(message.guild) + " to the Admin list."
        }
        return "Command Error"
    }

    fun addCasinoBalance(args: Array<String>, message: IMessage, mentioned: IUser): String { // !@casino balance add @user <numofchips>
        CasinoConfig.readCasino(mentioned, message.guild)
        CasinoConfig.CHIPS = CasinoConfig.CHIPS + Utils.convertToInt(args[4])
        CasinoConfig.writeCasino(mentioned, message.guild)
        val nf = NumberFormat.getInstance()
        return "Successfully added " + nf.format(Utils.convertToInt(args[4]).toLong()) + " chips to " + mentioned.getDisplayName(message.guild) + "'s casino balance."
    }

    fun subtractCasinoBalance(args: Array<String>, message: IMessage, mentioned: IUser): String { // !@casino balance add @user <numofchips>
        CasinoConfig.readCasino(mentioned, message.guild)
        CasinoConfig.CHIPS = CasinoConfig.CHIPS - Utils.convertToInt(args[4])
        CasinoConfig.writeCasino(mentioned, message.guild)
        val nf = NumberFormat.getInstance()
        return "Successfully removed " + nf.format(Utils.convertToInt(args[4]).toLong()) + " chips from " + mentioned.getDisplayName(message.guild) + "'s casino balance."
    }

    fun setCasinoBalance(args: Array<String>, message: IMessage, mentioned: IUser): String { // !@casino balance add @user <numofchips>
        CasinoConfig.readCasino(mentioned, message.guild)
        CasinoConfig.CHIPS = Utils.convertToInt(args[4])
        CasinoConfig.writeCasino(mentioned, message.guild)
        val nf = NumberFormat.getInstance()
        return "Successfully set " + mentioned.getDisplayName(message.guild) + "'s casino balance to " + nf.format(Utils.convertToInt(args[4]).toLong())
    }

    fun setBotAbuser(args: Array<String>, message: IMessage, mentioned: IUser): String { //!@admin botabuse <@User> days reason
        Conf.applyBotAbuser(message, mentioned)
        Message.sendDM(mentioned, "You have been banned from using " + client.ourUser.mention(true) + " because of " + args[4])
        try {
            Thread.sleep((86400000 * Utils.convertToInt(args[3])).toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        Conf.removeBotAbuser(message, mentioned)
        return mentioned.mention(true) + " you have been released from your bot abuse punishment."
    }

    fun setNickname(args: Array<String>): String {
        var makeNewString = ""
        println("Begin making new string.")
        var i = 2
        while (i < args.size) {
            makeNewString = makeNewString + " " + args[i]
            i++
        }
        println("End making new string.")
        client.changeUsername(makeNewString)
        return "Name successfully set to :$makeNewString"
    }

    fun setProfilePic(args: Array<String>): String {
        client.changeAvatar(Image.forUrl(args[2], args[3]))
        return "Changed Profile Picture Sucessfully."
    }

    fun leaveGuild(args: Array<String>): String {// !admin leaveGuild <GuildLongID>
        val guild = client.getGuildByID(Utils.convertToLong(args[2])!!)
        guild.leave()
        return "Sucessfully left \"" + guild.name + "\""
    }

    fun changeRolePerm(message: IMessage, args: Array<String>): String { // !admin perms <Permission> <Role>
        var formated = ""
        var i = 3
        while (i < args.size) {
            if (i == 3) {
                formated += args[i]
            } else {
                formated = formated + " " + args[i]
            }

            i++
        }
        Roles.changeRolePerm(message, args[2], formated)
        return "Sucessfully edited role perms."

    }

    fun permFix(message: IMessage) {
        val guild = message.guild
        val guildOwner = guild.owner
        val name = client.applicationName
        guild.leave()
        Message.sendDM(guildOwner, "The permissions of $name have been broke in someway. Your guild's data that is stored on the bot has not been affected. Please use the following link to re-add $name to your server. https://discordapp.com/oauth2/authorize?client_id=315313967759097857&scope=bot&permissions=470281304")
        Message.sendDM(client.applicationOwner, client.applicationName + " was removed from guild " + guild + " in the process of a guild perm reset.")
    }

    fun muteUser(message: IMessage, mentioned: IUser, time: Int) {
        val guild = message.guild
        val channels = guild.channels
        val channelArray = channels.toTypedArray()
        var i = 0
        while (i < channelArray.size) {
            val channel = channelArray[i] as IChannel
            channel.overrideUserPermissions(mentioned, EnumSet.of(Permissions.READ_MESSAGE_HISTORY, Permissions.READ_MESSAGES), EnumSet.of(Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES))
            i++
        }
        i = 0
        Message.sendMessage(message.channel, Message.simpleEmbed(message.author, "Mute", mentioned.getDisplayName(message.guild) + " has been muted for " + time + " minutes", message))
        try {
            Thread.sleep((60000 * time).toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        while (i < channelArray.size) {
            val channel = channelArray[i] as IChannel
            channel.removePermissionsOverride(mentioned)
            i++
        }

    }

    fun unmuteUser(message: IMessage, mentioned: IUser) {
        val guild = message.guild
        val channels = guild.channels
        val channelArray = channels.toTypedArray()
        var i = 0
        while (i < channelArray.size) {
            val channel = channelArray[i] as IChannel
            channel.removePermissionsOverride(mentioned)
            i++
        }
        Message.sendMessage(message.channel, Message.simpleEmbed(message.author, "Mute", mentioned.getDisplayName(message.guild) + " has been unmuted", message))
    }

    fun unmuteUser(message: IMessage, mentioned: IUser, channel: IChannel) {
        channel.removePermissionsOverride(mentioned)
        Message.sendMessage(message.channel, Message.simpleEmbed(message.author, "Mute", mentioned.getDisplayName(message.guild) + " has been unmuted", message))
    }

    fun onAnnounceCommand(args: Array<Any>, message: IMessage) {
        if (Perms.checkOwner(message)) {
            val sendMessage = Utils.makeNewString(args, 1)
            val guilds = client.guilds
            val guildArray = guilds.toTypedArray()
            var i = 0
            while (i < guildArray.size) {
                val guild = guildArray[i] as IGuild
                val announcementsList = guild.getChannelsByName("announcements")

                if (announcementsList.size != 0) {
                    val announcements = announcementsList[0]
                    Message.sendMessage(announcements, "@here $sendMessage")
                } else if (guild.systemChannel != null) {
                    Message.sendMessage(guild.systemChannel, "@here $sendMessage")
                } else {
                    Message.sendMessage(guild.defaultChannel, "@here $sendMessage")
                }
                i++
            }
        }

    }

    fun clearChannel(channel: IChannel) {
        val history = channel.fullMessageHistory
        for (message in history) {
            if (!message.isPinned) {
                message.delete()
            }
            Thread.sleep(1250L)
        }
    }

    fun backupChat(channel: IChannel) {
        val history = channel.fullMessageHistory
        val sb = StringBuilder()
        for (message in history) {
            sb.append(message.author.getDisplayName(message.guild) + " " + message.timestamp.toString() + "\n" + message.content)
        }
        val file = File(s + "/channelbackup/" + channel.name + "/" + Instant.now().toString() + ".txt")
        file.parentFile.mkdirs()
        file.createNewFile()
        val fw = FileWriter(file)
        fw.write(sb.toString())
        Message.sendMessage(channel, "Successfully backed up this channel.")
    }


}
