package maxdistructo.droidbot2;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import maxdistructo.droidbot2.background.*;
import maxdistructo.droidbot2.background.Config;
import maxdistructo.droidbot2.background.audio.AudioMain;
import sx.blah.discord.api.IDiscordClient;

public class BaseBot {
    public static IDiscordClient client;
    public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public final static String version = "2.0.4";
    public final static Listener listener = new Listener();
    public final static BackupListener bl = new BackupListener();
    public final static String changeLog = "";
    private final static Path currentRelativePath = Paths.get("");
    public final static String s = currentRelativePath.toAbsolutePath().toString();

    public static void main(String[] args){
        Update.checkUpdate();
        LOGGER.setLevel(Level.INFO);
        String token = Config.readToken();
        client = Client.createClient(token);
        LOGGER.info("Client Created");
        client.getDispatcher().registerListener(new AudioMain());
        LOGGER.info("Registered Audio Commands ");
        client.getDispatcher().registerListener(listener);
        LOGGER.info("Registered Listener");
      /*  do {
            try {
                Thread.sleep(86400000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                ZIPBackup.startBackup();


        }
        while (client.isLoggedIn()); */






    }

}
