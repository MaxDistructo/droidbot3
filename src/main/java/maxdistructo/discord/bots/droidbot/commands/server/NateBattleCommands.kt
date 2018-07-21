package maxdistructo.discord.bots.droidbot.commands.server

import maxdistructo.discord.bots.droidbot.background.EnumSelector
import maxdistructo.discord.bots.droidbot.background.coreadditions.ICommandRegistry
import maxdistructo.discord.core.command.BaseCommand
import maxdistructo.discord.core.command.IBaseListener
import maxdistructo.discord.core.message.Message
import sx.blah.discord.handle.obj.IMessage

object NateBattleCommands : ICommandRegistry {

    override fun registerCommands(listener : IBaseListener){
        val roleCard = RoleCard()
        listener.registerCommand(roleCard)
    }

    class RoleCard : BaseCommand(){
        override val commandName: String
            get() = "rolecard"
        override val hasOutput: Boolean
            get() = false
        override val helpMessage: String
            get() = "rolecard <role> - Shows the rolecard for the specified server role"

        override fun init(message: IMessage, args: List<String>): String {
            Message.sendMessage(message.channel, NateBattleRolecards.onAsk(message, EnumSelector.nateBattleSelector(args[1])))
            return ""
        }
    }



}