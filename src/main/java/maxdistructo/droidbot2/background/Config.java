package maxdistructo.droidbot2.background;

import java.io.*;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import maxdistructo.droidbot2.BaseBot;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

public class Config{
    public static String token;
    public static String PLAYER = "null";
    public static int CHIPS;
    public static String MEMBERSHIP;
    public static int PAYDAY;
    public static int ALLIN;
    public static String[] trivia;
    public static boolean ISMOD = false;
    public static boolean ISADMIN = false;
    public static boolean ISOWNER = false;
    public static boolean ISGAME = false;

    public static void newCasino(IMessage message){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        IUser user = message.getAuthor();
        String stringUser = user.getName();
        File f = new File(s+"/droidbot/config/" + message.getGuild().getLongID() +"/casino/"+ user.getLongID() + ".txt");
        f.getParentFile().mkdirs(); //Create Directories for File
        try {
            f.createNewFile(); //Create file
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject newUser = new JSONObject();
        newUser.put("User",stringUser);
        newUser.put("Chips",100);
        newUser.put("Membership","null");
        newUser.put("Payday",0);
        newUser.put("Allin",0);

        try (FileWriter file = new FileWriter(s+"/droidbot/config/" + message.getGuild().getLongID() +"/casino/"+ user.getLongID() + ".txt")) {
            file.write(newUser.toString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + newUser);
        } catch (IOException e) {
            BaseBot.LOGGER.warning("Config.newCasino Error.");
            e.printStackTrace();
        }

    }
    public static void readCasino(IMessage message){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        IUser user = message.getAuthor();
        String stringUser = user.getName();

        File file = new File (s+"/droidbot/config/" + message.getGuild().getLongID() +"/casino/"+ user.getLongID() + ".txt");
        URI uri = file.toURI();
        JSONTokener tokener = null;
        try {
            tokener = new JSONTokener(uri.toURL().openStream());
            System.out.println("Successfully read file " + stringUser + ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject root = new JSONObject(tokener);
        System.out.println("Converted JSON file to JSONObject");
        PLAYER = root.getString("User");
        CHIPS = root.getInt("Chips");
        MEMBERSHIP = root.getString("Membership");
        PAYDAY = root.getInt("Payday");
        ALLIN = root.getInt("Allin");
        System.out.println("Successfully read values from file.");

    }

    public static void readCasino(IUser user, IGuild guild){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        String stringUser = user.getName();

        File file = new File (s+"/droidbot/config/" + guild.getLongID() +"/casino/"+ user.getLongID() + ".txt");
        URI uri = file.toURI();
        JSONTokener tokener = null;
        try {
            tokener = new JSONTokener(uri.toURL().openStream());
            System.out.println("Successfully read file " + stringUser + ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject root = new JSONObject(tokener);
        System.out.println("Converted JSON file to JSONObject");
        PLAYER = root.getString("User");
        CHIPS = root.getInt("Chips");
        MEMBERSHIP = root.getString("Membership");
        PAYDAY = root.getInt("Payday");
        ALLIN = root.getInt("Allin");
        System.out.println("Successfully read values from file.");

    }
    public static void writeCasino(IMessage message){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        IUser user = message.getAuthor();
        JSONObject newUser = new JSONObject();
        newUser.put("User", PLAYER);
        newUser.put("Chips", CHIPS);
        newUser.put("Membership",MEMBERSHIP);
        newUser.put("Payday",PAYDAY);
        newUser.put("Allin",ALLIN);

        try (FileWriter file = new FileWriter(s+"/droidbot/config/" + message.getGuild().getLongID() +"/casino/"+ user.getLongID() + ".txt")) {
            file.write(newUser.toString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + newUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeCasino(IUser user, IGuild guild){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        JSONObject newUser = new JSONObject();
        newUser.put("User", PLAYER);
        newUser.put("Chips", CHIPS);
        newUser.put("Membership",MEMBERSHIP);
        newUser.put("Payday",PAYDAY);
        newUser.put("Allin",ALLIN);

        try (FileWriter file = new FileWriter(s+"/droidbot/config/" + guild.getLongID() +"/casino/"+ user.getLongID() + ".txt")) {
            file.write(newUser.toString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + newUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  public static void triviaReadLine(String file, int line){
      Path currentRelativePath = Paths.get("");
      String s = currentRelativePath.toAbsolutePath().toString();
        File fileVar = new File(s + "/droidbot/config/trivia/" + file + ".txt");
      try{
          // Open the file that is the first
          // command line parameter
          FileInputStream fstream = new FileInputStream(fileVar);
          // Get the object of DataInputStream
          DataInputStream in = new DataInputStream(fstream);
          BufferedReader br = new BufferedReader(new InputStreamReader(in));
          String strLine;
          int readLine = 1;
          //Read File Line By Line
          while ((strLine = br.readLine()) != null && readLine == line)   {
              trivia = strLine.split("`");
          }
          in.close();
      }catch (Exception e){//Catch exception if any
          System.err.println("Error: " + e.getMessage());
      }
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
           e.printStackTrace();
       }
       JSONObject root = new JSONObject(tokener);
       System.out.println("Converted JSON file to JSONObject");
       return root.getString("Token");

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
            System.out.println("Successfully read file "+ guild.getLongID() + ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONObject(tokener);
    }


}
