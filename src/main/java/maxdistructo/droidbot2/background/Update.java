package maxdistructo.droidbot2.background;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;

import org.apache.commons.io.FileUtils;
import org.json.*;

public class Update {
    public static void checkUpdate(){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        File file = new File (s + "/droidbot/VERSION.properties");
        URI uri = file.toURI();
        JSONTokener tokener = null;
        try {
            tokener = new JSONTokener(uri.toURL().openStream());
            System.out.println("Successfully read file VERSION.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject root = new JSONObject(tokener);
        System.out.println("Converted JSON file to JSONObject");
        String currentVersion = root.getString("version");
        URL url = null;
        if(root.getString("releaseChannel").equals("NONE")){

        }
        try {
            url = new URL("https://maxdistructo.github.io/droidbot2/downloads/latest/" + root.getString("releaseChannel") + "/" + "VERSION.json");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            FileUtils.copyURLToFile(url, new File(s+ "/droidbot/VERSION.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file2 = new File (s + "/droidbot/VERSION.json");
        URI uri2 = file2.toURI();
        JSONTokener tokener2 = null;
        try {
            tokener2 = new JSONTokener(uri2.toURL().openStream());
            System.out.println("Successfully read file VERSION.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject root2 = new JSONObject(tokener2);
        System.out.println("Converted JSON file to JSONObject");
        String latestVersion = root2.getString("version");

        if(latestVersion.equals(currentVersion)){

        }
        else{
            try {
                FileUtils.copyURLToFile(new URL(s + "https://maxdistructo.github.io/droidbot2/downloads/updater/latest/droidbot2Updater.jar"), new File(s + "droidbot2Updater.jar"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                Process ps = Runtime.getRuntime().exec(new String[] {"java", "-jar", "droidbot2Updater.jar "});
                System.exit(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
