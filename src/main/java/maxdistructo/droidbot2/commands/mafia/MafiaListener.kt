package maxdistructo.droidbot2.commands.mafia

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
        if (message.guild.longID == 249615705517981706L || message.guild.longID == 268370862661435392L) {
                if (!MafiaConfig.getDayStatus(message) && message.channel === message.guild.getChannelByID(MafiaConfig.getDeadChat(message)) && !message.author.isBot) { //Dead to Medium
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getMediumChat(message)), message.author.getDisplayName(message.guild) + ": " + message.content)
                }
                if (!MafiaConfig.getDayStatus(message) && message.channel === message.guild.getChannelByID(MafiaConfig.getMediumChat(message)) && !message.author.isBot && MafiaConfig.getJailed(message) != message.author.longID) { //Medium to Dead
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getDeadChat(message)), "Medium:" + message.content)
                }
                if (message.channel === message.guild.getChannelByID(MafiaConfig.getMafiaChat(message))) { //Mafia to Spy
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getSpyChat(message)), "Mafia: " + message.content)
                }
                if (message.channel === message.guild.getChannelByID(MafiaConfig.getJailorChat(message))) { //Jailor to Jailed
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getJailedChat(message)), "Jailor: " + message.content)
                }
                if (message.channel === message.guild.getChannelByID(MafiaConfig.getJailedChat(message))) { //Jailed to Jailor
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getJailorChat(message)), message.author.getDisplayName(message.guild) + message.content)
                }
            }
            if (messageContent[0] == prefix + "mafia") { //This is all this listener will handle so putting this requirement for the rest of the code to execute.
                if (messageContent[1] == "start" && Perms.checkMod(message)) {
                    Mafia.onGameStart(message)
                    message.delete()
                } else if (messageContent[1] == "continue" && Perms.checkMod(message)) {
                    Mafia.onGameToggle(message)
                    message.delete()
                } else if (messageContent[1] == "join") {
                    Mafia.onGameJoinCommand(message)
                    message.delete()
                } else if (messageContent[1] == "leave") {
                    Mafia.onGameLeaveCommand(message)
                    message.delete()
                } else if (messageContent[1] == "pm") {
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getSpyChat(message)), Utils.makeNewString(messageContent, 3)) //Send PMs to Spy
                    Message.sendDM(Utils.getMentionedUser(message)!!, Utils.makeNewString(messageContent, 3) + "\n To reply, use /mafia pm " + message.author.mention() + " in the mafia commands channel.") //Send PM to desired recipient
                    message.delete()
                } else if (messageContent[1] == "shuffle") {
                    //message.reply(Mafia.assignRoles(message).toString());
                    message.delete()
                } else if (messageContent[1] == "getInfo" && Perms.checkMod(message)) {
                    val details = MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).longID)
                    val builder = EmbedBuilder()
                    builder.withTitle("Player Info on " + Utils.getMentionedUser(message).getDisplayName(message.guild))
                    builder.withDesc("Alignment: " + details[0] + "\nClass: " + details[1] + "\nRole: " + details[2] + "\nIs Dead: " + details[3] + "\nAttack Power: " + details[4] + "\nDefence Power: " + details[5])
                    builder.withColor(message.author.getColorForGuild(message.guild))
                    builder.withAuthorName(message.author.name + "#" + message.author.discriminator)
                    builder.withAuthorIcon(message.author.avatarURL)
                    builder.withTimestamp(Instant.now())
                    builder.withFooterIcon(message.guild.iconURL)
                    builder.withFooterText(message.guild.name)
                    Message.sendDM(message.author, "Player Info on " + message.author.getDisplayName(message.guild) + "\nAlignment: " + details[0] + "\nClass: " + details[1] + "\nRole: " + details[2] + "\nIs Dead: " + details[3] + "\nAttack Power: " + details[4] + "\nDefence Power: " + details[5])
                } else if (messageContent[1] == "getInfo") {
                    val details = MafiaConfig.getPlayerDetails(message)
                    val builder = EmbedBuilder()
                    builder.withTitle("Player Info on " + Utils.getMentionedUser(message).getDisplayName(message.guild))
                    builder.withDesc("Alignment: " + details[0] + "\nClass: " + details[1] + "\nRole: " + details[2] + "\nIs Dead: " + details[3] + "\nAttack Power: " + details[4] + "\nDefence Power: " + details[5])
                    builder.withColor(message.author.getColorForGuild(message.guild))
                    builder.withAuthorName(message.author.name + "#" + message.author.discriminator)
                    builder.withAuthorIcon(message.author.avatarURL)
                    builder.withTimestamp(Instant.now())
                    builder.withFooterIcon(message.guild.iconURL)
                    builder.withFooterText(message.guild.name)
                    Message.sendDM(message.author, "Player Info on " + message.author.getDisplayName(message.guild) + "\nAlignment: " + details[0] + "\nClass: " + details[1] + "\nRole: " + details[2] + "\nIs Dead: " + details[3] + "\nAttack Power: " + details[4] + "\nDefence Power: " + details[5])
                } else if (messageContent[1] == "setrole" && Perms.checkAdmin(message)) { // /mafia setrole @user roleName
                    Mafia.setRole(message, Utils.getMentionedUser(message), messageContent[3] as String)
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), "The role of " + Utils.getMentionedUser(message).getDisplayName(message.guild) + " has been set to " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).longID)[2])
                    message.delete()
                } else if (messageContent[1] == "akill" && Perms.checkMod(message)) { //Deaths List: Admin, Mafia, Serial Killer, Werewolf, Arsonist, Jester, Veteran, Vigilante, Jailor
                    Mafia.killPlayer(message, Utils.getMentionedUser(message).longID)
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " has committed suicide. **__" + "\n" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).longID)[2])
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.guild) + " was killed by " + message.author.getDisplayName(message.guild))
                    message.delete()
                } else if (messageContent[1] == "mkill" && Perms.checkMod(message)) {
                    Mafia.killPlayer(message, Utils.getMentionedUser(message).longID)
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was killed by the Mafia**__" + "\n" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).longID)[2])
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.guild) + " was killed by the Mafia. Authorizing Member: " + message.author.getDisplayName(message.guild))
                    message.delete()
                } else if (messageContent[1] == "skkill" && Perms.checkMod(message)) {
                    Mafia.killPlayer(message, Utils.getMentionedUser(message).longID)
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was stabbed by a Serial Killer**__" + "\n" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).longID)[2])
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.guild) + " was Stabbed by a Serial Killer. Authorizing Member: " + message.author.getDisplayName(message.guild))
                    message.delete()
                } else if (messageContent[1] == "wwkill" && Perms.checkMod(message)) {
                    Mafia.killPlayer(message, Utils.getMentionedUser(message).longID)
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was attacked by a Werewolf.**__" + "\n" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).longID)[2])
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.guild) + " was attacked by a Werewolf. Authorizing Member: " + message.author.getDisplayName(message.guild))
                    message.delete()
                } else if (messageContent[1] == "arsokill" && Perms.checkMod(message)) {
                    Mafia.killPlayer(message, Utils.getMentionedUser(message).longID)
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was burnt to a crisp by an Arsonist**__" + "\n" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).longID)[2])
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.guild) + " was burnt to a crisp by an Arsonist. Authorizing Member: " + message.author.getDisplayName(message.guild))
                    message.delete()
                } else if (messageContent[1] == "jestkill" && Perms.checkMod(message)) {
                    Mafia.killPlayer(message, Utils.getMentionedUser(message).longID)
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was haunted by the Jester they linched.**__" + "\n" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).longID)[2])
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.guild) + " was haunted by the Jester they linched. Authorizing Member: " + message.author.getDisplayName(message.guild))
                    message.delete()
                } else if (messageContent[1] == "vetkill" && Perms.checkMod(message)) {
                    Mafia.killPlayer(message, Utils.getMentionedUser(message).longID)
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was shot by the Veteran they visited.**__" + "\n" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).longID)[2])
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.guild) + " was shot by a Veteran. Authorizing Member: " + message.author.getDisplayName(message.guild))
                    message.delete()
                } else if (messageContent[1] == "vigkill" && Perms.checkMod(message)) {
                    Mafia.killPlayer(message, Utils.getMentionedUser(message).longID)
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was shot by a Vigilante**__" + "\n" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).longID)[2])
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.guild) + " was shot by a Vigilante. Authorizing Member: " + message.author.getDisplayName(message.guild))
                    message.delete()
                } else if (messageContent[1] == "jailkill" && Perms.checkMod(message)) {
                    Mafia.killPlayer(message, Utils.getMentionedUser(message).longID)
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was killed by the Jailor**__ " + "\n" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).longID)[2])
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.guild) + " was killed by the Jailor. Authorizing Member: " + message.author.getDisplayName(message.guild))
                    message.delete()
                } else if (messageContent[1] == "mkill" && MafiaConfig.getPlayerDetails(message)[0].toString() == "mafia") {
                  
            if(Perms.checkMod(message) && messageContent[0] == prefix + "mafia"){
                when(messageContent[1]){
                    "start" ->{
                        Mafia.onGameStart(message)
                        message.delete()
                    }
                    "continue" ->{
                        Mafia.onGameToggle(message)
                        message.delete()
                    }
                    "pm" ->{
                        Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getSpyChat(message)), Utils.makeNewString(messageContent, 3))
                        Message.sendDM(Utils.getMentionedUser(message)!!, Utils.makeNewString(messageContent, 3) + "\n To reply, use /mafia pm " + message.author.mention() + " in the mafia commands channel.") //Send PM to desired recipient
                        message.delete()
                    }
                    "getInfo" -> {
                        val details = MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).longID)
                        Message.sendDM(message.author, "Player Info on " + message.author.getDisplayName(message.guild) + "\nAlignment: " + details[0] + "\nClass: " + details[1] + "\nRole: " + details[2] + "\nIs Dead: " + details[3] + "\nAttack Power: " + details[4] + "\nDefence Power: " + details[5])
                    }
                    "setrole" -> {
                        Mafia.setRole(message, Utils.getMentionedUser(message), messageContent[3] as String)
                        Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), "The role of " + Utils.getMentionedUser(message).getDisplayName(message.guild) + " has been set to " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).longID)[2])
                        message.delete()
                    }
                    "akill" -> {
                        Mafia.killPlayer(message, Utils.getMentionedUser(message).longID)
                        Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " has committed suicide. **__" + "\n" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).longID)[2])
                        Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.guild) + " was killed by " + message.author.getDisplayName(message.guild))
                        message.delete()
                    }
                    "mkill" -> {
                        Mafia.killPlayer(message, Utils.getMentionedUser(message).longID)
                        Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was killed by the Mafia**__" + "\n" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).longID)[2])
                        Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.guild) + " was killed by the Mafia. Authorizing Member: " + message.author.getDisplayName(message.guild))
                        message.delete()
                    }
                    "skkill" -> {
                        Mafia.killPlayer(message, Utils.getMentionedUser(message).longID)
                        Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was stabbed by a Serial Killer**__" + "\n" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).longID)[2])
                        Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.guild) + " was Stabbed by a Serial Killer. Authorizing Member: " + message.author.getDisplayName(message.guild))
                        message.delete()
                    }
                    "wwkill" ->{
                        Mafia.killPlayer(message, Utils.getMentionedUser(message).longID)
                        Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was attacked by a Werewolf.**__" + "\n" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).longID)[2])
                        Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.guild) + " was attacked by a Werewolf. Authorizing Member: " + message.author.getDisplayName(message.guild))
                        message.delete()
                    }
                    "arsokill" -> {
                        Mafia.killPlayer(message, Utils.getMentionedUser(message).longID)
                        Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was burnt to a crisp by an Arsonist**__" + "\n" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).longID)[2])
                        Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.guild) + " was burnt to a crisp by an Arsonist. Authorizing Member: " + message.author.getDisplayName(message.guild))
                        message.delete()
                    }
                    "jestkill" -> {
                        Mafia.killPlayer(message, Utils.getMentionedUser(message).longID)
                        Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was haunted by the Jester they linched.**__" + "\n" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).longID)[2])
                        Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.guild) + " was haunted by the Jester they linched. Authorizing Member: " + message.author.getDisplayName(message.guild))
                        message.delete()
                    }
                    "vetkill" -> {
                        Mafia.killPlayer(message, Utils.getMentionedUser(message).longID)
                        Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was shot by the Veteran they visited.**__" + "\n" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).longID)[2])
                        Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.guild) + " was shot by a Veteran. Authorizing Member: " + message.author.getDisplayName(message.guild))
                        message.delete()
                    }
                    "vigkill" -> {
                        Mafia.killPlayer(message, Utils.getMentionedUser(message).longID)
                        Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was shot by a Vigilante**__" + "\n" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).longID)[2])
                        Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.guild) + " was shot by a Vigilante. Authorizing Member: " + message.author.getDisplayName(message.guild))
                        message.delete()
                    }
                    "jailkill" -> {
                        Mafia.killPlayer(message, Utils.getMentionedUser(message).longID)
                        Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was killed by the Jailor**__ " + "\n" + Utils.getMentionedUser(message).getDisplayName(message.guild) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).longID)[2])
                        Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.guild) + " was killed by the Jailor. Authorizing Member: " + message.author.getDisplayName(message.guild))
                        message.delete()
                    }
                    "shuffle" -> {
                        Mafia.assignRoles(message)
                        message.delete()
                    }
                }
            }
            else if (messageContent[0] == prefix + "mafia") { //This is all this listener will handle so putting this requirement for the rest of the code to execute.
                when(messageContent[1]){
                    "join" ->{
                        Mafia.onGameJoinCommand(message)
                        message.delete()
                    }
                    "leave" ->{
                        Mafia.onGameLeaveCommand(message)
                        message.delete()
                    }
                    "getInfo" ->{
                        val details = MafiaConfig.getPlayerDetails(message)
                        Message.sendDM(message.author, "Player Info on " + message.author.getDisplayName(message.guild) + "\nAlignment: " + details[0] + "\nClass: " + details[1] + "\nRole: " + details[2] + "\nIs Dead: " + details[3] + "\nAttack Power: " + details[4] + "\nDefence Power: " + details[5])
                    }
                }
                if (messageContent[1] == "mkill" && MafiaConfig.getPlayerDetails(message)[0].toString() == "mafia") {
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), "The mafia would like to kill " + Utils.getMentionedUser(message).getDisplayName(message.guild))
                    Message.sendMessage(message.channel, "You have decided to kill " + Utils.getMentionedUser(message).getDisplayName(message.guild))
                    message.delete()
                } else if (messageContent[1] == "skkill" && MafiaConfig.getPlayerDetails(message)[2].toString() == "serial_killer") {
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), "A serial killer has decided to stab " + Utils.getMentionedUser(message).getDisplayName(message.guild))
                    Message.sendDM(message.author, "You have decided to kill " + Utils.getMentionedUser(message).getDisplayName(message.guild))
                    message.delete()
                } else if (messageContent[1] == "wwkill" && MafiaConfig.getPlayerDetails(message)[2].toString() == "werewolf") {
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), "The werewolf has decided to visit " + Utils.getMentionedUser(message).getDisplayName(message.guild))
                    Message.sendDM(message.author, "You have decided to go pay " + Utils.getMentionedUser(message).getDisplayName(message.guild) + " a visit tonight")
                    message.delete()
                } else if (messageContent[1] == "arsokill" && MafiaConfig.getPlayerDetails(message)[2].toString() == "arsonist") {
                    if (Utils.getMentionedUser(message) !== message.author) {
                        Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), "The arsonist is gonna douse " + Utils.getMentionedUser(message).getDisplayName(message.guild))
                        Message.sendDM(message.author, "You have decided to douse " + Utils.getMentionedUser(message).getDisplayName(message.guild))
                    } else {
                        Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), "The arsonist is gonna set all doused players on fire")
                        Message.sendDM(message.author, "You have decided to set all targets on fire tonight")
                    }
                    message.delete()
                } else if (messageContent[1] == "jestkill" && MafiaConfig.getPlayerDetails(message)[2].toString() == "jester") {
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), "The jester is gonna haunt " + Utils.getMentionedUser(message).getDisplayName(message.guild))
                    Message.sendDM(message.author, "You have decided to haunt " + Utils.getMentionedUser(message).getDisplayName(message.guild))
                    message.delete()
                } else if (messageContent[1] == "vetkill" && MafiaConfig.getPlayerDetails(message)[2].toString() == "veteran") {
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), "The Veteran " + message.author + " is going on alert tonight.")
                    Message.sendDM(message.author, "You have decided to go on alert tonight")
                    message.delete()
                } else if (messageContent[1] == "vigkill" && MafiaConfig.getPlayerDetails(message)[2].toString() == "vigilante") {
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), "The Vigilante is going to shoot " + Utils.getMentionedUser(message).getDisplayName(message.guild))
                    Message.sendDM(message.author, "You have decided to shoot " + Utils.getMentionedUser(message).getDisplayName(message.guild))
                    message.delete()
                } else if (messageContent[1] == "jailkill" && MafiaConfig.getPlayerDetails(message)[2].toString() == "jailor") {
                    if (Utils.getMentionedUser(message).longID == MafiaConfig.getJailed(message)) {
                        Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), "The jailor is going to shoot " + Utils.getMentionedUser(message).getDisplayName(message.guild))
                        Message.sendMessage(message.channel, "You have decided to shoot " + Utils.getMentionedUser(message).getDisplayName(message.guild))
                    } else {
                        Message.sendDM(message.author, "You can only shoot the person you have jailed!")
                    }
                    message.delete()
                } //TODO add kills for VH and Vampire. Vampire kills Mafia if they try to bite them. VH kills Vampire on visit.
                else if (messageContent[1] == "jail" && MafiaConfig.getPlayerDetails(message)[2].toString() == "jailor") {
                    if (Utils.getMentionedUser(message) === message.author) {
                        Message.sendDM(message.author, "You can not jail yourself!")
                    } else {
                        Mafia.jailPlayer(message, Utils.getMentionedUser(message))
                        Message.sendDM(message.author, "You have successfully jailed " + Utils.getMentionedUser(message))
                        Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), "The jailor has jailed " + Utils.getMentionedUser(message).getDisplayName(message.guild))
                    }
                    message.delete()
                } else if (messageContent[1] == "watch" && MafiaConfig.getPlayerDetails(message)[2].toString() == "lookout") {
                    Message.sendDM(message.author, "You will be watching " + Utils.getMentionedUser(message).getDisplayName(message.guild) + " tonight.")
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), message.author.getDisplayName(message.guild) + " will be watching " + Utils.getMentionedUser(message).getDisplayName(message.guild) + " tonight.")
                    message.delete()
                } else if (messageContent[1] == "invest" && Perms.checkMod(message)) { // /mafia invest @target @user
                    val mentionedList = message.mentions
                    val investResults1 = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + ".dat")
                    val investResults = investResults1.getJSONObject("invest_results")
                    val targetDetails = MafiaConfig.getPlayerDetails(message, mentionedList[0].longID)
                    Message.sendDM(mentionedList[1], investResults.getString(targetDetails[2].toString()))
                    println("Send DM to " + mentionedList[1])
                    message.delete()
                } else if (messageContent[1] == "invest" && MafiaConfig.getPlayerDetails(message)[2].toString() == "investigator") {
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), message.author.getDisplayName(message.guild) + " would like to investigate " + Utils.getMentionedUser(message).getDisplayName(message.guild) + " tonight.")
                    message.delete()
                } else if (messageContent[1] == "sheriff" && Perms.checkMod(message)) { // /mafia sheriff @target @user
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
                } else if (messageContent[1] == "sheriff" && MafiaConfig.getPlayerDetails(message)[2].toString() == "sheriff") {
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), "The sheriff would like to interrogate " + Utils.getMentionedUser(message).getDisplayName(message.guild) + " tonight.")
                    Message.sendDM(message.author, "You are going to be interrogating " + Utils.getMentionedUser(message) + " tonight.")
                    message.delete()
                } else if (messageContent[1] == "transport" && MafiaConfig.getPlayerDetails(message)[2].toString() == "transporter") {
                    val mentionedList = message.mentions
                    val mentionedArray = mentionedList.toTypedArray()
                    val target = mentionedList[0]
                    val invest = mentionedList[1]
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), message.author.getDisplayName(message.guild) + " would like to swap positions of " + invest.getDisplayName(message.guild) + " & " + target.getDisplayName(message.guild))
                    Message.sendDM(message.author, "You will be transporting " + invest.getDisplayName(message.guild) + " & " + target.getDisplayName(message.guild))
                    message.delete()
                } else if (messageContent[1] == "witch" && MafiaConfig.getPlayerDetails(message)[2].toString() == "witch") {
                    val mentionedList = message.mentions
                    val mentionedArray = mentionedList.toTypedArray()
                    val target = mentionedList[0]
                    val invest = mentionedList[1]
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), message.author.getDisplayName(message.guild) + " would like to control " + invest.getDisplayName(message.guild) + " into using their ability onto " + target.getDisplayName(message.guild))
                    Message.sendDM(message.author, "You will be witching " + invest.getDisplayName(message.guild) + " into " + target.getDisplayName(message.guild))
                    message.delete()
                } else if (messageContent[1] == "heal" && MafiaConfig.getPlayerDetails(message)[2].toString() == "doctor") {
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), message.author.getDisplayName(message.guild) + " will be healing " + Utils.getMentionedUser(message).getDisplayName(message.guild) + " tonight.")
                    Message.sendDM(message.author, "You will be healing " + Utils.getMentionedUser(message).getDisplayName(message.guild) + " tonight.")
                    message.delete()
                } else if (messageContent[1] == "guard") {
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), message.author.getDisplayName(message.guild) + " will be guarding " + Utils.getMentionedUser(message).getDisplayName(message.guild) + " tonight.")
                    Message.sendDM(message.author, "You will be guarding " + Utils.getMentionedUser(message).getDisplayName(message.guild) + " tonight.")
                    message.delete()
                } else if (messageContent[1] == "escort") {
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), message.author.getDisplayName(message.guild) + " would like to roleblock " + Utils.getMentionedUser(message) + " tonight.")
                    Message.sendDM(message.author, "You will be escorting " + Utils.getMentionedUser(message).getDisplayName(message.guild) + " tonight.")
                    message.delete()
                } else if (messageContent[1] == "reveal" && MafiaConfig.getDayStatus(message) && MafiaConfig.getPlayerDetails(message)[2].toString() == "mayor") {
                    Message.sendMessage(message.channel, message.author.getDisplayName(message.guild) + " has revealed themselves as the Mayor!")
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), message.author.getDisplayName(message.guild) + " has revealed as the mayor. Their votes now count as 3.")
                    message.delete()
                } else if (messageContent[1] == "vote" && MafiaConfig.getDayStatus(message) && Utils.getMentionedUser(message) !== message.author) {
                    Message.sendMessage(message.channel, message.author.toString() + " has voted for " + Utils.getMentionedUser(message))
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), message.author.toString() + " has voted for " + Utils.getMentionedUser(message))
                    message.delete()
                } else if (messageContent[1] == "secance" && !MafiaConfig.getDayStatus(message) && MafiaConfig.getPlayerDetails(message)[2].toString() == "medium" && MafiaConfig.getPlayerDetails(message)[3] as Boolean) {
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), "The medium would like to talk to " + Utils.getMentionedUser(message).getDisplayName(message.guild))
                    Message.sendDM(message.author, "Your message has been sent to the Admin. Please wait for them to respond to your secance request")
                    message.delete()
                } else if (messageContent[1] == "revive" && MafiaConfig.getPlayerDetails(message)[2].toString() == "retributionist") {
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), "The retributionist will be reviving " + Utils.getMentionedUser(message) + " tonight.")
                    Message.sendDM(message.author, "You will be reviving " + Utils.getMentionedUser(message).getDisplayName(message.guild) + " tonight.")
                    message.delete()
                } else if (messageContent[1] == "vampcheck") {
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), "The vampire hunter " + message.author.getDisplayName(message.guild) + " will be checking if " + Utils.getMentionedUser(message) + " is a vampire tonight.")
                    Message.sendDM(message.author, "You will be checking to see if " + Utils.getMentionedUser(message).getDisplayName(message.guild) + "is a Vampire.")
                    message.delete()
                } else if (messageContent[1] == "disguise") {
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), "The Disguiser is going to be disguised as " + Utils.getMentionedUser(message).getDisplayName(message.guild) + " in role.")
                    Message.sendDM(message.author, "You will be disguising as " + Utils.getMentionedUser(message).getDisplayName(message.guild))
                    message.delete()
                } else if (messageContent[1] == "forge") {
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), "The forger is gonna forge the role of " + Utils.getMentionedUser(message).getDisplayName(message.guild) + " to be Forger.")
                    Message.sendDM(message.author, "You will be forging the role of " + Utils.getMentionedUser(message).getDisplayName(message.guild))
                    message.delete()
                } else if (messageContent[1] == "frame") {
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), "The framer is gonna frame " + Utils.getMentionedUser(message).getDisplayName(message.guild))
                    Message.sendDM(message.author, "You will be framing " + Utils.getMentionedUser(message).getDisplayName(message.guild))
                    message.delete()
                } else if (messageContent[1] == "clean") {
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), message.author.getDisplayName(message.guild) + " would like to clean the role of " + Utils.getMentionedUser(message))
                    Message.sendDM(message.author, "You will be cleaning " + Utils.getMentionedUser(message).getDisplayName(message.guild))
                    message.delete()
                } else if (messageContent[1] == "blackmail") {
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), message.author.getDisplayName(message.guild) + "would like to Blackmail " + Utils.getMentionedUser(message).getDisplayName(message.guild))
                    Message.sendDM(message.author, "You will be blackmailing " + Utils.getMentionedUser(message))
                    message.delete()
                } else if (messageContent[1] == "consig") {
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), message.author.getDisplayName(message.guild) + " would like to know the role of " + Utils.getMentionedUser(message))
                    Message.sendDM(message.author, "You will receive the role of " + Utils.getMentionedUser(message))
                    message.delete()
                } else if (messageContent[1] == "remember") {
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), message.author.getDisplayName(message.guild) + " would like to remember the role of " + Utils.getMentionedUser(message))
                    Message.sendDM(message.author, "You will remember the role of " + Utils.getMentionedUser(message))
                    message.delete()
                } else if (messageContent[1] == "vest") {
                    Message.sendMessage(message.guild.getChannelByID(MafiaConfig.getAdminChannel(message)), message.author.getDisplayName(message.guild) + " will be putting on a vest tonight.")
                    Message.sendDM(message.author, "You will be putting on a vest tonight.")
                }


            }

        }
    }

}
