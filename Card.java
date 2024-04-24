public class Card {
    /*
    private final int SPADE = 0;
    private final int CLUB = 1;
    private final int DIAMOND = 2;
    private final int HEART = 3;
    */
    private final String[] suits = {"SPADE", "CLUB", "DIAMOND", "HEART"};

    /*
    private final int TWO = 0;
    private final int THREE = 1;
    private final int FOUR = 2;
    private final int FIVE = 3;
    private final int SIX = 4;
    private final int SEVEN = 5;
    private final int EIGHT = 6;
    private final int NINE = 7;
    private final int TEN = 8;
    private final int JACK = 9;
    private final int QUEEN = 10;
    private final int KING = 11;
    private final int ACE = 12;
    */
    private final String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

    private int suit;
    private int value;

    public Card(int suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    public int getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return suits[suit] + " " + values[value];
    }

    public String toMessage() {
        return suit + " " + value;
    }
}
