package maxdistructo.droidbot2.commands;

public class Restart {
    public static String run(IMessage message){
        if(Perms.checkAdmin(message)){
         Process ps = Runtime.getRuntime().exec(new String[] {"java", "-jar", "droidbot2.jar"});
         return "Reboot in progress.";
         System.exit(0);
        }
        else{
            return "You do not have the perms to run this command. If you would like this bot to be restarted, please ask the owner of this server or owner of this bot to restart it.";
        }
    }
}
