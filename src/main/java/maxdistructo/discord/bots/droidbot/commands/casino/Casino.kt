package maxdistructo.discord.bots.droidbot.commands.casino

import maxdistructo.discord.bots.droidbot.background.Conf
import maxdistructo.discord.bots.droidbot.background.Listener
import maxdistructo.discord.core.message.Message
import sx.blah.discord.api.internal.json.objects.EmbedObject
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser
import sx.blah.discord.util.EmbedBuilder
import java.text.NumberFormat
import java.time.Instant

object Casino {
    //@Command(aliases = {"/casino" }, description = "Casino Commands.", usage = "/casino [payday|balance]")
    private val nf = NumberFormat.getInstance()

    fun onCasinoCommand(args: Array<String>, message: IMessage, mentioned: IUser): String {
        val author = message.author

        if (args[1] == "join") {
            CasinoConfig.newCasino(message)
            message.delete()
            return "You have been registered to join " + message.guild.name + " Casino"
        } else if (args.size == 2) {
            if (args[1] == "payday" && !Conf.checkForPayday(message)) {
                checkMembership(message)
                when (CasinoConfig.MEMBERSHIP) {
                    "null" -> {
                        CasinoConfig.CHIPS = CasinoConfig.CHIPS + 1000
                        CasinoConfig.writeCasino(message)
                        Message.sendMessage(message.channel, Message.simpleEmbed(message.author, "Casino", "You have collected your 1,000 chip payday", message))
                        payday(message, message.author)
                        return author.mention(true) + "Your payday is ready!"
                    }
                    "A" -> {
                        CasinoConfig.CHIPS = CasinoConfig.CHIPS + 1500
                        CasinoConfig.writeCasino(message)
                        Message.sendMessage(message.channel, Message.simpleEmbed(message.author, "Casino", "You have collected your 1,500 chip payday", message))
                        payday(message, message.author)
                        return author.mention(true) + "Your payday is ready!"
                    }
                    "B" -> {
                        CasinoConfig.CHIPS = CasinoConfig.CHIPS + 1500
                        CasinoConfig.writeCasino(message)
                        Message.sendMessage(message.channel, Message.simpleEmbed(message.author, "Casino", "You have collected your 1,500 chip payday", message))
                        payday(message, message.author)
                        return author.mention(true) + "Your payday is ready!"
                    }
                    "C" -> {
                        CasinoConfig.CHIPS = CasinoConfig.CHIPS + 1800
                        CasinoConfig.writeCasino(message)
                        Message.sendMessage(message.channel, Message.simpleEmbed(message.author, "Casino", "You have collected your 1,800 chip payday", message))
                        payday(message, message.author)
                        return author.mention(true) + "Your payday is ready!"
                    }
                    "D" -> {
                        CasinoConfig.CHIPS = CasinoConfig.CHIPS + 2000
                        CasinoConfig.writeCasino(message)
                        Message.sendMessage(message.channel, Message.simpleEmbed(message.author, "Casino", "You have collected your 2,000 chip payday", message))
                        payday(message, message.author)
                        return author.mention(true) + "Your payday is ready!"
                    }
                    "E" -> {
                        CasinoConfig.CHIPS = CasinoConfig.CHIPS + 3000
                        CasinoConfig.writeCasino(message)
                        Message.sendMessage(message.channel, Message.simpleEmbed(message.author, "Casino", "You have collected your 3,000 chip payday", message))
                        payday(message, message.author)
                        return author.mention(true) + "Your payday is ready!"
                    }
                    "F" -> {
                        CasinoConfig.CHIPS = CasinoConfig.CHIPS + 4500
                        CasinoConfig.writeCasino(message)
                        Message.sendMessage(message.channel, Message.simpleEmbed(message.author, "Casino", "You have collected your 4,500 chip payday", message))
                        payday(message, message.author)
                        return author.mention(true) + "Your payday is ready!"
                    }
                    "G" -> {
                        CasinoConfig.CHIPS = CasinoConfig.CHIPS + 6000
                        CasinoConfig.writeCasino(message)
                        Message.sendMessage(message.channel, Message.simpleEmbed(message.author, "Casino", "You have collected your 6,000 chip payday", message))
                        payday(message, message.author)
                        return author.mention(true) + "Your payday is ready!"
                    }
                    "H" -> {
                        CasinoConfig.CHIPS = CasinoConfig.CHIPS + 6000
                        CasinoConfig.writeCasino(message)
                        Message.sendMessage(message.channel, Message.simpleEmbed(message.author, "Casino", "You have collected your 6,000 chip payday", message))
                        payday(message, message.author)
                        return author.mention(true) + "Your payday is ready!"
                    }
                    "I" -> {
                        CasinoConfig.CHIPS = CasinoConfig.CHIPS + 7500
                        CasinoConfig.writeCasino(message)
                        Message.sendMessage(message.channel, Message.simpleEmbed(message.author, "Casino", "You have collected your 7,500 chip payday", message))
                        payday(message, message.author)
                        return author.mention(true) + "Your payday is ready!"
                    }
                    ":star:" -> {
                        CasinoConfig.CHIPS += 7500
                        CasinoConfig.writeCasino(message)
                        Message.sendMessage(message.channel, casinoPayday(message, 7500))
                        payday(message, message.author)
                        return author.mention(true) + "Your payday is ready!"
                    }
                    ":star::star:" -> {
                        CasinoConfig.CHIPS += 9000
                        CasinoConfig.writeCasino(message)
                        Message.sendMessage(message.channel, casinoPayday(message, 9000))
                        payday(message, message.author)
                        return author.mention(true) + "Your payday is ready!"
                    }
                    ":star::star::star:" -> {
                        CasinoConfig.CHIPS += 9000
                        CasinoConfig.writeCasino(message)
                        Message.sendMessage(message.channel, casinoPayday(message, 9000))
                        payday(message, message.author)
                        return author.mention(true) + "Your payday is ready!"
                    }
                    ":star::star::star::star:" -> {
                        CasinoConfig.CHIPS += 12000
                        CasinoConfig.writeCasino(message)
                        Message.sendMessage(message.channel, casinoPayday(message, 12000))
                        payday(message, message.author)
                        return author.mention(true) + "Your payday is ready!"
                    }
                    ":star::star::star::star::star:" -> {
                        CasinoConfig.CHIPS += 12000
                        CasinoConfig.writeCasino(message)
                        Message.sendMessage(message.channel, casinoPayday(message, 12000))
                        payday(message, message.author)
                        return author.mention(true) + "Your payday is ready!"
                    }
                    ":crown:" -> {
                        CasinoConfig.CHIPS += 15000
                        CasinoConfig.writeCasino(message)
                        Message.sendMessage(message.channel, casinoPayday(message, 15000))
                        payday(message, message.author)
                        return author.mention(true) + "Your payday is ready!"
                    }
                    else -> return "Command " + Listener.prefix + "casino payday has errored. Your balance has not been affected."
                }
            } else if (args[1] == "balance") {
                CasinoConfig.readCasino(message)
                return "You have " + nf.format(CasinoConfig.CHIPS.toLong()) + " Casino chips"
            } else if (args[1] == "payday" && Conf.checkForPayday(message)) {
                return "Please wait until your payday role is removed to receive your next payday."
            }
        }

        return "Error in command: Casino."
    }

    fun checkMembership(user: IMessage) {
        CasinoConfig.readCasino(user)
        if (CasinoConfig.CHIPS < 10000) {
            CasinoConfig.MEMBERSHIP = "null"
        } else if (CasinoConfig.CHIPS in 10000..24999) {
            CasinoConfig.MEMBERSHIP = "A"
        } else if (CasinoConfig.CHIPS in 25000..59999) {
            CasinoConfig.MEMBERSHIP = "B"
        } else if (CasinoConfig.CHIPS in 60000..99999) {
            CasinoConfig.MEMBERSHIP = "C"
        } else if (CasinoConfig.CHIPS in 100000..199999) {
            CasinoConfig.MEMBERSHIP = "D"
        } else if (CasinoConfig.CHIPS in 200000..349999) {
            CasinoConfig.MEMBERSHIP = "E"
        } else if (CasinoConfig.CHIPS in 350000..499999) {
            CasinoConfig.MEMBERSHIP = "F"
        } else if (CasinoConfig.CHIPS in 500000..799999) {
            CasinoConfig.MEMBERSHIP = "G"
        } else if (CasinoConfig.CHIPS in 800000..999999) {
            CasinoConfig.MEMBERSHIP = "H"
        } else if (CasinoConfig.CHIPS in 1000000..1199999) {
            CasinoConfig.MEMBERSHIP = "I"
        } else if (CasinoConfig.CHIPS in 1200000..1499999) {
            CasinoConfig.MEMBERSHIP = ":star:"
        } else if (CasinoConfig.CHIPS in 1500000..1799999) {
            CasinoConfig.MEMBERSHIP = ":star::star:"
        } else if (CasinoConfig.CHIPS in 1800000..2099999) {
            CasinoConfig.MEMBERSHIP = ":star::star::star:"
        } else if (CasinoConfig.CHIPS in 2100000..2499999) {
            CasinoConfig.MEMBERSHIP = ":star::star::star::star:"
        } else if (CasinoConfig.CHIPS in 2500000..2899999) {
            CasinoConfig.MEMBERSHIP = ":star::star::star::star::star:"
        } else {
            CasinoConfig.MEMBERSHIP = ":crown:"
        }
    }

    private fun payday(message: IMessage, mentioned: IUser) {
        Conf.applyPayday(message, mentioned)
        try {
            Thread.sleep(21600000)
        } catch (e: InterruptedException) {
            Conf.removePayday(message, mentioned)
            e.printStackTrace()
        }

        Conf.removePayday(message, mentioned)
    }

    fun onCasinoInfo(message: IMessage): EmbedObject {
        CasinoConfig.readCasino(message)
        val builder = EmbedBuilder()
        builder.withTitle("Casino")
        builder.withDesc(".")
        builder.withColor(message.author.getColorForGuild(message.guild))
        builder.withAuthorName(message.author.name + "#" + message.author.discriminator)
        builder.withAuthorIcon(message.author.avatarURL)
        builder.withTimestamp(Instant.now())
        builder.withFooterIcon(message.guild.iconURL)
        builder.withFooterText(message.guild.name)
        builder.appendField("Casino Balance", "" + nf.format(CasinoConfig.CHIPS.toLong()), false)
        builder.appendField("Casino Membership", CasinoConfig.MEMBERSHIP, false)

        return builder.build()
    }

    fun onCasinoInfo(message: IMessage, mentioned: IUser): EmbedObject {
        CasinoConfig.readCasino(mentioned, message.guild)
        val builder = EmbedBuilder()
        builder.withColor(message.author.getColorForGuild(message.guild))
        builder.withAuthorName(message.author.name + "#" + message.author.discriminator)
        builder.withAuthorIcon(message.author.avatarURL)
        builder.withTimestamp(Instant.now())
        builder.withFooterIcon(message.guild.iconURL)
        builder.withFooterText(message.guild.name)
        builder.appendField("Casino Member", "" + mentioned.name, false)
        builder.appendField("Casino Balance", "" + nf.format(CasinoConfig.CHIPS.toLong()), false)
        builder.appendField("Casino Membership", CasinoConfig.MEMBERSHIP, false)

        return builder.build()
    }

    fun casinoPayday(message: IMessage, payday: Any): EmbedObject {
        val builder = EmbedBuilder()
        builder.withTitle("Casino")
        builder.withDesc("You have collected your " + nf.format(payday) + " payday")
        builder.withColor(message.author.getColorForGuild(message.guild))
        builder.withAuthorName(message.author.name + "#" + message.author.discriminator)
        builder.withAuthorIcon(message.author.avatarURL)
        builder.withTimestamp(Instant.now())
        builder.withFooterIcon(message.guild.iconURL)
        builder.withFooterText(message.guild.name)

        return builder.build()

    }


}
