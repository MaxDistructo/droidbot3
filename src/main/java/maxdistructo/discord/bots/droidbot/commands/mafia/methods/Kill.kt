package maxdistructo.discord.bots.droidbot.commands.mafia.methods

import maxdistructo.discord.bots.droidbot.commands.mafia.obj.Player
import maxdistructo.discord.core.Utils
import maxdistructo.discord.core.message.Message
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser

object Kill {
    fun message(message1: IMessage, messageContent: Array<Any>): String {
        val message = StringBuilder() // 0 = Command, 1 = Subcommand, 2 = User 3 = Param 1
        val json1 = Utils.readJSONFromFile("/config/mafia/roles.dat")
        val single = json1.getJSONObject("single_kill")
        val multi = json1.getJSONObject("multi_kill")
        var mentioned: IUser? = null
        try {
            mentioned = Utils.getUserFromInput(message1, messageContent[2])!!
        } catch (e: Exception) {
            Message.throwError(e)
        }
        message.append(mentioned!!.mention(true))
        when {
            messageContent[3] == "-2kill" -> {
                message.append(single.getString(messageContent[4].toString()))
                message.append(" ")
                message.append(multi.getString(messageContent[5].toString()))
            }
            messageContent[3] == "-3kill" -> {
                message.append(single.getString(messageContent[4].toString()))
                message.append(" ")
                message.append(multi.getString(messageContent[5].toString()))
                message.append(" ")
                message.append(multi.getString(messageContent[6].toString()))
            }
            else ->
                message.append(single.getString(messageContent[3].toString()))
        }
        if (!messageContent.contains("-clean")) {
            message.append(" ")
            val player = Player(MafiaConfig.getPlayerDetails(message1, mentioned.longID))
            if(player.role == "disguiser") {
                message.append("Their role was __**" + Player(MafiaConfig.getPlayerDetails(message1, Utils.convertToLong(MafiaConfig.getPlayerDetails(message1, mentioned.longID)[6])!!)).role.toUpperCase() + "**__.")
            }
            else{
                message.append("Their role was __**" + player.role.toUpperCase() + "**__.")
            }
        } else {
            message.append(" ")
            message.append("Their role was Cleaned")
        }

        return message.toString()
    }
}