import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import java.util.*;

public class Display2 extends JComponent implements KeyListener, MouseListener {

    public static void main(String[] args) {
        Display2 display = new Display2(4);
        display.run();
    }

    private int players;
    private Card[] cards;

    public Display2(int p) {
        players = p;
        cards = new Card[2];

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

    public void run() {

    }
}
