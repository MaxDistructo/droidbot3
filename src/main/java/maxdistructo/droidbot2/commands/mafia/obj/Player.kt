package maxdistructo.droidbot2.commands.mafia.obj

import maxdistructo.droidbot2.commands.mafia.init.IPlayer
import maxdistructo.droidbot2.commands.mafia.MafiaConfig
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser

class Player : IPlayer {
    private var details: Array<Any>? = null
    private var privPlayerID : Long? = 0
    override val role: String
        get() = details!![2] as String

    override val allignment: String
        get() = details!![0] as String

    override val dead: Boolean
        get() = details!![3] as Boolean

    override val attack: Int
        get() = details!![4] as Int

    override val defence: Int
        get() = details!![5] as Int

    constructor() {
        details = null
        privPlayerID = null
    }
    constructor(detailsin : Array<Any>) {
        details = detailsin
        privPlayerID = null
    }

    constructor(message: IMessage, playerID : Long){
        details = MafiaConfig.getPlayerDetails(message, playerID)
        privPlayerID = playerID
    }
    constructor(message: IMessage, player : IUser){
        details = MafiaConfig.getPlayerDetails(message, player.longID)
        privPlayerID = player.longID
    }
}
