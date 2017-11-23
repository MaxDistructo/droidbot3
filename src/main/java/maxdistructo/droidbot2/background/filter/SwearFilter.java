package maxdistructo.droidbot2.background.filter;

import maxdistructo.droidbot2.core.Config;
import org.json.JSONArray;
import org.json.JSONObject;
import sx.blah.discord.handle.obj.IMessage;

import java.util.Arrays;
import java.util.List;

public class SwearFilter {

    public static void filter(IMessage message, Object[] messageContentIn){
        List<String> messageContent2 = Arrays.asList((String[])messageContentIn);
        List<String> messageContent = Arrays.asList(new String[messageContentIn.length]);
        int iiii = 0;
        while(iiii < messageContent2.size()){
            messageContent.set(iiii, messageContent2.get(iiii).toLowerCase());
            iiii++;
        }

        JSONObject config = Config.readServerConfig(message.getGuild());
        JSONArray array = config.getJSONArray("SwearWords");
        String[] swearWords = new String[array.length()];
        String[] pronouns = {"u", "you"};
        for (int i = 0; i < array.length(); i++) {
            swearWords[i] = array.getString(i).toLowerCase();
        }

        int ii = 0;
        int iii = 0;
        while(ii < swearWords.length) {
            if (messageContent.contains(swearWords[ii]) && config.getBoolean("SwearFilter")) {
                while(iii < pronouns.length) {
                    if (messageContent.contains(pronouns[iii])) {
                        message.reply("please stop swearing. If you keep swearing, a Mod/Admin will mute/kick/ban you depending on severity.");
                        message.delete();
                    }
                    iii++;
                }
            }
            iii = 0;
            ii++;
        }

    }


}
