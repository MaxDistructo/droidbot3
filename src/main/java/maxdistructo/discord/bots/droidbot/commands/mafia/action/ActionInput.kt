package maxdistructo.discord.bots.droidbot.commands.mafia.action

import maxdistructo.discord.bots.droidbot.commands.mafia.methods.UserDo
import maxdistructo.discord.bots.droidbot.commands.mafia.obj.Game
import maxdistructo.discord.bots.droidbot.commands.mafia.obj.Player
import maxdistructo.discord.bots.droidbot.commands.mafia.obj.Roles
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser

object ActionInput {
    fun submitAction(player : Player, game : Game, target : IUser, target2 : IUser, message : IMessage){
        UserDo.message(message, message.content.split(" ").toTypedArray())
        when(player.roleEnum){
            Roles.ARSONIST -> {
                DoAction.addAction(message, player.user, target.longID, 0, "kill")
            }
            Roles.AMNESIAC ->{
                DoAction.addAction(message, player.user, target.longID, 0, "remember")
            }
            Roles.BLACKMAIL ->{
                DoAction.addAction(message, player.user, target.longID, 0, "blackmail")
            }
            Roles.BODYGUARD ->{
                DoAction.addAction(message, player.user, target.longID, 0, "protect")
            }
            Roles.CONSIG ->{
                DoAction.addAction(message, player.user, target.longID, 0, "invest-absolute")
            }
            Roles.CONSORT ->{
                DoAction.addAction(message, player.user, target.longID, 0, "escort")
            }
            Roles.DISGUISER ->{
                DoAction.addAction(message, player.user, target.longID, 0, "disguise")
            }
            Roles.DOCTOR ->{
                DoAction.addAction(message, player.user, target.longID, 0, "heal")
            }
            Roles.ESCORT ->{
                DoAction.addAction(message, player.user, target.longID, 0, "escort")
            }
            Roles.FORGER ->{
                DoAction.addAction(message, player.user, target.longID, 0, "forge")
            }
            Roles.FRAMER ->{
                DoAction.addAction(message, player.user, target.longID, 0, "frame")
            }
            Roles.GODFATHER ->{
                DoAction.addAction(message, player.user, target.longID, 0, "kill")
            }
            Roles.INVEST ->{
                DoAction.addAction(message, player.user, target.longID, 0, "invest")
            }
            Roles.JANITOR ->{
                DoAction.addAction(message, player.user, target.longID, 0, "clean")
            }
            Roles.JESTER ->{
                DoAction.addAction(message, player.user, target.longID, 0, "kill")
            }
            Roles.LOOKOUT ->{
                DoAction.addAction(message, player.user, target.longID, 0, "watch")
            }
            Roles.MAFIOSO ->{
                DoAction.addAction(message, player.user, target.longID, 0, "kill")
            }
            Roles.MEDIUM ->{
                DoAction.addAction(message, player.user, target.longID, 0, "seance")
            }
            Roles.RETRI ->{
                DoAction.addAction(message, player.user, target.longID, 0, "revive")
            }
            Roles.SERIAL_KILLER ->{
                DoAction.addAction(message, player.user, target.longID, 0, "kill")
            }
            Roles.SHERIFF ->{
                DoAction.addAction(message, player.user, target.longID, 0, "interrogate")
            }
            Roles.SURVIVOR ->{
                DoAction.addAction(message, player.user, target.longID, 0, "vest")
            }
            Roles.TRANS ->{
                DoAction.addAction(message, player.user, target.longID, target2.longID, "trans")
            }
            Roles.VAMPIRE ->{
                DoAction.addAction(message, player.user, target.longID, 0, "convert")
            }
            Roles.VAMP_HUNTER ->{
                DoAction.addAction(message, player.user, target.longID, 0, "kill")
            }
            Roles.VETERAN ->{
                DoAction.addAction(message, player.user, player.id, 0, "rampage")
            }
            Roles.VIGILANTE ->{
                DoAction.addAction(message, player.user, target.longID, 0, "kill")
            }
            Roles.WEREWOLF ->{
                DoAction.addAction(message, player.user, target.longID, 0, "rampage")
            }
            Roles.WITCH ->{
                DoAction.addAction(message, player.user, target.longID, target2.longID, "witch")
            }
        }
    }
}