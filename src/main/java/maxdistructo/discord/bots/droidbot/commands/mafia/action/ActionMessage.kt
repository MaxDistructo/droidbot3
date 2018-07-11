package maxdistructo.discord.bots.droidbot.commands.mafia.action

import maxdistructo.discord.bots.droidbot.commands.mafia.obj.Player
import maxdistructo.discord.core.message.Message

object ActionMessage {
    enum class Actions{
        HEAL,
        ATTACKED,
        TRANSPORTED,
        CONVERTED,
        IMMUNE,
        REMEMBERED,
        DNR,
        HEALED,
        RBED,
        NULL
    }
    fun getMessage(action : Enum<Actions>, user : Player){
        when(action){
            Actions.ATTACKED -> Message.sendDM(user.user, "You were attacked last night!")
            Actions.REMEMBERED -> Message.sendDM(user.user, "You have remembered who you were.")
            Actions.CONVERTED -> Message.sendDM(user.user, "You have been converted to a Vampire!")
            Actions.IMMUNE -> Message.sendDM(user.user, "Your target was immune to your attack!")
            Actions.DNR -> Message.sendDM(user.user, "Your target did not require healing.")
            Actions.RBED -> Message.sendDM(user.user, "You were roleblocked last night.")
            Actions.TRANSPORTED -> Message.sendDM(user.user, "You were transported")
            Actions.HEAL -> Message.sendDM(user.user, "You were attacked and healed")
            Actions.HEALED -> Message.sendDM(user.user, "You have successfully healed your target.")
        }
    }
}