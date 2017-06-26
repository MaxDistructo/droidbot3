package maxdistructo.droidbot2.commands;

import de.btobastian.sdcf4j.CommandExecutor;
import maxdistructo.droidbot2.background.Config;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import maxdistructo.droidbot2.background.PlayerList;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Trivia implements CommandExecutor{
    private static String[] triviaLists = {"general"};
    private static String[] trivia;
    private static String[] players = new String[20];
    private static int[] score = new int[20];
    private static int question = 0;
    private static int answer = 1;
    private static String listenAnswer;
    private static String triviaList;
    private static int line = 1;
    private static Path currentRelativePath = Paths.get("");
    private static String s = currentRelativePath.toAbsolutePath().toString();
    private static String dir = s + "/droidbot/config/trivia/";
    private static PlayerList list;
   // @Command(aliases = {"/trivia"}, description = "Trivia Game", usage = "/trivia <listname>|join")
    public static String onTriviaCommand(Object[] args, IMessage message){
        IUser author = message.getAuthor();
        if(args.length == 2 && args[0].equals("start")) {
            int i = 0;
            while (i < triviaLists.length - 1) {
                if (args[2].equals(triviaLists[i])) {
                    triviaList = triviaLists[i];
                    startTrivia(triviaLists[i], message);

                    return "Started Trivia " + triviaList;
                }
                else {

                }
                i++;

            }
        }
        else if(args[0].equals("join")){
            PlayerList.addToList(list, author.getName());
        }
        return ".";
    }
    private static void startTrivia(String triviaList, IMessage message){
        Config.readLine(dir + triviaList + ".txt", 1);
        trivia = Config.trivia;
        try {
            message.reply(trivia[question]);
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
        listenAnswer = trivia[answer];
    }
    private static void nextQuestion(String triviaList, IMessage message){
        Config.readLine(dir + list + ".txt", 1);
        trivia = Config.trivia;
        try {
            message.reply(trivia[question]);
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
        listenAnswer = trivia[answer];
    }
    private static void triviaEndCheck(IMessage message){
        int i = 0;
        while (i < score.length - 1){
            if(score[i] == 10){
                try {
                    message.reply(triviaEnd(players[i]));
                } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                    e.printStackTrace();
                }
            }
            else{
                nextQuestion(triviaList, message);

            }
        }
    }
    private static String triviaEnd(String player){
        return player + " has won!";
    }
}
