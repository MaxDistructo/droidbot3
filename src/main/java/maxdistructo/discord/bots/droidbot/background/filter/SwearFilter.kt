package maxdistructo.discord.bots.droidbot.background.filter

import maxdistructo.discord.bots.droidbot.core.Config
import org.json.JSONArray
import org.json.JSONObject
import sx.blah.discord.handle.obj.IMessage

import java.util.Arrays

object SwearFilter {

    fun filter(message: IMessage, messageContentIn: Array<Any>) {
        val messageContent2 = Arrays.asList(*messageContentIn as Array<String>)
        val messageContent = Arrays.asList<String>(*arrayOfNulls(messageContentIn.size))
        var doFilter = true
        var iiii = 0
        while (iiii < messageContent2.size) {
            messageContent[iiii] = messageContent2[iiii].toLowerCase()
            iiii++
        }

        val config = Config.readServerConfig(message.guild)
        var array: JSONArray? = null
        try {
            array = config.getJSONArray("SwearWords")
        } catch (e: Exception) {
            doFilter = false
        }

        if (doFilter && config.getBoolean("SwearFilter")) {
            val swearWords = arrayOfNulls<String>(array!!.length())
            val pronouns = arrayOf("u", "you")
            for (i in 0 until array.length()) {
                swearWords[i] = array.getString(i).toLowerCase()
            }

            var ii = 0
            var iii = 0
            while (ii < swearWords.size) {
                if (messageContent.contains(swearWords[ii])) {
                    while (iii < pronouns.size) {
                        if (messageContent.contains(pronouns[iii])) {
                            message.reply("please stop swearing. If you keep swearing, a Mod/Admin will mute/kick/ban you depending on severity.")
                            message.delete()
                        }
                        iii++
                    }
                }
                iii = 0
                ii++
            }
        }

    }


}
