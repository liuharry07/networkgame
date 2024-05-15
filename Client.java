import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.*;

public class Client extends Thread implements ActionListener {
    private BufferedReader in; //for receiving messages from the server
    private PrintWriter out; //for sending messages to the server
    private JFrame frame;
    private JButton bet;
    private JButton call;
    private JButton check;
    private JButton fold;
    private JTextArea hand;
    private JTextField betValue;
    private JTextArea chipsDisplay;
    private JTextArea tableDisplay;
   
    //need to write method
    private int players;
    private Card[] cards;
    private int chips;
    private int pot;
    private int incomingBet;
    private ArrayList<Card> community;
    private boolean[] foldedPlayers;

    public Client(String ipAddress) {
        try {
            frame = new JFrame("poker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            cards = new Card[2];

            chips = 1000;
            incomingBet = 0;
            pot = 0;
            community = new ArrayList<Card>();

            frame.getContentPane().setLayout(new GridLayout(2, 4));

            Font font = new Font(null, Font.PLAIN, 72);

            bet = new JButton("bet");
            bet.setFont(font);
            bet.setEnabled(false);
            bet.setActionCommand("bet");
            bet.addActionListener(this);
            frame.getContentPane().add(bet);

            call = new JButton("call");
            call.setFont(font);
            call.setEnabled(false);
            call.setActionCommand("call");
            call.addActionListener(this);
            frame.getContentPane().add(call);

            check = new JButton("check");
            check.setFont(font);
            check.setEnabled(false);
            check.setActionCommand("check");
            check.addActionListener(this);
            frame.getContentPane().add(check);

            fold = new JButton("fold");
            fold.setFont(font);
            fold.setEnabled(false);
            fold.setActionCommand("fold");
            fold.addActionListener(this);
            frame.getContentPane().add(fold);

            if(cards != null) {
                hand = new JTextArea("no hand");
                frame.getContentPane().add(hand);
            }

            betValue = new JTextField("0");
            frame.getContentPane().add(betValue);

            chipsDisplay = new JTextArea(Integer.toString(chips));
            frame.getContentPane().add(chipsDisplay);

            tableDisplay = new JTextArea();
            frame.getContentPane().add(tableDisplay);

            frame.pack();

            //connect to server running on port 9000 of given ipAddress
            Socket socket = new Socket(ipAddress, 9000);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            frame.setSize(new Dimension(1000, 600));
            frame.setVisible(true);

            start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //keep receiving messages from server
    public void run() {
        try {
            while(true) {
                String message = in.readLine();
                System.out.println("Client received: " + message);

                //convert message string into array of tokens (originally separated by spaces)
                String[] tokens = message.split(" ");
                if(tokens[0].equals("start")) {
                    bet.setEnabled(true);
                    players = Integer.parseInt(tokens[1]);
                    foldedPlayers = new boolean[players];
                    //call.setEnabled(true);
                }
                if(tokens[0].equals("deal")) {
                    for(int i = 1; i < 5; i += 2) {
                        cards[i / 2] = new Card(Integer.parseInt(tokens[i]), Integer.parseInt(tokens[i + 1]));
                    }
                    hand.setText(cards[0].toString() + " " + cards[1].toString());
                }
                if(tokens[0].equals("turn")) {
                    bet.setEnabled(true);
                    fold.setEnabled(true);
                    if(Integer.parseInt(tokens[1]) == 0) {
                        check.setEnabled(true);
                    }
                    else {
                        call.setEnabled(true);
                    }
                    incomingBet = Integer.parseInt(tokens[1]);
                }
                if(tokens[0].equals("pot")) {
                    pot = Integer.parseInt(tokens[1]);
                }
                if(tokens[0].equals("community")) {
                    Card card;
                    card = new Card(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
                    community.add(card);
                }
                if(tokens[0].equals("win")) {
                    chips += Integer.parseInt(tokens[1]);
                }
                if(tokens[0].equals("reset")) {
                    community = new ArrayList<Card>();
                    incomingBet = 0;
                    pot = 0;
                }
                updateDisplay();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //send message to server
    private void send(String message) {
        System.out.println("Client sending: " + message);
        out.println(message);
    }

    //called when bet is pressed
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch(command) {
            case "bet": {
                int betAmount = Integer.parseInt(betValue.getText());
                if(betAmount > incomingBet && betAmount <= chips) {
                    send("bet " + betValue.getText());
                    bet.setEnabled(false);
                    check.setEnabled(false);
                    call.setEnabled(false);
                    fold.setEnabled(false);
                    chips -= Integer.parseInt(betValue.getText());
                    updateDisplay();
                }
                else if (betAmount > incomingBet) {
                    chipsDisplay.setText(chips + "\n incoming bet: " + incomingBet + "\n ERROR NEED BIGGER BET DUMBASS");
                }
                else if (betAmount <= chips) {
                    chipsDisplay.setText(chips + "\n incoming bet: " + incomingBet + "\n ERROR YOU CAN'T BET MONEY YOU DON'T HAVE YOU SILLY GOOBER");
                }
                break;
            }
            case "call": {
                send("bet " + incomingBet);
                bet.setEnabled(false);
                check.setEnabled(false);
                call.setEnabled(false);
                fold.setEnabled(false);
                chips -= incomingBet;
                updateDisplay();
                break;
            }
            case "check": {
                send("bet " + 0);
                bet.setEnabled(false);
                check.setEnabled(false);
                call.setEnabled(false);
                fold.setEnabled(false);
                updateDisplay();
                break;
            }
            case "fold": {
                send("fold");
                bet.setEnabled(false);
                check.setEnabled(false);
                call.setEnabled(false);
                fold.setEnabled(false);
                updateDisplay();
                break;
            }
        }
    }

    private void updateDisplay() {
        chipsDisplay.setText(Integer.toString(chips));
        tableDisplay.setText("incoming bet: " + incomingBet + "\npot: " + pot);
        for(Card card : community) {
            tableDisplay.setText(tableDisplay.getText() + "\n" + card.toString());
        }
    }
}
