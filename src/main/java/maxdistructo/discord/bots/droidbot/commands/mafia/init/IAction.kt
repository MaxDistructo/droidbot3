package maxdistructo.discord.bots.droidbot.commands.mafia.init

interface IAction {
    val player: Long
    val target: Long
    val target2: Long
    val action: String
}