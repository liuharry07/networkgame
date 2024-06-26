import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private int players;
    private ServerThread threads[];
    private Socket sockets[];

    private int bet;
    private int pot;
    private Card[] community;
    private boolean[] foldedPlayers;
    private int numFoldedPlayers;
    private int startingPlayer;
    private Map<Integer, Card[]> playerCards;

    Deck deck;

    public Server() throws InterruptedException {
        try {
            ServerSocket serverSocket = new ServerSocket(9000);
            players = 2;
            threads = new ServerThread[players];
            sockets = new Socket[players];

            for(int i = 0; i < players; ++i) {
                sockets[i] = serverSocket.accept();
                threads[i] = new ServerThread(sockets[i], this, i);
            }

            startingPlayer = 0;
            playerCards = new HashMap<Integer, Card[]>();

            for(int i = 0; i < players; ++i) {
                threads[i].send("start " + players + " " + i);
            }

            while(true) {
                pot = 0;
                bet = 0;
                deck = new Deck();
                community = new Card[5];
                foldedPlayers = new boolean[players];
                numFoldedPlayers = 0;
                for(int i = 0; i < players; ++i) {
                    threads[i].send("reset");
                }

                deal();
                if(!getBets(startingPlayer)) {
                    flop();
                    if(!getBets(startingPlayer)) {
                        turn();
                        if(!getBets(startingPlayer)) {
                            river();
                            getBets(startingPlayer);
                        }
                    }
                }


                int highestRank = 0;
                int highestHighCard = 0;
                int highestRandomCard = 0;
                ArrayList<Integer> winners = new ArrayList<Integer>();

                int winner = 0;
                if(numFoldedPlayers != players - 1) {
                    for(int i = 0; i < players; ++i) {
                        if(!foldedPlayers[i]) {
                            Card[] combinedCards = new Card[7];
                            for(int j = 0; j < 2; ++j) {
                                combinedCards[j] = playerCards.get(i)[j];
                            }

                            for(int j = 2; j < 7; ++j) {
                                combinedCards[j] = community[j - 2];
                            }

                            System.out.println(Arrays.toString(combinedCards));
                            //for(int j = 0; j < 7; ++j) {
                            //  System.out.println(combinedCards[j].toString());
                            // }


                            int[] scores = (new Scoring(combinedCards)).valuateHand();

                            System.out.println(Arrays.toString(scores));


                            int rank = scores[0];
                            int highCard = scores[1];
                            int someRandomNumber = scores[2];

                            if(rank > highestRank) {
                                winner = i;
                                highestRank = rank;
                                highestHighCard = highCard;
                                highestRandomCard = someRandomNumber;
                                winners = new ArrayList<Integer>();
                                winners.add(i);
                            }
                            else if(rank == highestRank) {
                                if(highCard > highestHighCard) {
                                    winner = i;
                                    winners = new ArrayList<Integer>();
                                    highestHighCard = highCard;
                                    winners.add(i);
                                }
                                else if(highCard == highestHighCard) {
                                    if(someRandomNumber > highestRandomCard) {
                                        winner = i;
                                        winners = new ArrayList<Integer>();
                                        winners.add(i);
                                        highestRandomCard = someRandomNumber;
                                    }
                                    else if(someRandomNumber == highestRandomCard) {
                                        winners.add(i);
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    for(int i = 0; i < players; ++i) {
                        if(!foldedPlayers[i]) {
                            winner = i;
                        }
                    }
                }

                /*
                if(!foldedPlayers[i]) {
                    winner = i;
                }
                */

                if(winners.size() > 1) {
                    int splitPot = pot / winners.size();
                    for(int i = 0; i < winners.size(); i++) {
                        System.out.println("win " + i + " " + splitPot);
                        threads[i].send("win " + splitPot);
                    }
                }
                else {
                    System.out.println("win " + winner);
                    threads[winner].send("win " + pot);
                }

                ++startingPlayer;
                if(startingPlayer == players) {
                    startingPlayer = 0;
                }
            }
        } catch (

        IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deal() {
        for(int i = 0; i < players; ++i) {
            Card card = deck.drawCard();
            Card card2 = deck.drawCard();
            threads[i].send("deal " + card.toMessage() + " " + card2.toMessage());
            Card[] cards = {card, card2};
            playerCards.put(i, cards);
        }
    }

    private void flop() {
        for(int i = 0; i < 3; ++i) {
            community[i] = deck.drawCard();
        }
        for(int i = 0; i < players; ++i) {
            for(int j = 0; j < 3; ++j) {
                threads[i].send("community " + community[j].toMessage());
            }
        }
    }

    private void turn() {
        community[3] = deck.drawCard();
        for(int i = 0; i < players; ++i) {
            threads[i].send("community " + community[3].toMessage());
        }
    }

    private void river() {
        community[4] = deck.drawCard();
        for(int i = 0; i < players; ++i) {
            threads[i].send("community " + community[4].toMessage());
        }
    }

    private synchronized int goAround(int startingPlayer) throws InterruptedException {
        int i = startingPlayer;
        while(foldedPlayers[i]) {
            ++i;
            if(i == players) {
                i = 0;
            }
        }
        int oldBet = bet;
        for(int j = 0; j < players; ++j)
            threads[j].send("turn " + bet + " " + i);
        wait();
        if(bet > oldBet) {
            startingPlayer = i;
        }
        if(!foldedPlayers[i]) {
            for(int j = 0; j < players; ++j) {
                threads[j].send("bet " + i + " " + bet);
            }
        }
        ++i;
        if(i == players) {
            i = 0;
        }
        while(i != startingPlayer) {
            if(!foldedPlayers[i]) {
                oldBet = bet;
                for(int j = 0; j < players; ++j)
                    threads[j].send("turn " + bet + " " + i);
                wait();
                if(bet > oldBet) {
                    startingPlayer = i;
                }
                if(!foldedPlayers[i]) {
                    for(int j = 0; j < players; ++j) {
                        threads[j].send("bet " + i + " " + bet);
                    }
                }
            }
            ++i;
            if(i == players) {
                i = 0;
            }
        }
        return bet;
    }

    public synchronized void setBetValue(int bet) {
        notifyAll();
        this.bet = bet;
    }

    public synchronized void fold(int playerNum) {
        for(int i = 0; i < players; ++i) {
            threads[i].send("fold " + playerNum);
        }
        foldedPlayers[playerNum] = true;
        ++numFoldedPlayers;
        notifyAll();
    }

    public boolean getBets(int startingPlayer) {
        try {
            pot += goAround(startingPlayer) * (players - numFoldedPlayers);
            for(int i = 0; i < players; ++i) {
                threads[i].send("pot " + pot);
            }
            bet = 0;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(numFoldedPlayers == players - 1) {
            return true;
        }
        return false;
    }

    public void reset() {
        pot = 0;
        bet = 0;
        deck = new Deck();
        community = new Card[5];
        foldedPlayers = new boolean[players];
        numFoldedPlayers = 0;
        for(int i = 0; i < players; ++i) {
            threads[i].send("reset");
        }
    }
}
