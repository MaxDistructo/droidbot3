package maxdistructo.discord.bots.droidbot.commands

import maxdistructo.discord.bots.droidbot.commands.admin.AdminCommand
import sx.blah.discord.handle.obj.IMessage

class Shutdown : AdminCommand(){
    override val requiresOwner: Boolean
        get() = true
    override val requiresAdmin: Boolean //To ensure even if registered in non Admin Listener, it can not be run by every person on the server.
        get() = true
    override val helpMessage: String
        get() = "@shutdown - Shuts the bot down."
    override val commandName: String
        get() = "shutdown"

    override fun init(message: IMessage, args: List<String>): String {
        return onShutdownCommand(message)
    }

    fun onShutdownCommand(message: IMessage): String {
            message.reply("Shutting Down")
            System.exit(0)
            return ""
    }
}
