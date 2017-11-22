package maxdistructo.droidbot2.background;

import maxdistructo.droidbot2.BaseBot;
import maxdistructo.droidbot2.background.message.Message;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Config{

  @SuppressWarnings("resource") @Deprecated //Unused method cause trivia is on backburner for me.
public static String triviaReadLine(String file, int line){
      Scanner input = null;
      Path currentRelativePath = Paths.get("");
      String s = currentRelativePath.toAbsolutePath().toString();
      try {
          input = new Scanner(new FileReader(s + "/droidbot/config/trivia/" + file + ".txt"));
      } catch (FileNotFoundException e) {
          Message.sendDM(BaseBot.client.getApplicationOwner(), e.toString());
          e.printStackTrace();
      }
      int i = 0;
      while(input.hasNextLine()){
          if(i == line) {
              return input.next();
          }
          i++;
      }
      return "Is this bot broken?`yes";
  }
  @Deprecated //Use Utils.convertToInt instead. Method still exists for legacy support
   public static int converToInt(Object in){
       return Integer.valueOf(in.toString());
   }

   public static String readToken(){
       Path currentRelativePath = Paths.get("");
       String s = currentRelativePath.toAbsolutePath().toString();
       File file = new File (s + "/droidbot/config.txt");
       URI uri = file.toURI();
       JSONTokener tokener = null;
       try {
           tokener = new JSONTokener(uri.toURL().openStream());
           System.out.println("Successfully read file config.txt");
       } catch (IOException e) {
           //Message.sendDM(BaseBot.client.getApplicationOwner(), e.toString());
           e.printStackTrace();
       }
       JSONObject root = new JSONObject(tokener);
       System.out.println("Converted JSON file to JSONObject");
       return root.getString("Token");

   }
    public static String readPrefix(){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        File file = new File (s + "/droidbot/config.txt");
        URI uri = file.toURI();
        JSONTokener tokener = null;
        try {
            tokener = new JSONTokener(uri.toURL().openStream());
        } catch (IOException e) {
            Message.sendDM(BaseBot.client.getApplicationOwner(), e.toString());
            e.printStackTrace();
        }
        JSONObject root = new JSONObject(tokener);
        return root.getString("Prefix");

    }
    public static long[] readServerModConfig(IGuild guild){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        File file = new File (s + "/droidbot/config/" + guild.getLongID() + ".txt");
        URI uri = file.toURI();
        JSONTokener tokener = null;
        try {
            tokener = new JSONTokener(uri.toURL().openStream());
            System.out.println("Successfully read file "+ guild.getLongID() + ".txt");
        } catch (IOException e) {
            Message.sendDM(BaseBot.client.getApplicationOwner(), e.toString());
            e.printStackTrace();
        }
        JSONObject root = new JSONObject(tokener);
        System.out.println("Converted JSON file to JSONObject");
        JSONArray array = root.getJSONArray("Moderators");
        long[] longArray = new long[array.length()];
        System.out.println("Created Long Array");
        int i = 0;
        while(i < longArray.length){
            longArray[i] = array.getLong(i);
            i++;
        }
        System.out.println("Converted JSON array to long Array");
        return longArray;

    }
    public static long[] readServerAdminConfig(IGuild guild){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        File file = new File (s + "/droidbot/config/" + guild.getLongID() + ".txt");
        URI uri = file.toURI();
        JSONTokener tokener = null;
        try {
            tokener = new JSONTokener(uri.toURL().openStream());
            System.out.println("Successfully read file "+ guild.getLongID() + ".txt");
        } catch (IOException e) {
            Message.sendDM(BaseBot.client.getApplicationOwner(), e.toString());
            e.printStackTrace();
        }
        JSONObject root = new JSONObject(tokener);
        System.out.println("Converted JSON file to JSONObject");
        JSONArray array = root.getJSONArray("Admins");
        long[] longArray = new long[array.length()];
        int i = 0;
        while(i < array.length()){
            longArray[i] = array.getLong(i);
            i++;
        }
        return longArray;

    }
    public static String[] readServerGamesConfig(IGuild guild){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        File file = new File (s + "/droidbot/config/" + guild.getLongID() + ".txt");
        URI uri = file.toURI();
        JSONTokener tokener = null;
        try {
            tokener = new JSONTokener(uri.toURL().openStream());
            System.out.println("Successfully read file "+ guild.getLongID() + ".txt");
        } catch (IOException e) {
            Message.sendDM(BaseBot.client.getApplicationOwner(), e.toString());
            e.printStackTrace();
        }
        JSONObject root = new JSONObject(tokener);
        System.out.println("Converted JSON file to JSONObject");
        JSONArray array = root.getJSONArray("GameChannels");
        String[] longArray = new String[array.length()];
        int i = 0;
        while(i < array.length()){
            longArray[i] = array.getString(i);
            i++;
        }
        return longArray;

    }
    public static JSONObject readServerConfig(IGuild guild){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        File file = new File (s + "/droidbot/config/" + guild.getLongID() + ".txt");
        URI uri = file.toURI();
        JSONTokener tokener = null;
        try {
            tokener = new JSONTokener(uri.toURL().openStream());
        } catch (IOException e) {
            Message.sendDM(BaseBot.client.getApplicationOwner(), e.toString());
            e.printStackTrace();
        }
        return new JSONObject(tokener);
    }

    @Deprecated //Use Utils.convertToLong instead. This is here for legacy support.
    public static long convertToLong(Object o){
        return Long.valueOf(o.toString());
    }

    public static List<String> readFileAsList(File file){
        List<String> lines = null;
        try{
            lines = Files.readAllLines(Paths.get(file.toURI()));
        }
        catch(Exception e){
            Message.sendDM(BaseBot.client.getApplicationOwner(), e.getLocalizedMessage());
            e.printStackTrace();
        }
        return lines;
    }


}
