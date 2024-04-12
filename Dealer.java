public class Dealer {
    public static void main(String[] args) {
        Deck deck = new Deck();
        while(deck.cardsLeft() > 0) {
            System.out.println(deck.drawCard());
        }
    }
}

//accept connections
//array of server threads
//deal cards, shuffle
//tell client cards
//record chips, tell chips to client
//subtract blinds
//turns
//
