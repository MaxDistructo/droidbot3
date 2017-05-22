package maxdistructo.droidbot2.background;

public class Perms {
    static long[] moderators = {};
    static long[] admin = {};
    static long owner = 2000958192395L;
    static long gameChannel = 2000L;
    public static void checkMod(long user){
        int i = 0;       
        while(i < moderators.length){
            if(moderators[i] == user){
                Config.ISMOD = true;
                i = moderators.length;
            }
            else{
                Config.ISMOD = false;
            }
            
        }
    }
    public static void checkAdmin(long user){
        int i = 0;
        while(i < admin.length){
            if(admin[i] == user){
                Config.ISADMIN = true;
                i = moderators.length;
            }
            else{
                Config.ISADMIN = false;
            }
    }
}
    public static void checkOwner(long user){
        if(user == owner){
            Config.ISOWNER = true;
        }
        else{
            Config.ISOWNER = false;
        }
    }
    
    public static void checkGames(long channel){
        if(channel == gameChannel){
            Config.ISGAME = true;
        }
        else{
            Config.ISGAME = false;
        }
    }

}
