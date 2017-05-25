package maxdistructo.droidbot2.background;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.List;

public class Role {
    public static void addRole(IUser user, String role, IGuild guild) {
        List<IRole> rolesByName = guild.getRolesByName(role);
        IRole[] roleArray = rolesByName.toArray(new IRole[0]);
        int i = 0;
        while (i <= roleArray.length) {
            try {
                user.addRole(roleArray[i]);
            } catch (MissingPermissionsException e) {
                e.printStackTrace();
            } catch (RateLimitException e) {
                e.printStackTrace();
            } catch (DiscordException e) {
                e.printStackTrace();
            }
            i++;
        }
    }
}
