package maxdistructo.droidbot2.background;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import sx.blah.discord.handle.obj.IUser;

public class Config{
    public static String token;
    public static String PLAYER = "null";
    public static int CHIPS;
    public static String MEMBERSHIP;
    public static int PAYDAY;
    public static int ALLIN;
    public static boolean ISMOD = false;
    public static boolean ISADMIN = false;
    public static boolean ISOWNER = false;
    public static boolean ISGAME = false;
    public static String SPLIT = ",";

    public static void newCasino(IUser user){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        String stringUser = user.getName();
        File f = new File(s+"/droidbot/config/casino/"+ stringUser + ".txt");
        f.getParentFile().mkdirs();
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writeString(s+"/droidbot/config/casino/"+ stringUser + ".txt", 0, stringUser + SPLIT); //Value of User
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writeString(s+"/droidbot/config/casino/"+ stringUser + ".txt", 1, "100" + SPLIT); //Base Chips
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writeString(s+"/droidbot/config/casino/"+ stringUser + ".txt",2,"null"+ SPLIT); //Membership
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writeString(s+"/droidbot/config/casino/"+ stringUser + ".txt", 3, "0"+ SPLIT); //Payday Cooldown
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writeString(s+"/droidbot/config/casino/"+ stringUser + ".txt", 4, "0"); //All In Cooldown
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void readCasino(IUser user){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        String stringUser = user.getName();
        String line = reader(s +"/droidbot/config/casino/"+ stringUser + ".txt", 1);
        Object[] lineArray = line.split(",");
        lineArray[0] = PLAYER;
        lineArray[1] = CHIPS;
        lineArray[2] = MEMBERSHIP;
        lineArray[3] = PAYDAY;
        lineArray[4] = ALLIN;

    }
    public static void writeCasino(IUser user){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        String stringUser = user.getName();
        try {
            writeInt(s+"/droidbot/config/casino/"+ stringUser + ".txt", 2, CHIPS); //Base Chips
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writeString(s+"/droidbot/config/casino/"+ stringUser + ".txt", 3, MEMBERSHIP); //Membership
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writeInt(s+"/droidbot/config/casino/"+ stringUser + ".txt", 4, PAYDAY); //Payday Cooldown
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writeInt(s+"/droidbot/config/casino/"+ stringUser + ".txt", 5, ALLIN); //All In Cooldown
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String reader(String file, int line){
       return "ERROR 404: Reader not found";
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


}
