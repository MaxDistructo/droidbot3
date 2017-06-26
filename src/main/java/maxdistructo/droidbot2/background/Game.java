package maxdistructo.droidbot2.background;

import sx.blah.discord.handle.obj.IUser;

public class Game {
    PlayerList list;
    String name;
    IUser host;
    boolean isHosting, lastcall;

    public Game(){
        this.list = null;
        this.name = "null";
        this.host = null;
        this.isHosting = false;
        this.lastcall = false;
    }
    public Game(PlayerList a,IUser host,String gameName, boolean hosting, boolean lastcall){
        this.list = a;
        this.name = gameName;
        this.host = host;
        this.isHosting = hosting;
        this.lastcall = lastcall;
    }

    public static PlayerList getPlayerList(Game g){
        return g.list;
    }

    public static IUser getHost(Game g){
        return g.host;
    }

    public static String getGameName(Game g){
        return g.name;
    }

    public static boolean getLastcallStatus(Game g){
        return g.lastcall;
    }

    public static boolean getHostingStatus(Game g){
        return g.isHosting;
    }

    public static void setHost (Game g, IUser host){
        g.host = host;
    }

    public static void setGameName (Game g, String name){
        g.name = name;
    }

    public static void setLastcallStatus(Game g, boolean lastcall){
        g.lastcall = lastcall;
    }

    public static void setHostingStatus(Game g, boolean hosting){
        g.isHosting = hosting;
    }

    public static void setPlayerList(Game g, PlayerList list){
        g.list = list;
    }

}
