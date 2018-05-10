package maxdistructo.discord.bots.droidbot.commands

import maxdistructo.discord.core.Config
import maxdistructo.discord.core.Perms
import maxdistructo.discord.core.message.Message
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser

object Spam {
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
