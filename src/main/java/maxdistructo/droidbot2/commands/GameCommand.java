package maxdistructo.droidbot2.commands;

import java.util.Arrays;
import java.util.List;

import com.sun.xml.internal.bind.v2.TODO;
import javazoom.jl.player.Player;
import maxdistructo.droidbot2.background.*;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

public class GameCommand implements CommandExecutor{

    private static Game game = new Game();
    private static PlayerList list;

    //@Command(aliases = {"/game", "/g"}, description = "Creates a new Game session" , usage = "/game create|join|lastcall|start|info")
    public static String onGameCommand(Object[] args, IMessage message){
        IUser author =  message.getAuthor();

        if(args[1].equals("create") && !Game.getHostingStatus(game) && args.length == 4){
            EmbedBuilder builder = new EmbedBuilder();
            int numPlayers = Config.converToInt(args[2]);
            list = new PlayerList(numPlayers);
            Game.setGameName(game, (String)args[3]);
            Game.setHost(game, author);
            Game.setLastcallStatus(game, true);
            Game.setHostingStatus(game, true);
            builder.withTitle("Game Created!");
            builder.withDescription("New Game Hosted by: " + author.getDisplayName(message.getGuild()));
            builder.appendField((String)args[3], "Player List: " + PlayerList.outputList(list), true);
        }


        return "Command Error";
    }
}
