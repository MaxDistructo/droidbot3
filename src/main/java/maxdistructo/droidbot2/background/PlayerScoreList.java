package maxdistructo.droidbot2.background;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import java.util.List;

public class PlayerScoreList {
    int[] scoreList; //Will Line Up with PlayerList slots.
    String[] players;
    int lastPlayer;
    String triviaList;

public PlayerScoreList(){
    this.scoreList = new int[1];
    this.players = new String[1];
    this.lastPlayer = 0;
    this.triviaList = "general";
}
public PlayerScoreList(String list, IGuild guild){
    List<IUser> userList = guild.getUsers();
    Object[] users = userList.toArray();
    this.scoreList = new int[users.length];
    this.players = new String[users.length];
    this.lastPlayer = 0;
    this.triviaList = list;
}
public static int getScoreForPlayer(PlayerScoreList p, String player){
int j = getPlayerNumber(p, player);
return p.scoreList[j];
}
public static void setScoreForPlayer(PlayerScoreList p, String player, int score){
    int j = getPlayerNumber(p, player);
    p.scoreList[j] = score;
}
public static void setTriviaList(PlayerScoreList p, String list){
    p.triviaList = list;
}
public static String getTriviaList(PlayerScoreList p){
    return p.triviaList;
}
public static void addPlayerToList(PlayerScoreList p, String player){
    p.players[p.lastPlayer] = player;
    p.lastPlayer++;
}
private static int getPlayerNumber(PlayerScoreList p, String player){
    int i = 0;
    int j = 0;
    while(i < p.players.length){
        if(p.players[i].equals(player)){
            j = i;
            i = p.players.length;
        }
    }
    return j;
}
public static String[] getPlayerArray(PlayerScoreList p){
   return p.players;
}
}
