package maxdistructo.droidbot2.background;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;

import static maxdistructo.droidbot2.BaseBot.client;
import static maxdistructo.droidbot2.BaseBot.jda;

public class Compatability {
    public static Role convertToJDA(IRole role){
        return jda.getRoleById(role.getLongID());
    }
    public static User convertToJDA(IUser user){
        return jda.getUserById(user.getLongID());
    }
    public static Guild convertToJDA(IGuild guild){
        return jda.getGuildById(guild.getLongID());
    }
    public static IRole converToJ4(Role role){
        return client.getRoleByID(role.getIdLong());
    }
    public static IUser convertToJ4(User user){
        return client.getUserByID(user.getIdLong());
    }
    public static IGuild convertToJ4(Guild guild){
        return client.getGuildByID(guild.getIdLong());
    }
}
