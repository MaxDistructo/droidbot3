package maxdistructo.droidbot2.commands

import java.io.IOException

import maxdistructo.droidbot2.core.Perms
import maxdistructo.droidbot2.core.message.Message
import sx.blah.discord.handle.obj.IMessage

object Restart {
    fun run(message: IMessage) {
        if (Perms.checkAdmin(message)) {
            try {
                val ps = Runtime.getRuntime().exec(arrayOf("java", "-jar", "droidbot2.jar"))
            } catch (e: IOException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }

            Message.sendMessage(message.channel, "Reboot in progress.")
            System.exit(0)
        }
    }
}
