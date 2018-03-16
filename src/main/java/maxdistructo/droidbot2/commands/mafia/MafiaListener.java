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
    public void onMessageReceivedEvent(MessageReceivedEvent event) {
        IMessage message = event.getMessage();
        Object[] messageContent = message.getContent().split(" ");
        if (message.getGuild().getLongID() == 249615705517981706L || message.getGuild().getLongID() == 268370862661435392L) {
            if (!MafiaConfig.getDayStatus(message) && message.getChannel() == message.getGuild().getChannelByID(MafiaConfig.getDeadChat(message)) && !message.getAuthor().isBot()) { //Dead to Medium
                Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getMediumChat(message)), message.getAuthor().getDisplayName(message.getGuild()) + ": " + message.getContent());
            }
            if (!MafiaConfig.getDayStatus(message) && message.getChannel() == message.getGuild().getChannelByID(MafiaConfig.getMediumChat(message)) && !message.getAuthor().isBot()) { //Medium to Dead
                Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getDeadChat(message)), "Medium:" + message.getContent());
            }
            if (message.getChannel() == message.getGuild().getChannelByID(MafiaConfig.getMafiaChat(message))) { //Mafia to Spy
                Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getSpyChat(message)), "Mafia: " + message.getContent());
            }
            if (message.getChannel() == message.getGuild().getChannelByID(MafiaConfig.getJailorChat(message))) { //Jailor to Jailed
                Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getJailedChat(message)), "Jailor: " + message.getContent());
            }
            if (message.getChannel() == message.getGuild().getChannelByID(MafiaConfig.getJailedChat(message))) { //Jailed to Jailor
                Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getJailorChat(message)), message.getAuthor().getDisplayName(message.getGuild()) + message.getContent());
            }
            if (messageContent[0].equals(prefix + "mafia")) { //This is all this listener will handle so putting this requirement for the rest of the code to execute.
                if (messageContent[1].equals("start") && Perms.checkMod(message)) {
                    Mafia.onGameStart(message);
                    message.delete();
                } else if (messageContent[1].equals("continue")) {
                    Mafia.onGameToggle(message);
                    message.delete();
                } else if (messageContent[1].equals("join")) {
                    Mafia.onGameJoinCommand(message);
                    message.delete();
                } else if (messageContent[1].equals("leave")) {
                    Mafia.onGameLeaveCommand(message);
                    message.delete();
                } else if (messageContent[1].equals("pm")) {
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getSpyChat(message)), Utils.makeNewString(messageContent, 3)); //Send PMs to Spy
                    Message.sendDM(Utils.getMentionedUser(message), Utils.makeNewString(messageContent, 3) + "\n To reply, use /mafia pm " + message.getAuthor().mention() + " in the mafia commands channel."); //Send PM to desired recipient
                    message.delete();
                } else if (messageContent[1].equals("shuffle")) {
                    message.reply(Mafia.assignRoles(message).toString());
                    message.delete();
                } else if (messageContent[1].equals("akill") && Perms.checkMod(message)) { //Deaths List: Admin, Mafia, Serial Killer, Werewolf, Arsonist, Jester, Veteran, Vigilante, Jailor
                    Mafia.killPlayer(message, Utils.getMentionedUser(message).getLongID());
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " has committed suicide. **__" + "\n" + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).getLongID())[2]);
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was killed by " + message.getAuthor().getDisplayName(message.getGuild()));
                    message.delete();
                } else if (messageContent[1].equals("mkill") && Perms.checkMod(message)) {
                    Mafia.killPlayer(message, Utils.getMentionedUser(message).getLongID());
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was killed by the Mafia**__" + "\n" + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).getLongID())[2]);
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was killed by the Mafia. Authorizing Member: " + message.getAuthor().getDisplayName(message.getGuild()));
                    message.delete();
                } else if (messageContent[1].equals("skkill") && Perms.checkMod(message)) {
                    Mafia.killPlayer(message, Utils.getMentionedUser(message).getLongID());
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was stabbed by a Serial Killer**__" + "\n" + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).getLongID())[2]);
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was Stabbed by a Serial Killer. Authorizing Member: " + message.getAuthor().getDisplayName(message.getGuild()));
                    message.delete();
                } else if (messageContent[1].equals("wwkill") && Perms.checkMod(message)) {
                    Mafia.killPlayer(message, Utils.getMentionedUser(message).getLongID());
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was attacked by a Werewolf.**__" + "\n" + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).getLongID())[2]);
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was attacked by a Werewolf. Authorizing Member: " + message.getAuthor().getDisplayName(message.getGuild()));
                    message.delete();
                } else if (messageContent[1].equals("arsokill") && Perms.checkMod(message)) {
                    Mafia.killPlayer(message, Utils.getMentionedUser(message).getLongID());
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was burnt to a crisp by an Arsonist**__" + "\n" + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).getLongID())[2]);
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was burnt to a crisp by an Arsonist. Authorizing Member: " + message.getAuthor().getDisplayName(message.getGuild()));
                    message.delete();
                } else if (messageContent[1].equals("jestkill") && Perms.checkMod(message)) {
                    Mafia.killPlayer(message, Utils.getMentionedUser(message).getLongID());
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was haunted by the Jester they linched.**__" + "\n" + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).getLongID())[2]);
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was haunted by the Jester they linched. Authorizing Member: " + message.getAuthor().getDisplayName(message.getGuild()));
                    message.delete();
                } else if (messageContent[1].equals("vetkill") && Perms.checkMod(message)) {
                    Mafia.killPlayer(message, Utils.getMentionedUser(message).getLongID());
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was shot by the Veteran they visited.**__" + "\n" + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).getLongID())[2]);
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was shot by a Veteran. Authorizing Member: " + message.getAuthor().getDisplayName(message.getGuild()));
                    message.delete();
                } else if (messageContent[1].equals("vigkill") && Perms.checkMod(message)) {
                    Mafia.killPlayer(message, Utils.getMentionedUser(message).getLongID());
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was shot by a Vigilante**__" + "\n" + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).getLongID())[2]);
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was shot by a Vigilante. Authorizing Member: " + message.getAuthor().getDisplayName(message.getGuild()));
                    message.delete();
                } else if (messageContent[1].equals("jailkill") && Perms.checkMod(message)) {
                    Mafia.killPlayer(message, Utils.getMentionedUser(message).getLongID());
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getDayChat(message)), "__**" + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was killed by the Jailor**__ " + "\n" + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was a " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).getLongID())[2]);
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " was killed by the Jailor. Authorizing Member: " + message.getAuthor().getDisplayName(message.getGuild()));
                    message.delete();
                } else if (messageContent[1].equals("mkill") && MafiaConfig.getPlayerDetails(message)[0].toString().equals("mafia")) {
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), "The mafia would like to kill " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()));
                    Message.sendMessage(message.getChannel(), "You have decided to kill " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()));
                    message.delete();
                } else if (messageContent[1].equals("skkill") && MafiaConfig.getPlayerDetails(message)[2].toString().equals("serial_killer")) {
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), "A serial killer has decided to stab " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()));
                    Message.sendDM(message.getAuthor(), "You have decided to kill " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()));
                    message.delete();
                } else if (messageContent[1].equals("wwkill") && MafiaConfig.getPlayerDetails(message)[2].toString().equals("werewolf")) {
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), "The werewolf has decided to visit " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()));
                    Message.sendDM(message.getAuthor(), "You have decided to go pay " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " a visit tonight");
                    message.delete();
                } else if (messageContent[1].equals("arsokill") && MafiaConfig.getPlayerDetails(message)[2].toString().equals("arsonist")) {
                    if (Utils.getMentionedUser(message) != message.getAuthor()) {
                        Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), "The arsonist is gonna douse " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()));
                        Message.sendDM(message.getAuthor(), "You have decided to douse " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()));
                    } else {
                        Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), "The arsonist is gonna set all doused players on fire");
                        Message.sendDM(message.getAuthor(), "You have decided to set all targets on fire tonight");
                    }
                    message.delete();
                } else if (messageContent[1].equals("jestkill") && MafiaConfig.getPlayerDetails(message)[2].toString().equals("jester")) {
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), "The jester is gonna haunt " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()));
                    Message.sendDM(message.getAuthor(), "You have decided to haunt " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()));
                    message.delete();
                } else if (messageContent[1].equals("vetkill") && MafiaConfig.getPlayerDetails(message)[2].toString().equals("veteran")) {
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), "The Veteran " + message.getAuthor() + " is going on alert tonight.");
                    Message.sendDM(message.getAuthor(), "You have decided to go on alert tonight");
                    message.delete();
                } else if (messageContent[1].equals("vigkill") && MafiaConfig.getPlayerDetails(message)[2].toString().equals("vigilante")) {
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), "The Vigilante is going to shoot " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()));
                    Message.sendDM(message.getAuthor(), "You have decided to shoot " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()));
                    message.delete();
                } else if (messageContent[1].equals("jailkill") && MafiaConfig.getPlayerDetails(message)[2].toString().equals("jailor")) {
                    if (Utils.getMentionedUser(message).getLongID() == MafiaConfig.getJailed(message)){
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), "The jailor is going to shoot " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()));
                    Message.sendMessage(message.getChannel(), "You have decided to shoot " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()));
                    }
                    else{
                        Message.sendDM(message.getAuthor(), "You can only shoot the person you have jailed!");
                    }
                    message.delete();
                }
                else if(messageContent[1].equals("jail")){
                    if(Utils.getMentionedUser(message) == message.getAuthor()){
                        Message.sendDM(message.getAuthor(), "You can not jail yourself!");
                    }
                    else{
                        Mafia.jailPlayer(message, Utils.getMentionedUser(message));
                        Message.sendDM(message.getAuthor(), "You have successfully jailed " + Utils.getMentionedUser(message));
                        Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), "The jailor has jailed " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()));
                    }
                }
                else if(messageContent[1].equals("setrole") && Perms.checkAdmin(message)){ // /mafia setrole @user roleName
                    Mafia.setRole(message, Utils.getMentionedUser(message), messageContent[3].toString());
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), "The role of " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " has been set to " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).getLongID())[2]);
                }

            }

        }
    }

}
