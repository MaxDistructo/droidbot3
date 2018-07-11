package maxdistructo.discord.bots.droidbot.commands.mafia.methods

import maxdistructo.discord.bots.droidbot.commands.mafia.obj.Game
import maxdistructo.discord.bots.droidbot.commands.mafia.MafiaCommand
import maxdistructo.discord.core.Utils
import maxdistructo.discord.core.Utils.s
import maxdistructo.discord.core.message.Message
import sx.blah.discord.handle.obj.IGuild
import sx.blah.discord.handle.obj.IMessage
import java.io.*

class MafiaReset : MafiaCommand(){
  override val commandName: String
    get() = "reset"
  override val helpMessage: String
    get() = "mafia reset - Resets the mafia game"
  override val requiresAdmin: Boolean
    get() = true

  override fun init(message: IMessage, args: List<String>): String {
    onCommand(Game(Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt")), message.guild)
    return ""
  }

  fun onCommand(game : Game, guild : IGuild){
  
    //Reset playerdat file. This holds all current role information for each user.
    val file = File(s + "/config/mafia/" + guild.longID + "_playerdat.txt")
    file.delete()
    /*
    //Backup the 3 main chat channels. Day, Dead, and Mafia.
    Admin.backupChat(game.dayChannel)
    Admin.backupChat(game.deadChannel)
    Admin.backupChat(game.mafiaChannel)
    */
    //Reset and clear all mafia game channels.

      println("Starting Chat Clearing")
    val channels = listOf(game.deadChannel, game.mafiaChannel, game.jailedChannel, game.jailorChannel, game.mediumChannel, game.spyChannel)
    for(channel in channels){
      channel.bulkDelete()
    }
    Message.sendMessage(game.adminChannel, "Mafia Reset Complete")
  }

}
