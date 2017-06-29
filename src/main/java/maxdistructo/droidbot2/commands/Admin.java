package maxdistructo.droidbot2.commands;


import maxdistructo.droidbot2.background.Config;
import org.json.JSONArray;
import org.json.JSONObject;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
            return "Sucessfully added " + message.getAuthor().getDisplayName(message.getGuild()) + " to the Moderator list.";
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
            return "Sucessfully added " + message.getAuthor().getDisplayName(message.getGuild()) + " to the Admin list.";
        }
        return "Command Error";
    }
}
