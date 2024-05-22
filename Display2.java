import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import java.util.*;

public class Display2 extends JComponent implements KeyListener, MouseListener {

    private Client client;
    private int players;
    private Card[] cards;
    private ArrayList<Card> community;

    public Display2(int players, Client client) {
        this.players = players;
        this.client = client;

        cards = new Card[2];
        community = new ArrayList<Card>();

        JFrame frame = new JFrame();
        frame.setTitle("Title");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setResizable(false);

        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if(gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(frame);
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width, screenSize.height);

        frame.getContentPane().add(this);
        //frame.pack();
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        int width = this.getWidth();
        int height = this.getHeight();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        if(cards[0] != null && cards[1] != null) {
            Image card1 = new ImageIcon("Resources/Cards/" + cards[0].getSuit() + "_" + cards[0].getValue() + ".png").getImage();
            Image card2 = new ImageIcon("Resources/Cards/" + cards[1].getSuit() + "_" + cards[1].getValue() + ".png").getImage();

            int cardWidth = width / 10;
            int cardHeight = (card1.getHeight(null) / card1.getWidth(null)) * cardWidth;
            g.drawImage(card1, width / 2 - cardWidth, height / 3 * 2, cardWidth, cardHeight, null);
            g.drawImage(card2, width / 2, height / 3 * 2, cardWidth, cardHeight, null);

            Image cardBack = new ImageIcon("Resources/Cards/card_back.png").getImage();
            cardWidth = width / 16;
            cardHeight = (cardBack.getHeight(null) / cardBack.getWidth(null)) * cardWidth;

            //only draw with card back when still in hand, change to empty card when folded

            g.drawImage(cardBack, width - 4 * cardWidth / 2, height / 2, cardWidth, cardHeight, null);
            g.drawImage(cardBack, width - 6 * cardWidth / 2, height / 2, cardWidth, cardHeight, null);
            //put player money underneath

            g.drawImage(cardBack, width - 4 * cardWidth / 2, height / 4, cardWidth, cardHeight, null);
            g.drawImage(cardBack, width - 6 * cardWidth / 2, height / 4, cardWidth, cardHeight, null);

            g.drawImage(cardBack, 4 * cardWidth / 2, height / 2, cardWidth, cardHeight, null);
            g.drawImage(cardBack, 2 *cardWidth / 2, height / 2, cardWidth, cardHeight, null);

            g.drawImage(cardBack, 4 * cardWidth / 2, height / 4, cardWidth, cardHeight, null);
            g.drawImage(cardBack, 2 * cardWidth / 2, height / 4, cardWidth, cardHeight, null);





            Image button = new ImageIcon("Resources/Button.png").getImage();

            int buttonWidth = (int) (width / 11.5);
            int buttonHeight = (button.getHeight(null) / button.getWidth(null)) * buttonWidth;

            g.drawImage(button, width / 2 + buttonWidth, height / 5 * 4, buttonWidth, buttonHeight, null);
            g.drawImage(button, width / 2, height / 5 * 4, buttonWidth, buttonHeight, null);
            g.drawImage(button, width / 2 - buttonWidth, height / 5 * 4, buttonWidth, buttonHeight, null);
            g.drawImage(button, width / 2 - 2 * buttonWidth, height / 5 * 4, buttonWidth, buttonHeight, null);

            Font font = new Font("VCR OSD Mono", Font.TYPE1_FONT, 30);
            g.setColor(new Color(77, 87, 100)); //make this color into variable
            g.setFont(font);
            g.drawString("RAISE", width / 2 - 2 * buttonWidth + 18, height / 5 * 4 + buttonHeight / 2 + 8);
            g.drawString("CALL", width / 2 - buttonWidth + 28, height / 5 * 4 + buttonHeight / 2 + 8);
            g.drawString("CHECK", width / 2 + 18, height / 5 * 4 + buttonHeight / 2 + 8);
            g.drawString("FOLD", width / 2 + buttonWidth + 28, height / 5 * 4 + buttonHeight / 2 + 8);




            g.setColor(new Color(77, 87, 100));
            g.drawString("CHIPS:", width / 2 - 3 * buttonWidth, height / 5 * 4 + buttonHeight / 2 + 8); //change with numbers laters
            g.drawString("INCOMING BET:", width / 2, height / 3 * 2 - cardHeight / 3);





            for(int i = 0; i < community.size(); ++i) {
                Image cardImage = new ImageIcon("Resources/Cards/" + community.get(i).getSuit() + "_" + community.get(i).getValue() + ".png").getImage();
                g.drawImage(cardImage, width / 2 - (i - 2) * cardWidth, height / 3, cardWidth, cardHeight, null);
            }

        }
    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {}

    public void keyTyped(KeyEvent e) {}

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void updateHand(Card[] cards) {
        this.cards = cards;
        repaint();
    }

    public void updateCommunity(ArrayList<Card> community) {
        this.community = community;
        repaint();
    }

    public void run() {

    }
}
