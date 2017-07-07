package maxdistructo.droidbot2.commands;


import maxdistructo.droidbot2.BaseBot;
import maxdistructo.droidbot2.background.Config;
import maxdistructo.droidbot2.background.message.Message;
import org.json.JSONObject;
import sx.blah.discord.handle.obj.IMessage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BlackJack {
    private static String[] main_deck = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    private static int[] bj_values = {2,3,4,5,6,7,8,9,10,10,10,11,2,3,4,5,6,7,8,9,10,10,10,11,2,3,4,5,6,7,8,9,10,10,10,11,2,3,4,5,6,7,8,9,10,10,10,11};
    private static int playerScore;
    private static int dealerScore;
    private static String playerHand;
    private static String dealerHand;
    private static int bet;

    public static String blackjack(Object[] args, IMessage message) { //!blackjack <bet>
        Config.readCasino(message);
        if(Config.converToInt(args[1]) > 1 && Config.converToInt(args[1]) < 500000 && Config.converToInt(args[1]) < Config.CHIPS) {
            Config.CHIPS -= Config.converToInt(args[1]);
            //Initial Hands
            bet = Config.converToInt(args[1]);
            playerHand = drawCard(main_deck, playerHand);
            dealerHand = drawCard(main_deck, dealerHand);
            playerHand = drawCard(main_deck, playerHand);
            dealerHand = drawCard(main_deck, dealerHand);
            playerScore = calculateScore(playerHand);
            dealerScore = calculateScore(dealerHand);
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            File file = new File(s + "/droidbot/config/" + message.getGuild().getLongID() + "/blackjack/" + message.getAuthor().getLongID());
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                Message.sendDM(BaseBot.client.getApplicationOwner(),e.toString());
                e.printStackTrace();
            }
            try {
                Thread.sleep(1250L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "BlackJack", "Your hand: " + playerHand + "\nYour Score: " + playerScore + "\nDealer Hand: " + dealerHand + "\nDealer Score: " + dealerScore, message));
            String end = checkEnd(playerScore, dealerScore);
            switch (checkEnd(playerScore, dealerScore)) {
                case "Continue":
                    Config.writeBlackjackFields(playerScore,playerHand,dealerScore,dealerHand,Config.converToInt(args[1]),message);
                    Config.writeCasino(message);
                    return "Would you like to Hit or Stay ?";
                case "Push":
                    Config.CHIPS += bet;
                    Config.resetBJ(message);
                    playerHand = null;
                    dealerHand = null;
                    Config.writeCasino(message);
                    return end;
                case "Bust! Dealer Wins!":
                    Config.resetBJ(message);
                    playerHand = null;
                    dealerHand = null;
                    Config.writeCasino(message);
                    return end;
                case "Player Wins!":
                    Config.CHIPS += bet;
                    Config.CHIPS += bet;
                    Config.resetBJ(message);
                    playerHand = null;
                    dealerHand = null;
                    Config.writeCasino(message);
                    return end;
            }

        }
        else{
            return "Casino Error: Blackjack: Invalid Bet";
        }
        return "Casino Error: Blackjack: Unknown Error";
    }
    private static String drawCard(String[] cardDeck, String deck){
        int random1 = (int) (Math.random() * cardDeck.length);
        if(deck == null){
            return cardDeck[random1];
        }
        return deck + "," + cardDeck[random1];
    }
    private static int calculateScore(String deck){
        int score = 0;
        String[] calculate = deck.split(",");
        int i = 0;
        while(i < calculate.length){
            switch (calculate[i]) {
                case "2":
                    score += 2;
                    break;
                case "3":
                    score += 3;
                    break;
                case "4":
                    score += 4;
                    break;
                case "5":
                    score += 5;
                    break;
                case "6":
                    score += 6;
                    break;
                case "7":
                    score += 7;
                    break;
                case "8":
                    score += 8;
                    break;
                case "9":
                    score += 9;
                    break;
                case "10":
                    score += 10;
                    break;
                case "Jack":
                    score += 10;
                    break;
                case "Queen":
                    score += 10;
                    break;
                case "King":
                    score += 10;
                    break;
                case "Ace":
                    score += 1;
                    break;
            }
            i++;
        }
        return score;
    }
    private static String checkEnd(int pScore, int dScore){
        System.out.println("Running check end");
        if(pScore == 21 && dScore == 21 || pScore > 21 && dScore > 21){
            return "Push";
        }
        else if(pScore > 21){
            return "Bust! Dealer Wins!";
        }
        else if(pScore == 21 && dScore < 21 || dScore > 21){
            return "Player Wins!";
        }
        else if(pScore < dScore && dScore > 17){
            return "Continue";
        }
        else if(pScore < 21 && dScore < 21 && dScore > 17){
            if(pScore > dScore){
                return "Player Wins!";
            }
            else{
                return "Bust! Dealer Wins!";
            }
        }
        else{
            return "Continue";
        }
    }
    private static String isEnd(String end, IMessage message){
        playerScore = calculateScore(playerHand);
        dealerScore = calculateScore(dealerHand);
        try {
            Thread.sleep(1250L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "BlackJack", "Your hand: " + playerHand + "\nYour Score: " + playerScore + "\nDealer Hand: " + dealerHand + "\nDealer Score: " + dealerScore, message));
        System.out.println("Running is end");
        switch (end) {
            case "Continue":
                Config.writeBlackjackFields(playerScore, playerHand, dealerScore, dealerHand, bet, message);
                return "Would you like to Hit or Stay ?";
            case "Push":
                Config.CHIPS += bet;
                Config.resetBJ(message);
                playerHand = null;
                dealerHand = null;
                Config.writeCasino(message);
                return checkEnd(playerScore, dealerScore);
            case "Bust! Dealer Wins!":
                Config.resetBJ(message);
                playerHand = null;
                dealerHand = null;
                Config.writeCasino(message);
                return checkEnd(playerScore, dealerScore);
            case "Player Wins!":
                Config.CHIPS += bet;
                Config.CHIPS += bet;
                Config.resetBJ(message);
                playerHand = null;
                dealerHand = null;
                Config.writeCasino(message);
                return checkEnd(playerScore, dealerScore);
        }
        return "Casino Error: Blackjack";
    }
    public static String continueGame(IMessage message, String[] messageContent, JSONObject root){
        playerHand = root.getString("BJ_playerHand");
        dealerHand = root.getString("BJ_dealerHand");
        playerScore = root.getInt("BJ_playerScore");
        dealerScore = root.getInt("BJ_dealerScore");
        bet = root.getInt("BJ_bet");
        switch (messageContent[0]) {
            case "hit":
                if (!root.getString("BJ_playerHand").equals("null")) {
                    playerHand = drawCard(main_deck, playerHand);
                }

                if(calculateScore(dealerHand) > 17) {
                    return isEnd(checkEnd(playerScore, dealerScore), message);
                }
                if (!root.getString("BJ_dealerHand").equals("null") && !(calculateScore(dealerHand) > 17)) {
                    dealerHand = drawCard(main_deck, dealerHand);
                }

                playerScore = calculateScore(playerHand);
                dealerScore = calculateScore(dealerHand);
                try {
                    Thread.sleep(1250L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "BlackJack", "Your hand: " + playerHand + "\nYour Score: " + playerScore + "\nDealer Hand: " + dealerHand + "\nDealer Score: " + dealerScore, message));
                switch (checkEnd(playerScore, dealerScore)) {
                    case "Continue":
                        Config.writeBlackjackFields(playerScore, playerHand, dealerScore, dealerHand, bet, message);
                        try {
                            Thread.sleep(1250L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return "Would you like to Hit or Stay ?";
                    case "Push":
                        Config.CHIPS += bet;
                        Config.resetBJ(message);
                        playerHand = null;
                        dealerHand = null;
                        Config.writeCasino(message);
                        return checkEnd(playerScore, dealerScore);
                    case "Bust! Dealer Wins!":
                        Config.resetBJ(message);
                        playerHand = null;
                        dealerHand = null;
                        Config.writeCasino(message);
                        return checkEnd(playerScore, dealerScore);
                    case "Player Wins!":
                        Config.CHIPS += bet;
                        Config.CHIPS += bet;
                        Config.resetBJ(message);
                        playerHand = null;
                        dealerHand = null;
                        Config.writeCasino(message);
                        return checkEnd(playerScore, dealerScore);
                }

                break;
            case "stay":
                if(calculateScore(dealerHand) > 17) {
                    return isEnd(checkEnd(playerScore, dealerScore), message);
                }
                if (!root.getString("BJ_dealerHand").equals("null") && !(calculateScore(dealerHand) > 17)) {
                    dealerHand = drawCard(main_deck, dealerHand);
                }
                playerScore = calculateScore(playerHand);
                dealerScore = calculateScore(dealerHand);
                try {
                    Thread.sleep(1250L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "BlackJack", "Your hand: " + playerHand + "\nYour Score: " + playerScore + "\nDealer Hand: " + dealerHand + "\nDealer Score: " + dealerScore, message));
                switch (checkEnd(playerScore, dealerScore)) {
                    case "Continue":
                        Config.writeBlackjackFields(playerScore, playerHand, dealerScore, dealerHand, bet, message);
                        try {
                            Thread.sleep(1250L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return "Would you like to Hit or Stay ?";
                    case "Push":
                        Config.CHIPS += bet;
                        Config.resetBJ(message);
                        playerHand = null;
                        dealerHand = null;
                        Config.writeCasino(message);
                        return checkEnd(playerScore, dealerScore);
                    case "Bust! Dealer Wins!":
                        Config.resetBJ(message);
                        playerHand = null;
                        dealerHand = null;
                        Config.writeCasino(message);
                        return checkEnd(playerScore, dealerScore);
                    case "Player Wins!":
                        Config.CHIPS += bet;
                        Config.CHIPS += bet;
                        Config.resetBJ(message);
                        playerHand = null;
                        dealerHand = null;
                        Config.writeCasino(message);
                        return checkEnd(playerScore, dealerScore);
                }
                break;
            default:
                return "HEY! Start a Blackjack game before saying \"hit\" or \"stay!\"";
        }

        return "How TF did you reach this?";
    }

}

