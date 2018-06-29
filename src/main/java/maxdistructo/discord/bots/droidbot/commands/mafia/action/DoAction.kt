package maxdistructo.discord.bots.droidbot.commands.mafia.action

import maxdistructo.discord.bots.droidbot.commands.mafia.Kill
import maxdistructo.discord.bots.droidbot.commands.mafia.MafiaConfig
import maxdistructo.discord.bots.droidbot.commands.mafia.obj.Action
import maxdistructo.discord.bots.droidbot.commands.mafia.obj.Player
import maxdistructo.discord.core.Utils
import maxdistructo.discord.core.message.Message
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser

class DoAction {
    fun addAction(message: IMessage, playerIn: IUser, target: Long, target2: Long, actionIn: String) {
        val action = Action(playerIn.longID, target, target2, actionIn)
        val actions = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_actions.txt")
        actions.remove("" + action.player)
        actions.append("" + action.player, action.toJSON())
        MafiaConfig.writeActions(message, actions)
    }

    fun addAction(message: IMessage, action: Action) {
        val actions = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_actions.txt")
        actions.remove("" + action.player)
        actions.append("" + action.player, action.toJSON())
        MafiaConfig.writeActions(message, actions)
    }

    fun escort(message: IMessage) {
        val actions = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_actions.txt")
        for (user in MafiaConfig.getPlayers(message, "Mafia Folks")) {
            val action = Action(user, actions.getJSONObject("" + user))
            val target = Player(message, action.target)
            if (action.action == "escort") {
                if (target.role != "serial_killer" || target.role != "werewolf" || target.role != "witch" || target.role != "escort" || target.role != "consort" || target.role != "juggernaut" || target.role != "veteran" || target.role != "transporter") {
                    actions.remove("" + action.target)
                    Message.sendDM(message.guild.getUserByID(user), "You have been roleblocked.")
                } else if (target.role == "serial_killer" || target.role == "werewolf" || target.role == "juggernaut") {
                    Message.sendDM(message.guild.getUserByID(user), "You have been killed by the person you visited.")
                    Kill.message(message, arrayOf("!mafia", "kill", target.role)) //Check that in roles.dat the name for the kill lines are also setup for the full role name of the killer.
                } else {
                    Message.sendDM(message.guild.getUserByID(user), "Your target was immune to your roleblock.")
                }
            }
        }
        MafiaConfig.writeActions(message, actions)
    }

    fun witchery(message: IMessage) {
        val actions = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_actions.txt")
        for (user in MafiaConfig.getPlayers(message, "Mafia Folks")) {
            lateinit var action: Action
            try {
                action = Action(user, actions.getJSONObject("" + user))
            } catch (e: Exception) {
                e.localizedMessage
            }
            if (action.action == "witch") {
                val targetAction = actions.getJSONObject("" + action.target)
                targetAction.remove("target")
                targetAction.put("target", action.target2)
            }
        }
        MafiaConfig.writeActions(message, actions)
    }

    fun transport(message: IMessage) {
        val actions = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_actions.txt")
        for (user in MafiaConfig.getPlayers(message, "Mafia Folks")) {
            lateinit var action: Action
            try {
                action = Action(user, actions.getJSONObject("" + user))
            } catch (e: Exception) {
                e.localizedMessage
            }
            if (action.action == "transport") {
                for (player in MafiaConfig.getPlayers(message, "Mafia Folks")) {
                    if (action.target == MafiaConfig.getJailed(message) || action.target2 == MafiaConfig.getJailed(message)) {
                        Message.sendDM(message.guild.getUserByID(player), "One of your targets was Jailed so your transport failed.")
                        break
                    } else {
                        lateinit var action2: Action
                        try {
                            action2 = Action(user, actions.getJSONObject("" + user))
                        } catch (e: Exception) {
                            e.localizedMessage
                        }
                        if (action2.target == action.target) {
                            val j = actions.getJSONObject("" + player)
                            j.remove("target")
                            j.put("target", action.target2)
                        }
                        if (action2.target2 == action.target2) {
                            val j = actions.getJSONObject("" + player)
                            j.remove("target")
                            j.put("target", action.target)
                        }
                        if (action2.target == action.target2) {
                            val j = actions.getJSONObject("" + player)
                            j.remove("target")
                            j.put("target", action.target)
                        }
                        if (action2.target2 == action.target) {
                            val j = actions.getJSONObject("" + player)
                            j.remove("target")
                            j.put("target", action.target2)
                        }
                    }
                }
            }
        }
    }

    fun investigate(message: IMessage) {
        val actions = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_actions.txt")
        for (user in MafiaConfig.getPlayers(message, "Mafia Folks")) {
            lateinit var action: Action
            try {
                action = Action(user, actions.getJSONObject("" + user))
            } catch (e: Exception) {
                e.localizedMessage
            }
            if (action.action == "investigate") {
                val target = Player(message, user)
                Message.sendDM(message.guild.getUserByID(user), MafiaConfig.investResults(message).getString(target.role))
            }
        }
    }
    fun sheriff(message : IMessage){
        val actions = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_actions.txt")
        for (user in MafiaConfig.getPlayers(message, "Mafia Folks")) {
            lateinit var action: Action
            try {
                action = Action(user, actions.getJSONObject("" + user))
            } catch (e: Exception) {
                e.localizedMessage
            }
            if (action.action == "sheriff") {
                val target = Player(message, user)
                if(target.allignment == "town" || target.role == "godfather" || target.role == "amnesiac" || target.role == "survivor" || target.role == "witch") {
                    Message.sendDM(message.guild.getUserByID(user), message.guild.getUserByID(user).getDisplayName(message.guild) + "is Not Suspicious.")
                }
                else if(target.allignment == "mafia"){
                    Message.sendDM(message.guild.getUserByID(user), message.guild.getUserByID(user).getDisplayName(message.guild) + "is a Member of the Mafia!")
                }
                else{
                    Message.sendDM(message.guild.getUserByID(user), message.guild.getUserByID(user).getDisplayName(message.guild) + "is a " + target.role.toUpperCase() + "!")
                }
            }
        }
    }


}