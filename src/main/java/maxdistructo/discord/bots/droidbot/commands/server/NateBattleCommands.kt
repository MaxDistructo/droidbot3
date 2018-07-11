package maxdistructo.discord.bots.droidbot.commands.server

import maxdistructo.discord.bots.droidbot.background.CommandRegistry
import maxdistructo.discord.bots.droidbot.background.EnumSelector
import maxdistructo.discord.core.command.BaseCommand
import maxdistructo.discord.core.message.Message
import sx.blah.discord.handle.obj.IMessage
import java.util.*

object NateBattleCommands {

    class NateBattleCommandRegistry : CommandRegistry(){
        override var commandHolder = LinkedList<BaseCommand>()
        init {
            val roleCard = RoleCard()
            this.commandHolder.add(roleCard)
        }
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