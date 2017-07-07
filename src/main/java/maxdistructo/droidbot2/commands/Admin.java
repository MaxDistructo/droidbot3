package maxdistructo.droidbot2.commands;


import maxdistructo.droidbot2.BaseBot;
import maxdistructo.droidbot2.background.Config;
import maxdistructo.droidbot2.background.Roles;
import maxdistructo.droidbot2.background.message.Message;
import org.json.JSONArray;
import org.json.JSONObject;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;

public class Admin {
    public static String addMod(IMessage message, IUser mentioned){
        if(message.getAuthor() == message.getGuild().getOwner() || message.getAuthor().getLongID() == 228111371965956099L){
               JSONObject root = Config.readServerConfig(message.getGuild());
               JSONArray list = new JSONArray();
               list.put(mentioned.getLongID());
               root.append("Moderators", list);
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
        Message.sendDM(mentioned,"You have been banned from using " + BaseBot.client.getOurUser().mention(true) + " because of " + args[4]);
        try {
            Thread.sleep(86400000 * Config.converToInt(args[3]));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Roles.removeBotAbuser(message,mentioned);
        return mentioned.mention(true) + " you have been released from your bot abuse punishment.";
    }


}