package maxdistructo.discord.bots.droidbot.commands

import maxdistructo.discord.bots.droidbot.BaseBot
import maxdistructo.discord.bots.droidbot.commands.mafia.methods.Perms
import maxdistructo.discord.core.Config
import maxdistructo.discord.core.command.BaseCommand
import maxdistructo.discord.core.message.Message
import sx.blah.discord.handle.obj.IMessage

class Help : BaseCommand(){

    override val commandName: String
        get() = "help"
    override val helpMessage: String
        get() = "help - Resends this message"
    override val hasOutput: Boolean
        get() = false

    override fun init(message: IMessage, args: List<String>): String {
        val builder = StringBuilder()
        when{
            Perms.checkAdmin(message) ->{
                var i = 0
                for(command in BaseBot.listener.adminCommands){
                    if(i != 0) {
                        builder.append(Config.readPrefix() + command.helpMessage)
                        builder.append("\n")
                    }
                    i++
                }
            }
            Perms.checkMod(message) ->{
                var i = 0
                for (command in BaseBot.listener.modCommands) {
                    if(i != 0) {
                        builder.append(Config.readPrefix() + command.helpMessage)
                        builder.append("\n")
                    }
                    i++
                }

            }
            else ->{
                var i = 0
                for(command in BaseBot.listener.commandsArray){
                    if(i != 0) {
                        builder.append(Config.readPrefix() + command.helpMessage)
                        builder.append("\n")
                    }
                    i++
                }
            }
        }
        Message.sendDM(message.author, builder.toString())
        return ""
    }
}
