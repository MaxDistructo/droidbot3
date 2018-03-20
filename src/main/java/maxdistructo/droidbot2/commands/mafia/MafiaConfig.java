package maxdistructo.droidbot2.commands.mafia;

import maxdistructo.droidbot2.core.Roles;
import maxdistructo.droidbot2.core.Utils;
import maxdistructo.droidbot2.core.message.Message;
import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public static long[] getPlayers(IMessage message, String role) {
        List<IUser> usersList = message.getGuild().getUsersByRole(Roles.getRole(message, role));
        usersList.remove(message.getClient().getOurUser());
        long[] players = new long[usersList.size()];
        int i = 0;
        for (IUser user : usersList) {
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
        JSONObject root1 = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + "_playerdat.txt");
        JSONObject root = root1.getJSONObject("" + message.getAuthor().getLongID());
        return new Object[]{root.getString("alignment"), root.getString("class"), root.getString("role"), root.getBoolean("dead"), root.getInt("attack"), root.getInt("defense"), root.getBoolean("blocked"), root.getBoolean("framed"), root.getBoolean("jailed")};
    }

    public static Object[] getPlayerDetails(IMessage message, long playerID) {
        JSONObject root1 = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + "_playerdat.txt");
        JSONObject root = root1.getJSONObject("" + playerID);
        return new Object[]{root.getString("alignment"), root.getString("class"), root.getString("role"), root.getBoolean("dead"), root.getInt("attack"), root.getInt("defense"), root.getBoolean("blocked"), root.getBoolean("framed"), root.getBoolean("jailed")};
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

    public static String[] shuffleJSONArray(JSONArray jsonArray) {
        String[] list = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            list[i] = jsonArray.getString(i);
        }
        ArrayUtils.shuffle(list);
        return list;
    }

    public static void writeGame(IMessage message, JSONObject object) {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

        File file = new File(s + "/config/mafia/" + message.getGuild().getLongID() + "_playerdat.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Message.throwError(e, message);
            }
        }

        try (FileWriter fileWriter = new FileWriter(s + "/config/mafia/" + message.getGuild().getLongID() + "_playerdat.txt")) {
            fileWriter.write(object.toString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("JSON Object: " + object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long getJailorChat(IMessage message) {
        JSONObject root1 = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + "_dat.txt");
        return root1.getLong("jailor_chat");
    }

    public static long getJailedChat(IMessage message) {
        JSONObject root1 = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + "_dat.txt");
        return root1.getLong("jailed_chat");
    }

    public static long getJailed(IMessage message) {
        JSONObject root = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + "_playerdat.txt");
        return root.getLong("jailed");
    }
    public static void writeGameDat(IMessage message, JSONObject object){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

        File file = new File(s + "/config/mafia/" + message.getGuild().getLongID() + "_dat.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Message.throwError(e, message);
            }
        }

        try (FileWriter fileWriter = new FileWriter(s + "/config/mafia/" + message.getGuild().getLongID() + "_dat.txt")) {
            fileWriter.write(object.toString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("JSON Object: " + object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}