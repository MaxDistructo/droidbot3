package maxdistructo.discord.bots.droidbot.commands


import maxdistructo.discord.core.Perms
import sx.blah.discord.handle.obj.IMessage

object Debug {
    //@Command(aliases = {"/debug"}, description = "Shows debug info for making code for the bot.", usage = "/debug")
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
