package maxdistructo.droidbot2.background;

import maxdistructo.droidbot2.background.message.Message;
import sx.blah.discord.handle.obj.*;

import java.util.EnumSet;
import java.util.List;
import java.awt.*;

public class Roles {
    public static boolean checkForBotAbuse(IMessage message){
        IUser user = message.getAuthor();
        List<IRole> rolesList = user.getRolesForGuild(message.getGuild());
        List<IRole> guildRoles = message.getGuild().getRolesByName("Bot Abuser");
        IRole botAbuser = guildRoles.get(0);
        return rolesList.contains(botAbuser);
    }
    public static boolean checkForPayday(IMessage message){
        IUser user = message.getAuthor();
        List<IRole> rolesList = user.getRolesForGuild(message.getGuild());
        List<IRole> guildRoles = message.getGuild().getRolesByName("Payday");
        IRole payday = guildRoles.get(0);
        return rolesList.contains(payday);
    }
    public static void applyBotAbuser(IMessage message, IUser mentioned){
        List<IRole> guildRoles = message.getGuild().getRolesByName("Bot Abuser");
        IRole role = guildRoles.get(0);
        mentioned.addRole(role);
    }
    public static void applyPayday(IMessage message, IUser mentioned){
        IGuild guild = message.getGuild();
        List<IRole> channelList = guild.getRolesByName("Payday");
        IRole role = channelList.get(0);
        mentioned.addRole(role);
    }
    public static void removeBotAbuser(IMessage message, IUser mentioned){
        List<IRole> guildRoles = message.getGuild().getRolesByName("Bot Abuser");
        IRole role = guildRoles.get(0);
        mentioned.removeRole(role);
    }
    public static void removePayday(IMessage message, IUser mentioned) {
        List<IRole> guildRoles = message.getGuild().getRolesByName("Payday");
        IRole role = guildRoles.get(0);
        mentioned.removeRole(role);
    }
    public static IRole getRole(IMessage message, String role){
        List<IRole> roles = message.getGuild().getRolesByName(role);
        if(roles.size() != 0){
            return roles.get(0);
        }
        else{
            return null;
        }
    }
    public static void copyRolePerms(IRole copy, IRole paste){
        EnumSet<Permissions> permissions = copy.getPermissions();
        paste.getPermissions().addAll(permissions);
        paste.changePermissions(permissions);
    }
    public static void applyRole(IMessage message, IUser mentioned, String role){
        List<IRole> roleList = message.getGuild().getRolesByName(role);
        if(roleList == null){
            Message.sendMessage(message.getChannel(), "The role "+ role + " was not found.");
            Thread.interrupted();
        }
        mentioned.addRole(roleList.get(0));
    }
    public static void applyRole(IMessage message, IUser mentioned, long roleL){
        IRole role = message.getGuild().getRoleByID(roleL);
        if(role == null){
            Message.sendMessage(message.getChannel(), "The role "+ roleL + " was not found.");
            Thread.interrupted();
        }
        mentioned.addRole(role);
    }
    public static void changeRolePerm(IMessage message, String perm, String role){
        List<IRole> roleList = message.getGuild().getRolesByName(role);
        if(roleList == null){
            Message.sendMessage(message.getChannel(), "The role "+ role + " was not found.");
            Thread.interrupted();
        }
        IRole roleNew = roleList.get(0);
        EnumSet<Permissions> set = roleNew.getPermissions();
        switch (perm){
            case "muteUsers":
                set.add(Permissions.VOICE_MUTE_MEMBERS);
            case "manageNicknames":
                set.add(Permissions.MANAGE_NICKNAMES);
            case "manageRoles":
                set.add(Permissions.MANAGE_ROLES);
            case "attachFiles":
                set.add(Permissions.ATTACH_FILES);
            case "addReactions":
                set.add(Permissions.ADD_REACTIONS);
            case "banMembers":
                set.add(Permissions.BAN);
            case "changeOwnUsername":
                set.add(Permissions.CHANGE_NICKNAME);
            case "createInvite":
                set.add(Permissions.CREATE_INVITE);
            case "embed":
                set.add(Permissions.EMBED_LINKS);
            case "kick":
                set.add(Permissions.KICK);
            case "manageThisChannel":
                set.add(Permissions.MANAGE_CHANNEL);
            case "manageChannels":
                set.add(Permissions.MANAGE_CHANNELS);
            case "manageEmojis":
                set.add(Permissions.MANAGE_EMOJIS);
            case "manageMessages":
                set.add(Permissions.MANAGE_MESSAGES);
            case "managePermissions":
                set.add(Permissions.MANAGE_PERMISSIONS);
            case "manageServer":
                set.add(Permissions.MANAGE_SERVER);
            case "manageWebhooks":
                set.add(Permissions.MANAGE_WEBHOOKS);
            case "mentionEveryone":
                set.add(Permissions.MENTION_EVERYONE);
            case "readMessageHistory":
                set.add(Permissions.READ_MESSAGE_HISTORY);
            case "readMessages":
                set.add(Permissions.READ_MESSAGES);
            case "sendMessages":
                set.add(Permissions.SEND_MESSAGES);
            case "sendTTSMessages":
                set.add(Permissions.SEND_TTS_MESSAGES);
            case "useExternalEmojis":
                set.add(Permissions.USE_EXTERNAL_EMOJIS);
            case "viewAuditLog":
                set.add(Permissions.VIEW_AUDIT_LOG);
            case "voiceConnect":
                set.add(Permissions.VOICE_CONNECT);
            case "voiceModerator":
                set.add(Permissions.VOICE_DEAFEN_MEMBERS);
                set.add(Permissions.VOICE_MOVE_MEMBERS);
                set.add(Permissions.VOICE_MUTE_MEMBERS);
            case "voiceSpeak":
                set.add(Permissions.VOICE_SPEAK);
            case "voiceUseVad":
                set.add(Permissions.VOICE_USE_VAD);

        }
        roleNew.changePermissions(set);


        }
        public static void changeColor(IRole role, String color){
            Color hex;
            if(color.contains("#")){
                hex = Color.decode(color);
            }
            else{
                hex = Color.decode("#" + color);
            }
            role.changeColor(hex);
        }
    }

