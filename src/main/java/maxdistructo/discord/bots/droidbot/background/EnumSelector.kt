package maxdistructo.discord.bots.droidbot.background

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
}