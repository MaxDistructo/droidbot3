package maxdistructo.discord.bots.droidbot.background

import maxdistructo.discord.bots.droidbot.commands.mafia.action.ActionMessage
import maxdistructo.discord.bots.droidbot.commands.server.NateBattleRoles

object EnumSelector {
    fun nateBattleSelector(name : String) : Enum<NateBattleRoles>{
        when(name.toLowerCase()){
            "pallet" -> return NateBattleRoles.PALLET
            "viridian" -> return NateBattleRoles.VIRIDIAN
            "vermilion" -> return NateBattleRoles.VERMILION
            "saffron" -> return NateBattleRoles.SAFFRON
            "pewter" -> return NateBattleRoles.PEWTER
            "lavender" -> return NateBattleRoles.LAVENDER
            "fuchsia" -> return NateBattleRoles.FUCHSIA
            "cinnabar" -> return NateBattleRoles.CINNABAR
            "cerulean" -> return NateBattleRoles.CERULEAN
            "celadon" -> return NateBattleRoles.CELADON
        }
        return NateBattleRoles.NULL
    }
    fun mafiaAction(name : String) : Enum<ActionMessage.Actions>{
       return when(name.toLowerCase()){
            "dnr" -> ActionMessage.Actions.DNR
            "healed" -> ActionMessage.Actions.HEALED
            "washealed" -> ActionMessage.Actions.HEAL
           "converted" -> ActionMessage.Actions.CONVERTED
           "attacked" -> ActionMessage.Actions.ATTACKED
           "transed" -> ActionMessage.Actions.TRANSPORTED
           "transported" -> ActionMessage.Actions.TRANSPORTED
           "remembered" -> ActionMessage.Actions.REMEMBERED
           "roleblocked" -> ActionMessage.Actions.RBED
           "rbed" -> ActionMessage.Actions.RBED
           else -> ActionMessage.Actions.NULL
        }
    }
}