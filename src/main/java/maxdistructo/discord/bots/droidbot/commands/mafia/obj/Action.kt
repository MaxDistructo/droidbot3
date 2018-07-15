package maxdistructo.discord.bots.droidbot.commands.mafia.obj

import maxdistructo.discord.bots.droidbot.commands.mafia.init.IAction
import org.json.JSONObject

class Action : IAction {
    private var privTarget: Long = target
    private var privTarget2: Long = target2
    private var privAction = action
    private var privPlayer = player
    private var privExtra = extra

    override val target: Long
        get() = privTarget

    override val target2: Long
        get() = privTarget2

    override val action: String
        get() = privAction

    override val player: Long
        get() = privPlayer

    val extra : Any
        get() = privExtra

    fun toJSON(): JSONObject {
        val obj = JSONObject()
        obj.put("target", privTarget)
        obj.put("target2", privTarget2)
        obj.put("action", privAction)
        obj.put("extra", privExtra)
        return obj
    }

    constructor() {
        privTarget = 0
        privTarget2 = 0
    }

    constructor(player: Long, target: Long, target2: Long, action: String, extra : Any) {
        privTarget = target
        privTarget2 = target2
        privAction = action
        privPlayer = player
        privExtra = extra
    }

    constructor(player: Long, root: JSONObject) {
        privTarget = root.getLong("target")
        privTarget2 = root.getLong("target2")
        privAction = root.getString("action")
        privPlayer = player
        privExtra = root.get("extra")
    }

}