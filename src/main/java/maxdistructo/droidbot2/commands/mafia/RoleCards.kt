package maxdistructo.droidbot2.commands.mafia

import maxdistructo.droidbot2.core.message.Message
import sx.blah.discord.api.internal.json.objects.EmbedObject
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser
import sx.blah.discord.util.EmbedBuilder
import java.time.Instant

object RoleCards {
    fun onRoleCardAsk(message : IMessage, role : String, user : IUser) : EmbedObject{
        when(role){
            "investigator" ->{
                    val builder = EmbedBuilder()
                    val guild = message.guild
                    builder.appendField("Class", "Town Investigative (TI)", true)
                    builder.appendField("Goal", "Lynch every criminal or evildoer",true)
                    builder.appendField("Attack: 0 - Defence: 0","Your allies are Survivors, Pirates, Guardian Angels, and other Town members",false)
                    builder.appendField("Summary", "A private eye who secretly gathers information.", false)
                    builder.withAuthorName("Investigator")
                    builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/2/20/Achievement_Investigator.png/revision/latest/scale-to-width-down/50?cb=20140825150920")
                    builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/c/cb/Investigator.png/revision/latest?cb=20141222203926")
                    builder.withDescription("Investigate one person each night for a clue to their role.")
                    builder.withTimestamp(Instant.now())
                    builder.withFooterIcon(guild.iconURL)
                    builder.withFooterText(guild.name)
                    builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "sheriff" ->{
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Town Investigative (TI)", true)
                builder.appendField("Goal", "Lynch every criminal or evildoer",true)
                builder.appendField("Attack: 0 - Defence: 0","Your allies are Survivors, Pirates, Guardian Angels, and other Town members", false)
                builder.appendField("Summary", "The law enforcer of the Town, forced into hiding from threat of murder.", false);
                builder.withAuthorName("Sheriff")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/e/e7/Achievement_Sheriff.png/revision/latest/scale-to-width-down/50?cb=20140825150706")
                builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/3/3e/Sheriff.png/revision/latest/scale-to-width-down/150?cb=20140802032529")
                builder.withDescription("Interrogate one person each night for suspicious activity.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "spy" ->{
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Town Investigative (TI)", true)
                builder.appendField("Goal", "Lynch every criminal or evildoer",true)
                builder.appendField("Attack: 0 - Defence: 0","Your allies are Survivors, Pirates, Guardian Angels, and other Town members", false)
                builder.appendField("Summary", "A talented watcher who keeps track of evil in the Town.", false);
                builder.withAuthorName("Spy")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/0/0b/Achievement_Spy.png/revision/latest/scale-to-width-down/50?cb=20140825150715")
                builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/3/36/Spy.png/revision/latest/scale-to-width-down/150?cb=20151129195522")
                builder.withDescription("You are able to read whispers and the chat of the mafia at night. You will also know who the mafia visits.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "lookout" ->{
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Town Investigative (TI)", true)
                builder.appendField("Goal", "Lynch every criminal or evildoer",true)
                builder.appendField("Attack: 0 - Defence: 0","Your allies are Survivors, Pirates, Guardian Angels, and other Town members", false)
                builder.appendField("Summary", "An eagle-eyed observer, stealthily camping outside houses to gain information.", false);
                builder.withAuthorName("Lookout")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/f/f6/Achievement_Lookout.png/revision/latest/scale-to-width-down/50?cb=20140825150631")
                //builder.withThumbnail("")
                builder.withDescription("Watch one person at night to see who visits them.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "vigilante" ->{
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Town Killing (TK)", true)
                builder.appendField("Goal", "Lynch every criminal or evildoer",true)
                builder.appendField("Attack: 1 - Defence: 0","Your allies are Survivors, Pirates, Guardian Angels, and other Town members", false)
                builder.appendField("Summary", "A militant officer willing to bend the law to enact justice.", false);
                builder.withAuthorName("Vigilante")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/1/1f/Achievement_Vigilante.png/revision/latest?cb=20140825150808")
                builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/3/3c/Brock_Smith.png/revision/latest/scale-to-width-down/138?cb=20160914033426")
                builder.withDescription("Choose to take justice into your own hands and shoot someone. You only have 3 shots and if you kill another town member, you will suicide.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "veteran" ->{
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Town Killing (TK)", true)
                builder.appendField("Goal", "Lynch every criminal or evildoer",true)
                builder.appendField("Attack: 2 - Defence: 0","Your allies are Survivors, Pirates, Guardian Angels, and other Town members", false)
                builder.appendField("Summary", "A paranoid war hero who will shoot anyone who visits him.", false);
                builder.withAuthorName("Veteran")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/1/1b/Achievement_Veteran.png/revision/latest/scale-to-width-down/50?cb=20140825150759")
                //builder.withThumbnail("")
                builder.withDescription("Decide if you will go on alert. You can go on alert 3 times killing all who visit you. When you are on alert, you will have a defence of 1 or general immunity.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }

        }
        val nulBuilder = EmbedBuilder()
        return nulBuilder.build()
    }
}