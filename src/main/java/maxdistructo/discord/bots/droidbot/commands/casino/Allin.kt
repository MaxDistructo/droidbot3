package maxdistructo.discord.bots.droidbot.commands.casino

import maxdistructo.droidbot2.core.Perms
import maxdistructo.droidbot2.core.message.Message
import sx.blah.discord.handle.obj.IMessage

object Allin {
    fun onAllinCommand(args: Array<Any>, message: IMessage): String {
        CasinoConfig.readCasino(message)
        if (args.size != 2) {
            return "Please specify the amount to multiply your balance by if you win."
        }
        if (Perms.checkGames(message)) {
            Message.sendMessage(message.channel, "You deposit all of your chips into the machine and pull the lever...")
            val multipy = Integer.valueOf(args[1] as String)
            val random = (Math.random() * multipy + 1).toInt()
            val random2 = (Math.random() * multipy + 1).toInt()

            if (random == random2) {
                CasinoConfig.CHIPS = CasinoConfig.CHIPS * multipy
                CasinoConfig.writeCasino(message)
                Casino.checkMembership(message)
                return "You win and have multiplied your chips by $multipy"
            } else {
                CasinoConfig.CHIPS = 0
                CasinoConfig.writeCasino(message)
                Casino.checkMembership(message)
                return "You lose and have lost all your chips."
            }
        }
        return "Please run this command in one of your bot channels."
    }
}
