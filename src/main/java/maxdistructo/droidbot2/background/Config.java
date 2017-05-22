package maxdistructo.droidbot2.background;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config{
    public static String token;
    public static long PLAYER = 0;
    public static int CHIPS;
    public static String MEMBERSHIP;
    public static int PAYDAY;
    public static int ALLIN;
    public static boolean ISMOD = false;
    public static boolean ISADMIN = false;
    public static boolean ISOWNER = false;
    public static boolean ISGAME = false;

    public static void newCasino(long user){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        File f = new File(s+"/droidbot/config/casino/"+ user + ".txt");
        f.getParentFile().mkdirs();
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeLong(s+"/droidbot/config/casino/"+ user + ".txt", 1, user); //Value of User
        writeInt(s+"/droidbot/config/casino/"+ user + ".txt", 2, 100); //Base Chips
        writeString(s+"/droidbot/config/casino/"+ user + ".txt",3,"null"); //Membership
        writeInt(s+"/droidbot/config/casino/"+ user + ".txt", 4, 0); //Payday Cooldown
        writeInt(s+"/droidbot/config/casino/"+ user + ".txt", 5, 0); //All In Cooldown
    }
    public static void readCasino(long user){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        reader(s +"/droidbot/config/casino/"+ user + ".txt", 1, PLAYER);
        reader(s +"/droidbot/config/casino/"+ user + ".txt", 2, CHIPS);
        reader(s +"/droidbot/config/casino/"+ user + ".txt", 3, MEMBERSHIP);
        reader(s +"/droidbot/config/casino/"+ user + ".txt", 4, PAYDAY);
        reader(s +"/droidbot/config/casino/"+ user + ".txt", 5, ALLIN);
    }
    public static void writeCasino(long user){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        writeInt(s+"/droidbot/config/casino/"+ user + ".txt", 2, CHIPS); //Base Chips
        writeString(s+"/droidbot/config/casino/"+ user + ".txt", 3, MEMBERSHIP); //Membership
        writeInt(s+"/droidbot/config/casino/"+ user + ".txt", 4, PAYDAY); //Payday Cooldown
        writeInt(s+"/droidbot/config/casino/"+ user + ".txt", 5, ALLIN); //All In Cooldown
    }

    public static void reader(String file, int line, Object var){
        FileInputStream fs = null;
        BufferedReader br;
        int cntr = 0;
        try {
            fs = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        br = new BufferedReader(new InputStreamReader(fs)); //Initialize BufferedReader
        while(cntr < line){ //Changes the line that the program is reading to the one I want to read
            try{
                br.readLine();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            cntr++;
        }
        try {
            var = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public static void writeInt(String file, int line, int var){
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
            ra.write(var);
       
        } catch (IOException e) {
            e.printStackTrace();
        } 

    }   
    public static void writeString(String file, int line, String var){
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
            ra.writeUTF(var);
       
        } catch (IOException e) {
            e.printStackTrace();
        } 

    }   

}
