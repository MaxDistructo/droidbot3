package maxdistructo.droidbot2.background;

import java.io.*;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import maxdistructo.droidbot2.BaseBot;
import org.json.JSONObject;
import org.json.JSONTokener;
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

    public static void newCasino(IUser user){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        String stringUser = user.getName();
        File f = new File(s+"/droidbot/config/casino/"+ stringUser + ".txt");
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

        try (FileWriter file = new FileWriter(s + "/droidbot/config/casino/" + stringUser + ".txt")) {
            file.write(newUser.toString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + newUser);
        } catch (IOException e) {
            BaseBot.LOGGER.warning("Config.newCasino Error.");
            e.printStackTrace();
        }

    }
    public static void readCasino(IUser user){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        String stringUser = user.getName();

        File file = new File (s + "/droidbot/config/casino/" + stringUser + ".txt");
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
    public static void writeCasino(IUser user){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        String stringUser = user.getName();
        JSONObject newUser = new JSONObject();
        newUser.put("User", PLAYER);
        newUser.put("Chips", CHIPS);
        newUser.put("Membership",MEMBERSHIP);
        newUser.put("Payday",PAYDAY);
        newUser.put("Allin",ALLIN);

        try (FileWriter file = new FileWriter(s + "/droidbot/config/casino/" + stringUser + ".txt")) {
            file.write(newUser.toString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + newUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String reader(String file, int line){
        String output = null;
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(file);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((output = bufferedReader.readLine()) != null) {
                return output;
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            file + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + file + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
        return "null,5,null,0,0";
    }

    public static void writeLong(String file, int line, long var){
        RandomAccessFile ra = null;
        try {
            ra = new RandomAccessFile(file,"rw" );
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }// (file name, mode of file)
        try {
            ra.seek(line);
        } catch (IOException e1) {
            e1.printStackTrace();
        }// set the poss to overwrite
        try {
            ra.writeLong(var);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void writeInt(String file, int line, int var) throws IOException {
        FileWriter fw = new FileWriter(file,true);

        for (int i = 0; i < 10; i++) {
            if(i == line) {
                fw.write(var);
            }
        }

        fw.close();

    }
    public static void writeString(String file, int line, String var) throws IOException{
        FileWriter fw = new FileWriter(file,true);

        for (int i = 0; i < 10; i++) {
            if(i == line) {
                fw.write(var);
            }
        }

        fw.close();
    }

  public static void readLine(String file, int line){
        File fileVar = new File(file);
      Scanner scanner = null;
      try {
          scanner = new Scanner(fileVar);
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      }
      while(scanner.hasNextLine()) {
          String theLine = scanner.nextLine();
          trivia = theLine.split("`");
    }
   }
   public static IUser convertToIUser(Object in){
      Object out = in.toString().replace('"',' ');
       return(IUser)out;
   }
   public static int converToInt(Object in){
       return Integer.valueOf(in.toString());
   }


}
