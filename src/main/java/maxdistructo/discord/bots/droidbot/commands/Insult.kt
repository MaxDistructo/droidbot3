package maxdistructo.discord.bots.droidbot.commands

import com.mashape.unirest.http.Unirest
import com.mashape.unirest.http.exceptions.UnirestException
import maxdistructo.discord.bots.droidbot.background.PrivUtils
import maxdistructo.discord.bots.droidbot.background.constructor.BaseCommand
import maxdistructo.discord.core.Utils
import org.json.JSONObject
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser
import java.util.concurrent.ThreadLocalRandom

class Insult : BaseCommand() {
    override val commandName: String
        get() = "insult"
    override val helpMessage: String
        get() = "insult <@User> - Insults the provided user."

    override fun init(message: IMessage, args: List<String>): String {
        return onInsultCommand(PrivUtils.listToArray(args), message, Utils.getMentionedUser(message)!!)
    }

    private fun onInsultCommand(args: Array<String>, message: IMessage, mentioned: IUser): String {
        val author = message.author
        val guild = message.guild
        val name = mentioned.getDisplayName(guild)
        val from = mentioned.getDisplayName(guild)
        val insults = arrayOf("anyway/$name/$from", "asshole/$from", "bag/$from", "ballmer/$name/$name/$from", "blackadder/$name/$from")
        val randomNum = ThreadLocalRandom.current().nextInt(0, insults.size)
        if (message.client.ourUser === mentioned) {
            return author.mention() + "How original. No one else had thought of trying to get the bot to insult itself. I applaud your creativity. \"yawn\" Perhaps this is why you don't have friends. You don't add anything new to any conversation. You are more of a bot than me, predictable answers, and absolutely dull to have an actual conversation with."
        } else {
            var insult: JSONObject? = null
            try {
                insult = Unirest.get("http://foaas.herokuapp.com/" + insults[randomNum]).header("Accept", "application/json").asJson().body.`object`
            } catch (e: UnirestException) {
                e.printStackTrace()
            }

            println(insult)
            return if (insult != null) {
                insult.getString("message")
            } else {
                "This bot is so stupid, your stupidity is even worse. (API Error)"
            }
        }

    }
}
