package maxdistructo.droidbot2.commands.mafia

import maxdistructo.droidbot2.commands.mafia.obj.Game
import maxdistructo.droidbot2.commands.mafia.obj.Player
import maxdistructo.droidbot2.core.Roles
import maxdistructo.droidbot2.core.Utils
import maxdistructo.droidbot2.core.Utils.s
import maxdistructo.droidbot2.core.message.Message
import org.json.JSONObject
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser
import sx.blah.discord.handle.obj.Permissions
import java.io.File
import java.util.*

object Mafia {

    fun onMafiaJoin(message: IMessage) {
        //TODO Read player file(Create if not existence), add new user ID to file, write file back to disk. Will return String saying player name has been successfully added to game.
    }

    fun onGameJoinCommand(message: IMessage) {
        val role = Roles.getRole(message, "Mafia Folks")
        message.author.addRole(role)
        Message.sendMessage(message.channel, message.author.mention(true) + ", you have been added to the Mafia game.")
        message.delete()
    }

    fun onGameLeaveCommand(message: IMessage) {
        val role = Roles.getRole(message, "Mafia Folks")
        message.author.removeRole(role)
        message.reply(message.author.mention(true) + ", you have been added to the Mafia game.")
        message.delete()
    }

    fun onGameStart(message: IMessage) {
        val game = Game(Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt"))
        val adminChannel = game.adminChannel
        val dayChannel = game.dayChannel
        assignRoles(message)
        Message.sendMessage(dayChannel, "@everyone The Mafia game has started! \n Day 1 has begun!")
        Message.sendMessage(adminChannel, message.author.getDisplayName(message.guild) + message.author.discriminator + " has started the Mafia game.")
        val players = MafiaConfig.getPlayers(message, "Mafia Folks")
        resetChannelOverrides(message, players)
        unjail(message)
        for (player in players) {
            val aliveRole = Roles.getRole(message, "Mafia(Alive)")
            val deadRole = Roles.getRole(message, "Mafia(Dead)")
            message.guild.getUserByID(player).removeRole(deadRole)
            message.guild.getUserByID(player).addRole(aliveRole)
            val playerInfo = MafiaConfig.getPlayerDetails(message)
            if (playerInfo[2].toString() == "spy") {
                allowSpyChat(message, message.guild.getUserByID(player))
            }
            else if (playerInfo[2].toString() == "medium") {
                allowMediumChat(message, message.guild.getUserByID(player))
            }
        }


    }

    fun onGameToggle(message: IMessage) {
        val game = Game(Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt"))
        val dayChannel = game.dayChannel
        val players = MafiaConfig.getPlayers(message, "Mafia Folks")

        //runActions();

        if (game.day) {
            Message.sendMessage(game.adminChannel, "Starting change over to night mode. Please wait.")
            resetChannelOverrides(message, players)
            for (player in players) {
                if (!Perms.checkMod(message, player)) {
                    val playerInfo = MafiaConfig.getPlayerDetails(message, player)
                    if (playerInfo[0].toString() == "mafia") {//check if mafia
                        game.mafiaChannel.overrideUserPermissions(message.guild.getUserByID(player), EnumSet.of(Permissions.READ_MESSAGE_HISTORY, Permissions.READ_MESSAGES, Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES), EnumSet.noneOf(Permissions::class.java))
                    } else {
                        game.mafiaChannel.overrideUserPermissions(message.guild.getUserByID(player), EnumSet.noneOf(Permissions::class.java), EnumSet.of(Permissions.READ_MESSAGE_HISTORY, Permissions.READ_MESSAGES, Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES))
                    }
                    println("Ran Mafia Toggle")
                    if (playerInfo[2].toString() == "jailor") {
                        allowJailorChat(message, message.guild.getUserByID(player))
                    } else {
                        denyJailorChat(message, message.guild.getUserByID(player))
                    }
                    println("Ran Jailor Toggle")
                    if (MafiaConfig.getJailed(message) == player) {
                        val history = game.jailedChannel.messageHistory
                        for(hist in history){
                            if(!hist.isPinned){
                                message.delete()
                            }
                        }
                        allowJailedChat(message, message.guild.getUserByID(player))
                    } else {
                        denyJailedChat(message, message.guild.getUserByID(player))
                    }
                    println("Ran Jailed Toggle")
                    if (playerInfo[2].toString() == "medium") {
                        allowMediumChat(message, message.guild.getUserByID(player))
                    } else {
                        denyMediumChat(message, message.guild.getUserByID(player))
                    }
                    println("Ran Medium Toggle")
                    denyDayChat(message, message.guild.getUserByID(player))
                    println("Ran Day Toggle")

                    if (message.guild.getUserByID(player).getRolesForGuild(message.guild).contains(message.guild.getRolesByName("Dead(Mafia)")[0])) {
                        val user = Player(message,player)
                        game.dayChannel.removePermissionsOverride(message.guild.getUserByID(player))
                        denyDayChat(message, message.guild.getUserByID(player))
                        game.mafiaChannel.removePermissionsOverride(message.guild.getUserByID(player))
                        if(user.allignment == "mafia") {
                            denyMafChat(message, message.guild.getUserByID(player))
                        }
                        else{
                            denyNonMaf(message, message.guild.getUserByID(player))
                        }
                        game.deadChannel.removePermissionsOverride(message.guild.getUserByID(player))
                        allowDeadChat(message, message.guild.getUserByID(player))
                    }

                }
            }
                    val root = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt")
                    root.remove("day")
                    root.put("day", false)
                    MafiaConfig.writeGameDat(message, root)
            Message.sendMessage(game.adminChannel, "Successfully converted over to night mode.")
                }
        else {
            Message.sendMessage(game.adminChannel, "Starting change over to day mode. Please wait.")
            resetChannelOverrides(message, players)
            for (player in players) {
                if (!Perms.checkMod(message, player)) {
                    dayChannel.removePermissionsOverride(message.guild.getUserByID(player))
                    dayChannel.overrideUserPermissions(message.guild.getUserByID(player), EnumSet.of(Permissions.READ_MESSAGES, Permissions.READ_MESSAGE_HISTORY, Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES), EnumSet.noneOf(Permissions::class.java))
                    val playerInfo = MafiaConfig.getPlayerDetails(message, player)
                    if (playerInfo[0].toString() == "mafia") {//check if mafia
                        denyMafChat(message, message.guild.getUserByID(player))
                    }
                    else{
                        denyNonMaf(message, message.guild.getUserByID(player))
                    }
                    if (playerInfo[2].toString() == "jailor") {
                        game.jailorChannel.overrideUserPermissions(message.guild.getUserByID(player), EnumSet.of(Permissions.READ_MESSAGES, Permissions.READ_MESSAGE_HISTORY), EnumSet.of(Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES))
                    }
                    else{
                        denyJailorChat(message, message.guild.getUserByID(player))
                    }
                    if (MafiaConfig.getJailed(message) == player) {
                        denyJailedChat(message, message.guild.getUserByID(player))
                        unjail(message)
                    }
                     denyMediumChat(message, message.guild.getUserByID(player))
                }
                if (message.guild.getUserByID(player).getRolesForGuild(message.guild).contains(message.guild.getRolesByName("Dead(Mafia)")[0])) {
                    val user = Player(message,player)
                    game.dayChannel.removePermissionsOverride(message.guild.getUserByID(player))
                    denyDayChat(message, message.guild.getUserByID(player))
                    game.mafiaChannel.removePermissionsOverride(message.guild.getUserByID(player))
                    if(user.allignment == "mafia") {
                        denyMafChat(message, message.guild.getUserByID(player))
                    }
                    else{
                        denyNonMaf(message, message.guild.getUserByID(player))
                    }
                    game.deadChannel.removePermissionsOverride(message.guild.getUserByID(player))
                    allowDeadChat(message, message.guild.getUserByID(player))
                }
            }
                    val root = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt")
                    root.remove("day")
                    root.put("day", true)
                    MafiaConfig.writeGameDat(message, root)
            Message.sendMessage(game.adminChannel, "Successfully converted over to day mode.")
        }
    }

    private fun denyMediumChat(message: IMessage, userByID: IUser?) {
        val game = Game(Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt"))
        game.mediumChannel.overrideUserPermissions(userByID, EnumSet.noneOf(Permissions::class.java), EnumSet.of(Permissions.READ_MESSAGES))
    }

    private fun denyJailedChat(message: IMessage, userByID: IUser?) {
        val game = Game(Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt"))
        game.jailedChannel.overrideUserPermissions(userByID, EnumSet.noneOf(Permissions::class.java), EnumSet.of(Permissions.READ_MESSAGES))
    }

    private fun denyJailorChat(message: IMessage, userByID: IUser) {
        val game = Game(Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt"))
        game.jailorChannel.overrideUserPermissions(userByID, EnumSet.noneOf(Permissions::class.java), EnumSet.of(Permissions.READ_MESSAGES))
    }
    fun denyDayChat(message: IMessage, user: IUser) {
        val game = Game(Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt"))
        game.dayChannel.overrideUserPermissions(user, EnumSet.of(Permissions.READ_MESSAGE_HISTORY, Permissions.READ_MESSAGES), EnumSet.of(Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES))
    }

    fun denyMafChat(message: IMessage, user: IUser) {
        val game = Game(Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt"))
        game.mafiaChannel.overrideUserPermissions(user, EnumSet.of(Permissions.READ_MESSAGE_HISTORY, Permissions.READ_MESSAGES), EnumSet.of(Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES))
    }
    fun denyNonMaf(message: IMessage, user: IUser){
        val game = Game(Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt"))
        game.mafiaChannel.overrideUserPermissions(user, EnumSet.noneOf(Permissions::class.java), EnumSet.of(Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES, Permissions.READ_MESSAGE_HISTORY, Permissions.READ_MESSAGES))
    }

    fun allowMediumChat(message: IMessage, user: IUser) {
        val game = Game(Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt"))
        game.mediumChannel.overrideUserPermissions(user, EnumSet.of(Permissions.READ_MESSAGE_HISTORY, Permissions.READ_MESSAGES, Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES), EnumSet.noneOf(Permissions::class.java))
    }

    fun allowJailorChat(message: IMessage, user: IUser) {
        val game = Game(Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt"))
        game.jailorChannel.overrideUserPermissions(user, EnumSet.of(Permissions.READ_MESSAGE_HISTORY, Permissions.READ_MESSAGES, Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES), EnumSet.noneOf(Permissions::class.java))
    }

    fun allowJailedChat(message: IMessage, user: IUser) {
        val game = Game(Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt"))
        game.jailedChannel.overrideUserPermissions(user, EnumSet.of(Permissions.READ_MESSAGES, Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES), EnumSet.noneOf(Permissions::class.java))
    }

    fun allowSpyChat(message: IMessage, user: IUser) {
        val game = Game(Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt"))
        game.spyChannel.overrideUserPermissions(user, EnumSet.of(Permissions.READ_MESSAGE_HISTORY, Permissions.READ_MESSAGE_HISTORY, Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES), EnumSet.noneOf(Permissions::class.java))
    }

    fun allowDeadChat(message: IMessage, user: IUser) {
        val game = Game(Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt"))
        game.deadChannel.overrideUserPermissions(user, EnumSet.of(Permissions.READ_MESSAGE_HISTORY, Permissions.READ_MESSAGES, Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES), EnumSet.noneOf(Permissions::class.java))
    }

    fun assignRoles(message: IMessage): JSONObject {
        val root = Utils.readJSONFromFile("/config/mafia/roles.dat")
        val jArrayRoles = root.getJSONArray("rolelist")
        val translator = root.getJSONObject("rolelist-translate")
        val roleData = root.getJSONObject("roleData")
        val players = MafiaConfig.getPlayers(message, "Mafia Folks")
        val roleList = MafiaConfig.shuffleJSONArray(jArrayRoles)
        val game = Game(Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt"))
        var i = 0
        for (role in roleList) {
            val roleDatJSON = translator.getJSONArray(role)
            val roleDat = MafiaConfig.shuffleJSONArray(roleDatJSON)
            roleList[i] = roleDat[0]
            i++
        }
        val out = JSONObject()
        var ii = 0
        val sb = StringBuilder()
        sb.append("The role list contains: \n")
        for (player in players) {
            out.put("" + player, roleData.getJSONObject(roleList[ii]))
            ii++
            Message.sendDM(message.guild.getUserByID(player), "You are a " + roleList[ii] + "\n For more details on this role visit town-of-salem.wikia.com/wiki/"+ roleList[ii])
            sb.append(message.guild.getUserByID(player).name + ": " + roleList[ii] + "\n")
        }
        Message.sendMessage(game.adminChannel, sb.toString())
        File(s + "/config/mafia/" + message.guild.longID + "_playerdat.txt").delete()
        MafiaConfig.writeGame(message, out)
        return out
    }

    fun killPlayer(message: IMessage, playerID: Long): String {
        val player = message.guild.getUserByID(playerID)
        val playerDetails = MafiaConfig.getPlayerDetails(message, playerID)
        if (playerDetails[3] as Boolean) {
            return "Player is already dead. Can not kill player " + player.getDisplayName(message.guild) + " because they are already dead. "
        }
        val root = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_playerdat.txt")
        val playerInfo = root.getJSONObject("" + playerID)
        playerInfo.remove("dead")
        playerInfo.put("dead", true)
        root.remove("" + playerID)
        root.put("" + playerID, playerInfo)
        MafiaConfig.writeGame(message, root)
        val dead = Roles.getRole(message, "Dead(Mafia)")
        val alive = Roles.getRole(message, "Alive(Mafia)")
        player.addRole(dead)
        player.removeRole(alive)
        return "Successfully killed player " + player.getDisplayName(message.guild)
    }

    fun jailPlayer(message: IMessage, user: IUser) {
        val playerDetails = MafiaConfig.getPlayerDetails(message, user.longID)
        val root = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt")
        root.remove("jailed")
        if (playerDetails[2].toString() == MafiaConfig.getPlayerDetails(message)[2].toString()) {
            println("Jailor's can not jail themselves!")
            root.put("jailed", 0L)
        } else {
            root.put("jailed", user.longID)
        }
        MafiaConfig.writeGameDat(message, root)
    }

    fun unjail(message: IMessage) {
        val root = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt")
        root.remove("jailed")
        root.put("jailed", 0)
        MafiaConfig.writeGameDat(message, root)
    }
    fun setRole(message: IMessage, user: IUser, role: String) {
        val root = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_playerdat.txt")
        val root1 = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + ".dat")
        val translator = root1.getJSONObject("rolelist-translate")
        val roleData = root1.getJSONObject("roleData")
        val roleAssign: String
        root.remove("" + user.longID)
        val roleDatJSON = translator.getJSONArray(role)
        val roleDat = MafiaConfig.shuffleJSONArray(roleDatJSON)
        roleAssign = roleDat[0].toString()
        root.put("" + user.longID, roleData.getJSONObject(roleAssign))
    }
    fun resetChannelOverrides(message : IMessage, players : LongArray){
        val game = Game(Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt"))
        for(player in players){
            game.mafiaChannel.removePermissionsOverride(message.guild.getUserByID(player))
            game.jailorChannel.removePermissionsOverride(message.guild.getUserByID(player))
            game.jailedChannel.removePermissionsOverride(message.guild.getUserByID(player))
            game.mediumChannel.removePermissionsOverride(message.guild.getUserByID(player))
            game.deadChannel.removePermissionsOverride(message.guild.getUserByID(player))
            game.spyChannel.removePermissionsOverride(message.guild.getUserByID(player))
        }
    }

}
