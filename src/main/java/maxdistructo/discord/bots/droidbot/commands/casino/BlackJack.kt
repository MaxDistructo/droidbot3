package maxdistructo.discord.bots.droidbot.commands.casino


import maxdistructo.discord.core.Utils
import maxdistructo.discord.core.message.Message
import org.json.JSONObject
import sx.blah.discord.handle.obj.IMessage
import java.io.File
import java.io.IOException
import java.nio.file.Paths

object BlackJack {
    private val main_deck = arrayOf("2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace")
    private var playerScore: Int = 0
    private var dealerScore: Int = 0
    private var playerHand: String? = null
    private var dealerHand: String? = null
    private var bet: Int = 0

    fun blackjack(args: Array<Any>, message: IMessage): String { //!blackjack <bet>
        CasinoConfig.readCasino(message)
        if (Utils.convertToInt(args[1]) in 1..499999 && Utils.convertToInt(args[1]) < CasinoConfig.CHIPS) {
            CasinoConfig.CHIPS -= Utils.convertToInt(args[1])
            //Initial Hands
            bet = Utils.convertToInt(args[1])
            playerHand = drawCard(main_deck, playerHand)
            dealerHand = drawCard(main_deck, dealerHand)
            playerHand = drawCard(main_deck, playerHand)
            playerScore = calculateScore(playerHand!!)
            dealerScore = calculateScore(dealerHand!!)
            val currentRelativePath = Paths.get("")
            val s = currentRelativePath.toAbsolutePath().toString()
            val file = File(s + "/droidbot/config/" + message.guild.longID + "/blackjack/" + message.author.longID + ".txt")
            file.parentFile.mkdirs()
            if (file.exists() && !file.isDirectory) {
                file.delete()
                try {
                    file.createNewFile()
                } catch (e: IOException) {
                    Message.sendDM(BaseBot.client.applicationOwner, e.toString())
                    e.printStackTrace()
                }

            } else {
                try {
                    file.createNewFile()
                } catch (e: IOException) {
                    Message.sendDM(BaseBot.client.applicationOwner, e.toString())
                    e.printStackTrace()
                }

            }
            try {
                Thread.sleep(1250L)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            Message.sendMessage(message.channel, Message.simpleEmbed(message.author, "BlackJack", "Your hand: $playerHand\nYour Score: $playerScore\nDealer Hand: $dealerHand\nDealer Score: $dealerScore", message))
            val end = checkEnd(playerScore, dealerScore)
            when (checkEnd(playerScore, dealerScore)) {
                "Continue" -> {
                    CasinoConfig.writeBlackjackFields(playerScore, playerHand!!, dealerScore, dealerHand!!, Utils.convertToInt(args[1]), message)
                    CasinoConfig.writeCasino(message)
                    return "Would you like to Hit or Stay ?"
                }
                "Push" -> {
                    CasinoConfig.CHIPS += bet
                    CasinoConfig.resetBJ(message)
                    playerHand = null
                    dealerHand = null
                    CasinoConfig.writeCasino(message)
                    return end
                }
                "Bust! Dealer Wins!" -> {
                    CasinoConfig.resetBJ(message)
                    playerHand = null
                    dealerHand = null
                    CasinoConfig.writeCasino(message)
                    return end
                }
                "Player Wins!" -> {
                    CasinoConfig.CHIPS += bet
                    CasinoConfig.CHIPS += bet
                    CasinoConfig.resetBJ(message)
                    playerHand = null
                    dealerHand = null
                    CasinoConfig.writeCasino(message)
                    return end
                }
            }

        } else {
            return "Casino Error: Blackjack: Invalid Bet"
        }
        return "Casino Error: Blackjack: Unknown Error"
    }

    private fun drawCard(cardDeck: Array<String>, deck: String?): String {
        val random1 = (Math.random() * cardDeck.size).toInt()
        return if (deck == null) {
            cardDeck[random1]
        } else deck + "," + cardDeck[random1]
    }

    private fun calculateScore(deck: String): Int {
        var score = 0
        val calculate = deck.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var i = 0
        while (i < calculate.size) {
            when (calculate[i]) {
                "2" -> score += 2
                "3" -> score += 3
                "4" -> score += 4
                "5" -> score += 5
                "6" -> score += 6
                "7" -> score += 7
                "8" -> score += 8
                "9" -> score += 9
                "10" -> score += 10
                "Jack" -> score += 10
                "Queen" -> score += 10
                "King" -> score += 10
                "Ace" -> score += 1
            }
            i++
        }
        return score
    }

    private fun checkEnd(pScore: Int, dScore: Int): String {
        println("Running check end")
        if (pScore == 21 && dScore == 21 || pScore > 21 && dScore > 21 || pScore == dScore && dScore > 17) {
            return "Push"
        } else if (pScore > 21) {
            return "Bust! Dealer Wins!"
        } else if (pScore == 21 && dScore < 21 || dScore > 21) {
            return "Player Wins!"
        } else if (pScore < 21 && dScore < 21 && dScore > 17) {
            return if (pScore > dScore) {
                "Player Wins!"
            } else {
                "Bust! Dealer Wins!"
            }
        } else if (dScore > 17) {
            if (pScore > dScore) {
                return "Player Wins!"
            } else if (dScore > pScore) {
                return "Bust! Dealer Wins!"
            }
        } else {
            return "Continue"
        }
        return "Push"
    }

    fun continueGame(message: IMessage, messageContent: Array<String>, root: JSONObject): String {
        playerHand = root.getString("BJ_playerHand")
        dealerHand = root.getString("BJ_dealerHand")
        playerScore = calculateScore(playerHand!!)
        dealerScore = calculateScore(dealerHand!!)
        bet = root.getInt("BJ_bet")
        when (messageContent[0].toLowerCase()) {
            "hit" -> {
                if (root.getString("BJ_playerHand") != "null") {
                    playerHand = drawCard(main_deck, playerHand)
                }

                playerScore = calculateScore(playerHand!!)
                dealerScore = calculateScore(dealerHand!!)
                try {
                    Thread.sleep(1250L)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                Message.sendMessage(message.channel, Message.simpleEmbed(message.author, "BlackJack", "Your hand: $playerHand\nYour Score: $playerScore\nDealer Hand: $dealerHand\nDealer Score: $dealerScore", message))
                when (checkEnd(playerScore, dealerScore)) {
                    "Continue" -> {
                        CasinoConfig.writeBlackjackFields(playerScore, playerHand!!, dealerScore, dealerHand!!, bet, message)
                        try {
                            Thread.sleep(1250L)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }

                        return "Would you like to Hit or Stay ?"
                    }
                    "Push" -> {
                        CasinoConfig.CHIPS += bet
                        CasinoConfig.resetBJ(message)
                        playerHand = null
                        dealerHand = null
                        CasinoConfig.writeCasino(message)
                        return checkEnd(playerScore, dealerScore)
                    }
                    "Bust! Dealer Wins!" -> {
                        CasinoConfig.resetBJ(message)
                        playerHand = null
                        dealerHand = null
                        CasinoConfig.writeCasino(message)
                        return checkEnd(playerScore, dealerScore)
                    }
                    "Player Wins!" -> {
                        CasinoConfig.CHIPS += bet
                        CasinoConfig.CHIPS += bet
                        CasinoConfig.resetBJ(message)
                        playerHand = null
                        dealerHand = null
                        CasinoConfig.writeCasino(message)
                        return checkEnd(playerScore, dealerScore)
                    }
                }
            }
            "stay" -> return dealerMove(message)
        }

        return "How TF did you reach this?"
    }

    private fun dealerMove(message: IMessage): String {
        while (calculateScore(dealerHand!!) <= 17) {
            dealerHand = drawCard(main_deck, dealerHand)
            dealerScore = calculateScore(dealerHand!!)
        }
        Message.sendMessage(message.channel, Message.simpleEmbed(message.author, "BlackJack", "Your hand: $playerHand\nYour Score: $playerScore\nDealer Hand: $dealerHand\nDealer Score: $dealerScore", message))
        when (checkEnd(playerScore, dealerScore)) {
            "Push" -> {
                CasinoConfig.CHIPS += bet
                CasinoConfig.resetBJ(message)
                playerHand = null
                dealerHand = null
                CasinoConfig.writeCasino(message)
                return checkEnd(playerScore, dealerScore)
            }
            "Bust! Dealer Wins!" -> {
                CasinoConfig.resetBJ(message)
                playerHand = null
                dealerHand = null
                CasinoConfig.writeCasino(message)
                return checkEnd(playerScore, dealerScore)
            }
            "Player Wins!" -> {
                CasinoConfig.CHIPS += bet
                CasinoConfig.CHIPS += bet
                CasinoConfig.resetBJ(message)
                playerHand = null
                dealerHand = null
                CasinoConfig.writeCasino(message)
                return checkEnd(playerScore, dealerScore)
            }
        }
        CasinoConfig.CHIPS += bet
        CasinoConfig.writeCasino(message)
        return "Command Error. Your balance has not been affected."
    }
}


