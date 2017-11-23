package maxdistructo.droidbot2.core;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Utils {

    private final static Path currentRelativePath = Paths.get("");
    public final static String s = currentRelativePath.toAbsolutePath().toString();

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
            if(i - 1 == input.length) {
                stringBuilder.append(input[i]);
            }
            else{
                stringBuilder.append(input[i]);
                stringBuilder.append(" ");
            }
            i++;
        }
        return stringBuilder.toString();
    }

    public static long convertToLong(Object o){
        return Long.valueOf(o.toString());
    }

    public static int convertToInt(Object in){
        return Integer.valueOf(in.toString());
    }


}
