import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class Display2 extends JComponent implements KeyListener, MouseListener, ChangeListener {

    private Client client;
    /*
    private int players;
    private Card[] cards;
    private ArrayList<Card> community;
    */

    private Region[] buttonRegions;
    private JSlider slider;
    private static Color dark = new Color(77, 87, 100);
    private static Color light = new Color(199, 215, 235);

    public Display2(int players, Client client) {
        this.client = client;

        /*
        cards = new Card[2];
        community = new ArrayList<Card>();
        */
        buttonRegions = new Region[4];


        JFrame frame = new JFrame();
        frame.setTitle("Title");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setUndecorated(true);
        frame.setResizable(false);

        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);

        /* 
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if(gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(frame);
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width, screenSize.height);
        */
        

        frame.setSize(1440, 800);

        slider = new JSlider(0, 100);
        slider.setBounds(frame.getWidth() / 3, frame.getHeight() / 10 * 9, frame.getWidth() / 3, frame.getHeight() / 10);
        slider.setMajorTickSpacing(100);
        slider.setMinorTickSpacing(50);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(this);

        frame.getContentPane().add(slider);


        frame.getContentPane().add(this);


        //frame.pack();
        frame.setVisible(true);



    }

    public void paintComponent(Graphics g) {
        int width = this.getWidth();
        int height = this.getHeight();


        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);


        Image table = new ImageIcon("Resources/Table.png").getImage();
        g.drawImage(table, 0, 0, width, (table.getHeight(null) / table.getWidth(null)) * width, null);


        if(client.cards[0] != null && client.cards[1] != null) {
            Image card1 = new ImageIcon("Resources/Cards/" + client.cards[0].getSuit() + "_" + client.cards[0].getValue() + ".png").getImage();
            Image card2 = new ImageIcon("Resources/Cards/" + client.cards[1].getSuit() + "_" + client.cards[1].getValue() + ".png").getImage();

            int cardWidth = width / 10;
            int cardHeight = (card1.getHeight(null) / card1.getWidth(null)) * cardWidth;
            g.drawImage(card1, width / 2 - cardWidth, height / 48 * 32, cardWidth, cardHeight, null);
            g.drawImage(card2, width / 2, height / 48 * 32, cardWidth, cardHeight, null);



            for(int i = 0; i < client.community.size(); ++i) {
                Image cardImage = new ImageIcon("Resources/Cards/" + client.community.get(i).getSuit() + "_" + client.community.get(i).getValue() + ".png").getImage();
                g.drawImage(cardImage, width / 2 + (i - 2) * cardWidth - cardWidth / 2, height / 3, cardWidth, cardHeight, null);
            }



            Font font = new Font("VCR OSD Mono", Font.TYPE1_FONT, 30);
            g.setColor(dark);
            g.setFont(font);

            Image cardBack = new ImageIcon("Resources/Cards/card_back.png").getImage();
            Image cardEmpty = new ImageIcon("Resources/Cards/card_empty.png").getImage();
            cardWidth = width / 16;
            cardHeight = (cardBack.getHeight(null) / cardBack.getWidth(null)) * cardWidth;

            //only draw with card back when still in hand, change to empty card when folded
            if(client.players > 1) {
                int num = (client.playerNum + 1) % client.players;
                if(client.foldedPlayers[num] == true) {
                    g.drawImage(cardEmpty, width - 4 * cardWidth / 2, height / 2, cardWidth, cardHeight, null);
                    g.drawImage(cardEmpty, width - 6 * cardWidth / 2, height / 2, cardWidth, cardHeight, null);
                }
                else {
                    g.drawImage(cardBack, width - 4 * cardWidth / 2, height / 2, cardWidth, cardHeight, null);
                    g.drawImage(cardBack, width - 6 * cardWidth / 2, height / 2, cardWidth, cardHeight, null);
                }
                if(client.turn == num) {
                    g.drawString("->", width - 7 * cardWidth / 2, height / 2 + 15);
                }
                else {
                    g.drawString(Integer.toString(client.playerBets[num]), width - 7 * cardWidth / 2, height / 2 + 15);
                }
            }
            //put player money underneath
            if(client.players > 2) {
                int num = (client.playerNum + 2) % client.players;
                if(client.foldedPlayers[num] == true) {
                    g.drawImage(cardEmpty, width - 4 * cardWidth / 2, height / 4, cardWidth, cardHeight, null);
                    g.drawImage(cardEmpty, width - 6 * cardWidth / 2, height / 4, cardWidth, cardHeight, null);
                }
                else {
                    g.drawImage(cardBack, width - 4 * cardWidth / 2, height / 4, cardWidth, cardHeight, null);
                    g.drawImage(cardBack, width - 6 * cardWidth / 2, height / 4, cardWidth, cardHeight, null);
                }

                if(client.turn == num) {
                    g.drawString("->", width - 7 * cardWidth / 2, height / 4 + 15);
                }
                else {
                    g.drawString(Integer.toString(client.playerBets[num]), width - 7 * cardWidth / 2, height / 4 + 15);

                }
            }

            if(client.players > 3) {
                int num = (client.playerNum + 3) % client.players;
                if(client.foldedPlayers[num] == true) {
                    g.drawImage(cardEmpty, 4 * cardWidth / 2, height / 2, cardWidth, cardHeight, null);
                    g.drawImage(cardEmpty, 2 * cardWidth / 2, height / 2, cardWidth, cardHeight, null);
                }
                else {
                    g.drawImage(cardBack, 4 * cardWidth / 2, height / 2, cardWidth, cardHeight, null);
                    g.drawImage(cardBack, 2 * cardWidth / 2, height / 2, cardWidth, cardHeight, null);
                }

                if(client.turn == num) {
                    g.drawString("<-", 6 * cardWidth / 2, height / 2 + 15);
                }
                else {
                    g.drawString(Integer.toString(client.playerBets[num]), 6 * cardWidth / 2, height / 2 + 15);
                }
            }

            if(client.players > 4) {
                g.drawImage(cardBack, 4 * cardWidth / 2, height / 4, cardWidth, cardHeight, null);
                g.drawImage(cardBack, 2 * cardWidth / 2, height / 4, cardWidth, cardHeight, null);
                g.drawString(Integer.toString(client.playerBets[(client.playerNum + 4) % client.players]), 6 * cardWidth / 2, height / 4 + 15);
            }



            Image button = new ImageIcon("Resources/Button.png").getImage();

            int buttonWidth = (int) (width / 11.5);
            int buttonHeight = (button.getHeight(null) / button.getWidth(null)) * buttonWidth;

            String[] buttonNames = {"RAISE", "CALL", "CHECK", "FOLD"};

            for(int i = 0; i < buttonNames.length; ++i) {
                int x = width / 2 + (i - 2) * buttonWidth;
                int y = height / 5 * 4;
                buttonRegions[i] = new Region(x, y, x + buttonWidth, y + buttonHeight);
                if(client.buttons[i]) {
                    g.setColor(dark);
                    buttonRegions[i].enabled(true);
                }
                else {
                    g.setColor(light);
                    buttonRegions[i].enabled(false);
                }
                g.drawImage(button, x, y, buttonWidth, buttonHeight, null);
                g.drawString(buttonNames[i], x + (68 - 10 * buttonNames[i].length()), y + buttonHeight / 2 + 8);
            }



            g.setColor(dark);
            g.drawString(Integer.toString(client.chips), width / 2 - 3 * buttonWidth, height / 5 * 4 + buttonHeight / 2 + 8); //change with numbers laters
            g.drawString(Integer.toString(client.incomingBet), width / 2 - Integer.toString(client.incomingBet).length() * 15 / 2, height / 3 * 2 - cardHeight / 3);
            if(client.win) {
                g.drawString("YOU WON $" + client.winAmount + "!", width / 2 - ("YOU WON $" + client.winAmount + "!").length() * 15 / 2, height / 5);
            }
            else {
                g.drawString("POT: " + client.pot, width / 2 - ("POT: " + client.pot).length() * 15 / 2, height / 5);
            }

            g.setColor(light);
            slider.setMaximum(client.chips);
            slider.setMinimum(client.incomingBet + 1);
            g.drawString(Integer.toString(slider.getValue()), width / 2 - 3 * buttonWidth, height / 5 * 4 + buttonHeight);



        }
    }

    public void stateChanged(ChangeEvent e) {
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 13) {
            client.reset();
        }
    }

    public void keyReleased(KeyEvent e) {}

    public void keyTyped(KeyEvent e) {}

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {
        for(int i = 0; i < buttonRegions.length; ++i) {
            if(buttonRegions[i].contains(e.getX(), e.getY())) {
                client.buttonAction(i, slider.getValue());

            }
        }
    }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void run() {

    }
}

/*
 * every player bet is on client done
 * shows whose turn it is done
 * chips under each player
 * indicate when someone wins!!!! done
 * can't call when too much
 */
