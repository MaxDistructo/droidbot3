package maxdistructo.discord.bots.droidbot.commands.mafia.action

import sx.blah.discord.handle.obj.IMessage

object RunActions {
    fun activate(message : IMessage){
        priority1(message)
        priority2(message)
        priority3(message)
        priority4(message)
        priority5(message)
        priority6(message)
    }
    private fun priority1(message : IMessage){
        DoAction.transport(message)
    }
    private fun priority2(message : IMessage){
        DoAction.escort(message)
        DoAction.witchery(message)
    }
    private fun priority3(message : IMessage){
        DoAction.investabsolute(message)
        DoAction.blackmail(message)
        DoAction.protect(message)
        DoAction.heal(message)
        DoAction.frame(message)
        DoAction.clean(message)
        DoAction.vest(message)
        DoAction.revive(message)
    }
    private fun priority4(message : IMessage){
        DoAction.investigate(message)
        DoAction.watch(message)
        DoAction.disguise(message)
        DoAction.sheriff(message)
    }
    private fun priority5(message : IMessage){
        DoAction.kill(message)
        DoAction.rampage(message)
    }
    private fun priority6(message : IMessage){
        DoAction.remember(message)
    }
}