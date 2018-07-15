package maxdistructo.discord.bots.droidbot.commands.mafia.action

import maxdistructo.discord.bots.droidbot.BaseBot
import maxdistructo.discord.bots.droidbot.commands.mafia.MafiaListener
import maxdistructo.discord.bots.droidbot.commands.mafia.methods.Kill
import maxdistructo.discord.bots.droidbot.commands.mafia.methods.Mafia
import maxdistructo.discord.bots.droidbot.commands.mafia.methods.MafiaConfig
import maxdistructo.discord.bots.droidbot.commands.mafia.obj.Action
import maxdistructo.discord.bots.droidbot.commands.mafia.obj.Details
import maxdistructo.discord.bots.droidbot.commands.mafia.obj.Player
import maxdistructo.discord.bots.droidbot.commands.mafia.obj.Roles
import maxdistructo.discord.core.Config
import maxdistructo.discord.core.Utils
import maxdistructo.discord.core.message.Message
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser

object DoAction {
    fun addAction(message: IMessage, playerIn: IUser, target: Long, target2: Long, actionIn: String, extra : Any?) {
        var action : Action = if(extra != null) {
            Action(playerIn.longID, target, target2, actionIn, extra)
        }
        else{
            Action(playerIn.longID, target, target2, actionIn, "none")
        }
        val actions = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_actions.txt")
        actions.remove("" + action.player)
        actions.append("" + action.player, action.toJSON())
        MafiaConfig.writeActions(message, actions)
    }
    fun addAction(message: IMessage, playerIn: IUser, target: Long, target2: Long, actionIn: String) {
        var action : Action = Action(playerIn.longID, target, target2, actionIn, "none")

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
                    Kill.message(message, arrayOf("!mafia", "kill", action.player.toString(),  target.role)) //Check that in roles.dat the name for the kill lines are also setup for the full role name of the killer.
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
            if (action.action == "invest") {
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
            if (action.action == "interrogate") {
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

    fun rampage(message : IMessage){
        val actions = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_actions.txt")
        var kills : Int = 0
        for(user in MafiaConfig.getPlayers(message, "Mafia Folks")){
            lateinit var action: Action
            try {
                action = Action(user, actions.getJSONObject("" + user))
            } catch (e: Exception) {
                e.localizedMessage
            }
            var original = Player(message, action.player)
            if(action.action == "rampage"){
                for(user2 in MafiaConfig.getPlayers(message, "Mafia Folks")) {
                    var action2 = Action(user2, actions.getJSONObject("" + user2))
                    val player2 = Player(message, action2.player)
                    if (player2.defence < original.attack) {
                        if (action2.target == action.target || action2.target2 == action.target && action2.player != action.player) {
                            kills++
                            Kill.message(message, arrayOf(Config.readPrefix() + "mafia", "kill", action2.player.toString(), original.role))
                        }

                        if (player2.role == "veteran" && MafiaConfig.getExtra(message, player2.id) as Boolean) {
                            Kill.message(message, arrayOf(Config.readPrefix() + "mafia", "kill", action.player.toString(), player2.role))
                        }
                    }
                }
            }
        }
    }
    fun kill(message : IMessage){
        val actions = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_actions.txt")
        for(user  in MafiaConfig.getPlayers(message, "Mafia Folks")){
            lateinit var action: Action
            try {
                action = Action(user, actions.getJSONObject("" + user))
            } catch (e: Exception) {
                e.localizedMessage
            }
            val player1 = Player(message, action.player)
            if(action.action == "kill"){
                val player = Player(message, action.target)
                if(player.defence < player1.attack){
                    Kill.message(message, arrayOf(Config.readPrefix(), "mafia", "kill", action.target.toString(), player1.role))
                }
            }
        }
    }

    fun convert(message : IMessage){ //For use with Vampires
        val actions = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_actions.txt")
        for(user  in MafiaConfig.getPlayers(message, "Mafia Folks")){
            lateinit var action: Action
            try {
                action = Action(user, actions.getJSONObject("" + user))
            } catch (e: Exception) {
                e.localizedMessage
            }
            val player1 = Player(message, action.player)
            if(action.action == "convert"){
                val player = Player(message, action.target)
                if(player.defence < player1.attack){
                    Mafia.setRole(message, BaseBot.client.getUserByID(action.target), player1.role)
                    Message.sendDM(player.user, "You have been converted to a " + player1.role)
                }
            }
        }
    }

    fun vest(message : IMessage){
        val actions = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_actions.txt")
        for(user  in MafiaConfig.getPlayers(message, "Mafia Folks")){
            lateinit var action: Action
            try {
                action = Action(user, actions.getJSONObject("" + user))
            } catch (e: Exception) {
                e.localizedMessage
            }
            val player = Player(message, action.player)
            if(action.action == "vest"){
                MafiaConfig.editDetails(message, player.user, Details.DEFENCE, 1)
                setDirty(player.user, Details.DEFENCE, 0) //Tells bot that the value needs to be set back on day toggle.
            }
        }
    }

    fun watch(message : IMessage){
        val actions = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_actions.txt")
        for(user  in MafiaConfig.getPlayers(message, "Mafia Folks")){
            lateinit var action: Action
            try {
                action = Action(user, actions.getJSONObject("" + user))
            } catch (e: Exception) {
                e.localizedMessage
            }

            if(action.action == "watch"){
                val builder = StringBuilder()
                builder.append("Your target was visited by: \n")
                for(user2 in MafiaConfig.getPlayers(message, "Mafia Folks")){
                    for(user3  in MafiaConfig.getPlayers(message, "Mafia Folks")){
                        lateinit var action2: Action
                        try {
                            action2 = Action(user, actions.getJSONObject("" + user))
                        } catch (e: Exception) {
                            e.localizedMessage
                        }
                        if(action2.target == action.target){
                            builder.append(message.guild.getUserByID(action2.player).name + "\n")
                        }
                    }
                }
                Message.sendDM(message.guild.getUserByID(action.player), builder.toString())
            }
        }
    }

    private fun setDirty(user : IUser, detail : Enum<Details>, originalValue : Any){
        MafiaListener.addDirtyValue(Triple(user, detail, originalValue))
    }

    //TODO revive, seance, clean (set extra tag on kill), frame(set extra tag on interrogates and invest), forge, heal (removes kills on target), disguise(sets extra tag on kills, invest, and interrogates), invest-absolute, protect(sets target defence as dirty to be 1 unless has defence higher) , blackmail, remember

}