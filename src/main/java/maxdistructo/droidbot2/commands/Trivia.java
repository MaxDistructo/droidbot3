package maxdistructo.droidbot2.commands;

import maxdistructo.droidbot2.BaseBot;
import maxdistructo.droidbot2.core.Config;
import maxdistructo.droidbot2.core.message.Message;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import sx.blah.discord.handle.obj.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Trivia{

    private static int question = 1;
    private static String list;
    private static long[] players;
    private static int[] scores;
	private static String gameFile = s + "/droidbot/trivia/" + guild.getLongID() + "/game.txt";

    public static void onTriviaCommand(Object[] args, IMessage message) {
        JSONArray players = new JSONArray();
        players.put(0, message.getAuthor().getLongID());
        JSONArray scores = new JSONArray();
        scores.put(0,0);
        writeTrivia(players,scores,(String)args[1], 1, message.getGuild());
        String questionAnswer = triviaReader((String)args[1], 1);
        String[] questionAnswerArray = questionAnswer.split("`");
        sendQuestion(message,questionAnswerArray[0], question);
    }
    private static void sendQuestion(IMessage message, String question, int questionNum){
        Message.sendMessage(message.getChannel(), "Question #" + questionNum + "\n\n" + question);
    }
}

private class Config{
	
	    private static void writeTrivia(JSONArray players, JSONArray scores, String list, int question, IGuild guild){
        JSONObject root = new JSONObject();
        root.put("list", list);
        root.put("players", players);
        root.put("scores",scores);
        root.put("question",question);

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

        try (FileWriter file = new FileWriter(s+"/droidbot/trivia/"+ guild.getLongID() +"/game.txt")) {
            file.write(root.toString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + root);
        } catch (IOException e) {
            Message.sendDM(BaseBot.client.getApplicationOwner(), e.toString());
            e.printStackTrace();
        }

    }
    private static void readTrivia(IGuild guild){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        File file = new File (s+"/droidbot/trivia/"+ guild.getLongID() + "game.txt");
        URI uri = file.toURI();
        JSONTokener tokener = null;
        try {
            tokener = new JSONTokener(uri.toURL().openStream());
            System.out.println("Successfully read file game.txt");
        } catch (IOException e) {
            Message.sendDM(BaseBot.client.getApplicationOwner(), e.toString());
            e.printStackTrace();
        }
        JSONObject root = new JSONObject(tokener);

        list = root.getString("list");
        question = root.getInt("question");
        JSONArray array = root.getJSONArray("");


    }
	    private static String triviaReader(String list, int line, IGuild guild){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        try {
            List<String> triviaList = Config.readFileAsList(new File(s + "/droidbot/trivia/" + list + ".txt"));
            return triviaList.get(line);
        }
        catch(Exception e){
            Message.sendDM(BaseBot.client.getApplicationOwner(), e.getLocalizedMessage());
            e.printStackTrace();
        }
        return "Is this command broken?`YES";

    }
}
