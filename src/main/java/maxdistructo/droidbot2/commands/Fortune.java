package maxdistructo.droidbot2.commands;

import com.mashape.unirest.http.Unirest;
import maxdistructo.droidbot2.core.message.Message;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import sx.blah.discord.handle.obj.*;

import java.io.*;
import java.net.URL;

import static maxdistructo.droidbot2.core.Utils.s;

public class Fortune{

    public static String onFortuneCommand(IMessage message){
        JSONObject fortune = null;
        try {
           fortune = Unirest.get("https://helloacm.com/api/fortune").asJson().getBody().getObject();
        } catch (Exception e) {
            Message.throwError(e, message);
        }
        if(fortune != null){
            return "Your fortune is " + fortune.get("fortune");
        }
        return "Your fortune seems to be out of reach. (API Error)";
    }
}
