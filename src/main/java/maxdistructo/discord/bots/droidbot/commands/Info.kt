package maxdistructo.discord.bots.droidbot.commands

import maxdistructo.discord.core.Perms
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IRole
import sx.blah.discord.handle.obj.IUser


object Info {
    // @Command(aliases = {"/i", "/info"}, description = "Gets your info. Mods can get info on any user.", usage = "/i|info @User#0000")
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
