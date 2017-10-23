package maxdistructo.droidbot2.background;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Utils {

    public static void restartApplication()
    {
        final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        File currentJar = null;
        try {
            currentJar = new File(Utils.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

  /* is it a jar file? */
        if(!currentJar.getName().endsWith(".jar"))
            return;

  /* Build command: java -jar application.jar */
        final ArrayList<String> command = new ArrayList<String>();
        command.add(javaBin);
        command.add("-jar");
        command.add(currentJar.getPath());

        final ProcessBuilder builder = new ProcessBuilder(command);
        try {
            builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public static String makeNewString(Object[] input, int startAt){
        StringBuilder stringBuilder = new StringBuilder();
        int i = startAt;
        while (i < input.length) {
            if(i + 1 > input.length) {
                stringBuilder.append(input[i]);
            }
            else{
                stringBuilder.append(input[i]);
                stringBuilder.append("");
            }
            i++;
        }
        return stringBuilder.toString();
    }

}
