package maxdistructo.droidbot2.background;

import java.util.List;

import maxdistructo.droidbot2.core.message.Message;
import maxdistructo.droidbot2.commands.*;
import maxdistructo.droidbot2.core.Config;
import maxdistructo.droidbot2.core.Perms;
import maxdistructo.droidbot2.core.Roles;
import sx.blah.discord.api.events.*;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelJoinEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.*;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelLeaveEvent;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.*;
import static maxdistructo.droidbot2.BaseBot.client;
@Deprecated //Unused due to removal of backup system. May be reimplemented at a later date as a bot lockdown.
public class BackupListener {
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

            String backupMessage = "The bot is currently doing a backup. Thank you for waiting for this daily backup to be complete.";

            if (!Roles.checkForBotAbuse(message)) {
                if (messageContent[0].equals(prefix + "bj")) { //WIP
                    message.reply(backupMessage);
                } else if (messageContent[0].toString().toLowerCase().equals("hit") && Perms.checkGames(message) || messageContent[0].toString().toLowerCase().equals("stay") && Perms.checkGames(message)) {
                    message.reply(backupMessage);
                } else if (messageContent[0].equals(prefix + "check")) { //Works
                    message.reply(backupMessage);
                } else if (messageContent[0].equals(prefix + "casino") && messageContent[1].equals("info") && mentioned != null && Perms.checkGames(message)) { //Works except for admin commands
                    message.reply(backupMessage);
                } else if (messageContent[0].equals(prefix + "casino") && messageContent[1].equals("info") && Perms.checkGames(message)) { //Works except for admin commands
                    message.reply(backupMessage);
                } else if (messageContent[0].equals(prefix + "casino") && Perms.checkGames(message)) { //Works except for admin commands
                    message.reply(backupMessage);
                } else if (messageContent[0].equals(prefix + "50") || messageContent[0].equals(prefix + "fifty") && Perms.checkGames(message)) { //Works
                    message.reply(backupMessage);
                } else if (messageContent[0].equals(prefix + "fortune")) { //Works
                    message.reply("", Message.simpleEmbed(message.getAuthor(), "Fortune", Fortune.onFortuneCommand(messageContent, message), message));
                    message.delete();
                }
                //else if(messageContent[0].equals(prefix + "game")){ //Broke
                //  message.reply(GameCommand.onGameCommand(messageContent, message));
                // }
                else if (messageContent[0].equals(prefix + "info")) { //Works Well
                    message.reply(backupMessage);
                } else if (messageContent[0].equals(prefix + "insult")) { //Works
                    Message.sendMessage(message.getChannel(),  Message.simpleEmbed(message.getAuthor(), "Insult", Insult.onInsultCommand(messageContent, message, mentioned), message));
                    message.delete();
                } else if (messageContent[0].equals(prefix + "debug")) { //Needs perms set.
                    message.reply(backupMessage);
                } else if (messageContent[0].equals(prefix + "shutdown")) { //Works
                    message.reply(backupMessage);
                    // } else if (messageContent[0].equals(prefix + "help")) {
                    //   Message.sendDM(message.getAuthor(), Help.onHelpCommand());
                    // message.delete();
                } else if (messageContent[0].equals(prefix + "allin") && Perms.checkGames(message)) {
                    message.reply(backupMessage);
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
                } else if (messageContent[0].equals(prefix + "mute")) {
                    Message.sendMessage(message.getChannel(), PlayerFun.onMuteCommand(message, mentioned));
                    message.delete();
                    // } else if (messageContent[0].equals(prefix + "lenny") || messageContent[0].equals("/lenny")) {
                    //     message.edit(PlayerFun.onLennyCommand());
                    // } else if (messageContent[0].equals("/shrug")) { Won't work cause F U Discord.
                    //      message.edit(PlayerFun.onShrugCommand());
                } else if (messageContent[0].equals(prefix + "xp")) {
                    Message.sendMessage(message.getChannel(), PlayerFun.onXpCommand(mentioned));
                    message.delete();
                } else if (messageContent[0].equals(prefix + "punch")) {
                    Message.sendMessage(message.getChannel(), PlayerFun.onPunchCommand(message, mentioned));
                    message.delete();
                }



                //  else if(messageContent[0].equals(prefix + "trivia")) {
                //          message.reply(Trivia.onTriviaCommand(messageContent, message));
                //   }
                // else if(messageContent[0].equals(triviaAnswer)){
                //       Trivia.addTriviaScore(event.getMessage());
                //        Trivia.checkTrivia(event.getMessage());
                // }
            } else if (content.contains(prefix)) {
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
        if (guild.getVoiceChannelByID(323617654436921346L).equals(channel) || guild.getVoiceChannelByID(318506455433084929L).equals(channel)) {
            IRole role = guild.getRoleByID(327940488483307530L);
            user.addRole(role);
        }
    }

    @EventSubscriber
    public void onVoiceChannelLeave(UserVoiceChannelLeaveEvent event) throws RateLimitException, DiscordException, MissingPermissionsException {
        IVoiceChannel channel = event.getVoiceChannel();
        IUser user = event.getUser();
        IGuild guild = channel.getGuild();
        if (guild.getVoiceChannelByID(323617654436921346L).equals(channel) || guild.getVoiceChannelByID(318506455433084929L).equals(channel)) {
            IRole role = guild.getRoleByID(327940488483307530L);
            user.removeRole(role);
        }
    }

 /*   @EventSubscriber
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
            Message.sendMessage(guild.getGeneralChannel(), name + " has been loaded. Version: " + BaseBot.version);

            if (client.getGuildByID(314569556809220118L) == guild) {
                List<IUser> paydayUsers = guild.getUsersByRole(guild.getRoleByID((330353751116480512L)));
                Object[] paydayArray = paydayUsers.toArray();
                int ii = 0;
                while (ii < paydayArray.length) {
                    IUser user = (IUser) paydayArray[ii];
                    user.removeRole(guild.getRoleByID(330353751116480512L));
                    ii++;
                }
            }
            i++;
        }


    }*/
}
