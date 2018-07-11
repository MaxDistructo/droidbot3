package maxdistructo.discord.bots.droidbot.commands

import maxdistructo.discord.bots.droidbot.background.PrivUtils
import maxdistructo.discord.core.Perms
import maxdistructo.discord.core.Utils
import maxdistructo.discord.core.command.BaseCommand
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IRole
import sx.blah.discord.handle.obj.IUser


class Info : BaseCommand() {

    override val commandName: String
        get() = "info"
    override val helpMessage: String
        get() = "info - Gets your info in this server. Mods can mention a user to get information on another user"

    override fun init(message: IMessage, args: List<String>): String {
        return onInfoCommand(PrivUtils.listToArray(args), message, Utils.getMentionedUser(message))
    }

    fun onInfoCommand(args: Array<String>, message: IMessage, mentioned: IUser?): String {
        val author = message.author
        val guild = message.guild
        val guildName = guild.name
        val nick = author.getNicknameForGuild(guild)
        val roles = author.getRolesForGuild(guild)
        val rolesArray = roles.toTypedArray()
        var roleNames = ""
        var i = 1
        while (i < rolesArray.size) {
            if (i == rolesArray.size - 1) {
                val roleCheck = rolesArray[i] as IRole
                roleNames = roleNames + roleCheck.name + " "
            } else {
                val roleCheck = rolesArray[i] as IRole
                roleNames = roleNames + roleCheck.name + ", "
            }
            i++
        }
        if (mentioned == null) {
            return "You are known as: $nick. \nYou are in Discord Server: $guildName\nYour roles in this server are: $roleNames"
        } else if (Perms.checkMod(message)) {
            val nickChecked = mentioned.getNicknameForGuild(guild)
            val rolesChecked = mentioned.getRolesForGuild(guild)
            val rolesCheckedArray = rolesChecked.toTypedArray()
            var roleNamesChecked = ""
            i = 1
            while (i < rolesCheckedArray.size) {
                if (i == rolesCheckedArray.size - 1) {
                    val roleCheck = rolesCheckedArray[i] as IRole
                    roleNamesChecked = roleNamesChecked + roleCheck.name + " "
                } else {
                    val roleCheck = rolesCheckedArray[i] as IRole
                    roleNamesChecked = roleNamesChecked + roleCheck.name + ", "
                }
                i++
            }

            return "\nMember " + mentioned.name + " is also known as " + nickChecked + "\nThey have the roles " + roleNamesChecked + "\nIn Discord Server: " + guildName

        }
        return "Command has errored. Please enter a valid command."
    }
}
