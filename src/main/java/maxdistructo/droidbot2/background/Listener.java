package maxdistructo.droidbot2.background;


import maxdistructo.droidbot2.BaseBot;
import maxdistructo.droidbot2.background.message.Message;
import maxdistructo.droidbot2.commands.*;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelJoinEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelLeaveEvent;
import sx.blah.discord.handle.impl.events.shard.ShardReadyEvent;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.List;

import static maxdistructo.droidbot2.BaseBot.client;

public class Listener {
    public static boolean blackJackRunning = false;
    public static String blackjackAnswer;
    public static String prefix = "@"; //Change back to ! for release!
    public static String triviaAnswer;

    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event) throws RateLimitException, DiscordException, MissingPermissionsException { // This method is NOT called because it doesn't have the @EventSubscriber annotation
        IMessage message = event.getMessage();
        List<IChannel> mentionedChannelList = message.getChannelMentions();
        Object[] mentionedChannelArray = mentionedChannelList.toArray();
        IChannel channelMention;
        if(mentionedChannelArray.length > 0){
            channelMention = (IChannel) mentionedChannelArray[0];
        }
        else{
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

        if(message.getGuild().getLongID() == 314569556809220118L) {
            //  if (messageContent[0].equals(prefix + "bj")) { //broken
            //      message.reply(BlackJack.onBlackjackCommand(messageContent, message));
            //  }
            //if(messageContent[0].equals("hit") || messageContent[0].equals("stay") || messageContent[0].equals("double") && blackJackRunning){
            //     blackjackAnswer = (String)messageContent[0];
            //  }
            if (messageContent[0].equals(prefix + "check")) { //Works
                Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "Check", Check.onCheckCommand(messageContent, message), message));
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
                Message.sendDM(message.getAuthor(), Help.onHelpCommand());
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
            }
            else if (messageContent[0].equals(prefix + "shoot")) {
                Message.sendMessage(message.getChannel(), PlayerFun.onShootCommand(message, mentioned));
                message.delete();
            }
            else if (messageContent[0].equals(prefix + "stab")) {
                Message.sendMessage(message.getChannel(), PlayerFun.onStabCommand(message, mentioned));
                message.delete();
            }
            else if (messageContent[0].equals(prefix + "mute")) {
                Message.sendMessage(message.getChannel(), PlayerFun.onMuteCommand(message, mentioned));
                message.delete();
            }
            //   else if(messageContent[0].equals(prefix + "trivia")) {
            //       message.reply(Trivia.onTriviaCommand(messageContent, message));
            //  }
            //  else if(messageContent[0].equals(triviaAnswer)){
            //      Trivia.addTriviaScore(event.getMessage());
            //      Trivia.checkTrivia(event.getMessage());
            //   }
        }
        else if(message.getGuild().getLongID() == 327954928624599041L){
            //  if (messageContent[0].equals(prefix + "bj")) { //broken
            //      message.reply(BlackJack.onBlackjackCommand(messageContent, message));
            //  }
            //if(messageContent[0].equals("hit") || messageContent[0].equals("stay") || messageContent[0].equals("double") && blackJackRunning){
            //     blackjackAnswer = (String)messageContent[0];
            //  }
            if (messageContent[0].equals(prefix + "check")) { //Works
                Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "Check", Check.onCheckCommand(messageContent, message), message));
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
                Message.sendDM(message.getAuthor(), Help.onHelpCommand());
                message.delete();
            } else if (messageContent[0].equals(prefix + "allin") && Perms.checkGames(message)) {
                message.reply("", Message.simpleEmbed(message.getAuthor(), "Allin", Allin.onAllinCommand(messageContent, message), message));
                message.delete();
            } else if (messageContent[0].equals(prefix + "say") && channelMention != null) {
                Message.sendMessage(channelMention, Say.onSayCommand(messageContent, message, channelMention));
            } else if (messageContent[0].equals(prefix + "say") && Perms.checkOwner(message)) {
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
            }
            else if (messageContent[0].equals(prefix + "shoot")) {
                Message.sendMessage(message.getChannel(), PlayerFun.onShootCommand(message, mentioned));
                message.delete();
            }
            else if (messageContent[0].equals(prefix + "stab")) {
                Message.sendMessage(message.getChannel(), PlayerFun.onStabCommand(message, mentioned));
                message.delete();
            }
            else if (messageContent[0].equals(prefix + "mute")) {
                Message.sendMessage(message.getChannel(), PlayerFun.onMuteCommand(message, mentioned));
                message.delete();
            }
            //   else if(messageContent[0].equals(prefix + "trivia")) {
            //       message.reply(Trivia.onTriviaCommand(messageContent, message));
            //  }
            //  else if(messageContent[0].equals(triviaAnswer)){
            //      Trivia.addTriviaScore(event.getMessage());
            //      Trivia.checkTrivia(event.getMessage());
            //   }
        }
    }


    @EventSubscriber
    public void onVoiceChannelJoin(UserVoiceChannelJoinEvent event) throws RateLimitException, DiscordException, MissingPermissionsException {
        IVoiceChannel channel = event.getVoiceChannel();
        IUser user = event.getUser();
        IGuild guild = channel.getGuild();
        if(guild.getVoiceChannelByID(323617654436921346L).equals(channel) || guild.getVoiceChannelByID(318506455433084929L).equals(channel)){
            IRole role = guild.getRoleByID(327940488483307530L);
            user.addRole(role);
        }
    }
    @EventSubscriber
    public void onVoiceChannelLeave(UserVoiceChannelLeaveEvent event) throws RateLimitException, DiscordException, MissingPermissionsException {
        IVoiceChannel channel = event.getVoiceChannel();
        IUser user = event.getUser();
        IGuild guild = channel.getGuild();
        if(guild.getVoiceChannelByID(323617654436921346L).equals(channel) || guild.getVoiceChannelByID(318506455433084929L).equals(channel)){
            IRole role = guild.getRoleByID(327940488483307530L);
            user.removeRole(role);
        }
    }
    @EventSubscriber
    public void onShardReadyEvent(ShardReadyEvent event){
        client.online(Listener.prefix + "help");
        BaseBot.LOGGER.info("Added playing content");
    }

}
