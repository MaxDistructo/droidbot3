package maxdistructo.droidbot2.commands.mafia.obj

import maxdistructo.droidbot2.commands.mafia.init.IAction
import org.json.JSONObject

class Action(player : Long, target: Long, target2 : Long, action: String) : IAction {
    private var privTarget : Long = target
    private var privTarget2 : Long = target2
    private var privAction = action
    private var privPlayer = player

    override val target: Long
        get() = privTarget

    override val target2: Long
        get() = privTarget2

    override val action: String
        get() = privAction

    override val player: Long
        get() = privPlayer

    fun toJSON() : JSONObject{
        val obj = JSONObject()
        val json = JSONObject()
        obj.put("target", privTarget)
        obj.put("target2", privTarget2)
        obj.put("action", privAction)
        json.put("" + player, obj)
        return json
    }

}