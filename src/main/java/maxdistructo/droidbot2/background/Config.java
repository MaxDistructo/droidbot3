package maxdistructo.droidbot2.background;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import maxdistructo.droidbot2.BaseBot;
import maxdistructo.droidbot2.background.message.Message;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

public class Config{



  @SuppressWarnings("resource")
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
    public static int readBotAbuse(IGuild guild, IUser user){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        File file = new File (s + "/droidbot/config/" + guild.getLongID() + "/" + user.getLongID() + ".txt");
        URI uri = file.toURI();
        JSONTokener tokener = null;
        try {
            tokener = new JSONTokener(uri.toURL().openStream());
            System.out.println("Successfully read file "+ user.getLongID() + ".txt");
        } catch (IOException e) {
            Message.sendDM(BaseBot.client.getApplicationOwner(), e.toString());
            e.printStackTrace();
        }
        JSONObject root = new JSONObject(tokener);
        int i;
        try{
                i = root.getInt("BotAbuse");
        }
        catch(NullPointerException e){
            Message.sendDM(BaseBot.client.getApplicationOwner(), e.toString());
            writeBotAbuse(guild,user,1);
            i = 1;
        }
        return i;
    }
    public static void writeBotAbuse(IGuild guild, IUser user, int abuse){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        JSONObject newUser = new JSONObject();
        newUser.put("BotAbuse", abuse);
        try (FileWriter file = new FileWriter(s+"/droidbot/config/" + guild.getLongID() +"/"+ user.getLongID() + ".txt")) {
            file.write(newUser.toString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + newUser);
        } catch (IOException e) {
            Message.sendDM(BaseBot.client.getApplicationOwner(), e.toString());
            e.printStackTrace();
        }
    }
    public static void writeBlackjackFields(int playerScore, String playerHand, int dealerScore, String dealerHand,int bet, IMessage message){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        JSONObject root = new JSONObject();
        root.put("BJ_playerScore", playerScore);
        root.put("BJ_playerHand", playerHand);
        root.put("BJ_dealerScore",dealerScore);
        root.put("BJ_dealerHand",dealerHand);
        root.put("BJ_bet",bet);
        try (FileWriter fileWriter = new FileWriter(s + "/droidbot/config/" + message.getGuild().getLongID() + "/blackjack/" + message.getAuthor().getLongID() + ".txt")) {
            fileWriter.write(root.toString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + root);
        } catch (IOException e) {
            Message.sendDM(BaseBot.client.getApplicationOwner(), e.toString());
            e.printStackTrace();
        }

    }
    public static JSONObject readBJFields(IMessage message){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        IUser user = message.getAuthor();
        String stringUser = user.getName();

        File file = new File (s + "/droidbot/config/" + message.getGuild().getLongID() + "/blackjack/" + message.getAuthor().getLongID() +  ".txt");
        URI uri = file.toURI();
        JSONTokener tokener = null;
        try {
            tokener = new JSONTokener(uri.toURL().openStream());
            System.out.println("Successfully read file " + stringUser + ".txt");
        } catch (IOException e) {
            Message.sendDM(BaseBot.client.getApplicationOwner(), e.toString());
            e.printStackTrace();
        }
        return new JSONObject(tokener);
    }
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
