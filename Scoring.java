public class Scoring {
    int rating;
    Card[] hand;

    public Scoring(Card[] cards) {
        hand = cards;
    }

    public Card[] sortHand()
    {
        Card[] best = new Card[7];
        Boolean[] pair = new Boolean[7];
        Boolean[] triple = new Boolean[7];
        Boolean[] quad = new Boolean[7];
        Boolean[] flush = new Boolean[7];
        Boolean[] straight = new Boolean[7];
        best[0] = hand[0];
        for(int i=1; i<hand.length; i++)
        {
            for(int j=0; j<7; j++)
            {if(hand[i].getValue()== best[j].getValue())
            {
                if(pair[j] && !pair[j+1])
                {
                    
                }
                best[j+1] = hand[i];
                pair[j] = true;
                pair[j+1] = true;
            }
            else
            if(hand[i].getValue() >= best[j].getValue()
            {

            }
            }
            

        }
        return best;
    }

}
//sort cards by value first
//level for 5 card set
//make sets of 5 from 7 (hand and down)
