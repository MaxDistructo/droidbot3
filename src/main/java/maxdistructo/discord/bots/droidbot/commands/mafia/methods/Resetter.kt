package maxdistructo.discord.bots.droidbot.commands.mafia.methods

import maxdistructo.discord.bots.droidbot.commands.admin.Admin
import maxdistructo.discord.bots.droidbot.commands.mafia.MafiaCommand
import maxdistructo.discord.bots.droidbot.commands.mafia.obj.Game
import maxdistructo.discord.core.Roles
import maxdistructo.discord.core.Utils
import maxdistructo.discord.core.Utils.s
import maxdistructo.discord.core.command.ICommandType
import maxdistructo.discord.core.message.Message
import sx.blah.discord.handle.obj.IMessage
import java.io.File

object Resetter {

    class Command : MafiaCommand(){
        override val commandName: String
            get() = "reset"
        override val helpMessage: String
            get() = "mafia reset - Resets the mafia game."
        override val commandType: Enum<ICommandType>
            get() = ICommandType.GAME
        override val requiresAdmin: Boolean
            get() = true

        override fun init(message: IMessage, args: List<String>): String {
            reset(message)
            return ""
        }
    }

    private fun reset(message : IMessage){
        Message.sendMessage(message.channel, "Reset has begun. Please wait.")
        println("Resetting Overrides")
        resetOverrides(message)
        println("Resetting Roles")
        resetRoles(message)
        println("Resetting PlayerDat file")
        val file = File(s + "/config/mafia/" + message.guild.longID + "_playerdat.txt")
        file.delete() //Clears the Player Data file
        println("Resetting Channels")
        channelClear(message)
        Message.sendMessage(message.channel, "Mafia Reset has been completed.")
    }
    private fun resetRoles(message : IMessage){
        val inRole = Roles.getRole(message, "Mafia Folks")
        val outRole = Roles.getRole(message, "Spectator(Mafia)")
        val aliveRole = Roles.getRole(message, "Alive(Mafia)")
        val deadRole = Roles.getRole(message, "Dead(Mafia)")
        for(user in message.guild.users){
            if(user != null) {
                val userRoles = user.getRolesForGuild(message.guild)
                if (userRoles.containsAll(listOf(inRole, outRole))) {
                    user.removeRole(outRole)
                }
                if (userRoles.contains(aliveRole)) {
                    user.removeRole(aliveRole)
                }
                if (userRoles.contains(deadRole)) {
                    user.removeRole(deadRole)
                }
            }
            Thread.sleep(4000L)
        }
    }
    private fun channelClear(message : IMessage){
        val game = Game(Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt"))
        Admin.clearChannel(game.vamphunterChannel)
        Admin.clearChannel(game.spyChannel)
        Admin.clearChannel(game.jailedChannel)
        Admin.clearChannel(game.mediumChannel)
        Admin.clearChannel(game.vampChannel)
        Admin.clearChannel(game.jailorChannel)
        Admin.clearChannel(game.mafiaChannel)
        Admin.clearChannel(game.deadChannel)
        Admin.clearChannel(game.dayChannel)
    }
    private fun resetOverrides(message : IMessage){
        val game = Game(Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt"))
        val channels = listOf(game.dayChannel, game.deadChannel, game.mafiaChannel, game.mediumChannel,game.spyChannel, game.jailedChannel, game.jailorChannel, game.vamphunterChannel, game.vampChannel)
        for(player in MafiaConfig.getPlayers(message, "Mafia Folks")){
            for(channel in channels){
                channel.removePermissionsOverride(message.guild.getUserByID(player))
            }
        }
    }
}