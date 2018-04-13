package maxdistructo.droidbot2.commands.mafia.action

import maxdistructo.droidbot2.commands.mafia.Kill
import maxdistructo.droidbot2.commands.mafia.Mafia
import maxdistructo.droidbot2.commands.mafia.MafiaConfig
import maxdistructo.droidbot2.commands.mafia.UserDo
import maxdistructo.droidbot2.commands.mafia.obj.Action
import maxdistructo.droidbot2.commands.mafia.obj.Game
import maxdistructo.droidbot2.commands.mafia.obj.Player
import maxdistructo.droidbot2.core.Utils
import maxdistructo.droidbot2.core.message.Message
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser

class DoAction {
    fun addAction(message : IMessage, playerIn : IUser, target : Long, target2 : Long, actionIn : String){
        val action = Action(playerIn.longID, target, target2, actionIn)
        val actions = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_actions.txt")
        actions.remove("" + action.player)
        actions.append("" + action.player, action.toJSON())
        MafiaConfig.writeActions(message, actions)
    }
    fun addAction(message : IMessage, action : Action){
        val actions = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_actions.txt")
        actions.remove("" + action.player)
        actions.append("" + action.player, action.toJSON())
        MafiaConfig.writeActions(message, actions)
    }
    fun escort(message : IMessage){
        val actions = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_actions.txt")
        for(user in MafiaConfig.getPlayers(message, "Mafia Folks")) {
            val action = Action(user, actions.getJSONObject("" + user))
            val target = Player(message, action.target)
            if(action.action == "escort"){
                if(target.role != "serial_killer" || target.role != "werewolf" || target.role != "witch" || target.role != "escort" || target.role != "consort" || target.role != "juggernaut" || target.role != "veteran") {
                    actions.remove("" + action.target)
                    Message.sendDM(message.guild.getUserByID(user), "You have been roleblocked.")
                }
                else if (target.role == "serial_killer" || target.role == "werewolf" || target.role == "juggernaut"){
                    Message.sendDM(message.guild.getUserByID(user), "You have been killed by the person you visited.")
                    Kill.message(message, arrayOf("!mafia", "kill", target.role)) //Check that in roles.dat the name for the kill lines are also setup for the full role name of the killer.
                }
                else{
                    Message.sendDM(message.guild.getUserByID(user), "Your target was immune to your roleblock.")
                }
            }
        }
        MafiaConfig.writeActions(message, actions)
    }
    fun witchery(message : IMessage){
        val actions = Utils.readJSONFromFile("/config/mafia/" + message.guild.longID + "_actions.txt")
        for(user in MafiaConfig.getPlayers(message, "Mafia Folks")){
            lateinit var action : Action
            try {
                action = Action(user, actions.getJSONObject("" + user))
            }
            catch(e : Exception){
                Message.throwError(e, message)
            }
            if(action.action == "witch"){
                val targetAction = actions.getJSONObject("" + action.target)
                targetAction.remove("target")
                targetAction.put("target", action.target2)
            }
        }
        MafiaConfig.writeActions(message, actions)
    }

}