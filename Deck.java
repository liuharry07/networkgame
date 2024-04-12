public class Deck {
    private int[][] deck;
    private int numCards;

    public Deck() {
        deck = new int[4][13];
        for(int i = 0; i < deck.length; ++i) {
            for(int j = 0; j < deck[i].length; ++j) {
                deck[i][j] = 1;
            }
        }
        numCards = 52;
    }

    public Card drawCard() {
        int suit = (int) (Math.random() * 4);
        int value = (int) (Math.random() * 13);
        while(deck[suit][value] != 1) {
            suit = (int) (Math.random() * 4);
            value = (int) (Math.random() * 13);
        }
        deck[suit][value] = 0;
        --numCards;
        return new Card(suit, value);
    }

    public int cardsLeft() {
        return numCards;
    }
}
