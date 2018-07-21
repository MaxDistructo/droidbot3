package maxdistructo.discord.bots.droidbot

import maxdistructo.discord.bots.droidbot.background.coreadditions.ICommandRegistry
import maxdistructo.discord.bots.droidbot.commands.*
import maxdistructo.discord.core.command.IBaseListener

object Commands : ICommandRegistry {

    override fun registerCommands(listener : IBaseListener){
        val game = Game()
        val ping = Ping()
        val say = Say()
        val webhookSay = WebhookSay()
        val debug = Debug()
        val info = Info()
        val check = Check()
        val fortune = Fortune()
        val horoscope = Horoscope()
        val insult = Insult()
        val help = Help()
        val tnt = PlayerFun.Tnt()
        val slap = PlayerFun.Slap()
        val kiss = PlayerFun.Kiss()
        val hug = PlayerFun.Hug()
        val poke = PlayerFun.Poke()
        val rip = PlayerFun.Respect()
        val shoot = PlayerFun.Shoot()
        val stab = PlayerFun.Stab()
        val banhammer = PlayerFun.BanHammer()
        val xp = PlayerFun.Xp()
        val punch = PlayerFun.Punch()
        val spam = Spam()
        listener.registerCommand(game, ping, say, webhookSay, debug, info, check, fortune, horoscope, insult, tnt, slap, kiss, hug, poke, rip, shoot, stab, banhammer, xp, punch, spam, help)
    }

}