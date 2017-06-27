package maxdistructo.droidbot2.commands;

import de.btobastian.sdcf4j.CommandExecutor;
import maxdistructo.droidbot2.background.Config;
import maxdistructo.droidbot2.background.Listener;
import maxdistructo.droidbot2.background.PlayerScoreList;
import maxdistructo.droidbot2.background.message.Message;
import sx.blah.discord.handle.obj.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Trivia implements CommandExecutor{
    private static int question = 0;
    public static PlayerScoreList list;
   // @Command(aliases = {"/trivia"}, description = "Trivia Game", usage = "/trivia <listname>|join")
    public static String onTriviaCommand(Object[] args, IMessage message){
        if(args[1].equals("start")){
            list = new PlayerScoreList((String)args[2], message.getGuild());
            PlayerScoreList.setTriviaList(list, (String)args[2]);
            triviaStart(message);
            return "Started trivia game.";
       }
       else if(args[1].equals("join")&& args.length == 2){
           triviaJoin(message);
       }
       return "Command Error: Trivia";
    }
    private static void triviaJoin(IMessage message){
        PlayerScoreList.addPlayerToList(list, message.getAuthor().getDisplayName(message.getGuild()));
    }
    private static void triviaStart(IMessage message){
        triviaJoin(message);
        question++;
        readTrivia(list, question);
        triviaQuestion(message);
    }
    public static void checkTrivia(IMessage message){
        int i = 0;
        while (i < PlayerScoreList.getPlayerArray(list).length){
        if(PlayerScoreList.getScoreForPlayer(list, PlayerScoreList.getPlayerArray(list)[i]) > 10){
            endTrivia(PlayerScoreList.getPlayerArray(list)[i], message);
        }
        else{
            triviaQuestion(message);
        }
        }
    }
    private static void triviaQuestion(IMessage message){
        question++;
        readTrivia(list, question);
        Message.sendMessage(message.getChannel(),Config.trivia[0]);
        Listener.triviaAnswer = Config.trivia[1];
    }
    private static void readTrivia(PlayerScoreList p, int questionNum){
        Config.triviaReadLine(PlayerScoreList.getTriviaList(p), questionNum);
    }
    private static void endTrivia(String player, IMessage message){
        Message.sendMessage(message.getChannel(),player + " has won the game!");
    }
    public static void addTriviaScore(IMessage message){
        int playerscore = PlayerScoreList.getScoreForPlayer(list, message.getAuthor().getDisplayName(message.getGuild()));
        playerscore++;
        PlayerScoreList.setScoreForPlayer(list, message.getAuthor().getDisplayName(message.getGuild()), playerscore);
    }
}
