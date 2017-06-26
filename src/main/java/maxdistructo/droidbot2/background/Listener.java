package maxdistructo.droidbot2.background;

import com.fasterxml.jackson.databind.ser.Serializers;
import maxdistructo.droidbot2.BaseBot;
import maxdistructo.droidbot2.background.message.Message;
import maxdistructo.droidbot2.commands.*;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.api.internal.DiscordClientImpl;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelJoinEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelLeaveEvent;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.MessageBuilder;

import java.util.List;

public class Listener {
    public static boolean blackJackRunning = false;
    public static String blackjackAnswer;
    public static String prefix = "d@"; //Change back to ! for release!

    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event) { // This method is NOT called because it doesn't have the @EventSubscriber annotation
        IMessage message = event.getMessage();
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
        if (Perms.checkOwner(message)) { //REMOVE THIS FOR RELEASE!!
            if (messageContent[0].equals(prefix + "bj")) { //broken
                message.reply(BlackJack.onBlackjackCommand(messageContent, message));
            }
            else if(messageContent[0].equals("hit") || messageContent[0].equals("stay") || messageContent[0].equals("double") && blackJackRunning){
                blackjackAnswer = (String)messageContent[0];
            }
            else if (messageContent[0].equals(prefix + "check")) { //Works
                Message.sendMessage(message.getChannel(), Check.onCheckCommand(messageContent, message));
            } else if (messageContent[0].equals(prefix + "casino")) { //Works except for admin commands
                message.reply(Casino.onCasinoCommand(messageContent, message, mentioned));
            } else if (messageContent[0].equals(prefix + "50") || messageContent[0].equals(prefix + "fifty")) { //Works
                message.reply(FiftyFifty.onFiftyCommand(messageContent, message));
            } else if (messageContent[0].equals(prefix + "fortune")) { //Works
                message.reply(Fortune.onFortuneCommand(messageContent, message));
            }
            //    else if(messageContent[0].equals(prefix + "game")){ //Broke
            //      message.reply(GameCommand.onGameCommand(messageContent, message));
            //}
            else if (messageContent[0].equals(prefix + "info")) { //Not Working ATM
                Message.sendMessage(message.getChannel(), Info.onInfoCommand(messageContent, message, mentioned));
            } else if (messageContent[0].equals(prefix + "insult")) { //Works
                Message.sendMessage(message.getChannel(), Insult.onInsultCommand(messageContent, message, mentioned));
            } else if (messageContent[0].equals(prefix + "debug")) { //Needs perms set.
                message.reply(Debug.onDebugCommand((String[]) messageContent, message));
            } else if (messageContent[0].equals(prefix + "shutdown")) { //Works
                message.reply(Shutdown.onShutdownCommand(message));
            } else if (messageContent[0].equals(prefix + "help")) {
                Message.sendMessage(message.getChannel(), Help.onHelpCommand());
            } else if (messageContent[0].equals(prefix + "allin")) {
                message.reply(Allin.onAllinCommand(messageContent, message));
            } else if (messageContent[0].equals(prefix + "say")) {
                Message.sendMessage(message.getChannel(), Say.onSayCommand(messageContent, message));
            } else if (messageContent[0].equals(prefix + "spam")) {
                Message.sendMessage(message.getChannel(), Spam.onSpamCommand(messageContent, message, mentioned));
            }
            //  else if(messageContent[0].equals(prefix + "trivia")){
            //        message.reply(Trivia.onTriviaCommand(messageContent, message));
            //   }

        } else if (message.getContent().contains("d@")) {
            message.reply("Command Error: User has attempted to run a WIP command.");
        }
    }


    @EventSubscriber
    public void onVoiceChannelJoin(UserVoiceChannelJoinEvent event){
        IVoiceChannel channel = event.getVoiceChannel();
        IUser user = event.getUser();
        IGuild guild = channel.getGuild();
        if(guild.getVoiceChannelByID(323617654436921346L).equals(channel) || guild.getVoiceChannelByID(318506455433084929L).equals(channel)){
            IRole role = guild.getRoleByID(327940488483307530L);
            user.addRole(role);
        }
    }
    @EventSubscriber
    public void onVoiceChannelLeave(UserVoiceChannelLeaveEvent event){
        IVoiceChannel channel = event.getVoiceChannel();
        IUser user = event.getUser();
        IGuild guild = channel.getGuild();
        if(guild.getVoiceChannelByID(323617654436921346L).equals(channel) || guild.getVoiceChannelByID(318506455433084929L).equals(channel)){
            IRole role = guild.getRoleByID(327940488483307530L);
            user.removeRole(role);
        }
    }


}
