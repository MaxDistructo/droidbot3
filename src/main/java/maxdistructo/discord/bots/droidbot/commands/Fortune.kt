package maxdistructo.discord.bots.droidbot.commands

import com.mashape.unirest.http.Unirest
import maxdistructo.droidbot2.core.message.Message
import org.json.JSONObject
import sx.blah.discord.handle.obj.IMessage

object Fortune {

    fun onFortuneCommand(message: IMessage): String {
        var fortune: JSONObject? = null
        try {
            fortune = Unirest.get("https://helloacm.com/api/fortune").asJson().body.`object`
        } catch (e: Exception) {
            Message.throwError(e, message)
        }

        return if (fortune != null) {
            "Your fortune is " + fortune.get("fortune")
        } else "Your fortune seems to be out of reach. (API Error)"
    }
}
