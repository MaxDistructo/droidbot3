package maxdistructo.droidbot2.commands.mafia

import maxdistructo.droidbot2.core.Utils
import sx.blah.discord.api.internal.json.objects.EmbedObject
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.util.EmbedBuilder
import java.time.Instant

object Embeds{
    fun getInfo(details : Array<Any>, message : IMessage) : EmbedObject{
        val builder = EmbedBuilder()
        builder.withTitle("Player Info on " + Utils.getMentionedUser(message).getDisplayName(message.guild))
        builder.withDesc("Alignment: " + details[0] + "\nClass: " + details[1] + "\nRole: " + details[2] + "\nIs Dead: " + details[3] + "\nAttack Power: " + details[4] + "\nDefence Power: " + details[5])
        builder.withColor(message.author.getColorForGuild(message.guild))
        builder.withAuthorName(message.author.name + "#" + message.author.discriminator)
        builder.withAuthorIcon(message.author.avatarURL)
        builder.withTimestamp(Instant.now())
        builder.withFooterIcon(message.guild.iconURL)
        builder.withFooterText(message.guild.name)
        return builder.build()

    }
}