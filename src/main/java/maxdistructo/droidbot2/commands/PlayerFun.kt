package maxdistructo.droidbot2.commands

import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser

object PlayerFun //Additions Ideas: Shoot,Stab,Fake mute
{
    fun onSlapCommand(message: IMessage, mentioned: IUser): String {
        return if (mentioned !== message.client.ourUser) {
            mentioned.toString() + " you have been slapped by " + message.author + ":clap:"
        } else {
            message.client.ourUser.toString() + " has slapped you " + message.author + " :clap:"
        }
    }

    fun onTntCommand(message: IMessage, mentioned: IUser): String {
        return if (mentioned !== message.client.ourUser) {
            mentioned.toString() + " you have been blown up by " + message.author + " using TNT! :boom:"
        } else {
            message.client.ourUser.toString() + " has blown " + message.author + " up using TNT! :boom:"
        }
    }

    fun onMarryCommand(message: IMessage, mentioned: IUser): String {
        return "This command is WIP"
    }

    fun onKissCommand(message: IMessage, mentioned: IUser): String {
        return if (mentioned !== message.client.ourUser) {
            mentioned.toString() + " you have been kissed by " + message.author + ":lips:"
        } else {
            message.client.ourUser.toString() + " has slapped you upside the head! :clap:"
        }
    }

    fun onHugCommand(message: IMessage, mentioned: IUser): String {
        return mentioned.toString() + " you have been hugged by " + message.author + ":hugging:"
    }

    fun onPokeCommand(message: IMessage, mentioned: IUser): String {
        return if (mentioned !== message.client.ourUser) {
            mentioned.toString() + " you have been poked by " + message.author + ":point_right: "
        } else {
            "You can't poke a bot " + message.author + "!"
        }
    }

    fun onPayRespects(message: IMessage, mentioned: IUser?): String { // /f command.
        return message.author.toString() + " pays their respects. https://cdn.discordapp.com/emojis/294160585179004928.png"
    }

    fun onBanHammer(message: IMessage, mentioned: IUser): String {
        return message.author.toString() + " picks up the <:blobhammer:315285738302341121> and prepares to swing it at " + mentioned + "! It misses " + mentioned + " by a hair and they live to see another day!"
    }

    fun onShootCommand(message: IMessage, mentioned: IUser): String {
        return if (mentioned !== message.client.ourUser) {
            message.author.toString() + " picks up a gun and shoots " + mentioned + "! "
        } else {
            "How dare you try to shoot me! \"takes out rocket launcher\" You shall pay! :boom:"
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

    fun onXpCommand(mentioned: IUser): String {
        return mentioned.toString() + "\n ```Diff\n" +
                "- How the XP system works:\n" +
                "XP is gained every time you talk with a 2 minute cooldown. XP is randomized from 10-20. There is global and local XP, local XP is XP gained on that server and is represented in the user's server score. Global XP is global and cannot be modified using commands. \n" +
                "\n" +
                "+ How to get credits: \n" +
                "You get credits for Talking, Leveling, and Using t!dailies. Credits are global and cannot be modified by a command. \n" +
                "```"
    }

    fun onPunchCommand(message: IMessage, mentioned: IUser): String {
        return if (mentioned !== message.client.ourUser) {
            mentioned.toString() + ", you have been punched by " + message.author + "! :punch:"
        } else {
            "\"ducks aside\""
        }
    }
}
