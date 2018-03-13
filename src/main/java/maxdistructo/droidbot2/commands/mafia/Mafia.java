package maxdistructo.droidbot2.commands.mafia;

import maxdistructo.droidbot2.core.Roles;
import maxdistructo.droidbot2.core.Utils;
import maxdistructo.droidbot2.core.message.Message;
import org.json.JSONObject;
import sx.blah.discord.handle.obj.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;

public class Mafia {

    public static void onMafiaJoin(IMessage message){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        File f  = new File(s + "/config/mafia/" + message.getGuild().getLongID() + "_players.txt");

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
        long[] players = MafiaConfig.getPlayers(message, "Mafia Folks");
        if(isDay){
            for(long player : players){
                Object[] playerInfo = MafiaConfig.getPlayerDetails(message);
                if(playerInfo[0].toString().equals("mafia")){//check if mafia
                    mafiaChannel.overrideUserPermissions(message.getGuild().getUserByID(player), EnumSet.of(Permissions.READ_MESSAGE_HISTORY, Permissions.READ_MESSAGES, Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES), EnumSet.noneOf(Permissions.class));
                }
                else{
                    mafiaChannel.overrideUserPermissions(message.getGuild().getUserByID(player), EnumSet.noneOf(Permissions.class), EnumSet.of(Permissions.READ_MESSAGE_HISTORY, Permissions.READ_MESSAGES, Permissions.SEND_MESSAGES, Permissions.SEND_TTS_MESSAGES));
                }
                denyDayChat(message, message.getGuild().getUserByID(player));
            }
        }
        else{
            for(long player : players){
                dayChannel.removePermissionsOverride(message.getGuild().getUserByID(player)); //enable day chat
                Object[] playerInfo = MafiaConfig.getPlayerDetails(message);
                if(playerInfo[0].toString().equals("mafia")){//check if mafia
                    denyMafChat(message, message.getGuild().getUserByID(player));
                }
            }

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
    private static void assignRoles(IMessage message){
        JSONObject root = Utils.readJSONFromFile("/config/mafia/" + message.getGuild().getLongID() + "_dat.txt");

    }

}
