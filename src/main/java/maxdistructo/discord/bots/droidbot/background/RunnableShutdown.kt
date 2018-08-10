package maxdistructo.discord.bots.droidbot.background

import maxdistructo.discord.bots.droidbot.BaseBot
import maxdistructo.discord.core.message.Message
import java.time.Instant
import java.time.LocalDate

object RunnableShutdown : Runnable{
    override fun run() {
        val date = LocalDate.now()
        Message.sendDM(BaseBot.client.applicationOwner, "Bot is Shutting Down. Time: " + Instant.now().toString() + " Date: " + date.month + "/" + date.dayOfMonth + "/" + date.year)
    }
}