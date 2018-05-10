package maxdistructo.discord.bots.droidbot.commands.casino

import maxdistructo.discord.core.Config
import maxdistructo.discord.core.message.Message
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import sx.blah.discord.handle.obj.IMessage
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Paths

object Trivia {

    private var question = 1
    private var list: String? = null
    private val players: LongArray? = null
    private val scores: IntArray? = null

    // @Command(aliases = {"/trivia"}, description = "Trivia Game", usage = "/trivia <listname>|join")
    fun onTriviaCommand(args: Array<Any>, message: IMessage) {
        val players = JSONArray()
        players.put(0, message.author.longID)
        val scores = JSONArray()
        scores.put(0, 0)
        writeTrivia(players, scores, args[1] as String, 1)
        val questionAnswer = triviaReader(args[1] as String, 1)
        val questionAnswerArray = questionAnswer.split("`".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        sendQuestion(message, questionAnswerArray[0], question)
    }

    private fun triviaReader(list: String, line: Int): String {
        val currentRelativePath = Paths.get("")
        val s = currentRelativePath.toAbsolutePath().toString()
        try {
            val triviaList = Config.readFileAsList(File("$s/droidbot/trivia/$list.txt"))
            return triviaList!![line]
        } catch (e: Exception) {
            Message.sendDM(BaseBot.client.applicationOwner, e.localizedMessage)
            e.printStackTrace()
        }

        return "Is this command broken?`YES"

    }

    private fun writeTrivia(players: JSONArray, scores: JSONArray, list: String, question: Int) {
        val root = JSONObject()
        root.put("list", list)
        root.put("players", players)
        root.put("scores", scores)
        root.put("question", question)

        val currentRelativePath = Paths.get("")
        val s = currentRelativePath.toAbsolutePath().toString()

        try {
            FileWriter("$s/droidbot/trivia/game.txt").use { file ->
                file.write(root.toString())
                println("Successfully Copied JSON Object to File...")
                println("\nJSON Object: $root")
            }
        } catch (e: IOException) {
            Message.sendDM(BaseBot.client.applicationOwner, e.toString())
            e.printStackTrace()
        }

    }

    private fun readTrivia() {
        val currentRelativePath = Paths.get("")
        val s = currentRelativePath.toAbsolutePath().toString()
        val file = File("$s/droidbot/trivia/game.txt")
        val uri = file.toURI()
        var tokener: JSONTokener? = null
        try {
            tokener = JSONTokener(uri.toURL().openStream())
            println("Successfully read file game.txt")
        } catch (e: IOException) {
            Message.sendDM(BaseBot.client.applicationOwner, e.toString())
            e.printStackTrace()
        }

        val root = JSONObject(tokener!!)

        list = root.getString("list")
        question = root.getInt("question")
        val array = root.getJSONArray("")


    }

    private fun sendQuestion(message: IMessage, question: String, questionNum: Int) {
        Message.sendMessage(message.channel, "Question #$questionNum\n\n$question")
    }
}
