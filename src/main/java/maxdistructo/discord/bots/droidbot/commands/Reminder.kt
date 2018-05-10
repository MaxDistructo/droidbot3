package maxdistructo.discord.bots.droidbot.commands

import maxdistructo.discord.core.Utils
import maxdistructo.discord.core.message.Message
import sx.blah.discord.handle.obj.IMessage

object Reminder {

    fun onReminderCommand(args: Array<String>, message: IMessage) {  //!remindme (time) "Message"
        val time = Utils.convertToLong(args[0])!! * 60000
        var reminder: String? = null
        var i = 2
        while (i < args.size) {
            if (i == args.size - 1) {
                reminder = reminder!! + args[i]
            } else {
                reminder = reminder + " " + args[i]
            }
            i++
        }
        Message.sendMessage(message.channel, Message.simpleEmbed(message.author, "Reminder", "Your reminder has been set to go off in " + args[0] + " minutes.", message))
        try {
            Thread.sleep(time)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        Message.sendMessage(message.channel, Message.simpleEmbed(message.author, "Reminder", reminder!!, message))

    }
}
