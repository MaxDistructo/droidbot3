package maxdistructo.droidbot2.background;


import maxdistructo.droidbot2.commands.BlackJack;
import sx.blah.discord.handle.obj.IMessage;

import java.util.ArrayList;

public class BlackjackGame {
    private BlackJack.Card[] Deck = new BlackJack.Card[52];
    private ArrayList<BlackJack.Card> playerHand = new ArrayList<>();
    private ArrayList<BlackJack.Card> dealerHand = new ArrayList<>();
    private int pValue = 0;
    private int dValue = 0;
    private int cardCounter = 0;
    private boolean endGame = false;


    public boolean isEndGame() {
        return endGame;
    }

    public int getCardCounter() {
        return cardCounter;
    }

    public void setCardCounter(int cardCounter) {
        this.cardCounter = cardCounter;
    }

    public int getpValue() {
        return pValue;
    }

    public int getdValue() {
        return dValue;
    }

    public ArrayList<BlackJack.Card> getPlayerHand() {
        return playerHand;
    }

    public ArrayList<BlackJack.Card> getDealerHand() {
        return dealerHand;
    }

    public void dealOneCardPlayer(int i) {
        playerHand.add(Deck[i]);
    }

    public void dealoneCardDealer(int i) {
        dealerHand.add(Deck[i]);
    }

    public void setDealerHand(ArrayList<BlackJack.Card> dealerHand) {
        this.dealerHand = dealerHand;
    }

    public void setDeck(BlackJack.Card[] Deck) {
        this.Deck = Deck;
    }


    public void displayPlayer(IMessage message) {
        pValue = 0;
        dValue = 0;
        System.out.println("---------------------------------------------");
        System.out.println("DEALER HAND");
        for (int i = 0; i < dealerHand.size(); i++) {
            System.out.println(dealerHand.get(i).getName() + " of " + dealerHand.get(i).getSuit());
            dValue = dValue + dealerHand.get(i).getValue();
        }
        message.reply("Dealer Hand Value = " + dValue);

        System.out.println("---------------------------------------------");
        System.out.println("PLAYER HAND");
        for (int i = 0; i < playerHand.size(); i++) {
            System.out.println(playerHand.get(i).getName() + " of " + playerHand.get(i).getSuit());
            pValue = pValue + playerHand.get(i).getValue();
        }
        message.reply("Player Hand Value = " + pValue);
        System.out.println("");
        System.out.println("");
    }


    public void turnLoop(IMessage message) {
        System.out.println("Do you want to (H)it, (S)tay, or (C)all?");
        String trigger = "hit";
        Listener.blackJackRunning = true;
        if(Listener.blackjackAnswer == null){
            try {
                Thread.sleep(1000L);
                turnLoop(message);
                Thread.interrupted();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else{
           trigger = Listener.blackjackAnswer;
        }
        trigger = trigger.toLowerCase();
        char hitstaycall = trigger.charAt(0);

        if ('h' == (hitstaycall)) {
            playerHand.add(Deck[cardCounter]);
            pValue = pValue + Deck[cardCounter].getValue();
            cardCounter++;
            if (dValue < 16) {
                dealerHand.add(Deck[cardCounter]);
                dValue = dValue + Deck[cardCounter].getValue();
                cardCounter++;
            }

            dValue = 0;
            pValue = 0;

            for (int i = 0; i < dealerHand.size(); i++) {
                dValue = dValue + dealerHand.get(i).getValue();
            }
            for (int i = 0; i < playerHand.size(); i++) {
                pValue = pValue + playerHand.get(i).getValue();
            }

            if (dValue > 21 && pValue > 21) {
                message.reply("You Lose. Dealer doesn't lose because you bust first. Its the House rules...");
                endGame = true;
            }
            if (pValue == 21 || dValue == 21) {
                message.reply("BLACKJACK!");
                endGame = true;
            }
            if (dValue > 21 && pValue < 22) {
                message.reply("Dealer BUST. You Win");
                endGame = true;
            }
            if (pValue > 21 && dValue < 22) {
                message.reply("You BUST. Dealer Win");
                endGame = true;
            }


        } else if ('s' == hitstaycall) {
            if (dValue < 16) {
                dealerHand.add(Deck[cardCounter]);
                dValue = dValue + Deck[cardCounter].getValue();
                cardCounter++;
            }
            dValue = 0;
            pValue = 0;

            for (int i = 0; i < dealerHand.size(); i++) {
                dValue = dValue + dealerHand.get(i).getValue();
            }
            for (int i = 0; i < playerHand.size(); i++) {
                pValue = pValue + playerHand.get(i).getValue();
            }

            if (dValue > 21 && pValue > 21) {
                message.reply("You Lose. Dealer doesn't lose because you bust first. Its the House rules...");
                endGame = true;
            }
            if (pValue == 21 || dValue == 21) {
                message.reply("BLACKJACK!");
                endGame = true;
            }
            if (dValue > 21 && pValue < 22) {
                message.reply("Dealer BUST. You Win");
                endGame = true;
            }
            if (pValue > 21 && dValue < 22) {
                message.reply("You BUST. Dealer Win");
                endGame = true;
            }
        } else if ('c' == hitstaycall) {
            dValue = 0;
            pValue = 0;

            for (int i = 0; i < dealerHand.size(); i++) {
                dValue = dValue + dealerHand.get(i).getValue();
            }
            for (int i = 0; i < playerHand.size(); i++) {
                pValue = pValue + playerHand.get(i).getValue();
            }

            if (dValue < pValue && pValue < 21) {
                dealerHand.add(Deck[cardCounter]);
                dValue = dValue + Deck[cardCounter].getValue();
                cardCounter++;
            }
            if (pValue > dValue && pValue < 22) {
                message.reply("YOU WIN!!!!");
                endGame = true;
            }
            if (dValue > pValue && dValue < 22) {
                message.reply("Dealer Wins!");
                endGame = true;
            }
            if (dValue > 21 && pValue > 21) {
                message.reply("You Lose. Dealer doesn't lose because you bust first. Its the House rules...");
                endGame = true;
            }
            if (pValue == 21 || dValue == 21) {
                message.reply("BLACKJACK!");
                endGame = true;
            }
            if (dValue > 21 && pValue < 22) {
                message.reply("Dealer BUST. You Win");
                endGame = true;
            }
            if (pValue > 21 && dValue < 22) {
                message.reply("You BUST. Dealer Win");
                endGame = true;
            }
        } else {
            message.reply("Fuck you.");
        }
    }


    public void check(IMessage message) {
        dValue = 0;
        pValue = 0;

        for (int i = 0; i < dealerHand.size(); i++) {
            dValue = dValue + dealerHand.get(i).getValue();
        }
        for (int i = 0; i < playerHand.size(); i++) {
            pValue = pValue + playerHand.get(i).getValue();
        }


        if (dValue > 21 && pValue > 21) {
            message.reply("You Lose. Dealer doesn't lose because you bust first. Its the House rules...");
            endGame = true;
        }
        if (pValue == 21 || dValue == 21) {
            message.reply("BLACKJACK!");
            endGame = true;
        }
        if (dValue > 21 && pValue < 22) {
            message.reply("Dealer BUST. You Win");
            endGame = true;
        }
        if (pValue > 21 && dValue < 22) {
            message.reply("You BUST. Dealer Win");
            endGame = true;
        }
    }
}

