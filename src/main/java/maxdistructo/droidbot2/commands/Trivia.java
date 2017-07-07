package maxdistructo.droidbot2.commands;

import de.btobastian.sdcf4j.CommandExecutor;
import maxdistructo.droidbot2.background.Config;
import maxdistructo.droidbot2.background.Listener;
import maxdistructo.droidbot2.background.Perms;
import maxdistructo.droidbot2.background.PlayerScoreList;
import maxdistructo.droidbot2.background.message.Message;
import org.json.JSONArray;
import org.json.JSONObject;
import sx.blah.discord.handle.obj.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Trivia implements CommandExecutor{
    private static int question = 0;
    public static PlayerScoreList list;
   // @Command(aliases = {"/trivia"}, description = "Trivia Game", usage = "/trivia <listname>|join")
    public static String onTriviaCommand(Object[] args, IMessage message) {
        JSONArray players = new JSONArray();
        players.put(0, message.getAuthor().getLongID());
        JSONArray scores = new JSONArray();
        scores.put(0,0);
        writeTrivia(players,scores,(String)args[1]);

        return "Trivia Error";
    }
    private static String triviaReader(String list, int line){


        return "Is this command broken?`YES";

    }
    private static void writeTrivia(JSONArray players, JSONArray scores, String list){
        JSONObject root = new JSONObject();
        root.put("list", list);
        root.put("players", players);
        root.put("scores",scores);
    }
}
