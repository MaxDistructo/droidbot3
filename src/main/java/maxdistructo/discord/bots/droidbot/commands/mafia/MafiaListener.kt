package maxdistructo.discord.bots.droidbot.commands.mafia


import maxdistructo.discord.bots.droidbot.BaseBot
import maxdistructo.discord.bots.droidbot.commands.mafia.obj.Game
import maxdistructo.discord.bots.droidbot.commands.mafia.obj.Player
import maxdistructo.discord.core.Config
import maxdistructo.discord.core.Utils
import maxdistructo.discord.core.message.Message
import maxdistructo.discord.core.message.Webhook
import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent

class MafiaListener {

    @EventSubscriber
    fun onMessageReceivedEvent(event: MessageReceivedEvent) {
        val message = event.message
        val prefix = Config.readPrefix()
        val messageContent = message.content.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray() as Array<Any>
        if (messageContent.isNotEmpty()) {
            if (message.guild.longID == 249615705517981706L || message.guild.longID == 268370862661435392L && Perms.checkMafiaChannels(message)) {
                var game = Game(Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt"))
                if (!game.day && message.channel === game.deadChannel && !message.author.isBot && !Perms.checkMod(message) && !Perms.checkSpectator(message)) { //Dead to Medium
                    Webhook.send(BaseBot.bot, game.mediumChannel, message.author.getDisplayName(message.guild), message.author.avatarURL, message.formattedContent)
                }
                if (!game.day && message.channel === game.mediumChannel && !message.author.isBot && MafiaConfig.getJailed(message) != message.author.longID && !Perms.checkMod(message)&& !Perms.checkSpectator(message)) { //Medium to Dead
                    Webhook.send(BaseBot.bot, game.deadChannel, "Medium", "https://i.imgur.com/WBTx4Kx.png", message.formattedContent)
                }
                if (message.channel === game.mafiaChannel && !Perms.checkMod(message)&& !Perms.checkSpectator(message)) { //Mafia to Spy
                    Webhook.send(BaseBot.bot, game.spyChannel, "Mafia", "https://vignette.wikia.nocookie.net/town-of-salem/images/7/70/DarkRevenant.png/revision/latest/scale-to-width-down/87?cb=20140701002425", message.formattedContent)
                }
                if (message.channel === game.jailorChannel && !Perms.checkMod(message)&& !Perms.checkSpectator(message)) {
                    Webhook.send(BaseBot.bot, game.jailedChannel, "Jailor", "https://vignette.wikia.nocookie.net/town-of-salem/images/7/7e/Jailor.png/revision/latest/scale-to-width-down/150?cb=20151021224315", message.formattedContent)
                }
                if (message.channel === game.jailedChannel && !Perms.checkMod(message)&& !Perms.checkSpectator(message)) { //Jailed to Jailor
                    Webhook.send(BaseBot.bot, game.jailorChannel, message.author.getDisplayName(message.guild), message.author.avatarURL, message.formattedContent)
                }
                if(message.channel == game.vampChannel && !Perms.checkMod(message) && !Perms.checkSpectator(message)){
                    Webhook.send(BaseBot.bot, game.vamphunterChannel, "Vampire", "https://vignette.wikia.nocookie.net/town-of-salem/images/4/4e/Vampire.png/revision/latest/scale-to-width-down/150?cb=20151101133009", message.formattedContent)
                }
                if (Perms.checkMod(message) && messageContent.size >= 2 && Perms.checkMafiaChannels(message) && messageContent[0] == prefix + "mafia") {
                    when (messageContent[1]) {
                        "start" -> {
                            Mafia.onGameStart(message)
                            message.delete()
                        }
                        "continue" -> {
                            Mafia.onGameToggle(message)
                            message.delete()
                        }
                        "reset" ->{
                            MafiaReset.onCommand(game, message.guild)
                            message.delete()
                        }
                        "getInfo" -> {
                            val details = MafiaConfig.getPlayerDetails(message, Utils.getUserFromInput(message, messageContent[2])!!.longID)
                            Message.sendDM(message.author, "Player Info on " + Utils.getUserFromInput(message, messageContent[2])!!.getDisplayName(message.guild) + "\nAlignment: " + details[0] + "\nClass: " + details[1] + "\nRole: " + details[2] + "\nIs Dead: " + details[3] + "\nAttack Power: " + details[4] + "\nDefence Power: " + details[5])
                        }
                        "rolecard" -> {
                            Message.sendMessage(message.channel, RoleCards.onRoleCardAsk(message, messageContent[2].toString(), message.author))
                        }
                        "info" -> {
                            val details = MafiaConfig.getPlayerDetails(message, Utils.getUserFromInput(message, messageContent[2])!!.longID)
                            Message.sendDM(message.author, "Player Info on " + Utils.getUserFromInput(message, messageContent[2])!!.getDisplayName(message.guild) + "\nAlignment: " + details[0] + "\nClass: " + details[1] + "\nRole: " + details[2] + "\nIs Dead: " + details[3] + "\nAttack Power: " + details[4] + "\nDefence Power: " + details[5])
                        }
                        "details" -> {
                            val details = MafiaConfig.getPlayerDetails(message, Utils.getUserFromInput(message, messageContent[2])!!.longID)
                            Message.sendDM(message.author, "Player Info on " + Utils.getUserFromInput(message, messageContent[2])!!.getDisplayName(message.guild) + "\nAlignment: " + details[0] + "\nClass: " + details[1] + "\nRole: " + details[2] + "\nIs Dead: " + details[3] + "\nAttack Power: " + details[4] + "\nDefence Power: " + details[5])
                        }
                        "checkrole" -> {
                            val details = MafiaConfig.getPlayerDetails(message, Utils.getUserFromInput(message, messageContent[2])!!.longID)
                            Message.sendDM(message.author, "Player Info on " + Utils.getUserFromInput(message, messageContent[2])!!.getDisplayName(message.guild) + "\nAlignment: " + details[0] + "\nClass: " + details[1] + "\nRole: " + details[2] + "\nIs Dead: " + details[3] + "\nAttack Power: " + details[4] + "\nDefence Power: " + details[5])
                        }
                        "setrole" -> {
                            Mafia.setRole(message, Utils.getUserFromInput(message, messageContent[2])!!, messageContent[3] as String)
                            Message.sendMessage(game.adminChannel, "The role of " + Utils.getUserFromInput(message, messageContent[2])!!.getDisplayName(message.guild) + " has been set to " + MafiaConfig.getPlayerDetails(message, Utils.getUserFromInput(message, messageContent[2])!!.longID)[2])
                            Message.sendDM(Utils.getUserFromInput(message, messageContent[2])!!, "Your role has been set to " + messageContent[3] + " due to either Unique role or specific role change.")
                            message.delete()
                        }
                        "kill" -> {
                            Mafia.killPlayer(message, Utils.getUserFromInput(message, messageContent[2])!!.longID)
                            Webhook.send(BaseBot.bot, game.dayChannel, "Graveyard", "https://cdn.discordapp.com/emojis/294160585179004928.png", Kill.message(message, messageContent))
                            message.delete()
                        }
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
                        "join" -> {
                            Mafia.onGameJoinCommand(message)
                            message.delete()
                        }
                        "leave" -> {
                            Mafia.onGameLeaveCommand(message)
                            message.delete()
                        }
                        "getInfo" -> {
                            val details = MafiaConfig.getPlayerDetails(message)
                            Message.sendDM(message.author, "Player Info on " + message.author.getDisplayName(message.guild) + "\nAlignment: " + details[0] + "\nClass: " + details[1] + "\nRole: " + details[2] + "\nIs Dead: " + details[3] + "\nAttack Power: " + details[4] + "\nDefence Power: " + details[5])
                        }
                        "pm" -> {
                            Message.sendMessage(game.spyChannel, Utils.makeNewString(messageContent, 3))
                            Message.sendDM(Utils.getUserFromInput(message, messageContent[2])!!, Utils.makeNewString(messageContent, 3) + "\n To reply, use /mafia pm " + message.author.mention() + " in the mafia commands channel.") //Send PM to desired recipient
                            message.delete()
                        }
                        "jail", player.role == "jailor" -> {
                            if (Utils.getUserFromInput(message, messageContent[2])!! === message.author) {
                                Message.sendDM(message.author, "You can not jail yourself!")
                            } else {
                                Mafia.jailPlayer(message, Utils.getUserFromInput(message, messageContent[2])!!)
                                Message.sendDM(message.author, "You have successfully jailed " + Utils.getMentionedUser(message))
                                Message.sendMessage(game.adminChannel, "The jailor has jailed " + Utils.getUserFromInput(message, messageContent[2])!!.getDisplayName(message.guild))
                            }
                            message.delete()
                        }
                        "vote", game.day, Utils.getUserFromInput(message, messageContent[2])!! != message.author -> {
                            if(player.role == "mayor") {
                                if (MafiaConfig.checkRevealed(message)) {
                                    Message.sendMessage(message.channel, "The Mayor has voted for " + Utils.getMentionedUser(message)!!.mention(true))
                                }
                            }
                            Message.sendMessage(message.channel, message.author.toString() + " has voted for " + Utils.getMentionedUser(message)!!.mention(true))
                            Message.sendMessage(game.adminChannel, message.author.toString() + " has voted for " + Utils.getMentionedUser(message)!!.mention(true))
                            message.delete()
                        }
                        "do" -> {
                            UserDo.message(message, messageContent)
                        }
                    }
                }

            }
        }

    }
}
