package maxdistructo.droidbot2.commands.mafia;

import maxdistructo.droidbot2.core.Roles;
import maxdistructo.droidbot2.core.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;

import java.util.List;

public class MafiaConfig {
    public static long getAdminChannel(IMessage message) {
        JSONObject root = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + "_dat.txt");
        return root.getLong("adminChannel");
    }
    public static long getDayChat(IMessage message) {
        JSONObject root = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + "_dat.txt");
        return root.getLong("dayChat");
    }

    public static long[] getPlayers(IMessage message) {
        JSONObject root1 = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + "_players.txt");
        JSONArray jsonArray = root1.getJSONArray("players");
        long[] strArray = new long[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            strArray[i] = jsonArray.getLong(i);
        }
        return strArray;
    }
    public static long[] getPlayers(IMessage message, String role){
        List<IUser> usersList = message.getGuild().getUsersByRole(Roles.getRole(message, role));
        long[] players = new long[usersList.size()];
        int i = 0;
        for(IUser user : usersList){
            players[i] = user.getLongID();
            i++;
        }
        return players;
    }

    public static long getMafiaChannel(IMessage message) {
        JSONObject root1 = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + "_dat.txt");
        return root1.getLong("mafia_chat");
    }

    public static boolean getDayStatus(IMessage message) {
        JSONObject root1 = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + "_dat.txt");
        return root1.getBoolean("day");
    }

    public static Object[] getPlayerDetails(IMessage message) {
        JSONObject root1 = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + "_dat.txt");
        JSONObject root = root1.getJSONObject("" + message.getAuthor().getLongID());
        return new Object[] {root.getString("alignment"), root.getString("class"), root.getString("role"), root.getBoolean("dead"), root.getInt("attack"), root.getInt("defence")};
    }

    public static long getMafiaChat(IMessage message) {
        return getMafiaChannel(message);
    }

    public static long getMediumChat(IMessage message) {
        JSONObject root1 = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + "_dat.txt");
        return root1.getLong("medium_chat");
    }

    public static long getSpyChat(IMessage message) {
        JSONObject root1 = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + "_dat.txt");
        return root1.getLong("spy_chat");
    }

    public static long getDeadChat(IMessage message) {
        JSONObject root1 = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + "_dat.txt");
        return root1.getLong("dead_chat");
    }
}
