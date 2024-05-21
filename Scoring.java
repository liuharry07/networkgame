import java.util.ArrayList;

public class Scoring {
    String rating;
    Card[] hand;

    public Scoring(Card[] cards) {
        hand = cards;
    }

    public static void main(String[] args) {
        Card[] bob = new Card[7];
        bob[0] = new Card(0, 3);
        bob[1] = new Card(0, 4);
        bob[2] = new Card(0, 1);
        bob[3] = new Card(1, 5);
        bob[4] = new Card(2, 8);
        bob[5] = new Card(3, 8);
        bob[6] = new Card(1, 2);
        Scoring joe = new Scoring(bob);
        int[] score = joe.valuateHand();
        System.out.println(score);
    }

    public int[] valuateHand() {
        int[] finalhand = new int[3];
        ArrayList<Card> posStraight = new ArrayList<Card>();
        boolean pair = false;
        boolean triple = false;
        boolean dubTrip = false;
        boolean quad = false;
        boolean straight = false;
        boolean flush = false;
        boolean dubPair = false;
        boolean tripPair = false;
        boolean straightFlush = false;
        int paired = -1;
        int trip = -1;
        int four = -1;
        int straightTop = -1;
        int flushFace = -1;
        int doublePair = -1;
        int dubTriple = -1;
        int triplePair = -1;
        int flushSpade = 0;
        int flushHeart = 0;
        int flushDiamond = 0;
        int flushClub = 0;
        int flushHighCard = 0;
        sortHand();
        Card[] down = hand;
        int highCard = down[0].getValue();
        posStraight.add(down[0]);
        for(int i = 1; i < 7; i++) {
            if(down[i] == null) {
                finalhand[0] = 0;
                finalhand[1] = 0;
                finalhand[2] = 0;
                return finalhand;
            }
            else if(down[i].getValue() != down[i - 1].getValue()) {
                posStraight.add(down[i]);
            }
            if(down[i].getValue() == down[i - 1].getValue() && !pair) {
                pair = true;
                paired = down[i].getValue();
            }
            else if(down[i].getValue() == down[i - 1].getValue() && paired == down[i].getValue() && !dubPair && !triple) {
                pair = false;
                paired = -1;
                triple = true;
                trip = down[i].getValue();
            }
            else if(down[i].getValue() == down[i - 1].getValue() && paired == down[i].getValue() && !dubPair && triple) {
                pair = false;
                paired = -1;
                dubTrip = true;
                dubTriple = down[i].getValue();
            }
            else if(down[i].getValue() == down[i - 1].getValue() && paired == down[i].getValue() && dubPair && !tripPair && !triple) {
                pair = true;
                paired = doublePair;
                dubPair = false;
                doublePair = -1;
                triple = true;
                trip = down[i].getValue();
            }
            else if(down[i].getValue() == down[i - 1].getValue() && paired == down[i].getValue() && dubPair && !tripPair && triple) {
                pair = true;
                paired = doublePair;
                dubPair = false;
                doublePair = -1;
                dubTrip = true;
                dubTriple = down[i].getValue();
            }
            else if(down[i].getValue() == down[i - 1].getValue() && paired == down[i].getValue() && dubPair && tripPair && !triple) {
                pair = true;
                paired = doublePair;
                dubPair = true;
                doublePair = triplePair;
                tripPair = false;
                triplePair = -1;
                triple = true;
                trip = down[i].getValue();
            }
            else if(down[i].getValue() == down[i - 1].getValue() && paired == down[i].getValue() && dubPair && tripPair && triple) {
                pair = true;
                paired = doublePair;
                dubPair = true;
                doublePair = triplePair;
                tripPair = false;
                triplePair = -1;
                dubTrip = true;
                dubTriple = down[i].getValue();
            }
            else if(down[i].getValue() == down[i - 1].getValue() && pair && !dubPair) {
                dubPair = true;
                doublePair = down[i].getValue();
            }
            else if(down[i].getValue() == down[i - 1].getValue() && doublePair == down[i].getValue() && !tripPair && !triple) {
                dubPair = false;
                doublePair = -1;
                triple = true;
                trip = down[i].getValue();
            }
            else if(down[i].getValue() == down[i - 1].getValue() && doublePair == down[i].getValue() && !tripPair && triple) {
                dubPair = false;
                doublePair = -1;
                dubTrip = true;
                dubTriple = down[i].getValue();
            }
            else if(down[i].getValue() == down[i - 1].getValue() && doublePair == down[i].getValue() && tripPair && !triple) {
                dubPair = true;
                doublePair = triplePair;
                tripPair = false;
                triplePair = -1;
                triple = true;
                trip = down[i].getValue();
            }
            else if(down[i].getValue() == down[i - 1].getValue() && doublePair == down[i].getValue() && tripPair && triple) {
                dubPair = true;
                doublePair = triplePair;
                tripPair = false;
                triplePair = -1;
                dubTrip = true;
                dubTriple = down[i].getValue();
            }
            else if(down[i].getValue() == down[i - 1].getValue() && pair && dubPair) {
                tripPair = true;
                triplePair = hand[i].getValue();
            }
            else if(down[i].getValue() == down[i - 1].getValue() && triplePair == down[i].getValue() && !triple) {
                tripPair = false;
                triplePair = -1;
                triple = true;
                trip = down[i].getValue();
            }
            else if(down[i].getValue() == down[i - 1].getValue() && triplePair == down[i].getValue() && triple) {
                tripPair = false;
                triplePair = -1;
                dubTrip = true;
                dubTriple = down[i].getValue();
            }
            else if(down[i].getValue() == down[i - 1].getValue() && trip == down[i].getValue() && !dubTrip && !quad) {
                triple = false;
                trip = -1;
                quad = true;
                four = down[i].getValue();
            }
            else if(down[i].getValue() == down[i - 1].getValue() && trip == down[i].getValue() && dubTrip && !quad) {
                triple = true;
                trip = dubTriple;
                quad = true;
                four = down[i].getValue();
            }
            else if(down[i].getValue() == down[i - 1].getValue() && dubTriple == down[i].getValue() && !quad) {
                dubTrip = false;
                dubTriple = -1;
                quad = true;
                four = down[i].getValue();
            }
            if(down[i].getSuit() == 0) {
                flushSpade++;
            }
            else if(down[i].getSuit() == 1) {
                flushClub++;
            }
            else if(down[i].getSuit() == 2) {
                flushDiamond++;
            }
            else if(down[i].getSuit() == 3) {
                flushHeart++;
            }
        }
        if(flushSpade >= 5) {
            flush = true;
            flushFace = 0;
        }
        else if(flushClub >= 5) {
            flush = true;
            flushFace = 1;
        }
        else if(flushDiamond >= 5) {
            flush = true;
            flushFace = 2;
        }
        else if(flushHeart >= 5) {
            flush = true;
            flushFace = 3;
        }
        int la = 0;
        while(la < 7 && down[la].getSuit() != flushFace) {
            la++;
        }
        Card[] straightList = new Card[5];
        if(la < 7) {
            flushHighCard = down[la].getValue();
        }
        if(posStraight.size() >= 5) {
            if(posStraight.get(0).getValue() == 12 && posStraight.get(posStraight.size() - 1).getValue() == 0 && posStraight.get(posStraight.size() - 2).getValue() == 1 && posStraight.get(posStraight.size() - 3).getValue() == 2 && posStraight.get(posStraight.size() - 4).getValue() == 3) {
                straight = true;
                straightTop = 4;
                straightList[0] = posStraight.get(0);
                straightList[1] = posStraight.get(posStraight.size() - 1);
                straightList[2] = posStraight.get(posStraight.size() - 2);
                straightList[3] = posStraight.get(posStraight.size() - 3);
                straightList[4] = posStraight.get(posStraight.size() - 4);
            }
            else {

                int a = 0;
                for(a = 1; a < posStraight.size(); a++) {
                    if((posStraight.get(a - 1).getValue() - posStraight.get(a).getValue()) == 1) {
                        straightList[a - 1] = posStraight.get(a - 1);
                    }
                    else {
                        break;
                    }
                }
                if(a >= 5) {
                    straightList[a - 1] = posStraight.get(a - 1);
                    straight = true;
                    straightTop = posStraight.get(0).getValue();
                }
                else {
                    a--;
                    if(a <= 1) {
                        posStraight.remove(0);
                        straightList = new Card[5];
                        if(a == 1) {
                            posStraight.remove(1);
                        }
                        int b = 1;

                        for(b = 1; b < posStraight.size(); b++) {
                            if((posStraight.get(b - 1).getValue() - posStraight.get(b).getValue()) == 1) {
                                straightList[b - 1] = posStraight.get(b - 1);
                            }
                            else {
                                break;
                            }
                        }
                        if(b == 5 && (posStraight.get(posStraight.size() - 2).getValue() - posStraight.get(posStraight.size() - 1).getValue()) == 1) {
                            straightList[4] = posStraight.get(posStraight.size() - 1);
                        }
                        if(b == 5) {
                            straight = true;
                            straightTop = posStraight.get(0).getValue();
                        }
                    }
                }
            }
        }

        if(straight) {
            int spade = 0;
            int club = 0;
            int diamond = 0;
            int heart = 0;
            int through = 0;
            while(through < 5) {
                if(straightList[through].getSuit() == 0) {
                    spade++;
                }
                if(straightList[through].getSuit() == 1) {
                    club++;
                }
                if(straightList[through].getSuit() == 2) {
                    diamond++;
                }
                if(straightList[through].getSuit() == 3) {
                    heart++;
                }
                through++;
            }
            if(spade >= 5 || club >= 5 || diamond >= 5 || heart >= 5) {
                straightFlush = true;
            }
        }
        if(straightFlush) {
            finalhand[0] = 8;
            finalhand[1] = 0;
        }
        else if(quad) {
            finalhand[0] = 7;
            finalhand[1] = four;
        }
        else if(flush) {
            finalhand[0] = 4;
            finalhand[1] = flushHighCard;
        }
        else if(straight) {
            finalhand[0] = 3;
            finalhand[1] = straightTop;
        }
        else if(triple) {
            if(dubTrip) {
                if(dubTriple > trip) {
                    if(pair) {
                        if(doublePair > triplePair && doublePair > paired) {
                            finalhand[0] = 6;
                            finalhand[1] = dubTriple;
                            finalhand[2] = doublePair;
                        }
                        else if(doublePair > triplePair && paired > doublePair) {
                            finalhand[0] = 6;
                            finalhand[1] = dubTriple;
                            finalhand[2] = paired;
                        }
                        else if(triplePair > paired) {
                            finalhand[0] = 6;
                            finalhand[1] = dubTriple;
                            finalhand[2] = triplePair;
                        }
                    }
                    else {
                        finalhand[0] = 3;
                        finalhand[1] = dubTriple;
                    }
                }
            }
            else {
                {
                    if(pair) {
                        if(!dubPair) {
                            finalhand[0] = 6;
                            finalhand[1] = trip;
                            finalhand[2] = paired;
                        }
                        if(doublePair > triplePair && doublePair > paired) {
                            finalhand[0] = 6;
                            finalhand[1] = trip;
                            finalhand[2] = doublePair;
                        }
                        else if(doublePair >= triplePair && paired > doublePair) {
                            finalhand[0] = 6;
                            finalhand[1] = trip;
                            finalhand[2] = paired;
                        }
                        else if(triplePair > paired) {
                            finalhand[0] = 6;
                            finalhand[1] = trip;
                            finalhand[2] = triplePair;
                        }
                    }
                    else {
                        finalhand[0] = 3;
                        finalhand[1] = trip;
                    }
                }
            }
        }
        else if(pair) {
            if(dubPair) {
                if(doublePair > triplePair && triplePair < paired) {
                    finalhand[0] = 2;
                    finalhand[1] = paired;
                    finalhand[2] = doublePair;
                }
                else if(triplePair > doublePair && doublePair < paired) {
                    finalhand[0] = 2;
                    finalhand[1] = paired;
                    finalhand[2] = triplePair;
                }
                else if(triplePair > paired && doublePair > paired) {
                    finalhand[0] = 2;
                    finalhand[1] = doublePair;
                    finalhand[2] = triplePair;
                }
            }
            else {
                finalhand[0] = 1;
                finalhand[1] = paired;
            }
        }
        else {
            finalhand[0] = 0;
            finalhand[1] = highCard;
        }

        return finalhand;
    }
    //8 is straight flush, 7 is four of a kind, 6 is fullhouse, 5 is flush, 4 is straight, 3 is three of a kind, 2 is two pairs,
    //1 is one pair, 0 is high card (i.e. nothing better)


    public void sortHand() {
        for(int count = 0; count < hand.length; count++) {
            for(int i = 0; i < hand.length - 1; i++) {
                if(hand[i].getValue() < hand[i + 1].getValue()) {
                    Card now = hand[i];
                    hand[i] = hand[i + 1];
                    hand[i + 1] = now;
                }
            }
        }
    }
}


//sort cards by value first
//level for 5 card set
//make sets of 5 from 7 (hand and down)
