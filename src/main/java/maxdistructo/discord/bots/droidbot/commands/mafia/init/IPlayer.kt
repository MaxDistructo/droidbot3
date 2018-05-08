package maxdistructo.droidbot2.commands.mafia.init

interface IPlayer {
    val role: String

    val allignment: String

    val dead: Boolean

    val attack: Int

    val defence: Int
}
