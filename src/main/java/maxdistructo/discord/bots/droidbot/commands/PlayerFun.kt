package maxdistructo.discord.bots.droidbot.commands

import maxdistructo.discord.bots.droidbot.background.constructor.BaseCommand
import maxdistructo.discord.core.Utils
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser

object PlayerFun //Additions Ideas: Shoot,Stab,Fake mute
{
    class Slap : BaseCommand(){
        override val commandName: String
            get() = "slap"
        override val helpMessage: String
            get() = "slap <@User> - Slaps another user"
        override fun init(message: IMessage, args: List<String>): String {
            return onSlapCommand(message, Utils.getMentionedUser(message)!!)
        }
    }

    fun onSlapCommand(message: IMessage, mentioned: IUser): String {
        return if (mentioned !== message.client.ourUser) {
            mentioned.toString() + " you have been slapped by " + message.author + ":clap:"
        } else {
            message.client.ourUser.toString() + " has slapped you " + message.author + " :clap:"
        }
    }

    class Tnt : BaseCommand(){
        override val commandName: String
            get() = "tnt"
        override val helpMessage: String
            get() = "tnt <@User> - Blows another user up with TNT"

        override fun init(message: IMessage, args: List<String>): String {
            return onTntCommand(message, Utils.getMentionedUser(message)!!)
        }
    }

    fun onTntCommand(message: IMessage, mentioned: IUser): String {
        return if (mentioned !== message.client.ourUser) {
            mentioned.toString() + " you have been blown up by " + message.author + " using TNT! :boom:"
        } else {
            message.client.ourUser.toString() + " has blown " + message.author + " up using TNT! :boom:"
        }
    }

    class Kiss : BaseCommand(){
        override val commandName: String
            get() = "kiss"
        override val helpMessage: String
            get() = "kiss <@User> - Kisses another user"

        override fun init(message: IMessage, args: List<String>): String {
            return onKissCommand(message, Utils.getMentionedUser(message)!!)
        }
    }

    fun onKissCommand(message: IMessage, mentioned: IUser): String {
        return if (mentioned !== message.client.ourUser) {
            mentioned.toString() + " you have been kissed by " + message.author + ":lips:"
        } else {
            message.client.ourUser.toString() + " has slapped you upside the head! :clap:"
        }
    }
    class Hug : BaseCommand(){
        override val commandName: String
            get() = "hug"
        override val helpMessage: String
            get() = "hug <@User> - Hugs another user"

        override fun init(message: IMessage, args: List<String>): String {
            return onHugCommand(message, Utils.getMentionedUser(message)!!)
        }
    }

    fun onHugCommand(message: IMessage, mentioned: IUser): String {
        return mentioned.toString() + " you have been hugged by " + message.author + ":hugging:"
    }
    class Poke : BaseCommand(){
        override val commandName: String
            get() = "poke"
        override val helpMessage: String
            get() = "poke <@User> - Pokes another user"

        override fun init(message: IMessage, args: List<String>): String {
            return onPokeCommand(message, Utils.getMentionedUser(message)!!)
        }
    }

    fun onPokeCommand(message: IMessage, mentioned: IUser): String {
        return if (mentioned !== message.client.ourUser) {
            mentioned.toString() + " you have been poked by " + message.author + ":point_right: "
        } else {
            "You can't poke a bot " + message.author + "!"
        }
    }
    class Respect : BaseCommand(){
        override val commandName: String
            get() = "rip"
        override val helpMessage: String
            get() = "rip <@User> - Allows you to pay respects"

        override fun init(message: IMessage, args: List<String>): String {
            return onPayRespects(message, Utils.getMentionedUser(message)!!)
        }
    }

    fun onPayRespects(message: IMessage, mentioned: IUser?): String { // /f command.
        return message.author.toString() + " pays their respects. https://cdn.discordapp.com/emojis/294160585179004928.png"
    }

    class BanHammer : BaseCommand(){
        override val commandName: String
            get() = "banhammer"
        override val helpMessage: String
            get() = "banhammer <@User> - Swings the banhammer at a user. May or may not miss."

        override fun init(message: IMessage, args: List<String>): String {
            return onBanHammer(message, Utils.getMentionedUser(message)!!)
        }
    }

    fun onBanHammer(message: IMessage, mentioned: IUser): String {
        return message.author.toString() + " picks up the <:blobhammer:426815241557508116> and prepares to swing it at " + mentioned + "! It misses " + mentioned + " by a hair and they live to see another day!"
    }

    class Shoot : BaseCommand(){
        override val commandName: String
            get() = "shoot"
        override val helpMessage: String
            get() = "shoot <@User> - Shoots another another user"

        override fun init(message: IMessage, args: List<String>): String {
            return onShootCommand(message, Utils.getMentionedUser(message)!!)
        }
    }

    fun onShootCommand(message: IMessage, mentioned: IUser): String {
        return if (mentioned !== message.client.ourUser) {
            message.author.toString() + " picks up a gun and shoots " + mentioned + "! "
        } else {
            "How dare you try to shoot me! *takes out rocket launcher* You shall pay! :boom:"
        }

    }

    class Stab : BaseCommand(){
        override val commandName: String
            get() = "stab"
        override val helpMessage: String
            get() = "stab <@User> - Stabs another user"

        override fun init(message: IMessage, args: List<String>): String {
            return onStabCommand(message, Utils.getMentionedUser(message)!!)
        }
    }

    fun onStabCommand(message: IMessage, mentioned: IUser): String {
        return if (mentioned !== message.client.ourUser) {
            mentioned.toString() + " was attacked by " + message.author + " using a knife!"
        } else {
            "\"breaks knife like a toothpick\" \n \"takes out sword\" \n \"slices " + message.author + " in half\" \n GET REKT!!! (John Cena Music plays)"
        }


    }

    fun onMuteCommand(message: IMessage, mentioned: IUser): String {
        return mentioned.toString() + " was muted by " + message.author + " for 1 second."
    }

    fun onLennyCommand(): String {
        return "( ͡° ͜ʖ ͡°)"
    }

    fun onShrugCommand(): String {
        return "¯\\_(ツ)_/¯"
    }

    class Xp : BaseCommand(){
        override val commandName: String
            get() = "xp"
        override val helpMessage: String
            get() = "xp <@User> - Tatsumaki XP System Information"

        override fun init(message: IMessage, args: List<String>): String {
            return onXpCommand(Utils.getMentionedUser(message)!!)
        }
    }

    fun onXpCommand(mentioned: IUser): String {
        return mentioned.toString() + "\n ```Diff\n" +
                "- How the XP system works:\n" +
                "XP is gained every time you talk with a 2 minute cooldown. XP is randomized from 10-20. There is global and local XP, local XP is XP gained on that server and is represented in the user's server score. Global XP is global and cannot be modified using commands. \n" +
                "\n" +
                "+ How to get credits: \n" +
                "You get credits for Talking, Leveling, and Using t!dailies. Credits are global and cannot be modified by a command. \n" +
                "```"
    }

    class Punch : BaseCommand(){
        override val commandName: String
            get() = "punch"
        override val helpMessage: String
            get() = "punch <@User> - Punches another user"

        override fun init(message: IMessage, args: List<String>): String {
            return onPunchCommand(message, Utils.getMentionedUser(message)!!)
        }
    }

    fun onPunchCommand(message: IMessage, mentioned: IUser): String {
        return if (mentioned !== message.client.ourUser) {
            mentioned.toString() + ", you have been punched by " + message.author + "! :punch:"
        } else {
            "\"ducks aside\""
        }
    }
}
