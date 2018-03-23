package maxdistructo.droidbot2.commands.mafia

import maxdistructo.droidbot2.commands.mafia.obj.Game
import maxdistructo.droidbot2.commands.mafia.obj.Player
import maxdistructo.droidbot2.core.Utils
import maxdistructo.droidbot2.core.message.Message
import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent

import maxdistructo.droidbot2.core.Client.prefix

class MafiaListener {

    @EventSubscriber
    fun onMessageReceivedEvent(event: MessageReceivedEvent) {
        val message = event.message
        val messageContent = message.content.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray() as Array<Any>
        if (message.guild.longID == 249615705517981706L || message.guild.longID == 268370862661435392L && Perms.checkMafiaChannels(message)) {
            val game = Game(Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt"))
            if (!game.day && message.channel === game.deadChannel && !message.author.isBot) { //Dead to Medium
                Message.sendMessage(game.mediumChannel, message.author.getDisplayName(message.guild) + ": " + message.content)
            }
            if (!game.day && message.channel === game.mediumChannel && !message.author.isBot && MafiaConfig.getJailed(message) != message.author.longID) { //Medium to Dead
                Message.sendMessage(game.deadChannel, "Medium:" + message.content)
            }
            if (message.channel === game.mafiaChannel) { //Mafia to Spy
                Message.sendMessage(game.spyChannel, "Mafia: " + message.content)
            }
            if (message.channel === game.jailorChannel) {
                Message.sendMessage(game.jailedChannel, "Jailor: " + message.content)
            }
            if (message.channel === game.jailedChannel) { //Jailed to Jailor
                Message.sendMessage(game.jailorChannel, message.author.getDisplayName(message.guild) + message.content)
            }
                    if (Perms.checkMod(message) && messageContent[0] == prefix + "mafia") {
                        when (messageContent[1]) {
                            "start" -> {
                                Mafia.onGameStart(message)
                                message.delete()
                            }
                            "continue" -> {
                                Mafia.onGameToggle(message)
                                message.delete()
                            }
                            "getInfo" -> {
                                val details = MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message)!!.longID)
                                Message.sendDM(message.author, "Player Info on " + message.author.getDisplayName(message.guild) + "\nAlignment: " + details[0] + "\nClass: " + details[1] + "\nRole: " + details[2] + "\nIs Dead: " + details[3] + "\nAttack Power: " + details[4] + "\nDefence Power: " + details[5])
                            }
                            "setrole" -> {
                                Mafia.setRole(message, Utils.getMentionedUser(message)!!, messageContent[3] as String)
                                Message.sendMessage(game.adminChannel, "The role of " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " has been set to " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message)!!.longID)[2])
                                message.delete()
                            }
                            "akill" -> {
                                Mafia.killPlayer(message, Utils.getMentionedUser(message)!!.longID)
                                Message.sendMessage(game.dayChannel, "__**" + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " has committed suicide. **__" + "\n" + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message)!!.longID)[2])
                                Message.sendMessage(game.adminChannel, Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was killed by " + message.author.getDisplayName(message.guild))
                                message.delete()
                            }
                            "mkill" -> {
                                Mafia.killPlayer(message, Utils.getMentionedUser(message)!!.longID)
                                Message.sendMessage(game.dayChannel, "__**" + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was killed by the Mafia**__" + "\n" + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message)!!.longID)[2])
                                Message.sendMessage(game.adminChannel, Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was killed by the Mafia. Authorizing Member: " + message.author.getDisplayName(message.guild))
                                message.delete()
                            }
                            "skkill" -> {
                                Mafia.killPlayer(message, Utils.getMentionedUser(message)!!.longID)
                                Message.sendMessage(game.dayChannel, "__**" + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was stabbed by a Serial Killer**__" + "\n" + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message)!!.longID)[2])
                                Message.sendMessage(game.adminChannel, Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was Stabbed by a Serial Killer. Authorizing Member: " + message.author.getDisplayName(message.guild))
                                message.delete()
                            }
                            "wwkill" -> {
                                Mafia.killPlayer(message, Utils.getMentionedUser(message)!!.longID)
                                Message.sendMessage(game.dayChannel, "__**" + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was attacked by a Werewolf.**__" + "\n" + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message)!!.longID)[2])
                                Message.sendMessage(game.adminChannel, Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was attacked by a Werewolf. Authorizing Member: " + message.author.getDisplayName(message.guild))
                                message.delete()
                            }
                            "arsokill" -> {
                                Mafia.killPlayer(message, Utils.getMentionedUser(message)!!.longID)
                                Message.sendMessage(game.dayChannel, "__**" + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was burnt to a crisp by an Arsonist**__" + "\n" + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message)!!.longID)[2])
                                Message.sendMessage(game.adminChannel, Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was burnt to a crisp by an Arsonist. Authorizing Member: " + message.author.getDisplayName(message.guild))
                                message.delete()
                            }
                            "jestkill" -> {
                                Mafia.killPlayer(message, Utils.getMentionedUser(message)!!.longID)
                                Message.sendMessage(game.dayChannel, "__**" + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was haunted by the Jester they linched.**__" + "\n" + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message)!!.longID)[2])
                                Message.sendMessage(game.adminChannel, Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was haunted by the Jester they linched. Authorizing Member: " + message.author.getDisplayName(message.guild))
                                message.delete()
                            }
                            "vetkill" -> {
                                Mafia.killPlayer(message, Utils.getMentionedUser(message)!!.longID)
                                Message.sendMessage(game.dayChannel, "__**" + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was shot by the Veteran they visited.**__" + "\n" + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message)!!.longID)[2])
                                Message.sendMessage(game.adminChannel, Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was shot by a Veteran. Authorizing Member: " + message.author.getDisplayName(message.guild))
                                message.delete()
                            }
                            "vigkill" -> {
                                Mafia.killPlayer(message, Utils.getMentionedUser(message)!!.longID)
                                Message.sendMessage(game.dayChannel, "__**" + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was shot by a Vigilante**__" + "\n" + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message)!!.longID)[2])
                                Message.sendMessage(game.adminChannel, Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was shot by a Vigilante. Authorizing Member: " + message.author.getDisplayName(message.guild))
                                message.delete()
                            }
                            "jailkill" -> {
                                Mafia.killPlayer(message, Utils.getMentionedUser(message)!!.longID)
                                Message.sendMessage(game.dayChannel, "__**" + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was killed by the Jailor**__ " + "\n" + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message)!!.longID)[2])
                                Message.sendMessage(game.adminChannel, Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " was killed by the Jailor. Authorizing Member: " + message.author.getDisplayName(message.guild))
                                message.delete()
                            }
                            "shuffle" -> {
                                Mafia.assignRoles(message)
                                message.delete()
                            }
                            "invest" -> {
                                val mentionedList = message.mentions
                                val investResults1 = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + ".dat")
                                val investResults = investResults1.getJSONObject("invest_results")
                                val targetDetails = MafiaConfig.getPlayerDetails(message, mentionedList[0].longID)
                                Message.sendDM(mentionedList[1], investResults.getString(targetDetails[2].toString()))
                                println("Sent DM to " + mentionedList[1])
                                message.delete()
                            }
                            "sheriff" -> {
                                val mentionedList = message.mentions
                                val target = mentionedList[0]
                                val invest = mentionedList[1]
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
                    } else if (messageContent[0] == prefix + "mafia") { //This is all this listener will handle so putting this requirement for the rest of the code to execute.
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
                                Message.sendDM(Utils.getMentionedUser(message)!!!!, Utils.makeNewString(messageContent, 3) + "\n To reply, use /mafia pm " + message.author.mention() + " in the mafia commands channel.") //Send PM to desired recipient
                                message.delete()
                            }
                            "mkill" , player.role == "mafia" ->{
                                Message.sendMessage(game.adminChannel, "The mafia would like to kill " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild))
                                Message.sendMessage(message.channel, "You have decided to kill " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild))
                                message.delete()
                            }
                            "skkill", player.role == "serial_killer" -> {
                                Message.sendMessage(game.adminChannel, "A serial killer has decided to stab " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild))
                                Message.sendDM(message.author, "You have decided to kill " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild))
                                message.delete()
                            }
                            "wwkill", player.role == "werewolf" ->{
                                Message.sendMessage(game.adminChannel, "The werewolf has decided to visit " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild))
                                Message.sendDM(message.author, "You have decided to go pay " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " a visit tonight")
                                message.delete()
                            }
                            "arsokill", player.role == "arsonist" ->{
                                if (Utils.getMentionedUser(message)!! !== message.author) {
                                    Message.sendMessage(game.adminChannel, "The arsonist is gonna douse " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild))
                                    Message.sendDM(message.author, "You have decided to douse " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild))
                                } else {
                                    Message.sendMessage(game.adminChannel, "The arsonist is gonna set all doused players on fire")
                                    Message.sendDM(message.author, "You have decided to set all targets on fire tonight")
                                }
                                message.delete()
                            }
                            "jestkill", player.role == "jester" ->{
                                Message.sendMessage(game.adminChannel, "The jester is gonna haunt " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild))
                                Message.sendDM(message.author, "You have decided to haunt " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild))
                                message.delete()
                            }
                            "vetkill", player.role == "veteran" ->{
                                Message.sendMessage(game.adminChannel, "The Veteran " + message.author + " is going on alert tonight.")
                                Message.sendDM(message.author, "You have decided to go on alert tonight")
                                message.delete()
                            }
                            "vigkill", player.role == "vigilante" ->{
                                Message.sendMessage(game.adminChannel, "The Vigilante is going to shoot " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild))
                                Message.sendDM(message.author, "You have decided to shoot " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild))
                                message.delete()
                            }
                            "jailkill", player.role == "jailor" ->{
                                if (Utils.getMentionedUser(message)!!.longID == MafiaConfig.getJailed(message)) {
                                    Message.sendMessage(game.adminChannel, "The jailor is going to shoot " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild))
                                    Message.sendMessage(message.channel, "You have decided to shoot " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild))
                                } else {
                                    Message.sendDM(message.author, "You can only shoot the person you have jailed!")
                                }
                                message.delete()
                            }
                            "bite", player.role == "vampire" ->{
                                Message.sendMessage(game.adminChannel, "The vampires are going to try and convert " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild))
                                Message.sendDM(message.author, "You will be biting " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild))
                            }
                            "stab", player.role == "vampire_hunter" ->{
                                Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " will be checking and stabbing " + Utils.getMentionedUser(message)!! + " if they are a vampire")
                                Message.sendDM(message.author, "You will be checking " + Utils.getMentionedUser(message))
                            }
                            "jail", player.role == "jailor" ->{
                                if (Utils.getMentionedUser(message)!! === message.author) {
                                    Message.sendDM(message.author, "You can not jail yourself!")
                                } else {
                                    Mafia.jailPlayer(message, Utils.getMentionedUser(message)!!)
                                    Message.sendDM(message.author, "You have successfully jailed " + Utils.getMentionedUser(message))
                                    Message.sendMessage(game.adminChannel, "The jailor has jailed " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild))
                                }
                                message.delete()
                            }
                            "watch", player.role == "lookout" ->{
                                Message.sendDM(message.author, "You will be watching " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " tonight.")
                                Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " will be watching " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " tonight.")
                                message.delete()
                            }
                            "invest", player.role == "investigator" ->{
                                Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " would like to investigate " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " tonight.")
                                message.delete()
                            }
                            "sheriff", player.role == "sheriff" ->{
                                Message.sendMessage(game.adminChannel, "The sheriff would like to interrogate " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " tonight.")
                                Message.sendDM(message.author, "You are going to be interrogating " + Utils.getMentionedUser(message)!! + " tonight.")
                                message.delete()
                            }
                            "transport", player.role == "transporter" ->{
                                val mentionedList = message.mentions
                                val target = mentionedList[0]
                                val invest = mentionedList[1]
                                Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " would like to swap positions of " + invest.getDisplayName(message.guild) + " & " + target.getDisplayName(message.guild))
                                Message.sendDM(message.author, "You will be transporting " + invest.getDisplayName(message.guild) + " & " + target.getDisplayName(message.guild))
                                message.delete()
                            }

                        }
                          //TODO add kills for VH and Vampire. Vampire kills Mafia if they try to bite them. VH kills Vampire on visit.
                        if (messageContent[1] == "witch" && player.role == "witch") {
                            val mentionedList = message.mentions
                            val mentionedArray = mentionedList.toTypedArray()
                            val target = mentionedList[0]
                            val invest = mentionedList[1]
                            Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " would like to control " + invest.getDisplayName(message.guild) + " into using their ability onto " + target.getDisplayName(message.guild))
                            Message.sendDM(message.author, "You will be witching " + invest.getDisplayName(message.guild) + " into " + target.getDisplayName(message.guild))
                            message.delete()
                        } else if (messageContent[1] == "heal" && player.role == "doctor") {
                            Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " will be healing " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " tonight.")
                            Message.sendDM(message.author, "You will be healing " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " tonight.")
                            message.delete()
                        } else if (messageContent[1] == "guard" && player.role == "bodyguard") {
                            Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " will be guarding " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " tonight.")
                            Message.sendDM(message.author, "You will be guarding " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " tonight.")
                            message.delete()
                        } else if (messageContent[1] == "escort" && player.role == "escort" || player.role == "consort") {
                            Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " would like to roleblock " + Utils.getMentionedUser(message)!! + " tonight.")
                            Message.sendDM(message.author, "You will be escorting " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " tonight.")
                            message.delete()
                        } else if (messageContent[1] == "reveal" && game.day && player.role == "mayor") {
                            Message.sendMessage(message.channel, message.author.getDisplayName(message.guild) + " has revealed themselves as the Mayor!")
                            Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " has revealed as the mayor. Their votes now count as 3.")
                            message.delete()
                        } else if (messageContent[1] == "vote" && game.day && Utils.getMentionedUser(message)!! !== message.author) {
                            Message.sendMessage(message.channel, message.author.toString() + " has voted for " + Utils.getMentionedUser(message))
                            Message.sendMessage(game.adminChannel, message.author.toString() + " has voted for " + Utils.getMentionedUser(message))
                            message.delete()
                        } else if (messageContent[1] == "secance" && !game.day && player.role == "medium" && player.dead) {
                            Message.sendMessage(game.adminChannel, "The medium would like to talk to " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild))
                            Message.sendDM(message.author, "Your message has been sent to the Admin. Please wait for them to respond to your secance request")
                            message.delete()
                        } else if (messageContent[1] == "revive" && player.role == "retributionist") {
                            Message.sendMessage(game.adminChannel, "The retributionist will be reviving " + Utils.getMentionedUser(message)!! + " tonight.")
                            Message.sendDM(message.author, "You will be reviving " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " tonight.")
                            message.delete()
                        } else if (messageContent[1] == "vampcheck" && player.role == "vampire_hunter") {
                            Message.sendMessage(game.adminChannel, "The vampire hunter " + message.author.getDisplayName(message.guild) + " will be checking if " + Utils.getMentionedUser(message)!! + " is a vampire tonight.")
                            Message.sendDM(message.author, "You will be checking to see if " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + "is a Vampire.")
                            message.delete()
                        } else if (messageContent[1] == "disguise" && player.role == "disguiser") {
                            Message.sendMessage(game.adminChannel, "The Disguiser is going to be disguised as " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " in role.")
                            Message.sendDM(message.author, "You will be disguising as " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild))
                            message.delete()
                        } else if (messageContent[1] == "forge" && player.role == "forger") {
                            Message.sendMessage(game.adminChannel, "The forger is gonna forge the role of " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild) + " to be Forger.")
                            Message.sendDM(message.author, "You will be forging the role of " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild))
                            message.delete()
                        } else if (messageContent[1] == "frame" && player.role == "framer") {
                            Message.sendMessage(game.adminChannel, "The framer is gonna frame " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild))
                            Message.sendDM(message.author, "You will be framing " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild))
                            message.delete()
                        } else if (messageContent[1] == "clean" && player.role == "janitor") {
                            Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " would like to clean the role of " + Utils.getMentionedUser(message))
                            Message.sendDM(message.author, "You will be cleaning " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild))
                            message.delete()
                        } else if (messageContent[1] == "blackmail" && player.role == "blackmailer") {
                            Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + "would like to Blackmail " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild))
                            Message.sendDM(message.author, "You will be blackmailing " + Utils.getMentionedUser(message))
                            message.delete()
                        } else if (messageContent[1] == "consig" && player.role == "consigerge") {
                            Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " would like to know the role of " + Utils.getMentionedUser(message))
                            Message.sendDM(message.author, "You will receive the role of " + Utils.getMentionedUser(message))
                            message.delete()
                        } else if (messageContent[1] == "remember" && player.role == "retributionist") {
                            Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " would like to remember the role of " + Utils.getMentionedUser(message))
                            Message.sendDM(message.author, "You will remember the role of " + Utils.getMentionedUser(message))
                            message.delete()
                        } else if (messageContent[1] == "vest" && player.role == "veteran") {
                            Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " will be putting on a vest tonight.")
                            Message.sendDM(message.author, "You will be putting on a vest tonight.")
                        }


                    }

                }
            }

        }
