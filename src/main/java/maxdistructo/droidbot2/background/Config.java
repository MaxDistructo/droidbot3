package maxdistructo.droidbot2.background;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

public class Config{
    public static String token;
    public static long PLAYER;
    public static double CHIPS;
    public static String MEMBERSHIP;
    public static int PAYDAY;
    public static int ALLIN;

    public static void newCasino(long user){
        JSONObject obj = new JSONObject();
        obj.append("name", user);
        obj.append("chips", 100);
        obj.append("membership",null);
        obj.append("payday",0);
        obj.append("allin", 0);

        try (FileWriter file = new FileWriter("/config/"+ user +".json")) {
            file.write(obj.toString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + obj);
        }
        catch(IOException e){
            System.out.println(e);
        }


    }
    public static void readCasino(long user){
        File jsonStr = new File("/config/"+user+".json");
        try {
            JSONObject rootObject = new JSONObject(jsonStr); // Parse the JSON to a JSONObject
            JSONArray rows = rootObject.getJSONArray("rows"); // Get all JSONArray rows

            for(int i=0; i < rows.length(); i++) { // Loop over each each row
                JSONObject row = rows.getJSONObject(i); // Get row object
                JSONArray elements = row.getJSONArray("elements"); // Get all elements for each row as an array

                for(int j=0; j < elements.length(); j++) { // Iterate each element in the elements array
                    JSONObject element =  elements.getJSONObject(j); // Get the element object
                    JSONObject duration = element.getJSONObject("duration"); // Get duration sub object
                    JSONObject distance = element.getJSONObject("distance"); // Get distance sub object

                    System.out.println("Duration: " + duration.getInt("value")); // Print int value
                    System.out.println("Distance: " + distance.getInt("value")); // Print int value
                }
            }
        } catch (JSONException e) {
            // JSON Parsing error
            e.printStackTrace();
        }
    }

}
