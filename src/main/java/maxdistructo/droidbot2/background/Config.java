package maxdistructo.droidbot2.background;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config{
    public static String token;
    public static long PLAYER;
    public static double CHIPS;
    public static String MEMBERSHIP;
    public static int PAYDAY;
    public static int ALLIN;

    public static void newCasino(long user){
        JSONObject obj = new JSONObject();
        obj.put("name", user);
        obj.put("chips", 100);
        obj.put("membership",null);
        obj.put("payday",0);
        obj.put("allin", 0);

        try (FileWriter file = new FileWriter("/config/"+ user +".json")) {
            file.write(obj.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + obj);
        }
        catch(IOException e){
            System.out.println(e);
        }


    }
    public static void readCasino(long user){
            JSONParser parser = new JSONParser();
        try {
            JSONArray a = (JSONArray) parser.parse(new FileReader("/config/" + user + ".json"));
            for (Object o : a)
            {
                JSONObject player = (JSONObject) o;

                PLAYER = (long)player.get("name");
                CHIPS = (double)player.get("chips");
                MEMBERSHIP = (String)player.get("membership");
                PAYDAY = (int)player.get("payday");
                ALLIN = (int)player.get("allin");
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
