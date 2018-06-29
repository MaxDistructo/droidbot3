package maxdistructo.discord.bots.droidbot.background.constructor


open class BaseCommand : ICommand {
    override val requiresAdmin: Boolean
        get() = false
    override val commandName: String
        get() = "command"
    override val helpMessage: String
        get() = "command <params> - A description of the command"
    override val requiresMod: Boolean
        get() = false
    override val hasOutput : Boolean
        get() = true

    fun register(listener : BaseListener){
        listener.registerCommand(this)
    }
}