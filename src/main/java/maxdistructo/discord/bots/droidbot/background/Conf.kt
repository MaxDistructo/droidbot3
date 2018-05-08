package maxdistructo.discord.bots.droidbot.background

import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser

object Conf {
    fun checkForBotAbuse(message: IMessage): Boolean {
        val user = message.author
        val rolesList = user.getRolesForGuild(message.guild)
        val guildRoles = message.guild.getRolesByName("Bot Abuser")
        if (guildRoles.isEmpty()) {
            println("Could not find role Bot Abuser for server " + message.guild.name)
            return false
        }
        val botAbuser = guildRoles[0]
        return rolesList.contains(botAbuser)
    }

    fun checkForPayday(message: IMessage): Boolean {
        val user = message.author
        val rolesList = user.getRolesForGuild(message.guild)
        val guildRoles = message.guild.getRolesByName("Payday")
        if (guildRoles.isEmpty()) {
            println("Could not find role Payday for server " + message.guild.name)
            return false
        }
        val payday = guildRoles[0]
        return rolesList.contains(payday)
    }

    fun applyBotAbuser(message: IMessage, mentioned: IUser) {
        val guildRoles = message.guild.getRolesByName("Bot Abuser")
        if (guildRoles.isEmpty()) {
            println("Could not find role Bot Abuser for server " + message.guild.name)
        } else {
            val role = guildRoles[0]
            mentioned.addRole(role)
        }
    }

    fun applyPayday(message: IMessage, mentioned: IUser) {
        val guild = message.guild
        val channelList = guild.getRolesByName("Payday")
        val role = channelList[0]
        mentioned.addRole(role)
    }

    fun removeBotAbuser(message: IMessage, mentioned: IUser) {
        val guildRoles = message.guild.getRolesByName("Bot Abuser")
        if (guildRoles.isEmpty()) {
            println("Could not find role Bot Abuser for server " + message.guild.name)
        } else {
            val role = guildRoles[0]
            mentioned.removeRole(role)
        }
    }

    fun removePayday(message: IMessage, mentioned: IUser) {
        val guildRoles = message.guild.getRolesByName("Payday")
        val role = guildRoles[0]
        mentioned.removeRole(role)
    }
}