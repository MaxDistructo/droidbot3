package maxdistructo.droidbot2.commands;


import maxdistructo.droidbot2.background.Config;
import maxdistructo.droidbot2.background.Roles;
import maxdistructo.droidbot2.background.message.Message;
import org.json.JSONArray;
import org.json.JSONObject;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.Image;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.EnumSet;
import java.util.List;

import static maxdistructo.droidbot2.BaseBot.client;

public class Admin {
    public static String addMod(IMessage message, IUser mentioned){
        if(message.getAuthor() == message.getGuild().getOwner() || message.getAuthor().getLongID() == 228111371965956099L){
               JSONObject root = Config.readServerConfig(message.getGuild());
               JSONArray list = root.getJSONArray("Moderators");
               list.put(mentioned.getLongID());
               root.put("Moderators", list);
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            try (FileWriter file = new FileWriter(s + "/droidbot/config/" + message.getGuild().getLongID() + ".txt")) {
                file.write(root.toString());
                System.out.println("Successfully Copied JSON Object to File...");
                System.out.println("\nJSON Object: " + root);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Sucessfully added " + mentioned.getDisplayName(message.getGuild()) + " to the Moderator list.";
        }
        return "Command Error";
    }
    public static String addAdmin(IMessage message, IUser mentioned){
        if(message.getAuthor() == message.getGuild().getOwner() || message.getAuthor().getLongID() == 228111371965956099L){
            JSONObject root = Config.readServerConfig(message.getGuild());
            JSONArray list = new JSONArray();
            list.put(mentioned.getLongID());
            root.append("Admins", list);
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            try (FileWriter file = new FileWriter(s + "/droidbot/config/" + message.getGuild().getLongID() + ".txt")) {
                file.write(root.toString());
                System.out.println("Successfully Copied JSON Object to File...");
                System.out.println("\nJSON Object: " + root);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Sucessfully added " + mentioned.getDisplayName(message.getGuild()) + " to the Admin list.";
        }
        return "Command Error";
    }
    public static String addCasinoBalance(Object[] args, IMessage message, IUser mentioned){ // !@casino balance add @user <numofchips>
        Config.readCasino(mentioned,message.getGuild());
        Config.CHIPS = Config.CHIPS + Config.converToInt(args[4]);
        Config.writeCasino(mentioned,message.getGuild());
        NumberFormat nf = NumberFormat.getInstance();
        return "Successfully added " + nf.format(Config.converToInt(args[4])) + " chips to " + mentioned.getDisplayName(message.getGuild()) + "'s casino balance.";
    }
    public static String subtractCasinoBalance(Object[] args, IMessage message, IUser mentioned){ // !@casino balance add @user <numofchips>
        Config.readCasino(mentioned,message.getGuild());
        Config.CHIPS = Config.CHIPS - Config.converToInt(args[4]);
        Config.writeCasino(mentioned,message.getGuild());
        NumberFormat nf = NumberFormat.getInstance();
        return "Successfully removed " + nf.format(Config.converToInt(args[4])) + " chips from " + mentioned.getDisplayName(message.getGuild()) + "'s casino balance.";
    }
    public static String setCasinoBalance(Object[] args, IMessage message, IUser mentioned){ // !@casino balance add @user <numofchips>
        Config.readCasino(mentioned,message.getGuild());
        Config.CHIPS = Config.converToInt(args[4]);
        Config.writeCasino(mentioned,message.getGuild());
        NumberFormat nf = NumberFormat.getInstance();
        return "Successfully set " + mentioned.getDisplayName(message.getGuild()) + "'s casino balance to " + nf.format(Config.converToInt(args[4]));
    }
    public static String setBotAbuser(Object[] args, IMessage message, IUser mentioned){ //!@admin botabuse <@User> days reason
        Roles.applyBotAbuser(message,mentioned);
        Message.sendDM(mentioned,"You have been banned from using " + client.getOurUser().mention(true) + " because of " + args[4]);
        try {
            Thread.sleep(86400000 * Config.converToInt(args[3]));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Roles.removeBotAbuser(message,mentioned);
        return mentioned.mention(true) + " you have been released from your bot abuse punishment.";
    }
    public static String setNickname(Object[] args){
        String makeNewString = "";
        System.out.println("Begin making new string.");
        int i = 2;
        while (i < args.length) {
            makeNewString = makeNewString + " " + args[i];
            i++;
        }
        System.out.println("End making new string.");
        client.changeUsername(makeNewString);
        return "Name successfully set to :" + makeNewString;
    }
    public static String setProfilePic(Object[] args){
        client.changeAvatar(Image.forUrl((String)args[2],(String)args[3]));
        return "Changed Profile Picture Sucessfully.";
    }
    public static String leaveGuild(Object[] args){// !admin leaveGuild <GuildLongID>
        IGuild guild = client.getGuildByID(Config.convertToLong(args[2]));
        guild.leave();
        return "Sucessfully left \"" + guild.getName() + "\"";
    }
    public static String changeRolePerm(IMessage message, Object[] args){ // !admin perms <Permission> <Role>
        String formated = "";
        int i = 3;
        while (i < args.length){
            if(i == 3){
                formated = formated + args[i];
            }
            else{
                formated = formated + " " + args[i];
            }

            i++;
        }
        Roles.changeRolePerm(message, (String) args[2], formated);
        return "Sucessfully edited role perms.";

    }
    public static void permFix(IMessage message){
        IGuild guild = message.getGuild();
        IUser guildOwner = guild.getOwner();
        String name = client.getApplicationName();
        guild.leave();
        Message.sendDM(guildOwner, "The permissions of " + name + " have been broke in someway. Your guild's data that is stored on the bot has not been affected. Please use the following link to re-add " + name + " to your server. https://discordapp.com/oauth2/authorize?client_id=315313967759097857&scope=bot&permissions=1010035777");
        Message.sendDM(client.getApplicationOwner(), client.getApplicationName() + " was removed from guild " + guild + " in the process of a guild perm reset.");
    }
    public static void muteUser(IMessage message, IUser mentioned, int time){
        IGuild guild = message.getGuild();
        List<IChannel> channels = guild.getChannels();
        Object[] channelArray = channels.toArray();
        int i = 0;
        while(i < channelArray.length) {
            IChannel channel = (IChannel) channelArray[i];
            channel.overrideUserPermissions(mentioned, EnumSet.of(Permissions.READ_MESSAGE_HISTORY, Permissions.READ_MESSAGES), EnumSet.of(Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES));
            i++;
        }
        i = 0;
        Message.sendMessage(message.getChannel(),Message.simpleEmbed(message.getAuthor(), "Mute", mentioned.getDisplayName(message.getGuild()) + " has been muted for " + time + " minutes", message));
        try {
            Thread.sleep(60000 * time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(i < channelArray.length) {
            IChannel channel = (IChannel) channelArray[i];
            channel.removePermissionsOverride(mentioned);
            i++;
        }

    }
    public static void unmuteUser(IMessage message, IUser mentioned){
        IGuild guild = message.getGuild();
        List<IChannel> channels = guild.getChannels();
        Object[] channelArray = channels.toArray();
        int i = 0;
        while(i < channelArray.length) {
            IChannel channel = (IChannel) channelArray[i];
            channel.removePermissionsOverride(mentioned);
            i++;
        }
        Message.sendMessage(message.getChannel(),Message.simpleEmbed(message.getAuthor(), "Mute", mentioned.getDisplayName(message.getGuild()) + " has been unmuted", message));
    }
    public static void unmuteUser(IMessage message, IUser mentioned, IChannel channel){
        channel.removePermissionsOverride(mentioned);
        Message.sendMessage(message.getChannel(),Message.simpleEmbed(message.getAuthor(), "Mute", mentioned.getDisplayName(message.getGuild()) + " has been unmuted", message));
    }



}
