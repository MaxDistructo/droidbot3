package maxdistructo.discord.bots.droidbot.commands


import maxdistructo.discord.bots.droidbot.background.PrivUtils
import maxdistructo.discord.core.Perms
import maxdistructo.discord.core.command.BaseCommand
import sx.blah.discord.handle.obj.IMessage

class Debug : BaseCommand() {
    override val commandName: String
        get() = "debug"
    override val helpMessage: String
        get() = "debug - Debug information"
    override val requiresAdmin: Boolean
        get() = true

    override fun init(message: IMessage, args: List<String>): String {
       return onDebugCommand(PrivUtils.listToArray(args), message)
    }

    fun onDebugCommand(args: Array<String>, message: IMessage): String {
        val author = message.author
        val channel = message.channel
        val guild = message.guild
        val owner = guild.owner
        val roles = guild.roles


        return if (Perms.checkMod(message)) {
            "You are: $author. \nYour channel is: $channel. \nYour Owner is: $owner. \nYour server's roles are: $roles"
        } else {
            "You have insufficient perms to use this command"
        }

    }
}
