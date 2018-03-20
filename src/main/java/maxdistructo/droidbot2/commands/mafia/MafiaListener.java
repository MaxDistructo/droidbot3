package maxdistructo.droidbot2.commands.mafia;

import maxdistructo.droidbot2.core.Perms;
import maxdistructo.droidbot2.core.Utils;
import maxdistructo.droidbot2.core.message.Message;
import org.json.JSONObject;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.EmbedBuilder;

import java.time.Instant;
import java.util.List;

import static maxdistructo.droidbot2.core.Client.prefix;

public class MafiaListener {

    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event) {
        IMessage message = event.getMessage();
        Object[] messageContent = message.getContent().split(" ");
        if (message.getGuild().getLongID() == 249615705517981706L || message.getGuild().getLongID() == 268370862661435392L) {
                if(!Perms.checkMod(message)) {
                    if (!MafiaConfig.getDayStatus(message) && message.getChannel() == message.getGuild().getChannelByID(MafiaConfig.getDeadChat(message)) && !message.getAuthor().isBot()) { //Dead to Medium
                        Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getMediumChat(message)), message.getAuthor().getDisplayName(message.getGuild()) + ": " + message.getContent());
                    }
                    if (!MafiaConfig.getDayStatus(message) && message.getChannel() == message.getGuild().getChannelByID(MafiaConfig.getMediumChat(message)) && !message.getAuthor().isBot() && MafiaConfig.getJailed(message) != message.getAuthor().getLongID()) { //Medium to Dead
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
                }
            if (messageContent[0].equals(prefix + "mafia")) { //This is all this listener will handle so putting this requirement for the rest of the code to execute.
                if (messageContent[1].equals("start") && Perms.checkMod(message)) {
                    Mafia.onGameStart(message);
                    message.delete();
                } else if (messageContent[1].equals("continue") && Perms.checkMod(message)) {
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
                    //message.reply(Mafia.assignRoles(message).toString());
                    message.delete();
                }
                else if(messageContent[1].equals("getInfo") && Perms.checkMod(message)){
                    Object[] details = MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).getLongID());
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.withTitle("Player Info on " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()));
                    builder.withDesc("Alignment: " + details[0] + "\nClass: " + details[1] + "\nRole: " + details[2] + "\nIs Dead: " + details[3] + "\nAttack Power: " + details[4] + "\nDefence Power: " + details[5]);
                    builder.withColor(message.getAuthor().getColorForGuild(message.getGuild()));
                    builder.withAuthorName(message.getAuthor().getName() + "#" + message.getAuthor().getDiscriminator());
                    builder.withAuthorIcon(message.getAuthor().getAvatarURL());
                    builder.withTimestamp(Instant.now());
                    builder.withFooterIcon(message.getGuild().getIconURL());
                    builder.withFooterText(message.getGuild().getName());
                    Message.sendDM(message.getAuthor(), "Player Info on " + message.getAuthor().getDisplayName(message.getGuild()) +  "\nAlignment: " + details[0] + "\nClass: " + details[1] + "\nRole: " + details[2] + "\nIs Dead: " + details[3] + "\nAttack Power: " + details[4] + "\nDefence Power: " + details[5]);
                }
                else if(messageContent[1].equals("getInfo")){
                    Object[] details = MafiaConfig.getPlayerDetails(message);
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.withTitle("Player Info on " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()));
                    builder.withDesc("Alignment: " + details[0] + "\nClass: " + details[1] + "\nRole: " + details[2] + "\nIs Dead: " + details[3] + "\nAttack Power: " + details[4] + "\nDefence Power: " + details[5]);
                    builder.withColor(message.getAuthor().getColorForGuild(message.getGuild()));
                    builder.withAuthorName(message.getAuthor().getName() + "#" + message.getAuthor().getDiscriminator());
                    builder.withAuthorIcon(message.getAuthor().getAvatarURL());
                    builder.withTimestamp(Instant.now());
                    builder.withFooterIcon(message.getGuild().getIconURL());
                    builder.withFooterText(message.getGuild().getName());
                    Message.sendDM(message.getAuthor(),"Player Info on " + message.getAuthor().getDisplayName(message.getGuild()) +  "\nAlignment: " + details[0] + "\nClass: " + details[1] + "\nRole: " + details[2] + "\nIs Dead: " + details[3] + "\nAttack Power: " + details[4] + "\nDefence Power: " + details[5]);
                }
                else if(messageContent[1].equals("setrole") && Perms.checkAdmin(message)){ // /mafia setrole @user roleName
                    Mafia.setRole(message, Utils.getMentionedUser(message), messageContent[3].toString());
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), "The role of " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " has been set to " + MafiaConfig.getPlayerDetails(message, Utils.getMentionedUser(message).getLongID())[2]);
                    message.delete();
                }
                else if (messageContent[1].equals("akill") && Perms.checkMod(message)) { //Deaths List: Admin, Mafia, Serial Killer, Werewolf, Arsonist, Jester, Veteran, Vigilante, Jailor
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
                } //TODO add kills for VH and Vampire. Vampire kills Mafia if they try to bite them. VH kills Vampire on visit.
                else if(messageContent[1].equals("jail") && MafiaConfig.getPlayerDetails(message)[2].toString().equals("jailor")){
                    if(Utils.getMentionedUser(message) == message.getAuthor()){
                        Message.sendDM(message.getAuthor(), "You can not jail yourself!");
                    }
                    else{
                        Mafia.jailPlayer(message, Utils.getMentionedUser(message));
                        Message.sendDM(message.getAuthor(), "You have successfully jailed " + Utils.getMentionedUser(message));
                        Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), "The jailor has jailed " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()));
                    }
                    message.delete();
                }

                else if(messageContent[1].equals("watch") && MafiaConfig.getPlayerDetails(message)[2].toString().equals("lookout")){
                    Message.sendDM(message.getAuthor(), "You will be watching " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " tonight.");
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), message.getAuthor().getDisplayName(message.getGuild()) + " will be watching " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " tonight.");
                    message.delete();
                }

                else if(messageContent[1].equals("invest") && Perms.checkMod(message)){ // /mafia invest @target @user
                    List<IUser> mentionedList = message.getMentions();
                    JSONObject investResults1 = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + ".dat");
                    JSONObject investResults = investResults1.getJSONObject("invest_results");
                    Object[] targetDetails = MafiaConfig.getPlayerDetails(message, mentionedList.get(0).getLongID());
                    Message.sendDM(mentionedList.get(1), investResults.getString(targetDetails[2].toString()));
                    System.out.println("Send DM to " + mentionedList.get(1));
                    message.delete();
                }
                else if(messageContent[1].equals("invest") && MafiaConfig.getPlayerDetails(message)[2].toString().equals("investigator")){
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), message.getAuthor().getDisplayName(message.getGuild()) + " would like to investigate " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " tonight.");
                    message.delete();
                }
                else if(messageContent[1].equals("sheriff") && Perms.checkMod(message)){ // /mafia sheriff @target @user
                    List<IUser> mentionedList = message.getMentions();
                    IUser target = mentionedList.get(0);
                    IUser invest = mentionedList.get(1);
                    Object[] details = MafiaConfig.getPlayerDetails(message, target.getLongID());

                    if (details[0].equals("mafia") || (boolean)details[7]){
                        Message.sendDM(invest, "Your target is a Member of the Mafia!");
                    }
                    else if(details[2].equals("serial_killer")){
                        Message.sendDM(invest, "Your target is a Serial Killer!");
                    }
                    else if(details[2].equals("werewolf")){
                        Message.sendDM(invest, "Your target is a Werewolf!");
                    }
                    else{
                        Message.sendDM(invest, "Your target is not suspicious");
                    }
                    message.delete();
                }
                else if(messageContent[1].equals("sheriff") && MafiaConfig.getPlayerDetails(message)[2].toString().equals("sheriff")){
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), "The sheriff would like to interrogate " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " tonight.");
                    Message.sendDM(message.getAuthor(), "You are going to be interrogating " + Utils.getMentionedUser(message) + " tonight.");
                    message.delete();
                }
                else if(messageContent[1].equals("transport") && MafiaConfig.getPlayerDetails(message)[2].toString().equals("transporter")){
                    List<IUser> mentionedList = message.getMentions();
                    Object[] mentionedArray = mentionedList.toArray();
                    IUser target = mentionedList.get(0);
                    IUser invest = mentionedList.get(1);
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), message.getAuthor().getDisplayName(message.getGuild()) + " would like to swap positions of " + invest.getDisplayName(message.getGuild()) + " & " + target.getDisplayName(message.getGuild()));
                    Message.sendDM(message.getAuthor(), "You will be transporting " + invest.getDisplayName(message.getGuild()) + " & " + target.getDisplayName(message.getGuild()));
                    message.delete();
                }
                else if(messageContent[1].equals("witch") && MafiaConfig.getPlayerDetails(message)[2].toString().equals("witch")){
                    List<IUser> mentionedList = message.getMentions();
                    Object[] mentionedArray = mentionedList.toArray();
                    IUser target = mentionedList.get(0);
                    IUser invest = mentionedList.get(1);
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), message.getAuthor().getDisplayName(message.getGuild()) + " would like to control " + invest.getDisplayName(message.getGuild()) + " into using their ability onto " + target.getDisplayName(message.getGuild()));
                    Message.sendDM(message.getAuthor(), "You will be witching " + invest.getDisplayName(message.getGuild()) + " into " + target.getDisplayName(message.getGuild()));
                    message.delete();
                }
                else if(messageContent[1].equals("heal") && MafiaConfig.getPlayerDetails(message)[2].toString().equals("doctor")){
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), message.getAuthor().getDisplayName(message.getGuild()) + " will be healing " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " tonight.");
                    Message.sendDM(message.getAuthor(), "You will be healing " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " tonight.");
                    message.delete();
                }
                else if(messageContent[1].equals("guard")){
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), message.getAuthor().getDisplayName(message.getGuild()) + " will be guarding " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " tonight.");
                    Message.sendDM(message.getAuthor(),  "You will be guarding " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " tonight.");
                    message.delete();
                }
                else if(messageContent[1].equals("escort")){
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), message.getAuthor().getDisplayName(message.getGuild()) + " would like to roleblock " + Utils.getMentionedUser(message) + " tonight.");
                    Message.sendDM(message.getAuthor(), "You will be escorting " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " tonight.");
                    message.delete();
                }
                else if(messageContent[1].equals("reveal") && MafiaConfig.getDayStatus(message) && MafiaConfig.getPlayerDetails(message)[2].toString().equals("mayor")){
                    Message.sendMessage(message.getChannel(), message.getAuthor().getDisplayName(message.getGuild()) + " has revealed themselves as the Mayor!");
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), message.getAuthor().getDisplayName(message.getGuild()) + " has revealed as the mayor. Their votes now count as 3." );
                    message.delete();
                }
                else if(messageContent[1].equals("vote") && MafiaConfig.getDayStatus(message) && Utils.getMentionedUser(message) != message.getAuthor()){
                    Message.sendMessage(message.getChannel(), message.getAuthor() + " has voted for " + Utils.getMentionedUser(message));
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), message.getAuthor() + " has voted for " + Utils.getMentionedUser(message));
                    message.delete();
                }
                else if(messageContent[1].equals("secance") && !MafiaConfig.getDayStatus(message) && MafiaConfig.getPlayerDetails(message)[2].toString().equals("medium") && (boolean) MafiaConfig.getPlayerDetails(message)[3]){
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), "The medium would like to talk to " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()));
                    Message.sendDM(message.getAuthor(), "Your message has been sent to the Admin. Please wait for them to respond to your secance request");
                    message.delete();
                }
                else if(messageContent[1].equals("revive") && MafiaConfig.getPlayerDetails(message)[2].toString().equals("retributionist")){
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), "The retributionist will be reviving " + Utils.getMentionedUser(message) + " tonight.");
                    Message.sendDM(message.getAuthor(), "You will be reviving " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " tonight.");
                    message.delete();
                }
                else if(messageContent[1].equals("vampcheck")){
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), "The vampire hunter " + message.getAuthor().getDisplayName(message.getGuild()) + " will be checking if " + Utils.getMentionedUser(message) + " is a vampire tonight.");
                    Message.sendDM(message.getAuthor(), "You will be checking to see if " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + "is a Vampire.");
                    message.delete();
                }
                else if(messageContent[1].equals("disguise")){
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), "The Disguiser is going to be disguised as " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " in role.");
                    Message.sendDM(message.getAuthor(), "You will be disguising as " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()));
                    message.delete();
                }
                else if(messageContent[1].equals("forge")){
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), "The forger is gonna forge the role of " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()) + " to be Forger.");
                    Message.sendDM(message.getAuthor(), "You will be forging the role of " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()));
                    message.delete();
                }
                else if(messageContent[1].equals("frame")){
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), "The framer is gonna frame " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()));
                    Message.sendDM(message.getAuthor(), "You will be framing " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()));
                    message.delete();
                }
                else if(messageContent[1].equals("clean")){
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), message.getAuthor().getDisplayName(message.getGuild()) + " would like to clean the role of " + Utils.getMentionedUser(message));
                    Message.sendDM(message.getAuthor(), "You will be cleaning " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()));
                    message.delete();
                }
                else if(messageContent[1].equals("blackmail")){
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), message.getAuthor().getDisplayName(message.getGuild()) + "would like to Blackmail " + Utils.getMentionedUser(message).getDisplayName(message.getGuild()));
                    Message.sendDM(message.getAuthor(), "You will be blackmailing " + Utils.getMentionedUser(message));
                    message.delete();
                }
                else if(messageContent[1].equals("consig")){
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), message.getAuthor().getDisplayName(message.getGuild()) + " would like to know the role of " + Utils.getMentionedUser(message));
                    Message.sendDM(message.getAuthor(), "You will receive the role of " + Utils.getMentionedUser(message));
                    message.delete();
                }
                else if(messageContent[1].equals("remember")){
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), message.getAuthor().getDisplayName(message.getGuild()) + " would like to remember the role of " + Utils.getMentionedUser(message));
                    Message.sendDM(message.getAuthor(), "You will remember the role of " + Utils.getMentionedUser(message));
                    message.delete();
                }
                else if(messageContent[1].equals("vest")){
                    Message.sendMessage(message.getGuild().getChannelByID(MafiaConfig.getAdminChannel(message)), message.getAuthor().getDisplayName(message.getGuild()) + " will be putting on a vest tonight.");
                    Message.sendDM(message.getAuthor(), "You will be putting on a vest tonight.");
                }


            }

        }
    }

}
