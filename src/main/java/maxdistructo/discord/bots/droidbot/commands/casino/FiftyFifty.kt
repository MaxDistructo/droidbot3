package maxdistructo.discord.bots.droidbot.commands.casino

import maxdistructo.discord.core.Perms
import maxdistructo.discord.core.Utils
import sx.blah.discord.handle.obj.IMessage

object FiftyFifty {
    //@Command(aliases = {"/50","/fifty"}, description = "Fifty Fifty chance of doubling your money imputed", usage = "/50|fifty <bet>")
    fun onFiftyCommand(args: Array<Any>, message: IMessage): String {
        val author = message.author
        Casino.checkMembership(message)

        if (args.size == 1) {
            return "You have not entered a bet. Please enter a bet by using the command /50|fifty <bet>"
        } else if (args.size == 2 && Integer.valueOf(args[1] as String) > 10 && Integer.valueOf(args[1] as String) < 500000 && Perms.checkGames(message)) {
            CasinoConfig.CHIPS -= Utils.convertToInt(args[1])
            val random = (Math.random() * 50 + 1).toInt()
            if (random > 25) {
                CasinoConfig.CHIPS += Utils.convertToInt(args[1])
                CasinoConfig.CHIPS += Utils.convertToInt(args[1])
                val wins = Utils.convertToInt(args[1]) + Utils.convertToInt(args[1])
                CasinoConfig.writeCasino(message)
                Casino.checkMembership(message)
                return "You have successfully won $wins"
            } else {
                CasinoConfig.writeCasino(message)
                Casino.checkMembership(message)
                return "Sorry! You were unlucky!"
            }

        }
        return "Fifty Fifty has errored. "
    }
}
