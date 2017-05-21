package maxdistructo.droidbot2.commands;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import maxdistructo.droidbot2.background.Config;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;


public class Blackjack implements CommandExecutor {

    @Command(aliases = {"/blackjack", "/bj"}, description = "Blackjack Game", usage = "/blackjack|bj <bet>")
    public String onBlackjackCommand(int[] args, Message message) {
        User author = message.getAuthor();
        long authorIdLong = author.getIdLong();
        Config.readCasino(authorIdLong);
        boolean end = true;
        boolean initialDraw = true;
        int[] cards = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
        int scoreDealer = 0;
        int scorePlayer = 0;

        if (args.length == 0) {
            return "You must specify a bet. Please enter /blackjack|bj <bet> to play.";
        } else if (args.length == 1) {
            Config.CHIPS = Config.CHIPS - args[0];
            double win = args[0] + args[0] + Config.CHIPS;

            if (args[0] < 50 || args[0] > 500) {
                Config.CHIPS += args[0];
                return "You have entered an invalid bet. Please enter a valid bet between 50 and 500";
            } else {
                while (initialDraw) {
                    int card1 = (int) (Math.random() * 12 + 0);
                    int card2 = (int) (Math.random() * 12 + 0);
                    scoreDealer = cards[card1] + cards[card2];
                    int card3 = (int) (Math.random() * 12 + 0);
                    int card4 = (int) (Math.random() * 12 + 0);
                    scorePlayer = cards[card3] + cards[card4];
                    initialDraw = false;
                }
                if (scoreDealer == 21) {
                    return "Player Points:" + scorePlayer + "\n"+
                            "Dealer Points: " + scoreDealer + "\n"+
                            "Dealer Wins! You have lost your money.";
                } else if (scorePlayer == 21) {
                    return "Player Points:" + scorePlayer + "\n"+
                            "Dealer Points: " + scoreDealer + "\n"+
                            "Player Wins! You have gained " + win + "Doge chips";
                } else {
                    System.out.printf("Player Points:" + scorePlayer + "\n"+
                            "Dealer Points: " + scoreDealer + "\n"
                            + "Neither player has gained 21 points. Will you draw another card?");

                }
            }

        }
        return "Error: Bot has errored on command BlackJack";
    }

}
