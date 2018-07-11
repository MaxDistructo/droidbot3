package maxdistructo.discord.bots.droidbot.commands

import maxdistructo.discord.bots.droidbot.BaseBot
import maxdistructo.discord.bots.droidbot.background.PrivUtils
import maxdistructo.discord.core.Utils
import maxdistructo.discord.core.command.BaseCommand
import maxdistructo.discord.core.message.Webhook
import sx.blah.discord.handle.obj.IChannel
import sx.blah.discord.handle.obj.IMessage

class WebhookSay : BaseCommand() {
    override val commandName: String
        get() = "webhook"
    override val helpMessage: String
        get() = "webhook @User <message> - Says the following message as the user"
    override val requiresMod: Boolean
        get() = true
    override val hasOutput : Boolean
        get() = false

    override fun init(message: IMessage, args: List<String>): String {
        onWebhookSay(PrivUtils.listToArray(args) as Array<Any>, message, Utils.getMentionedChannel(message))
        return ""
    }

    private fun onWebhookSay(args: Array<Any>, message : IMessage, mentionedChannel: IChannel?){
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