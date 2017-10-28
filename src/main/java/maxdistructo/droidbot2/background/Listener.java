package maxdistructo.droidbot2.background;


import maxdistructo.droidbot2.BaseBot;
import maxdistructo.droidbot2.background.message.Message;
import maxdistructo.droidbot2.commands.*;
import org.json.JSONException;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelJoinEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelLeaveEvent;
import sx.blah.discord.handle.impl.events.shard.ShardReadyEvent;
import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import sx.blah.discord.util.RoleBuilder;

import static maxdistructo.droidbot2.BaseBot.client;

public class Listener {
    public static boolean blackJackRunning = false;
    public static String blackjackAnswer;
    public static String prefix = "!"; //Change back to ! for release!
    public static String triviaAnswer;
    public static boolean triviaRunning = true;

    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event) throws RateLimitException, DiscordException, MissingPermissionsException { // This method is NOT called because it doesn't have the @EventSubscriber annotation
        try {
            IMessage message = event.getMessage();
            
            List<IChannel> mentionedChannelList = message.getChannelMentions();
            Object[] mentionedChannelArray = mentionedChannelList.toArray();
            IChannel channelMention;
            if (mentionedChannelArray.length > 0) {
                channelMention = (IChannel) mentionedChannelArray[0];
            } else {
                channelMention = null;
            }
            
            List<IUser> mentionedList = message.getMentions();
            Object[] mentionedArray = mentionedList.toArray();
            IUser mentioned;
            if (mentionedArray.length > 0) {
                mentioned = (IUser) mentionedArray[0];
            } else {
                mentioned = null;
            }
            String content = message.getContent();
            Object messageContent[] = content.split(" ");
            prefix = Config.readPrefix();
            JSONObject root = Config.readServerConfig(message.getGuild());
            IChannel loggingChannel;
            try {
                if (client.getChannelByID(root.getLong("GuildLoggingChannel")) == null) {
                    loggingChannel = client.getChannelByID(root.getLong("GuildLoggingChannel"));
                    if (content.charAt(0) == prefix.charAt(0)) {
                        Message.sendMessage(loggingChannel, message.getAuthor().getName() + " " + content);
                    }
                }
            }
            catch(JSONException e){
                e.printStackTrace();
            }

            if (!Roles.checkForBotAbuse(message)) {
                if (messageContent[0].equals(prefix + "bj")) { //WIP
                    message.reply(BlackJack.blackjack(messageContent, message));
                } else if (messageContent[0].toString().toLowerCase().equals("hit") && Perms.checkGames(message) || messageContent[0].toString().toLowerCase().equals("stay") && Perms.checkGames(message)) {
                    message.reply(BlackJack.continueGame(message, (String[]) messageContent, Config.readBJFields(message)));
                } else if (messageContent[0].equals(prefix + "check")) { //Works
                    Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "Check", Check.onCheckCommand(messageContent, message), message));
                    message.delete();
                } else if (messageContent[0].equals(prefix + "casino") && messageContent[1].equals("info") && mentioned != null && Perms.checkGames(message)) { //Works except for admin commands
                    message.reply("", Casino.onCasinoInfo(message, mentioned));
                    message.delete();
                } else if (messageContent[0].equals(prefix + "casino") && messageContent[1].equals("info") && Perms.checkGames(message)) { //Works except for admin commands
                    message.reply("", Casino.onCasinoInfo(message));
                    message.delete();
                } else if (messageContent[0].equals(prefix + "casino") && Perms.checkGames(message)) { //Works except for admin commands
                    message.reply("", Message.simpleEmbed(message.getAuthor(), "Casino", Casino.onCasinoCommand(messageContent, message, message.getAuthor()), message));
                    message.delete();
                } else if (messageContent[0].equals(prefix + "50") || messageContent[0].equals(prefix + "fifty") && Perms.checkGames(message)) { //Works
                    message.reply("", Message.simpleEmbed(message.getAuthor(), "FiftyFifty", FiftyFifty.onFiftyCommand(messageContent, message), message));
                    message.delete();
                } else if (messageContent[0].equals(prefix + "fortune")) { //Works
                    message.reply("", Message.simpleEmbed(message.getAuthor(), "Fortune", Fortune.onFortuneCommand(messageContent, message), message));
                    message.delete();
                }
                //else if(messageContent[0].equals(prefix + "game")){ //Broke
                //  message.reply(GameCommand.onGameCommand(messageContent, message));
                // }
                else if (messageContent[0].equals(prefix + "info")) { //Works Well
                    Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "Info", Info.onInfoCommand(messageContent, message, mentioned), message));
                    message.delete();
                } else if (messageContent[0].equals(prefix + "insult")) { //Works
                    Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "Insult", Insult.onInsultCommand(messageContent, message, mentioned), message));
                    message.delete();
                } else if (messageContent[0].equals(prefix + "debug")) { //Needs perms set.
                    message.reply("", Message.simpleEmbed(message.getAuthor(), "Debug", Debug.onDebugCommand((String[]) messageContent, message), message));
                    message.delete();
                } else if (messageContent[0].equals(prefix + "shutdown")) { //Works
                    message.reply("", Message.simpleEmbed(message.getAuthor(), "Shutdown", Shutdown.onShutdownCommand(message), message));
                    message.delete();
                } else if (messageContent[0].equals(prefix + "help")) {
                    if(Perms.checkAdmin(message)){
                        Help.onAdminHelpCommand(message);
                    }
                    else{
                        Message.sendDM(message.getAuthor(), Help.onHelpCommand());
                    }
                    message.delete();
                } else if (messageContent[0].equals(prefix + "allin") && Perms.checkGames(message)) {
                    message.reply("", Message.simpleEmbed(message.getAuthor(), "Allin", Allin.onAllinCommand(messageContent, message), message));
                    message.delete();
                } else if (messageContent[0].equals(prefix + "say") && channelMention != null) {
                    Message.sendMessage(channelMention, Say.onSayCommand(messageContent, message, channelMention));
                } else if (messageContent[0].equals(prefix + "say")) {
                    Message.sendMessage(message.getChannel(), Say.onSayCommand(messageContent, message, channelMention));
                } else if (messageContent[0].equals(prefix + "spam")) {
                    Message.sendMessage(message.getChannel(), Spam.onSpamCommand(messageContent, message, mentioned));
                    message.delete();
                } else if (messageContent[0].equals(prefix + "slap")) {
                    Message.sendMessage(message.getChannel(), PlayerFun.onSlapCommand(message, mentioned));
                    message.delete();
                } else if (messageContent[0].equals(prefix + "tnt")) {
                    Message.sendMessage(message.getChannel(), PlayerFun.onTntCommand(message, mentioned));
                    message.delete();
                } else if (messageContent[0].equals(prefix + "kiss")) {
                    Message.sendMessage(message.getChannel(), PlayerFun.onKissCommand(message, mentioned));
                    message.delete();
                } else if (messageContent[0].equals(prefix + "hug")) {
                    Message.sendMessage(message.getChannel(), PlayerFun.onHugCommand(message, mentioned));
                    message.delete();
                } else if (messageContent[0].equals(prefix + "poke")) {
                    Message.sendMessage(message.getChannel(), PlayerFun.onPokeCommand(message, mentioned));
                    message.delete();
                } else if (messageContent[0].equals(prefix + "respect") || messageContent[0].equals("/f")) {
                    Message.sendMessage(message.getChannel(), PlayerFun.onPayRespects(message, mentioned));
                    message.delete();
                } else if (messageContent[0].equals(prefix + "banhammer")) {
                    Message.sendMessage(message.getChannel(), PlayerFun.onBanHammer(message, mentioned));
                    message.delete();
                } else if (messageContent[0].equals(prefix + "shoot")) {
                    Message.sendMessage(message.getChannel(), PlayerFun.onShootCommand(message, mentioned));
                    message.delete();
                } else if (messageContent[0].equals(prefix + "stab")) {
                    Message.sendMessage(message.getChannel(), PlayerFun.onStabCommand(message, mentioned));
                    message.delete();
                } //else if (messageContent[0].equals(prefix + "mute")) {
                   // Message.sendMessage(message.getChannel(), PlayerFun.onMuteCommand(message, mentioned));
                   // message.delete();
                    // } else if (messageContent[0].equals(prefix + "lenny") || messageContent[0].equals("/lenny")) {
                    //     message.edit(PlayerFun.onLennyCommand());
                    // } else if (messageContent[0].equals("/shrug")) { Won't work cause F U Discord.
                    //      message.edit(PlayerFun.onShrugCommand());
                 else if (messageContent[0].equals(prefix + "xp")) {
                    Message.sendMessage(message.getChannel(), PlayerFun.onXpCommand(mentioned));
                    message.delete();
                } else if (messageContent[0].equals(prefix + "punch")) {
                    Message.sendMessage(message.getChannel(), PlayerFun.onPunchCommand(message, mentioned));
                    message.delete();
                } else if (messageContent[0].equals(prefix + "@admin") && messageContent[1].equals("addMod") && Perms.checkAdmin(message)) {
                    Message.sendMessage(message.getChannel(), Admin.addMod(message, mentioned));
                } else if (messageContent[0].equals(prefix + "@admin") && messageContent[1].equals("addAdmin") && Perms.checkAdmin(message)) {
                    Message.sendMessage(message.getChannel(), Admin.addAdmin(message, mentioned));
                } else if (messageContent[0].equals(prefix + "@casino") && messageContent[1].equals("balance") && messageContent[2].equals("add") && Config.converToInt(messageContent[4]) != 0 && mentioned != null && Perms.checkAdmin(message)) {
                    Message.sendMessage(message.getChannel(), Admin.addCasinoBalance(messageContent, message, mentioned));
                } else if (messageContent[0].equals(prefix + "@casino") && messageContent[1].equals("balance") && messageContent[2].equals("remove") && Config.converToInt(messageContent[4]) != 0 && mentioned != null && Perms.checkAdmin(message)) {
                    Message.sendMessage(message.getChannel(), Admin.subtractCasinoBalance(messageContent, message, mentioned));
                } else if (messageContent[0].equals(prefix + "@casino") && messageContent[1].equals("balance") && messageContent[2].equals("set") && Config.converToInt(messageContent[4]) != 0 && mentioned != null && Perms.checkAdmin(message)) {
                    Message.sendMessage(message.getChannel(), Admin.setCasinoBalance(messageContent, message, mentioned));
                } else if (messageContent[0].equals(prefix + "@admin") && messageContent[1].equals("botAbuse") && Perms.checkAdmin(message)) {
                    Message.sendMessage(message.getChannel(), Admin.setBotAbuser(messageContent, message, mentioned));
                } else if (messageContent[0].equals(prefix + "@admin") && messageContent[1].equals("name") && Perms.checkOwner(message)) {
                    Message.sendMessage(message.getChannel(), Admin.setNickname(messageContent));
                } else if (messageContent[0].equals(prefix + "@admin") && messageContent[1].equals("image") && Perms.checkOwner(message)) {
                    Message.sendMessage(message.getChannel(), Admin.setProfilePic(messageContent));
                } else if (messageContent[0].equals(prefix + "@admin") && messageContent[1].equals("leaveGuild") && Perms.checkOwner(message)) {
                    Message.sendMessage(message.getChannel(), Admin.leaveGuild(messageContent));
                } else if (messageContent[0].equals(prefix + "@admin") && messageContent[1].equals("perms") && Perms.checkAdmin(message)) {
                    Message.sendMessage(message.getChannel(), Admin.changeRolePerm(message, messageContent));
                } else if (messageContent[0].equals(prefix + "@admin") && messageContent[1].equals("restart")) {
                    Restart.run(message);
                } else if (messageContent[0].equals(prefix + "remindme") || messageContent[0].equals(prefix + "reminder")) {
                    Reminder.onReminderCommand(messageContent, message);
                    message.delete();
                } else if (messageContent[0].equals(prefix + "emote") && messageContent[1].equals("add")) {
                    Emote.addEmoteCommand(message, messageContent);
                    message.addReaction(ReactionEmoji.of("/:heavy_check_mark:"));
                } else if (messageContent[0].equals(prefix + "emote") && messageContent[1].equals("request")) {
                    Emote.requestEmoteCommand(message, messageContent);
                    message.delete();
                } else if (messageContent[0].equals(prefix + "emote")) {
                    Emote.onEmoteCommand(message, messageContent);
                    message.delete();
                } else if (messageContent[0].equals(prefix + "@copyPerms") && Perms.checkOwner_Guild(message)) {
                    System.out.println("Getting Role 1 - User's Highest Role");
                    IRole role1 = message.getAuthor().getRolesForGuild(message.getGuild()).get(0);
                    System.out.println("Getting Role 2 - Your Specified Role");
                    IRole role2 = Roles.getRole(message, Utils.makeNewString(messageContent, 1));
                    if (role1 != null && role2 != null) {
                        System.out.println("Pasting Perms from Role 2 to Role 1");
                        Roles.copyRolePerms(role2, role1);
                    }
                    message.delete();
                } else if (messageContent[0].equals(prefix + "admin") && messageContent[1].equals("fixPerms") && !messageContent[2].equals(null)){
                    if(Perms.checkOwner(message)){
                        Message.sendDM(message.getGuild().getOwner(), "CasinoBot has left your server because the bot owner though it was missing perms or its permissions were screwed up. Please use this url to re-add CasinoBot to your server. Your server's data has not been affected. https://discordapp.com/oauth2/authorize?client_id=315313967759097857&scope=bot&permissions=470281296");
                        message.getGuild().leave();
                    }
                } else if (messageContent[0].equals(prefix + "@admin") && messageContent[1].equals("setColor") && !messageContent[2].equals(null) && Perms.checkMod(message)){
                    Roles.changeColor(Roles.getRole(message,(String) messageContent[2]), (String) messageContent[3]);
                    message.delete();
                } else if (messageContent[0].equals(prefix + "@mute") && !messageContent[2].equals(null) && Perms.checkAdmin(message)){ //!@mute @User time
                    Admin.muteUser(message, mentioned, Config.converToInt(messageContent[2]));
                    message.delete();
                } else if (messageContent[0].equals(prefix + "@unmute") && Perms.checkAdmin(message) && channelMention != null){
                    Admin.unmuteUser(message, mentioned, channelMention);
                    message.delete();
                } else if (messageContent[0].equals(prefix + "@unmute") && Perms.checkAdmin(message)){
                    Admin.unmuteUser(message, mentioned);
                    message.delete();
                } else if (messageContent[0].equals(prefix + "fixServerConfig")){
                    IGuild guild = message.getGuild();
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
                    Message.sendMessage(guild.getDefaultChannel(), "Thank you for letting me join your server. I am " + client.getOurUser().getName() + " and my features can be found by using the command " + prefix + "help.(Broken RN OOPS) Please DM " + client.getApplicationOwner().mention() + " to add additional moderators/admins for your server.");

                }




                //  else if(messageContent[0].equals(prefix + "trivia")) {
                //          message.reply(Trivia.onTriviaCommand(messageContent, message));
                //   }
                // else if(messageContent[0].equals(triviaAnswer)){
                //       Trivia.addTriviaScore(event.getMessage());
                //        Trivia.checkTrivia(event.getMessage());
                // }
            } else if (content.charAt(0) == prefix.charAt(0)) {
                message.reply("Please wait until you have lost your Bot Abuser role to use this command.");
            }
        } catch (Exception e) {
            Message.sendDM(client.getApplicationOwner(), e.getLocalizedMessage());
        }

    }


    @EventSubscriber
    public void onVoiceChannelJoin(UserVoiceChannelJoinEvent event) throws RateLimitException, DiscordException, MissingPermissionsException {
        IVoiceChannel channel = event.getVoiceChannel();
        IUser user = event.getUser();
        IGuild guild = channel.getGuild();
        List<IRole> roles = guild.getRolesByName("Voice Chatting");
        user.addRole(roles.get(0));
    }

    @EventSubscriber
    public void onVoiceChannelLeave(UserVoiceChannelLeaveEvent event) throws RateLimitException, DiscordException, MissingPermissionsException {
        IVoiceChannel channel = event.getVoiceChannel();
        IUser user = event.getUser();
        IGuild guild = channel.getGuild();
        List<IRole> roles = guild.getRolesByName("Voice Chatting");
        user.removeRole(roles.get(0));
    }

    @EventSubscriber
    public void onShardReadyEvent(ShardReadyEvent event) {
        client.online(Listener.prefix + "help");
        BaseBot.LOGGER.info("Added playing content");

        List<IGuild> guildsList = client.getGuilds();
        Object[] guilds = guildsList.toArray();
        int i = 0;

        while (i < guilds.length) {
            IGuild guild = (IGuild) guilds[i];
            String name;
            if (client.getOurUser().getNicknameForGuild(guild) != null) {
                name = client.getOurUser().getNicknameForGuild(guild);
            } else {
                name = client.getApplicationName();
            }
            //Message.sendMessage(guild.getDefaultChannel(), name + " has been loaded. Version: " + BaseBot.version);

            List<IRole> rolesList = guild.getRolesByName("Payday");
            IRole paydayRole = rolesList.get(0);
            List<IUser> paydayUsers = guild.getUsersByRole(paydayRole);
            Object[] paydayArray = paydayUsers.toArray();

            int ii = 0;
            while (ii < paydayArray.length) {
                IUser user = (IUser) paydayArray[ii];
                user.removeRole(paydayRole);
                ii++;
            }
            i++;
        }

    }
    @EventSubscriber
    public static void onGuildJoinEvent(GuildCreateEvent event){
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

    }
}

