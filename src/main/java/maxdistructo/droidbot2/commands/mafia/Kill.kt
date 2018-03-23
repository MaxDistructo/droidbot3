package maxdistructo.droidbot2.commands.mafia

import maxdistructo.droidbot2.BaseBot
import maxdistructo.droidbot2.commands.mafia.obj.Player
import maxdistructo.droidbot2.core.Utils
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser

object Kill{
    fun message(message1 : IMessage, messageContent : Array<Any>) : String{
        val message = StringBuilder() // 0 = Command, 1 = Subcommand, 2 = User 3 = Param 1
        val json1 = Utils.readJSONFromFile("/config/mafia/roles.dat")
        val single = json1.getJSONObject("single_kill")
        val multi = json1.getJSONObject("multi_kill")
        val mentioned : IUser
        if(Utils.getMentionedUser(message1) == null){
            mentioned = BaseBot.client.getUserByID(Utils.convertToLong(messageContent[2]))
        }
        else{
           mentioned = Utils.getMentionedUser(message1)!!
        }
        message.append(mentioned.getDisplayName(message1.guild) + " ")
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
        if(!messageContent.contains("-clean")){
            message.append(" ")
            val player = Player(MafiaConfig.getPlayerDetails(message1, mentioned.longID))
            message.append("The role of " + mentioned.getDisplayName(message1.guild) + " was " + player.role )
        }
        else{
            message.append(" ")
            message.append("The role of " + mentioned.getDisplayName(message1.guild) + " was Cleaned")
        }

        return message.toString()
    }
}