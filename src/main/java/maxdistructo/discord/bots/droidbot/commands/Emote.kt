package maxdistructo.discord.bots.droidbot.commands

import maxdistructo.discord.bots.droidbot.BaseBot
import maxdistructo.discord.core.Perms
import maxdistructo.discord.core.message.Message
import org.apache.commons.io.FileUtils
import sx.blah.discord.handle.obj.IMessage
import java.io.File
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.nio.file.Paths

object Emote {

    fun onEmoteCommand(message: IMessage, args: Array<String>) { //!emote <EmoteName>

        val currentRelativePath = Paths.get("")
        val s = currentRelativePath.toAbsolutePath().toString()
        val file = File(s + "/droidbot/config/emotes/" + args[1] + ".png")
        if (file.exists()) {
            Message.sendMessage(message.channel, file)
        } else {
            Message.sendMessage(message.channel, "Emote not found.")
        }


    }

    fun addEmoteCommand(message: IMessage, args: Array<String>) { //!emote add <EmoteName> <URL>
        if (Perms.checkOwner(message)) {
            val currentRelativePath = Paths.get("")
            val s = currentRelativePath.toAbsolutePath().toString()
            val file = File(s + "/droidbot/config/emotes/" + args[2] + ".png")
            var url: URL? = null
            try {
                url = URL(args[3])
            } catch (e: MalformedURLException) {
                Thread.currentThread().interrupt()
                e.printStackTrace()

            }

            try {
                FileUtils.copyURLToFile(url!!, file)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        } else {
            Message.sendMessage(message.channel, "You do not have perms to add Emotes.")
        }
    }

    fun requestEmoteCommand(message: IMessage, args: Array<String>) { //!emote request <EmoteName> <URL>
        Message.sendDM(BaseBot.client.applicationOwner, "User: " + message.author.name + message.author.discriminator + " would like you to add the emote " + args[2] + " which is found at " + args[3])
    }
}
