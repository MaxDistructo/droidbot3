package maxdistructo.droidbot2.commands.mafia;

import maxdistructo.droidbot2.core.Perms;
import maxdistructo.droidbot2.core.Utils;
import maxdistructo.droidbot2.core.message.Message;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.*;

import static maxdistructo.droidbot2.core.Client.prefix;

public class MafiaListener {

    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event){
        IMessage message = event.getMessage();
        Object[] messageContent = message.getContent().split(" ");
        if(!MafiaConfig.getDayStatus(message) && message.getChannel() == message.getGuild().getChannelByID(MafiaConfig.getDeadChat(message)) && !message.getAuthor().isBot() ){ //Dead to Medium
           Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getMediumChat(message)), message.getAuthor().getDisplayName(message.getGuild()) + ": " + message.getContent());
        }
        if(!MafiaConfig.getDayStatus(message) && message.getChannel() == message.getGuild().getChannelByID(MafiaConfig.getMediumChat(message)) && !message.getAuthor().isBot()){ //Medium to Dead
            Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getDeadChat(message)), "Medium:" + message.getContent());
        }
        if(message.getChannel() == message.getGuild().getChannelByID(MafiaConfig.getMafiaChat(message))){ //Mafia to Spy
            Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getSpyChat(message)), "Mafia: " + message.getContent());
        }
        if(messageContent[0].equals(prefix + "mafia") && message.getGuild().getLongID() == 249615705517981706L){ //This is all this listener will handle so putting this requirement for the rest of the code to execute.
            if(messageContent[1].equals("start") && Perms.checkMod(message)){
                Mafia.onGameStart(message);
                message.delete();
            }
            else if(messageContent[1].equals("continue")){
                Mafia.onGameToggle(message);
                message.delete();
            }
            else if(messageContent[1].equals("join")){
                Mafia.onGameJoinCommand(message);
                message.delete();
            }
            else if(messageContent[1].equals("leave")){
                Mafia.onGameLeaveCommand(message);
                message.delete();
            }
            else if(messageContent[1].equals("pm")){
                Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getSpyChat(message)), Utils.makeNewString(messageContent, 3)); //Send PMs to Spy
                Message.sendDM(Utils.getMentionedUser(message), Utils.makeNewString(messageContent, 3) + "\n To reply, use /mafia pm " + message.getAuthor().mention() + " in the mafia commands channel."); //Send PM to desired recipient
            }
        }
    }

}
