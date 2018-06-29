package maxdistructo.discord.bots.droidbot.background.constructor

import sx.blah.discord.handle.obj.IMessage

interface ICommand{
    fun init(message : IMessage, args : List<String>) : String{
        return "Command Error: $commandName"
    }
    val requiresMod : Boolean
    val requiresAdmin : Boolean
    val commandName : String
    val helpMessage : String
    val hasOutput : Boolean
}