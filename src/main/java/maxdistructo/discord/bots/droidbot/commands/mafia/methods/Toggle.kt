package maxdistructo.discord.bots.droidbot.commands.mafia.methods

import maxdistructo.discord.bots.droidbot.BaseBot
import maxdistructo.discord.bots.droidbot.commands.mafia.obj.Game
import maxdistructo.discord.bots.droidbot.commands.mafia.obj.Player
import maxdistructo.discord.core.Roles
import maxdistructo.discord.core.Utils
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.Permissions
import java.util.*

object Toggle {
    fun fixAlivePlayerPermsDay(message : IMessage, game : Game){
        val dayChannel = game.dayChannel
        for(player in MafiaConfig.getPlayers(message ,"Mafia Folks")) {
            if (!Perms.checkMod(message, player)) {
                dayChannel.removePermissionsOverride(message.guild.getUserByID(player))
                dayChannel.overrideUserPermissions(message.guild.getUserByID(player), EnumSet.of(Permissions.READ_MESSAGES, Permissions.READ_MESSAGE_HISTORY, Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES), EnumSet.noneOf(Permissions::class.java))
                val playerInfo = MafiaConfig.getPlayerDetails(message, player)
                if (playerInfo[0].toString() == "mafia") {//check if mafia
                    Perms.denyMafChat(message, message.guild.getUserByID(player))
                } else {
                    Perms.denyNonMaf(message, message.guild.getUserByID(player))
                }
                if (playerInfo[2].toString() == "jailor") {
                    game.jailorChannel.overrideUserPermissions(message.guild.getUserByID(player), EnumSet.of(Permissions.READ_MESSAGES, Permissions.READ_MESSAGE_HISTORY), EnumSet.of(Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES))
                } else {
                    Perms.denyJailorChat(message, message.guild.getUserByID(player))
                }
                if (MafiaConfig.getJailed(message) == player) {
                    Perms.denyJailedChat(message, message.guild.getUserByID(player))
                    Mafia.unjail(message)
                }
                if (playerInfo[0].toString() == "vampire") {
                    game.vampChannel.overrideUserPermissions(message.guild.getUserByID(player), EnumSet.of(Permissions.READ_MESSAGES), EnumSet.of(Permissions.SEND_MESSAGES))
                }
                if (playerInfo[0].toString() == "vampire_hunter") {
                    game.vamphunterChannel.overrideUserPermissions(message.guild.getUserByID(player), EnumSet.of(Permissions.READ_MESSAGES), EnumSet.of(Permissions.SEND_MESSAGES))
                }
                Perms.denyMediumChat(message, message.guild.getUserByID(player))
            }
        }
    }
    fun fixAlivePlayerPermsNight(message : IMessage, game : Game) {
        for(player in MafiaConfig.getPlayers(message, "Mafia Folks")) {
            if (!Perms.checkMod(message, player)) {
                val playerInfo = MafiaConfig.getPlayerDetails(message, player)
                if (playerInfo[0].toString() == "mafia") {//check if mafia
                    game.mafiaChannel.overrideUserPermissions(message.guild.getUserByID(player), EnumSet.of(Permissions.READ_MESSAGE_HISTORY, Permissions.READ_MESSAGES, Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES), EnumSet.noneOf(Permissions::class.java))
                } else {
                    game.mafiaChannel.overrideUserPermissions(message.guild.getUserByID(player), EnumSet.noneOf(Permissions::class.java), EnumSet.of(Permissions.READ_MESSAGE_HISTORY, Permissions.READ_MESSAGES, Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES))
                }
                println("Ran Mafia Toggle")
                if (playerInfo[2].toString() == "jailor") {
                    Perms.allowJailorChat(message, message.guild.getUserByID(player))
                } else {
                    Perms.denyJailorChat(message, message.guild.getUserByID(player))
                }
                println("Ran Jailor Toggle")
                if (MafiaConfig.getJailed(message) == player) {
                    val history = game.jailedChannel.messageHistory
                    for (hist in history) {
                        if (!hist.isPinned) {
                            message.delete()
                        }
                    }
                    Perms.allowJailedChat(message, message.guild.getUserByID(player))
                } else {
                    Perms.denyJailedChat(message, message.guild.getUserByID(player))
                }
                println("Ran Jailed Toggle")
                if (playerInfo[2].toString() == "medium") {
                    Perms.allowMediumChat(message, message.guild.getUserByID(player))
                } else {
                    Perms.denyMediumChat(message, message.guild.getUserByID(player))
                }
                println("Ran Medium Toggle")
                if (playerInfo[2].toString() == "spy" || playerInfo[2].toString() == "blackmailer") {
                    Perms.allowSpyChat(message, message.guild.getUserByID(player))
                }
                else{
                    Perms.denySpyChat(message, message.guild.getUserByID(player))
                }
                println("Ran Spy Toggle")
                if (playerInfo[0].toString() == "vampire") {
                    game.vampChannel.overrideUserPermissions(message.guild.getUserByID(player), EnumSet.of(Permissions.READ_MESSAGES, Permissions.SEND_MESSAGES), EnumSet.noneOf(Permissions::class.java))
                }
                if (playerInfo[0].toString() == "vampire_hunter") {
                    game.vamphunterChannel.overrideUserPermissions(message.guild.getUserByID(player), EnumSet.of(Permissions.READ_MESSAGES), EnumSet.of(Permissions.SEND_MESSAGES))
                }
                Perms.denyDayChat(message, message.guild.getUserByID(player))
                println("Ran Night Toggle")

            }
        }
    }
    fun fixDeadPerms(message : IMessage, game : Game){
        for(player in MafiaConfig.getPlayers(message ,"Mafia Folks")) {
            if (message.guild.getUserByID(player).getRolesForGuild(message.guild).contains(message.guild.getRolesByName("Dead(Mafia)")[0])) {
                val user = Player(message, player)
                game.dayChannel.removePermissionsOverride(message.guild.getUserByID(player))
                Perms.denyDayChat(message, message.guild.getUserByID(player))
                game.mafiaChannel.removePermissionsOverride(message.guild.getUserByID(player))
                if (user.allignment == "mafia") {
                    Perms.denyMafChat(message, message.guild.getUserByID(player))
                } else {
                    Perms.denyNonMaf(message, message.guild.getUserByID(player))
                }
                game.deadChannel.removePermissionsOverride(message.guild.getUserByID(player))
                Perms.allowDeadChat(message, message.guild.getUserByID(player))
            }
        }
    }
    fun setDayConfig(message : IMessage, game : Game){
        val root = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt")
        if(root.getBoolean("day")) {
            root.remove("day")
            root.put("day", false)
            MafiaConfig.writeGameDat(message, root)
        }
        else{
            root.remove("day")
            root.put("day", true)
            root.remove("daynum")
            root.put("daynum", game.dayNum + 1)
            MafiaConfig.writeGameDat(message, root)
        }
    }

    fun updateTopic(message : IMessage, game : Game){
        val daynumber = game.dayNum
        game.dayChannel.changeTopic("Day " + daynumber + " - " +  message.guild.getUsersByRole(message.guild.getRolesByName("Alive(Mafia)")[0]) + " alive, "  + message.guild.getUsersByRole(message.guild.getRolesByName("Dead(Mafia)")[0]) + " dead")
    }

    fun fixRoles(message : IMessage, game : Game){
        val players = MafiaConfig.getPlayers(message, "Mafia Folks")
        for (player in players) {
            val aliveRole = Roles.getRole(message, "Mafia(Alive)")!!
            val deadRole = Roles.getRole(message, "Mafia(Dead)")!!
            message.guild.getUserByID(player).removeRole(deadRole)
            message.guild.getUserByID(player).addRole(aliveRole)
            val playerInfo = MafiaConfig.getPlayerDetails(message)
            if (playerInfo[2].toString() == "spy" || playerInfo[2].toString() == "blackmailer") {
                Perms.allowSpyChat(message, message.guild.getUserByID(player))
            } else if (playerInfo[2].toString() == "medium") {
                Perms.allowMediumChat(message, message.guild.getUserByID(player))
            }
            else if(playerInfo[2].toString() == "vampire"){
                game.vampChannel.overrideUserPermissions(BaseBot.bot.client.getUserByID(player), EnumSet.of(Permissions.READ_MESSAGES), EnumSet.of(Permissions.SEND_MESSAGES))
            }
            else if(playerInfo[2].toString() == "vampire_hunter"){
                game.vamphunterChannel.overrideUserPermissions(BaseBot.bot.client.getUserByID(player),EnumSet.of(Permissions.READ_MESSAGES), EnumSet.of(Permissions.SEND_MESSAGES))
            }
        }
    }
}