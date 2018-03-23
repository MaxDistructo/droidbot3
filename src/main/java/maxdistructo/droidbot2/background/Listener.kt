package maxdistructo.droidbot2.background


import maxdistructo.droidbot2.BaseBot
import maxdistructo.droidbot2.BaseBot.client
import maxdistructo.droidbot2.commands.*
import maxdistructo.droidbot2.commands.casino.Casino
import maxdistructo.droidbot2.commands.casino.CasinoConfig
import maxdistructo.droidbot2.core.Client
import maxdistructo.droidbot2.core.Perms
import maxdistructo.droidbot2.core.Roles
import maxdistructo.droidbot2.core.Utils
import maxdistructo.droidbot2.core.Utils.s
import maxdistructo.droidbot2.core.message.Message
import org.apache.commons.io.FileUtils
import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelJoinEvent
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelLeaveEvent
import sx.blah.discord.handle.impl.events.shard.ShardReadyEvent
import sx.blah.discord.handle.impl.obj.ReactionEmoji
import sx.blah.discord.handle.obj.IGuild
import sx.blah.discord.handle.obj.IUser
import sx.blah.discord.util.DiscordException
import sx.blah.discord.util.MissingPermissionsException
import sx.blah.discord.util.RateLimitException
import java.io.File
import java.net.URL

class Listener {

    @EventSubscriber
    @Throws(RateLimitException::class, DiscordException::class, MissingPermissionsException::class)
    fun onMessageReceivedEvent(event: MessageReceivedEvent) {
        try {
            val message = event.message
            val guild = message.guild
            val prefix = Client.prefix // To allow for easy compatability with old code. All new code will reference #Client.prefix directly.

            val channelMention = Utils.getMentionedChannel(message)
            val mentioned = Utils.getMentionedUser(message)

            val content = message.content
            val messageContent = content.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val messageContentAny = content.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray() as Array<Any>

            if (!Roles.checkForBotAbuse(message) && messageContent.isNotEmpty()) {
                if (messageContent[0] == prefix + "bj") {
                    //message.reply(BlackJack.blackjack(messageContent, message));
                } else if (messageContent[0].toLowerCase() == "hit" && Perms.checkGames(message) || messageContent[0].toLowerCase() == "stay" && Perms.checkGames(message)) {
                    //message.reply(BlackJack.continueGame(message, (String[]) messageContent, CasinoConfig.readBJFields(message)));
                } else if (messageContent[0] == prefix + "check") { //Works
                    Message.sendMessage(message.channel, Message.simpleEmbed(message.author, "Check", Check.onCheckCommand(messageContent, message), message))
                    message.delete()
                } else if (messageContent[0] == prefix + "casino" && messageContent[1] == "info" && mentioned != null && Perms.checkGames(message)) { //Works except for admin commands
                    message.reply("", Casino.onCasinoInfo(message, mentioned))
                    message.delete()
                } else if (messageContent[0] == prefix + "casino" && messageContent[1] == "info" && Perms.checkGames(message)) { //Works except for admin commands
                    message.reply("", Casino.onCasinoInfo(message))
                    message.delete()
                } else if (messageContent[0] == prefix + "casino" && Perms.checkGames(message)) { //Works except for admin commands
                    message.reply("", Message.simpleEmbed(message.author, "Casino", Casino.onCasinoCommand(messageContent, message, message.author), message))
                    message.delete()
                } else if (messageContent[0] == prefix + "50" || messageContent[0] == prefix + "fifty" && Perms.checkGames(message)) { //Works
                    //message.reply("", Message.simpleEmbed(message.getAuthor(), "FiftyFifty", FiftyFifty.onFiftyCommand(messageContent, message), message));
                    message.delete()
                } else if (messageContent[0] == prefix + "fortune") { //Works
                    message.reply("", Message.simpleEmbed(message.author, "Fortune", Fortune.onFortuneCommand(message), message))
                    message.delete()
                } else if (messageContent[0] == prefix + "horoscope") { //Works
                    message.reply("", Message.simpleEmbed(message.author, "Horoscope", Horoscope.onHoroscopeCommand(messageContent), message))
                    message.delete()
                } else if (messageContent[0] == prefix + "info") { //Works Well
                    Message.sendMessage(message.channel, Message.simpleEmbed(message.author, "Info", Info.onInfoCommand(messageContent, message, mentioned), message))
                    message.delete()
                } else if (messageContent[0] == prefix + "insult") { //Works
                    Message.sendMessage(message.channel, Message.simpleEmbed(message.author, "Insult", Insult.onInsultCommand(messageContent, message, mentioned!!), message))
                    message.delete()
                } else if (messageContent[0] == prefix + "debug") { //Needs perms set.
                    message.reply("", Message.simpleEmbed(message.author, "Debug", Debug.onDebugCommand(messageContent, message), message))
                    message.delete()
                } else if (messageContent[0] == prefix + "shutdown") { //Works
                    message.reply("", Message.simpleEmbed(message.author, "Shutdown", Shutdown.onShutdownCommand(message), message))
                    message.delete()
                } else if (messageContent[0] == prefix + "help") {
                    if (Perms.checkAdmin(message)) {
                        // Help.onAdminHelpCommand(message);
                    } else {
                        //  Message.sendDM(message.getAuthor(), Help.onHelpCommand());
                    }
                    message.delete()
                } else if (messageContent[0] == prefix + "allin" && Perms.checkGames(message)) {
                    //message.reply("", Message.simpleEmbed(message.getAuthor(), "Allin", Allin.onAllinCommand(messageContent, message), message));
                    message.delete()
                } else if (messageContent[0] == prefix + "say" && channelMention != null) {
                    Message.sendMessage(channelMention, Say.onSayCommand(messageContentAny, message, channelMention))
                    message.delete()
                } else if (messageContent[0] == prefix + "say") {
                    Message.sendMessage(message.channel, Say.onSayCommand(messageContentAny, message, channelMention))
                    message.delete()
                } else if (messageContent[0] == prefix + "spam") {
                    //Message.sendMessage(message.getChannel(), Spam.onSpamCommand(messageContent, message, mentioned));
                    message.delete()
                } else if (messageContent[0] == prefix + "slap") {
                    Message.sendMessage(message.channel, PlayerFun.onSlapCommand(message, mentioned!!))
                    message.delete()
                } else if (messageContent[0] == prefix + "tnt") {
                    Message.sendMessage(message.channel, PlayerFun.onTntCommand(message, mentioned!!))
                    message.delete()
                } else if (messageContent[0] == prefix + "kiss") {
                    Message.sendMessage(message.channel, PlayerFun.onKissCommand(message, mentioned!!))
                    message.delete()
                } else if (messageContent[0] == prefix + "hug") {
                    Message.sendMessage(message.channel, PlayerFun.onHugCommand(message, mentioned!!))
                    message.delete()
                } else if (messageContent[0] == prefix + "poke") {
                    Message.sendMessage(message.channel, PlayerFun.onPokeCommand(message, mentioned!!))
                    message.delete()
                } else if (messageContent[0] == prefix + "respect" || messageContent[0] == "/f") {
                    Message.sendMessage(message.channel, PlayerFun.onPayRespects(message, mentioned!!))
                    message.delete()
                } else if (messageContent[0] == prefix + "banhammer") {
                    Message.sendMessage(message.channel, PlayerFun.onBanHammer(message, mentioned!!))
                    message.delete()
                } else if (messageContent[0] == prefix + "shoot") {
                    Message.sendMessage(message.channel, PlayerFun.onShootCommand(message, mentioned!!))
                    message.delete()
                } else if (messageContent[0] == prefix + "stab") {
                    Message.sendMessage(message.channel, PlayerFun.onStabCommand(message, mentioned!!))
                    message.delete()
                } else if (messageContent[0] == prefix + "ping") {
                    Ping.onPingCommand(message)
                    message.delete()
                } else if (messageContent[0] == prefix + "xp") {
                    Message.sendMessage(message.channel, PlayerFun.onXpCommand(mentioned!!))
                    message.delete()
                } else if (messageContent[0] == prefix + "punch") {
                    Message.sendMessage(message.channel, PlayerFun.onPunchCommand(message, mentioned!!))
                    message.delete()
                } else if (messageContent[0] == "$prefix@admin" && messageContent[1] == "addMod" && Perms.checkAdmin(message)) {
                    Message.sendMessage(message.channel, Admin.addMod(message, mentioned!!))
                } else if (messageContent[0] == "$prefix@admin" && messageContent[1] == "addAdmin" && Perms.checkAdmin(message)) {
                    Message.sendMessage(message.channel, Admin.addAdmin(message, mentioned!!))
                } else if (messageContent[0] == "$prefix@casino" && messageContent[1] == "balance" && messageContent[2] == "add" && Utils.convertToInt(messageContent[4]) != 0 && mentioned != null && Perms.checkAdmin(message)) {
                    Message.sendMessage(message.channel, Admin.addCasinoBalance(messageContent, message, mentioned))
                } else if (messageContent[0] == "$prefix@casino" && messageContent[1] == "balance" && messageContent[2] == "remove" && Utils.convertToInt(messageContent[4]) != 0 && mentioned != null && Perms.checkAdmin(message)) {
                    Message.sendMessage(message.channel, Admin.subtractCasinoBalance(messageContent, message, mentioned))
                } else if (messageContent[0] == "$prefix@casino" && messageContent[1] == "balance" && messageContent[2] == "set" && Utils.convertToInt(messageContent[4]) != 0 && mentioned != null && Perms.checkAdmin(message)) {
                    Message.sendMessage(message.channel, Admin.setCasinoBalance(messageContent, message, mentioned))
                } else if (messageContent[0] == "$prefix@admin" && messageContent[1] == "botAbuse" && Perms.checkAdmin(message)) {
                    Message.sendMessage(message.channel, Admin.setBotAbuser(messageContent, message, mentioned!!))
                } else if (messageContent[0] == "$prefix@admin" && messageContent[1] == "name" && Perms.checkOwner(message)) {
                    Message.sendMessage(message.channel, Admin.setNickname(messageContent))
                } else if (messageContent[0] == "$prefix@admin" && messageContent[1] == "image" && Perms.checkOwner(message)) {
                    Message.sendMessage(message.channel, Admin.setProfilePic(messageContent))
                } else if (messageContent[0] == "$prefix@admin" && messageContent[1] == "leaveGuild" && Perms.checkOwner(message)) {
                    Message.sendMessage(message.channel, Admin.leaveGuild(messageContent))
                } else if (messageContent[0] == "$prefix@admin" && messageContent[1] == "perms" && Perms.checkAdmin(message)) {
                    Message.sendMessage(message.channel, Admin.changeRolePerm(message, messageContent))
                } else if (messageContent[0] == "$prefix@admin" && messageContent[1] == "restart") {
                    Restart.run(message)
                } else if (messageContent[0] == prefix + "emote" && messageContent[1] == "add") {
                    Emote.addEmoteCommand(message, messageContent)
                    message.addReaction(ReactionEmoji.of("/:heavy_check_mark:"))
                } else if (messageContent[0] == prefix + "emote" && messageContent[1] == "request") {
                    Emote.requestEmoteCommand(message, messageContent)
                    message.delete()
                } else if (messageContent[0] == prefix + "emote") {
                    Emote.onEmoteCommand(message, messageContent)
                    message.delete()
                } else if (messageContent[0] == prefix + "admin" && messageContent[1] == "fixPerms") {
                    if (Perms.checkOwner(message)) {
                        Message.sendDM(message.guild.owner, client.applicationName + "has left your server because the bot owner though it was missing perms or its permissions were screwed up. Please use this url to re-add " + client.applicationName + " to your server. Your server's data has not been affected. https://discordapp.com/oauth2/authorize?client_id=423268575718014976&scope=bot&permissions=470281296")
                        message.guild.leave()
                    }
                } else if (messageContent[0] == "$prefix@admin" && messageContent[1] == "setColor" && Perms.checkMod(message)) {
                    Roles.changeColor(Roles.getRole(message, messageContent[2])!!, messageContent[3])
                    message.delete()
                } else if (messageContent[0] == "$prefix@mute" && Perms.checkAdmin(message)) { //!@mute @User time
                    Admin.muteUser(message, mentioned!!, Utils.convertToInt(messageContent[2]))
                    message.delete()
                } else if (messageContent[0] == "$prefix@unmute" && Perms.checkAdmin(message) && channelMention != null) {
                    Admin.unmuteUser(message, mentioned!!, channelMention)
                    message.delete()
                } else if (messageContent[0] == "$prefix@unmute" && Perms.checkAdmin(message)) {
                    Admin.unmuteUser(message, mentioned!!)
                    message.delete()
                } else if (messageContent[0] == "$prefix@announce") {
                    Admin.onAnnounceCommand(messageContentAny, message)
                    message.delete()
                } else if (messageContent[0] == prefix + "fixServer" && Perms.checkOwner_Guild(message)) { //Due to requirement of server configs (Blame Swear Filter), this command is separated so that if errors are being thrown this command can still run.
                    try {
                        FileUtils.copyURLToFile(URL("https://maxdistructo.github.io/droidbot2/downloads/config/defaultconfig.txt"), File(s + "/droidbot/config/" + message.guild.longID + ".txt"))
                    } catch (e: Exception) {
                        Message.throwError(e, message)
                    }

                    Roles.makeNewRole(guild, "Voice Chatting", false, false)
                    Roles.makeNewRole(guild, "Payday", false, false)
                    Roles.makeNewRole(guild, "Bot Abuser", false, false)
                    Message.sendMessage(guild.defaultChannel, "Thank you for letting me join your server. I am " + client.ourUser.name + " and my features can be found by using the command " + prefix + "help.")

                } else if (messageContent[0] == prefix + "getMentions" && Perms.checkOwner(message)) {
                    val mentionedList = message.mentions
                    val mentionedArray = mentionedList.toTypedArray()
                    val target = mentionedArray[0] as IUser
                    val invest = mentionedArray[1] as IUser
                    Message.sendDM(message.author, "The user in slot 0 is " + target.getDisplayName(message.guild) + "\n The user in slot 1 is " + invest.getDisplayName(message.guild))
                }


            }
        }
        catch(e : Exception){
            Message.throwError(e)
        }
    }


    @EventSubscriber
    @Throws(RateLimitException::class, DiscordException::class, MissingPermissionsException::class)
    fun onVoiceChannelJoin(event: UserVoiceChannelJoinEvent) {
        val channel = event.voiceChannel
        val user = event.user
        val guild = channel.guild
        val roles = guild.getRolesByName("Voice Chatting")
        user.addRole(roles[0])
    }

    @EventSubscriber
    @Throws(RateLimitException::class, DiscordException::class, MissingPermissionsException::class)
    fun onVoiceChannelLeave(event: UserVoiceChannelLeaveEvent) {
        val channel = event.voiceChannel
        val user = event.user
        val guild = channel.guild
        val roles = guild.getRolesByName("Voice Chatting")
        user.removeRole(roles[0])
    }

    @EventSubscriber
    fun onShardReadyEvent(event: ShardReadyEvent) {
        client.isLoggedIn
        BaseBot.LOGGER.info("Added playing content")
        val guildsList = client.guilds
        val guilds = guildsList.toTypedArray()
        var i = 0
        var guild: IGuild?
        while (i < guilds.size) {
            guild = guilds[i] as IGuild
            val name: String
            if (client.ourUser.getNicknameForGuild(guild) != null) {
                name = client.ourUser.getNicknameForGuild(guild)
            } else {
                name = client.applicationName
            }
            CasinoConfig.doPaydayReset(guild)
            i++
        }

    }

    companion object {
        var blackJackRunning = false
        var blackjackAnswer: String? = null
        var prefix = "!" //Change back to ! for release!
        var triviaAnswer: String? = null
        var triviaRunning = true
    }
}


/*public static void onGuildJoinEvent(GuildCreateEvent event){
        IGuild guild = event.getGuild();
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        IGuild commandLogServer = client.getGuildByID(365320862225924096L);
        IChannel guildChannel = commandLogServer.createChannel(guild.getName());
        File file = new File(s + "/droidbot/config/" + guild.getLongID() + ".txt");
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jo = new JSONObject();
        jo.put("Name", guild.getName());
        jo.put("Admins", new JSONArray());
        jo.put("Moderators", new JSONArray());
        jo.put("GameChannels",new JSONArray());
        jo.put("GuildLoggingChannel", guildChannel.getLongID());
        jo.put("INFO","All of the above values use Discord Debug IDs. Just Google how to find these IDs. The above IDs will throw an error in the program so please remove/change them. You do NOT have to put your ID in more than one of the categories (EX. Admins do not need to have their ID put in the Admins set and the Moderators set. Just the Admins set.). Owners do not have to add their ID in anything as they will be given full perms. This file is auto-generated via the program and an example version of this file can be found at https://github.com/MaxDistructo/droidbot2/blob/master/droidbot2/config/ExampleServerIdConfig.txt");
        try (FileWriter fileW = new FileWriter(s + "/droidbot/config/" + guild.getLongID() + ".txt")) {
            fileW.write(jo.toString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + jo);
        } catch (IOException e) {
            BaseBot.LOGGER.warn("Listener.newServer Error.");
            Message.sendDM(BaseBot.client.getApplicationOwner(), e.toString());
            e.printStackTrace();
        }
        RoleBuilder rb = new RoleBuilder(guild);
        rb.withName("Voice Chatting");
        rb.setHoist(false);
        rb.setMentionable(false);
        rb.build();
        RoleBuilder rb2 = new RoleBuilder(guild);
        rb2.withName("Payday");
        rb2.setHoist(false);
        rb2.setMentionable(false);
        rb2.build();
        RoleBuilder rb3 = new RoleBuilder(guild);
        rb3.withName("Bot Abuser");
        rb3.setHoist(false);
        rb3.setMentionable(false);
        rb3.build();




        Message.sendMessage(guild.getDefaultChannel(), "Thank you for letting me join your server. I am " + client.getOurUser().getName() + " and my features can be found by using the command " + prefix + "help. Please DM " + client.getApplicationOwner().mention() + " to add additional moderators/admins for your server.");

    }*/
