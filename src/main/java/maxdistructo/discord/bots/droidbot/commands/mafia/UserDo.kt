package maxdistructo.discord.bots.droidbot.commands.mafia

import maxdistructo.discord.bots.droidbot.commands.mafia.obj.Game
import maxdistructo.discord.bots.droidbot.commands.mafia.obj.Player
import maxdistructo.discord.core.Utils
import maxdistructo.discord.core.message.Message
import sx.blah.discord.handle.obj.IMessage

object UserDo {

    fun message(message: IMessage, messageContent: Array<Any>) {
        val player = Player(message, message.author)
        val game = Game(Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_dat.txt"))
        val mentioned = Utils.getUserFromInput(message, messageContent[2])
        when (player.role) {
            "mafioso" -> {
                Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " has voted to kill " + mentioned!!.getDisplayName(message.guild))
                Message.sendDM(message.author, "You have voted to kill " + mentioned.getDisplayName(message.guild))
                message.delete()
            }
            "godfather" -> {
                Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " has decided to kill " + mentioned!!.getDisplayName(message.guild))
                Message.sendDM(message.author, "You have decided to kill " + mentioned.getDisplayName(message.guild))
                message.delete()
            }
            "serial_killer" -> {
                Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " is gonna go and stab " + mentioned!!.getDisplayName(message.guild))
                Message.sendDM(message.author, "You have decided to go and stab " + mentioned.getDisplayName(message.guild))
                message.delete()
            }
            "werewolf" -> {
                Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " is gonna go and visit " + mentioned!!.getDisplayName(message.guild))
                Message.sendDM(message.author, "You have decided to go and rampage at " + mentioned.getDisplayName(message.guild) + "'s house")
                message.delete()
            }
            "arsonist" -> {
                if (mentioned!! !== message.author) {
                    Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " is gonna douse " + mentioned!!.getDisplayName(message.guild))
                    Message.sendDM(message.author, "You have decided to douse " + mentioned!!.getDisplayName(message.guild))
                } else {
                    Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " is gonna set all doused players on fire")
                    Message.sendDM(message.author, "You have decided to set all targets on fire tonight")
                }
                message.delete()
            }
            "jester" -> {
                Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " is gonna haunt " + mentioned!!.getDisplayName(message.guild))
                Message.sendDM(message.author, "You have decided to haunt " + mentioned!!.getDisplayName(message.guild))
                message.delete()
            }
            "veteran" -> {
                Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " is going on alert tonight.")
                Message.sendDM(message.author, "You have decided to go on alert tonight")
                message.delete()
            }
            "vigilante" -> {
                Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " is going to shoot " + mentioned!!.getDisplayName(message.guild))
                Message.sendDM(message.author, "You have decided to shoot " + mentioned!!.getDisplayName(message.guild))
                message.delete()
            }
            "jailor" -> {
                if (mentioned!!.longID == MafiaConfig.getJailed(message)) {
                    Message.sendMessage(game.adminChannel, "The jailor is going to shoot " + mentioned!!.getDisplayName(message.guild))
                    Message.sendMessage(message.channel, "You have decided to shoot " + mentioned!!.getDisplayName(message.guild))
                } else {
                    Message.sendDM(message.author, "You can only shoot the person you have jailed!")
                }
                message.delete()
            }
            "vampire" -> {
                Message.sendMessage(game.adminChannel, "The vampires are going to try and convert " + mentioned!!.getDisplayName(message.guild))
                Message.sendDM(message.author, "You will be biting " + mentioned!!.getDisplayName(message.guild))
                message.delete()
            }
            "vampire_hunter" -> {
                Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " will be checking and stabbing " + mentioned!!.getDisplayName(message.guild) + " if they are a vampire")
                Message.sendDM(message.author, "You will be checking " + mentioned.getDisplayName(message.guild))
                message.delete()
            }
            "lookout" -> {
                Message.sendDM(message.author, "You will be watching " + mentioned!!.getDisplayName(message.guild) + " tonight.")
                Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " will be watching " + mentioned!!.getDisplayName(message.guild) + " tonight.")
                message.delete()
            }
            "investigator" -> {
                Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " would like to investigate " + mentioned!!.getDisplayName(message.guild) + " tonight.")
                Message.sendDM(message.author, "You are going to investigate " + mentioned.getDisplayName(message.guild))
                message.delete()
            }
            "sheriff" -> {
                Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + "would like to interrogate " + mentioned!!.getDisplayName(message.guild) + " tonight.")
                Message.sendDM(message.author, "You are going to be interrogating " + mentioned!! + " tonight.")
                message.delete()
            }
            "transporter" -> {
                val target = Utils.getUserFromInput(message, messageContent[2])
                val invest = Utils.getUserFromInput(message, messageContent[3])
                Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " would like to swap positions of " + invest!!.getDisplayName(message.guild) + " & " + target!!.getDisplayName(message.guild))
                Message.sendDM(message.author, "You will be transporting " + invest.getDisplayName(message.guild) + " & " + target!!.getDisplayName(message.guild))
                message.delete()
            }
            "witch" -> {
                val target = Utils.getUserFromInput(message, messageContent[2])
                val invest = Utils.getUserFromInput(message, messageContent[3])
                Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " would like to control " + invest!!.getDisplayName(message.guild) + " into using their ability onto " + target!!.getDisplayName(message.guild))
                Message.sendDM(message.author, "You will be witching " + invest.getDisplayName(message.guild) + " into " + target!!.getDisplayName(message.guild))
                message.delete()
            }
            "doctor" -> {
                Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " will be healing " + mentioned!!.getDisplayName(message.guild) + " tonight.")
                Message.sendDM(message.author, "You will be healing " + mentioned!!.getDisplayName(message.guild) + " tonight.")
                message.delete()
            }
            "bodyguard" -> {
                Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " will be guarding " + mentioned!!.getDisplayName(message.guild) + " tonight.")
                Message.sendDM(message.author, "You will be guarding " + mentioned!!.getDisplayName(message.guild) + " tonight.")
                message.delete()
            }
            "escort" -> {
                Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " would like to roleblock " + mentioned!! + " tonight.")
                Message.sendDM(message.author, "You will be escorting " + mentioned!!.getDisplayName(message.guild) + " tonight.")
                message.delete()
            }
            "consort" -> {
                Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " would like to roleblock " + mentioned!! + " tonight.")
                Message.sendDM(message.author, "You will be escorting " + mentioned!!.getDisplayName(message.guild) + " tonight.")
                message.delete()
            }
            "mayor" -> {
                Message.sendMessage(message.channel, message.author.getDisplayName(message.guild) + " has revealed themselves as the Mayor!")
                //Set the variable for mayor reveal to true
                val f = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_playerdat.txt").getJSONObject("" + message.author.longID)
                f.remove("revealed")
                f.put("revealed", true)
                MafiaConfig.writeGame(message, f)
                Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " has revealed as the mayor. Their votes now count as 3.")
                message.delete()
            }

            "medium" -> {
                if (!game.day && player.dead) {
                    Message.sendMessage(game.adminChannel, message.author.getDisplayName(message.guild) + " would like to talk to " + mentioned!!.getDisplayName(message.guild))
                    Message.sendDM(message.author, "Your message has been sent to the Admin. Please wait for them to respond to your secance request")
                    message.delete()
                }
            }
            "retributionist" -> {
                Message.sendMessage(game.adminChannel, message.author.name + " will be reviving " + mentioned!! + " tonight.")
                Message.sendDM(message.author, "You will be reviving " + mentioned!!.getDisplayName(message.guild) + " tonight.")
                message.delete()
            }
            "disguiser" -> {
                Message.sendMessage(game.adminChannel, message.author.name + " is going to be disguised as " + mentioned!!.getDisplayName(message.guild) + " in role.")
                Message.sendDM(message.author, "You will be disguising as " + mentioned!!.getDisplayName(message.guild))
                message.delete()
            }
            "forger" -> {
                Message.sendMessage(game.adminChannel, message.author.name + " is gonna forge the role of " + mentioned!!.getDisplayName(message.guild) + " to be Forger.")
                Message.sendDM(message.author, "You will be forging the role of " + mentioned!!.getDisplayName(message.guild))
                message.delete()
            }
            "framer" -> {
                Message.sendMessage(game.adminChannel, message.author.name + " is gonna frame " + mentioned!!.getDisplayName(message.guild))
                Message.sendDM(message.author, "You will be framing " + mentioned!!.getDisplayName(message.guild))
                message.delete()
            }
            "janitor" -> {
                Message.sendMessage(game.adminChannel, message.author.name + " would like to clean the role of " + Utils.getMentionedUser(message)!!.getDisplayName(message.guild))
                Message.sendDM(message.author, "You will be cleaning " + mentioned!!.getDisplayName(message.guild))
                message.delete()
            }
            "blackmailer" -> {
                Message.sendMessage(game.adminChannel, message.author.name + "would like to Blackmail " + mentioned!!.getDisplayName(message.guild))
                Message.sendDM(message.author, "You will be blackmailing " + mentioned!!.getDisplayName(message.guild))
                message.delete()
            }
            "consigerge" -> {
                Message.sendMessage(game.adminChannel, message.author.name + " would like to know the role of " + mentioned!!.getDisplayName(message.guild))
                Message.sendDM(message.author, "You will receive the role of " + mentioned!!.getDisplayName(message.guild))
                message.delete()
            }
            "amnesiac" -> {
                Message.sendMessage(game.adminChannel, message.author.name + " would like to remember the role of " + mentioned!!.getDisplayName(message.guild))
                Message.sendDM(message.author, "You will remember the role of " + mentioned!!.getDisplayName(message.guild))
                message.delete()
            }
            "survivor" -> {
                Message.sendMessage(game.adminChannel, message.author.name + " will be putting on a vest tonight.")
                Message.sendDM(message.author, "You will be putting on a vest tonight.")
                message.delete()
            }
            "tracker" -> {
                Message.sendMessage(game.adminChannel, message.author.name + " will be tracking " + mentioned!!.getDisplayName(message.guild) + " tonight.")
                Message.sendDM(message.author, "You will be tracking " + mentioned.getDisplayName(message.guild))
                message.delete()
            }
            "trapper" -> {
                Message.sendMessage(game.adminChannel, message.author.name + " will be placing a trap at " + mentioned!!.getDisplayName(message.guild) + "'s house tonight.")
                Message.sendDM(message.author, "You will be placing a trap at " + mentioned!!.getDisplayName(message.guild) + "'s house.")
                message.delete()
            }
            "court_wizard" -> {
                Message.sendMessage(game.adminChannel, message.author.name + " will protect " + mentioned!!.getDisplayName(message.guild) + " from Escorts, Consorts, Transporters, and Witches")
                Message.sendDM(message.author, "You will be guarding " + mentioned.getDisplayName(message.guild))
                message.delete()
            }

        }


    }
}
