package maxdistructo.discord.bots.droidbot.commands

import maxdistructo.discord.bots.droidbot.BaseBot
import maxdistructo.discord.bots.droidbot.background.PrivUtils
import maxdistructo.discord.bots.droidbot.background.constructor.BaseCommand
import maxdistructo.discord.core.Config
import maxdistructo.discord.core.Perms
import maxdistructo.discord.core.Utils
import maxdistructo.discord.core.message.Message
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser

class Spam : BaseCommand() {

    override val commandName: String
        get() = "spam"
    override val helpMessage: String
        get() = "spam <@User> NumberOfTimes - Spams a user in their DMs."
    override val requiresAdmin: Boolean
        get() = true

    override fun init(message: IMessage, args: List<String>): String {
        return onSpamCommand(PrivUtils.listToArray(args) as Array<Any>, message, Utils.getMentionedUser(message)!!)
    }

    fun onSpamCommand(args: Array<Any>, message: IMessage, mentioned: IUser): String {
        //Spams a user in their DMs Command: prefix + spam <@User> NumberOfTimes
        val author = message.author
        var spamPlayer = mentioned
        val spamNum: Int
        if (args.size == 3) {
            spamNum = Config.converToInt(args[2])
        } else {
            return "You did not enter enough arguments to run this command."
        }
        if (mentioned === BaseBot.client.ourUser && !Perms.checkMod(message)) {
            spamPlayer = author
        }
        var i = 0
        while (i < spamNum) {
            try {
                Thread.sleep(1250L)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            Message.sendDM(spamPlayer, "YOU GOT SPAMMED SON!")
            i++
        }
        return "Player $spamPlayer was successfully spammed $spamNum times."

    }
}
