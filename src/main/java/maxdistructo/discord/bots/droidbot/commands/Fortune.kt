package maxdistructo.discord.bots.droidbot.commands

import com.mashape.unirest.http.Unirest
import maxdistructo.discord.core.command.BaseCommand
import maxdistructo.discord.core.message.Message
import org.json.JSONObject
import sx.blah.discord.handle.obj.IMessage

class Fortune : BaseCommand() {

    override val commandName: String
        get() = "fortune"
    override val helpMessage: String
        get() = "fortune - Gets your fortune"

    override fun init(message: IMessage, args: List<String>): String {
        return onFortuneCommand(message)
    }

    private fun onFortuneCommand(message: IMessage): String {
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
