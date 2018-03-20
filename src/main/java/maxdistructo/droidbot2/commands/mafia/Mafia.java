package maxdistructo.droidbot2.commands.mafia;

import maxdistructo.droidbot2.core.Roles;
import maxdistructo.droidbot2.core.Utils;
import maxdistructo.droidbot2.core.message.Message;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import sx.blah.discord.handle.obj.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.List;

import static maxdistructo.droidbot2.core.Utils.s;

public class Mafia {

    public static void onMafiaJoin(IMessage message){
        //TODO Read player file(Create if not existence), add new user ID to file, write file back to disk. Will return String saying player name has been successfully added to game.
    }
    public static void onGameJoinCommand(IMessage message){
        IRole role = Roles.getRole(message, "Mafia Folks");
        message.getAuthor().addRole(role);
        Message.sendMessage(message.getChannel(), message.getAuthor().mention(true) + ", you have been added to the Mafia game.");
    }
    public static void onGameLeaveCommand(IMessage message){
        IRole role = Roles.getRole(message, "Mafia Folks");
        message.getAuthor().removeRole(role);
        message.reply(message.getAuthor().mention(true) + ", you have been added to the Mafia game.");
    }
    public static void onGameStart(IMessage message){
        long adminChannelLong = MafiaConfig.getAdminChannel(message);
        IChannel adminChannel = message.getGuild().getChannelByID(adminChannelLong);
        long dayChannelLong = MafiaConfig.getDayChat(message);
        IChannel dayChannel = message.getGuild().getChannelByID(dayChannelLong);
        assignRoles(message);
        Message.sendMessage(dayChannel, "@everyone The Mafia game has started! \n Day 1 has begun!");
        Message.sendMessage(adminChannel, message.getAuthor().getDisplayName(message.getGuild()) + message.getAuthor().getDiscriminator() + " has started the Mafia game.");
        long[] players = MafiaConfig.getPlayers(message, "Mafia Folks");

        for(long player : players){
            dayChannel.removePermissionsOverride(message.getGuild().getUserByID(player));
            Object[] playerInfo = MafiaConfig.getPlayerDetails(message);
            if(playerInfo[2].toString().equals("spy")){
                allowSpyChat(message, message.getGuild().getUserByID(player));
            }
            else if(playerInfo[2].toString().equals("medium")){
                allowMediumChat(message, message.getGuild().getUserByID(player));
            }
        }


    }
    public static void onGameToggle(IMessage message){
        long dayChannelLong = MafiaConfig.getDayChat(message);
        IChannel dayChannel = message.getGuild().getChannelByID(dayChannelLong);
        long mafiaChannelLong = MafiaConfig.getMafiaChannel(message);
        IChannel mafiaChannel = message.getGuild().getChannelByID(mafiaChannelLong);
        boolean isDay = MafiaConfig.getDayStatus(message);
        IChannel jailorChannel = message.getGuild().getChannelByID(MafiaConfig.getJailorChat(message));
        IChannel jailedChannel = message.getGuild().getChannelByID(MafiaConfig.getJailedChat(message));
        IChannel mediumChannel = message.getGuild().getChannelByID(MafiaConfig.getMediumChat(message));
        long[] players = MafiaConfig.getPlayers(message, "Mafia Folks");

        //runActions();

        if(isDay){
            for(long player : players){
                Object[] playerInfo = MafiaConfig.getPlayerDetails(message, player);
                if(playerInfo[0].toString().equals("mafia")){//check if mafia
                    mafiaChannel.overrideUserPermissions(message.getGuild().getUserByID(player), EnumSet.of(Permissions.READ_MESSAGE_HISTORY, Permissions.READ_MESSAGES, Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES), EnumSet.noneOf(Permissions.class));
                }
                else{
                    mafiaChannel.overrideUserPermissions(message.getGuild().getUserByID(player), EnumSet.noneOf(Permissions.class), EnumSet.of(Permissions.READ_MESSAGE_HISTORY, Permissions.READ_MESSAGES, Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES));
                }
                if(playerInfo[2].toString().equals("jailor")) {
                    allowJailorChat(message, message.getGuild().getUserByID(player));
                }
                else{
                    denyJailorChat(message, message.getGuild().getUserByID(player));
                }
                if(MafiaConfig.getJailed(message) == player){
                    allowJailedChat(message, message.getGuild().getUserByID(player));
                }
                else{
                    //denyJailedChat(message, message.getGuild().getUserByID(player));
                }
                if(playerInfo[2].toString().equals("medium")){
                    allowMediumChat(message, message.getGuild().getUserByID(player));
                }
                else{
                    //denyMediumChat(message, message.getGuild().getUserByID(player));
                }

                denyDayChat(message, message.getGuild().getUserByID(player));
            }
            JSONObject root = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + "_dat.txt");
            root.remove("day");
            root.put("day", false);
            MafiaConfig.writeGameDat(message, root);
        }
        else{
            for(long player : players){
                dayChannel.removePermissionsOverride(message.getGuild().getUserByID(player)); //enable day chat
                Object[] playerInfo = MafiaConfig.getPlayerDetails(message, player);
                if(playerInfo[0].toString().equals("mafia")){//check if mafia
                    denyMafChat(message, message.getGuild().getUserByID(player));
                }
                if(playerInfo[2].toString().equals("jailor")){
                    jailorChannel.removePermissionsOverride(message.getGuild().getUserByID(player));
                }
                if(MafiaConfig.getJailed(message) == player){
                    jailedChannel.removePermissionsOverride(message.getGuild().getUserByID(player));
                    unjail(message);
                }
                if(playerInfo[2].toString().equals("medium")){
                    mediumChannel.removePermissionsOverride(message.getGuild().getUserByID(player));
                }
            }
            JSONObject root = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + "_dat.txt");
            root.remove("day");
            root.put("day", true);
            MafiaConfig.writeGameDat(message, root);
        }
        for(long player : players){
            Object[] playerDetails = MafiaConfig.getPlayerDetails(message);
            if((boolean)playerDetails[3]){
                denyDayChat(message, message.getGuild().getUserByID(player));
                denyMafChat(message, message.getGuild().getUserByID(player));
                allowDeadChat(message, message.getGuild().getUserByID(player));
            }

        }
    }

    private static void denyJailorChat(IMessage message, IUser userByID) {
        long mafChatLong = MafiaConfig.getJailorChat(message);
        IChannel channel = message.getGuild().getChannelByID(mafChatLong);
        channel.overrideUserPermissions(userByID, EnumSet.noneOf(Permissions.class) , EnumSet.of(Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES));
    }

    /* private static void runActions(IMessage message) {
         List<String> actions  = null;
         try {
             actions = FileUtils.readLines(new File(s + "/config/mafia/" + message.getGuild().getLongID() + "_actions.txt"), "UTF-8");
         } catch (IOException e) {
             e.printStackTrace();
         }
         if(actions != null) {
             for(String action : actions){
                 String[] a = action.split(" ");
                 if(a[1].equals("alert")){
                     setVeteran(message, message.getGuild().getUserByID(Long.valueOf(a[0])));
                 }
                 if(a[1].equals("swap")){
                     actions = swapUsers(message, message.getGuild().getUserByID(Long.valueOf(a[0])), message.getGuild().getUserByID(Long.valueOf(a[2])), actions);
                 }
                 if(a[1].equals("witch")){
                     actions = witchUser(message, message.getGuild().getUserByID(Long.valueOf(a[0])), message.getGuild().getUserByID(Long.valueOf(a[2])), actions);
                 }

             }
             for (String action : actions) {
                 String[] a = action.split(" ");
                 if(a[1].equals("blocked")){ // To make sure that escort/consort values go through before invest results are released
                     blockUser(message, message.getGuild().getUserByID(Long.valueOf(a[2])));
                 }
             }
             for (String action : actions){
                 String[] a = action.split(" ");
                 if (a[1].equals("framed")){
                     frameUser(message, message.getGuild().getUserByID(Long.valueOf(a[2])));
                 }

             }
         }

     }

     private static List<String> witchUser(IMessage message, IUser userByID, IUser userByID1, List<String> actions) {

     }

     private static List<String> swapUsers(IMessage message, IUser userByID, IUser userByID1, List<String> actions) {

     }
 */
    private static void setVeteran(IMessage message, IUser userByID) {

    }

    public static void denyDayChat(IMessage message, IUser user){
        long dayChannelLong = MafiaConfig.getDayChat(message);
        IChannel channel = message.getGuild().getChannelByID(dayChannelLong); //EnumSet.of(add,remove)
        channel.overrideUserPermissions(user, EnumSet.of(Permissions.READ_MESSAGE_HISTORY, Permissions.READ_MESSAGES), EnumSet.of(Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES));
    }
    public static void denyMafChat(IMessage message, IUser user){
        long mafChatLong = MafiaConfig.getMafiaChat(message);
        IChannel channel = message.getGuild().getChannelByID(mafChatLong);
        channel.overrideUserPermissions(user, EnumSet.of(Permissions.READ_MESSAGE_HISTORY, Permissions.READ_MESSAGES), EnumSet.of(Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES));
    }
    public static void allowMediumChat(IMessage message, IUser user){
        long mediumChatLong = MafiaConfig.getMediumChat(message);
        IChannel channel = message.getGuild().getChannelByID(mediumChatLong);
        channel.overrideUserPermissions(user, EnumSet.of(Permissions.READ_MESSAGE_HISTORY, Permissions.READ_MESSAGES, Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES), EnumSet.noneOf(Permissions.class));
    }
    public static void allowJailorChat(IMessage message, IUser user){
        long mediumChatLong = MafiaConfig.getJailorChat(message);
        IChannel channel = message.getGuild().getChannelByID(mediumChatLong);
        channel.overrideUserPermissions(user, EnumSet.of(Permissions.READ_MESSAGE_HISTORY, Permissions.READ_MESSAGES, Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES), EnumSet.noneOf(Permissions.class));
    }
    public static void allowJailedChat(IMessage message, IUser user){
        long mediumChatLong = MafiaConfig.getJailedChat(message);
        IChannel channel = message.getGuild().getChannelByID(mediumChatLong);
        channel.overrideUserPermissions(user, EnumSet.of(Permissions.READ_MESSAGE_HISTORY, Permissions.READ_MESSAGES, Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES), EnumSet.noneOf(Permissions.class));
    }
    public static void allowSpyChat(IMessage message, IUser user){
        long spyChatLong = MafiaConfig.getSpyChat(message);
        IChannel channel = message.getGuild().getChannelByID(spyChatLong);
        channel.overrideUserPermissions(user, EnumSet.of(Permissions.READ_MESSAGE_HISTORY, Permissions.READ_MESSAGE_HISTORY, Permissions.SEND_MESSAGES,Permissions.SEND_TTS_MESSAGES), EnumSet.noneOf(Permissions.class));
    }
    public static void allowDeadChat(IMessage message, IUser user){
        long deadChatLong = MafiaConfig.getDeadChat(message);
        IChannel channel  = message.getGuild().getChannelByID(deadChatLong);
        channel.overrideUserPermissions(user, EnumSet.of(Permissions.READ_MESSAGE_HISTORY, Permissions.READ_MESSAGES, Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES), EnumSet.noneOf(Permissions.class));
    }
    public static JSONObject assignRoles(IMessage message){
        JSONObject root = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + ".dat");
        JSONArray jArrayRoles  = root.getJSONArray("rolelist");
        JSONObject translator = root.getJSONObject("rolelist-translate");
        JSONObject roleData = root.getJSONObject("roleData");
        long[] players = MafiaConfig.getPlayers(message, "Mafia Folks");
        String[] roleList = MafiaConfig.shuffleJSONArray(jArrayRoles);
        int i = 0;
        for(String role : roleList){
            JSONArray roleDatJSON = translator.getJSONArray(role);
            String[] roleDat = MafiaConfig.shuffleJSONArray(roleDatJSON);
            roleList[i] = roleDat[0];
            i++;
        }
        JSONObject out = new JSONObject();
        int ii = 0;
        for(long player : players) {
            out.put("" + player, roleData.getJSONObject(roleList[ii]));
            ii++;
        }
        MafiaConfig.writeGame(message, out);
        return out;
    }
    public static String killPlayer(IMessage message, long playerID){
        IUser player = message.getGuild().getUserByID(playerID);
        Object[] playerDetails = MafiaConfig.getPlayerDetails(message, playerID);
        if((boolean)playerDetails[3]){
            return "Player is already dead. Can not kill player " + player.getDisplayName(message.getGuild()) + " because they are already dead. ";
        }
        JSONObject root = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + "_playerdat.txt");
        JSONObject playerInfo = root.getJSONObject("" + playerID);
        playerInfo.remove("dead");
        playerInfo.put("dead", true);
        root.remove("" + playerID);
        root.put("" + playerID, playerInfo);
        MafiaConfig.writeGame(message, root);
        return "Successfully killed player " + player.getDisplayName(message.getGuild());
    }
    public static void jailPlayer(IMessage message, IUser user){
        Object[] playerDetails = MafiaConfig.getPlayerDetails(message, user.getLongID());
        JSONObject root = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + "_dat.txt");
        root.remove("jailed");
        if(playerDetails[2].toString().equals(MafiaConfig.getPlayerDetails(message)[2].toString())){
            System.out.println("Jailor's can not jail themselves!");
            root.put("jailed", 0L);
        }
        else{
            root.put("jailed", user.getLongID());
        }
        MafiaConfig.writeGame(message, root);
    }
    public static void unjail(IMessage message){
        JSONObject root = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + "_dat.txt");
        root.remove("jailed");
        root.put("jailed", 0L);
        MafiaConfig.writeGame(message, root);
    }
    public static boolean checkCleaned(){
        return false;
    }
    public static boolean checkVest(){
        return false;
    }
    public static void vest(){

    }
    public static boolean checkBlackmailed(){
        return false;
    }
    public static boolean checkBlocked(IMessage message, IUser user){
        Object[] details = MafiaConfig.getPlayerDetails(message, user.getLongID());
        return (boolean)details[6];
    }
    public static void blockUser(IMessage message, IUser user){
        JSONObject root = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + "_playerdat.txt");
        JSONObject playerDetails = root.getJSONObject("" + user.getLongID());
        playerDetails.remove("blocked");
        playerDetails.put("blocked", true);
        root.remove("" + user.getLongID());
        root.put("" + user.getLongID(), playerDetails);
        MafiaConfig.writeGame(message, root);
    }
    public static void frameUser(IMessage message, IUser user){
        JSONObject root = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + "_playerdat.txt");
        JSONObject playerDetails = root.getJSONObject("" + user.getLongID());
        playerDetails.remove("framed");
        playerDetails.put("framed", true);
        root.remove("" + user.getLongID());
        root.put("" + user.getLongID(), playerDetails);
        MafiaConfig.writeGame(message, root);
    }
    public static void setRole(IMessage message, IUser user, String role){
        JSONObject root = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + "_playerdat.txt");
        JSONObject root1 = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + ".dat");
        JSONObject translator = root1.getJSONObject("rolelist-translate");
        JSONObject roleData = root1.getJSONObject("roleData");
        String roleAssign;
        root.remove("" + user.getLongID());
        JSONArray roleDatJSON = translator.getJSONArray(role);
        String[] roleDat = MafiaConfig.shuffleJSONArray(roleDatJSON);
        roleAssign = roleDat[0];
        root.put("" + user.getLongID(), roleData.getJSONObject(roleAssign));
    }

}
