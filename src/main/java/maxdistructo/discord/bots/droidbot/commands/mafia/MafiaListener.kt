package maxdistructo.discord.bots.droidbot.commands.mafia


import kotlinx.coroutines.experimental.launch
import maxdistructo.discord.bots.droidbot.BaseBot
import maxdistructo.discord.bots.droidbot.background.BaseListener
import maxdistructo.discord.bots.droidbot.background.PrivUtils
import maxdistructo.discord.bots.droidbot.commands.mafia.init.IHandler
import maxdistructo.discord.bots.droidbot.commands.mafia.methods.*
import maxdistructo.discord.bots.droidbot.commands.mafia.obj.Details
import maxdistructo.discord.bots.droidbot.commands.mafia.obj.Game
import maxdistructo.discord.bots.droidbot.commands.mafia.obj.Player
import maxdistructo.discord.core.Config
import maxdistructo.discord.core.Utils
import maxdistructo.discord.core.command.BaseCommand
import maxdistructo.discord.core.command.ICommand
import maxdistructo.discord.core.message.Message
import maxdistructo.discord.core.message.Webhook
import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser
import java.util.*

class MafiaListener : BaseListener() {

    override var adminCommands = listOf<BaseCommand>()
    override var commandsArray = listOf<BaseCommand>()
    override var modCommands = listOf<BaseCommand>()
    override val name = "Botfather.Mafia"
    override var commandRegistry = LinkedList<ICommand>()


    @EventSubscriber
    override fun onMessageReceivedEvent(event: MessageReceivedEvent) {
        val message = event.message
        val prefix = Config.readPrefix()
        val messageContent = message.content.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray() as Array<Any>
        if (messageContent.isNotEmpty()) {
            if (message.guild.longID == 249615705517981706L || message.guild.longID == 268370862661435392L && Perms.checkMafiaChannels(message)) {
                var game = Game(Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt"))
                if (!game.day && message.channel === game.deadChannel && !message.author.isBot && !Perms.checkMod(message) && !Perms.checkSpectator(message)) { //Dead to Medium
                    Webhook.send(BaseBot.bot, game.mediumChannel, message.author.getDisplayName(message.guild), message.author.avatarURL, message.formattedContent)
                }
                if (!game.day && message.channel === game.mediumChannel && !message.author.isBot && MafiaConfig.getJailed(message) != message.author.longID && !Perms.checkMod(message) && !Perms.checkSpectator(message)) { //Medium to Dead
                    Webhook.send(BaseBot.bot, game.deadChannel, "Medium", "https://i.imgur.com/WBTx4Kx.png", message.formattedContent)
                }
                if (message.channel === game.mafiaChannel && !Perms.checkMod(message) && !Perms.checkSpectator(message) && !message.author.isBot) { //Mafia to Spy
                    Webhook.send(BaseBot.bot, game.spyChannel, "Mafia", "https://vignette.wikia.nocookie.net/town-of-salem/images/7/70/DarkRevenant.png/revision/latest/scale-to-width-down/87?cb=20140701002425", message.formattedContent)
                }
                if (message.channel === game.jailorChannel && !Perms.checkMod(message) && !Perms.checkSpectator(message) && !message.author.isBot) {
                    Webhook.send(BaseBot.bot, game.jailedChannel, "Jailor", "https://vignette.wikia.nocookie.net/town-of-salem/images/7/7e/Jailor.png/revision/latest/scale-to-width-down/150?cb=20151021224315", message.formattedContent)
                }
                if (message.channel === game.jailedChannel && !Perms.checkMod(message) && !Perms.checkSpectator(message) && !message.author.isBot) { //Jailed to Jailor
                    Webhook.send(BaseBot.bot, game.jailorChannel, message.author.getDisplayName(message.guild), message.author.avatarURL, message.formattedContent)
                }
                if (message.channel == game.vampChannel && !Perms.checkMod(message) && !Perms.checkSpectator(message) && !message.author.isBot) {
                    Webhook.send(BaseBot.bot, game.vamphunterChannel, "Vampire", "https://vignette.wikia.nocookie.net/town-of-salem/images/4/4e/Vampire.png/revision/latest/scale-to-width-down/150?cb=20151101133009", message.formattedContent)
                }
                if (message.content.startsWith(prefix + "mafia")) {
                    launch {
                        val player = Player(message, message.author)
                        when {
                            Perms.checkAdmin(message) -> for (command in adminCommands) {
                                if (messageContent[1] == command.commandName) {
                                    if (command.hasOutput) {
                                        if((command as MafiaCommand).roleRestriction == "none") {
                                            Message.sendMessage(message.channel, command.init(message, PrivUtils.arrayToList(message.content.split(" ").toTypedArray())))
                                            message.delete()
                                        }
                                        else if((command as MafiaCommand).roleRestriction == player.role ){
                                            Message.sendMessage(message.channel, command.init(message, PrivUtils.arrayToList(message.content.split(" ").toTypedArray())))
                                            message.delete()
                                        }
                                    }
                                    else if ((command as MafiaCommand).roleRestriction != "none"){
                                        if((command).roleRestriction == player.role && !command.hasOutput){
                                            command.init(message, PrivUtils.arrayToList(message.content.split(" ").toTypedArray()))
                                            message.delete()
                                        }
                                    }
                                    else{
                                        command.init(message,PrivUtils.arrayToList(message.content.split(" ").toTypedArray()))
                                        message.delete()
                                    }
                                }
                            }
                            Perms.checkMod(message) -> for (command in modCommands) {
                                if (messageContent[1] == command.commandName) {
                                    if (command.hasOutput) {
                                        if((command as MafiaCommand).roleRestriction == "none") {
                                            Message.sendMessage(message.channel, command.init(message, PrivUtils.arrayToList(message.content.split(" ").toTypedArray())))
                                            message.delete()
                                        }
                                        else if((command as MafiaCommand).roleRestriction == player.role ){
                                            Message.sendMessage(message.channel, command.init(message, PrivUtils.arrayToList(message.content.split(" ").toTypedArray())))
                                            message.delete()
                                        }
                                    }
                                    else{
                                        if((command as MafiaCommand).roleRestriction == "none") {
                                            command.init(message, PrivUtils.arrayToList(message.content.split(" ").toTypedArray()))
                                            message.delete()
                                        }
                                        else if((command as MafiaCommand).roleRestriction == player.role){
                                            command.init(message, PrivUtils.arrayToList(message.content.split(" ").toTypedArray()))
                                            message.delete()
                                        }
                                    }
                                }
                            }
                            else -> for (command in commandsArray) {
                                if (messageContent[1] == command.commandName) {
                                    if (command.hasOutput) {
                                        if((command as MafiaCommand).roleRestriction == "none") {
                                            Message.sendMessage(message.channel, command.init(message, PrivUtils.arrayToList(message.content.split(" ").toTypedArray())))
                                            message.delete()
                                        }
                                        else if((command as MafiaCommand).roleRestriction == player.role ){
                                            Message.sendMessage(message.channel, command.init(message, PrivUtils.arrayToList(message.content.split(" ").toTypedArray())))
                                            message.delete()
                                        }
                                    }
                                    else{
                                        if((command as MafiaCommand).roleRestriction == "none") {
                                            command.init(message, PrivUtils.arrayToList(message.content.split(" ").toTypedArray()))
                                            message.delete()
                                        }
                                        else if((command as MafiaCommand).roleRestriction == player.role){
                                            command.init(message, PrivUtils.arrayToList(message.content.split(" ").toTypedArray()))
                                            message.delete()
                                        }
                                    }
                                }
                            }
                        }
                    }
                   /* if (Perms.checkMod(message) && messageContent.size >= 2 && Perms.checkMafiaChannels(message) && messageContent[0] == prefix + "mafia") {
                        when (messageContent[1]) {
                            "revive" -> {
                                val root = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_playerdat.txt")
                                val playerInfo = root.getJSONObject("" + Utils.getUserFromInput(message, messageContent[2])!!.longID)
                                playerInfo.remove("dead")
                                playerInfo.put("dead", false)
                                root.remove("" + Utils.getUserFromInput(message, messageContent[2])!!.longID)
                                root.put("" + Utils.getUserFromInput(message, messageContent[2])!!.longID, playerInfo)
                                MafiaConfig.writeGame(message, root)
                                message.delete()
                            }
                            "shuffle" -> {
                                Mafia.assignRoles(message)
                                message.delete()
                            }
                            "invest" -> { // !mafia invest @target @invest
                                val investResults1 = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + ".dat")
                                val investResults = investResults1.getJSONObject("invest_results")
                                val targetDetails = MafiaConfig.getPlayerDetails(message, Utils.getUserFromInput(message, messageContent[2])!!.longID)
                                Message.sendDM(Utils.getUserFromInput(message, messageContent[3])!!, investResults.getString(targetDetails[2].toString()))
                                message.delete()
                            }
                            "sheriff" -> { // !mafia sheriff @target @invest
                                val target = Utils.getUserFromInput(message, messageContent[2])!!
                                val invest = Utils.getUserFromInput(message, messageContent[3])!!
                                val details = MafiaConfig.getPlayerDetails(message, target.longID)
                                if (details[0] == "mafia" || details[7] as Boolean) {
                                    Message.sendDM(invest, "Your target is a Member of the Mafia!")
                                } else if (details[2] == "serial_killer") {
                                    Message.sendDM(invest, "Your target is a Serial Killer!")
                                } else if (details[2] == "werewolf") {
                                    Message.sendDM(invest, "Your target is a Werewolf!")
                                } else {
                                    Message.sendDM(invest, "Your target is not suspicious")
                                }
                                message.delete()
                            }
                        }
                    } else if (messageContent.size >= 2 && Perms.checkMafiaChannels(message) && messageContent[0] == prefix + "mafia") { //This is all this listener will handle so putting this requirement for the rest of the code to execute.
                        val player = Player(MafiaConfig.getPlayerDetails(message))
                        when (messageContent[1]) {
                            "leave" -> {
                                Mafia.onGameLeaveCommand(message)
                                message.delete()
                            }
                            "pm" -> {
                                Message.sendMessage(game.spyChannel, Utils.makeNewString(messageContent, 3))
                                Message.sendDM(Utils.getUserFromInput(message, messageContent[2])!!, Utils.makeNewString(messageContent, 3) + "\n To reply, use /mafia pm " + message.author.mention() + " in the mafia commands channel.") //Send PM to desired recipient
                                message.delete()
                            }
                        }
                    }*/
                }
            }

        }
    }
    companion object {
        var dirtyValues : Array<Triple<IUser, Enum<Details>, Any>> = arrayOf()
        var handlers : LinkedList<IHandler> = LinkedList()
        fun addDirtyValue(triple : Triple<IUser,Enum<Details>,Any>){
            dirtyValues += triple
        }
        fun fixDirty(message : IMessage){
            var i = 0
            for(value in dirtyValues){
                MafiaConfig.editDetails(message, value.first, value.second, value.third)
                dirtyValues.drop(i)
                i++
            }
        }
        fun registerHandler(handler : IHandler){
            handlers.add(handler)
        }
        fun updateHandlers(message : IMessage){
            for(handler in handlers){
                handler.update(message)
            }
        }
        fun resetHandlers(message : IMessage){
            for(handler in handlers){
                handler.reset(message)
            }
        }
    }

}
