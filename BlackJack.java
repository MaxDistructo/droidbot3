public class BlackJack
{
   public static void main(String[] args)
   {
      new BlackJack();
   }
   
   public BlackJack()
   {
      Card c1 = randomCard();
      Card c2 = randomCard();
      printCard(c1);
      printCard(c2);
      if (sameCard(c1,c2))
      {  System.out.println("CHEATER!!!"); }
      if (  (c1.rank == 11 && c2.rank == 1)
          || (c1.rank == 1 && c2.rank == 11) 
         )
      {  System.out.println("BLACKJACK!!"); }
   }   
   
   class Card 
   { 
      int suit, rank; 

      public Card () 
      { 
       this.suit = 0;  this.rank = 0; 
      } 

      public Card (int suit, int rank) 
      { 
         this.suit = suit;  this.rank = rank; 
      } 
   } 
   
   public Card randomCard()
   {
      int suit = (int)(Math.random()*4);
      int rank = (int)(Math.random()*13 + 1);
      Card card = new Card (suit, rank); 
      return card;
   }
   
   public void printCard (Card c) 
   { 
      String[] suits = { "Clubs", "Diamonds", "Hearts", "Spades" }; 
      String[] ranks = { "narf", "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" }; 

      System.out.println (ranks[c.rank] + " of " + suits[c.suit]); 
   }
   
   public boolean sameCard (Card c1, Card c2) 
   { 
      return (c1.suit == c2.suit && c1.rank == c2.rank); 
   } 
   
}
 public boolean checkBlackJack(Card first, Card second)
   {
      if ( (first.rank >= 10 && second.rank == 1)
         ||(first.rank == 1 && second.rank >= 10) )
      { return true; }
      else 
      { return false; }
   }
   
   public int value(Card c)
   { if (c.rank > 10)
     { return 10; }
     else if (c.rank == 1)
     { return 11; }
     else
     { return c.rank; }
   }
   
   public int total(Card first, Card second)
   { int v1 = value(first);
     int v2 = value(second);
     return v1+v2;
   } 
   
   class Hand
   {
     Card[] cards = new Card[5];
     
     public Hand(Card first, Card second)
     {
        cards[0] = first;
        cards[1] = second;       
     }
     
     public Hand(Card c0, Card c1, Card c2, Card c3, Card c4)
     {
        cards[0] = c0;
        cards[1] = c1;
        cards[2] = c2;
        cards[3] = c3;
        cards[4] = c4;
     }
   }
   
   public Hand deal()
   {
      Hand these = new Hand(randomCard(), randomCard(),
                     randomCard(), randomCard(), randomCard());
      return these;
   }

   public void printHand(Hand h)
   {
      for (int c=0; c < 5; c++)
      {
        printCard(h.cards[c]);
      }
   }
   
   public boolean checkFlush(Hand h)
   {
      if (   h.cards[0].suit == h.cards[1].suit 
          && h.cards[1].suit == h.cards[2].suit    
          && h.cards[2].suit == h.cards[3].suit    
          && h.cards[3].suit == h.cards[4].suit    
         )
      { return true; }
      else
      { return false; }
   }