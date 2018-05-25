package maxdistructo.discord.bots.droidbot.commands

import maxdistructo.discord.bots.droidbot.BaseBot
import maxdistructo.discord.core.Utils
import maxdistructo.discord.core.message.Webhook
import sx.blah.discord.handle.obj.IChannel
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.util.DiscordException
import sx.blah.discord.util.MissingPermissionsException
import sx.blah.discord.util.RateLimitException

object Say {
    @Throws(RateLimitException::class, DiscordException::class, MissingPermissionsException::class)
    fun onSayCommand(args: Array<Any>, message: IMessage, mentionedChannel: IChannel?): String {
        var anotherChannel = false
        var attachments: List<IMessage.Attachment>? = null
        attachments = message.attachments
        if (mentionedChannel != null) {
            anotherChannel = true
        }

        return if (anotherChannel) {
            if (!attachments.isEmpty()) {
                Utils.makeNewString(args, 2) + attachments[0].url
            } else {
                Utils.makeNewString(args, 2)
            }
        } else {
            if (!attachments.isEmpty()) {
                Utils.makeNewString(args, 1) + attachments[0].url
            } else { //This is technically not required though I want to make sure it does what it is suppost to do.
                Utils.makeNewString(args, 1)
            }
        }
    }
    fun onWebhookSay(args: Array<Any>, message : IMessage, mentionedChannel: IChannel?){
        val user = Utils.getUserFromInput(message, args[1])
        var anotherChannel = false
        val output : String
        if(mentionedChannel != null){
            anotherChannel = true
        }
        if(anotherChannel){
            output = Utils.makeNewString(args, 3)
            Webhook.send(BaseBot.bot, mentionedChannel!!, user!!.getDisplayName(message.guild), user.avatarURL, output)
        }
        else{
            output = Utils.makeNewString(args, 2)
            Webhook.send(BaseBot.bot, message.channel, user!!.getDisplayName(message.guild), user.avatarURL, output)
        }
    }
}
