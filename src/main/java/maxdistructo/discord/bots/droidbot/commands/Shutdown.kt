package maxdistructo.discord.bots.droidbot.commands

import maxdistructo.discord.core.Perms
import sx.blah.discord.handle.obj.IMessage

object Shutdown {

    fun onShutdownCommand(message: IMessage): String {
        if (Perms.checkOwner(message)) {
            message.reply("Shutting Down")
            System.exit(0)
            return ""
        }
        return "You do not have perms to shutdown this bot."
    }
}
