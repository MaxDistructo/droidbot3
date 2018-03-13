package maxdistructo.droidbot2.commands;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import maxdistructo.droidbot2.core.api.http.HTTPGet;
import maxdistructo.droidbot2.core.message.Message;
import org.json.JSONObject;
import sx.blah.discord.handle.obj.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

public class Insult{
    public static String onInsultCommand(Object[] args, IMessage message, IUser mentioned){
        IUser author = message.getAuthor();
        IGuild guild = message.getGuild();
        String name = mentioned.getDisplayName(guild);
        String from = mentioned.getDisplayName(guild);
        String[] insults = {"anyway/"+ name + "/" + from,
                "asshole/" + from,
                "bag/" + from,
                "ballmer/" + name + "/" + name + "/" + from,
                "blackadder/" + name + "/" + from
        };
        int randomNum = ThreadLocalRandom.current().nextInt(0, insults.length);
        if(message.getClient().getOurUser() == mentioned){
            return author.mention() + "How original. No one else had thought of trying to get the bot to insult itself. I applaud your creativity. \"yawn\" Perhaps this is why you don't have friends. You don't add anything new to any conversation. You are more of a bot than me, predictable answers, and absolutely dull to have an actual conversation with.";
        }
        else{
            JSONObject insult = null;
                try {
                    insult = Unirest.get("http://foaas.herokuapp.com/" + insults[randomNum]).header("Accept", "application/json").asJson().getBody().getObject();
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
                System.out.println(insult);
            if(insult != null){
                return insult.getString("message");
            }
            else{
                return "This bot is so stupid, your stupidity is even worse. (API Error)";
            }
        }

    }
}
