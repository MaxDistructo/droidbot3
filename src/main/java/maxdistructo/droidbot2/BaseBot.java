package maxdistructo.droidbot2;

import com.google.common.util.concurrent.FutureCallback;
import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import de.btobastian.sdcf4j.CommandHandler;
import de.btobastian.sdcf4j.handler.JavacordHandler;
import maxdistructo.droidbot2.commands.Debug;

public class BaseBot {
    public static String PREFIX;
    public static void main(String[] args){
        String token = "MzE1MzEzOTY3NzU5MDk3ODU3.DANVQw.jygOczrZr40m7OXfBv4b1kYQdPI";
        DiscordAPI api = Javacord.getApi(token, true);
        api.connect(new FutureCallback<DiscordAPI>() {
            public void onSuccess(final DiscordAPI api) {
                CommandHandler handler = new JavacordHandler(api);
                handler.registerCommand(new Debug());
            }
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

    }

}
