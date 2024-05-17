import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import java.util.*;

//The Display is the region in the window where drawing occurs.
public class Display extends JComponent implements
KeyListener,  //need for keyboard input
MouseListener  //need for mouse input
{
    //main method for testing
    public static void main(String[] args)
    {
        Display display = new Display(4);
        display.run();
    }

    private Image image;  //image to draw
    private Image myCard1;
    private Image myCard2;
    private Image f1;
    private Image f2;
    private Image f3;
    private Image t;
    private Image r;
    private Image check;
    private Image call;
    private Image raise;
    private Image fold;
    private Image dollar;
    private Image num1;
    private Image num2;
    private Image num3;
    private Image num4;
    private Image cardBack;
    private Image dealerButton;
    private Image placeholder;
    private int imageX;  //position of left edge of image
    private int imageY;  //position of top edge of image
    private boolean dealt;
    private boolean someoneHasBet;
    private boolean betting;
    private boolean folded;
    private boolean turnCard;
    private int players;
    private int[][] deck;
    private int numCards;
    private int balance;
    private int pot;
    private int betAmount;
    private ArrayList<Location> playerlocs;
    public Display(int p)
    {
        players = p;
        imageX = 200;
        imageY = 200;
        balance = 1000;
        pot = 0;
        betAmount = 0;
        dealt = false;
        someoneHasBet = false;
        betting = false;
        folded = false;
        deck = new int[4][13];
        for(int i = 0; i < deck.length; ++i) {
            for(int j = 0; j < deck[i].length; ++j) {
                deck[i][j] = 1;
            }
        }
        numCards = 52;
        //load image
        String fileName = "image.gif";
        URL url = getClass().getResource(fileName);
        if (url == null)
            throw new RuntimeException("Unable to load:  " + fileName);
        image = new ImageIcon(url).getImage();

        int suit = (int) (Math.random() * 4);
        int value = (int) (Math.random() * 13);
        while(deck[suit][value] != 1) {
            suit = (int) (Math.random() * 4);
            value = (int) (Math.random() * 13);
        }
        deck[suit][value] = 0;
        int suit1 = (int) (Math.random() * 4);
        int value1 = (int) (Math.random() * 13);
        while(deck[suit1][value1] != 1) {
            suit1 = (int) (Math.random() * 4);
            value1 = (int) (Math.random() * 13);
        }
        deck[suit1][value1] = 0;
        numCards -= 2;
        int flopSuit1 = (int) (Math.random() * 4);
        int flopValue1 = (int) (Math.random() * 13);
        while(deck[flopSuit1][flopValue1] != 1) {
            flopSuit1 = (int) (Math.random() * 4);
            flopValue1 = (int) (Math.random() * 13);
        }
        deck[flopSuit1][flopValue1] = 0;
        int flopSuit2 = (int) (Math.random() * 4);
        int flopValue2 = (int) (Math.random() * 13);
        while(deck[flopSuit2][flopValue2] != 1) {
            flopSuit2 = (int) (Math.random() * 4);
            flopValue2 = (int) (Math.random() * 13);
        }
        deck[flopSuit2][flopValue2] = 0;
        int flopSuit3 = (int) (Math.random() * 4);
        int flopValue3 = (int) (Math.random() * 13);
        while(deck[flopSuit3][flopValue3] != 1) {
            flopSuit3 = (int) (Math.random() * 4);
            flopValue3 = (int) (Math.random() * 13);
        }
        deck[flopSuit3][flopValue3] = 0;
        int turnSuit = (int) (Math.random() * 4);
        int turnValue = (int) (Math.random() * 13);
        while(deck[turnSuit][turnValue] != 1) {
            turnSuit = (int) (Math.random() * 4);
            turnValue = (int) (Math.random() * 13);
        }
        deck[turnSuit][turnValue] = 0;
        int riverSuit = (int) (Math.random() * 4);
        int riverValue = (int) (Math.random() * 13);
        while(deck[riverSuit][riverValue] != 1) {
            riverSuit = (int) (Math.random() * 4);
            riverValue = (int) (Math.random() * 13);
        }
        deck[riverSuit][riverValue] = 0;
        numCards -= 5;
        String img = "Cards (large)/card_" + suit + "_" + value + ".png";
        String img1 = "Cards (large)/card_" + suit1 + "_" + value1 + ".png";
        String flop1 = "Cards (large)/card_" + flopSuit1 + "_" + flopValue1 + ".png";
        String flop2 = "Cards (large)/card_" + flopSuit2 + "_" + flopValue2 + ".png";
        String flop3 = "Cards (large)/card_" + flopSuit3 + "_" + flopValue3 + ".png";
        String turn = "Cards (large)/card_" + turnSuit + "_" + turnValue + ".png";
        String river = "Cards (large)/card_" + riverSuit + "_" + riverValue + ".png";
        myCard1 = new ImageIcon(getClass().getResource(img)).getImage();
        myCard2 = new ImageIcon(getClass().getResource(img1)).getImage();
        f1 = new ImageIcon(getClass().getResource(flop1)).getImage();
        f2 = new ImageIcon(getClass().getResource(flop2)).getImage();
        f3 = new ImageIcon(getClass().getResource(flop3)).getImage();
        t = new ImageIcon(getClass().getResource(turn)).getImage();
        r = new ImageIcon(getClass().getResource(river)).getImage();

        cardBack = new ImageIcon(getClass().getResource("Cards (large)/card_back.png")).getImage();
        dollar = new ImageIcon(getClass().getResource("$.png")).getImage();
        call = new ImageIcon(getClass().getResource("callbutton.png")).getImage();
        check = new ImageIcon(getClass().getResource("checkbutton.png")).getImage();
        raise = new ImageIcon(getClass().getResource("raisebutton.png")).getImage();
        fold = new ImageIcon(getClass().getResource("foldbutton.png")).getImage();
        dealerButton = new ImageIcon(getClass().getResource("dealerbutton.png")).getImage();
        placeholder = new ImageIcon(getClass().getResource("placeholder.png")).getImage(); 
        
        JFrame frame = new JFrame();  //create window
        frame.setTitle("Title");  //set title of window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //closing window will exit program
        setPreferredSize(new Dimension(1400, 1000));  //set size of drawing regio

        //need for keyboard input
        setFocusable(true);  //indicates that Display can process key presses
        addKeyListener(this);  //will notify Display when a key is pressed

        //need for mouse input
        addMouseListener(this);  //will notify Display when the mouse is pressed

        frame.getContentPane().add(this);  //add drawing region to window
        frame.pack();  //adjust window size to fit drawing region
        frame.setVisible(true);  //show window
    }

    //called automatically when Java needs to draw the Display
    public void paintComponent(Graphics g)
    {
        int width = getWidth();  //get width of drawing region
        int height = getHeight();  //get height of drawing region
        g.setColor(new Color(40, 119, 91));
        g.fillRect(0, 0, width, height);  //fill with white rectangle
        g.drawImage(f1, 320, 200, null);
        g.drawImage(f2, 470, 200, null);
        g.drawImage(f3, 620, 200, null);
        g.setColor(new Color(0, 0, 0));
        Font stringFont = new Font("SansSerif", Font.TYPE1_FONT, 30);
        g.setFont(stringFont);
        g.drawString("Balance:", 850, 700);
        g.drawString("Pot:", 630, 400);
        g.drawString("<", 160, 735);
        g.drawString(String.valueOf(pot), 700, 400);
        g.drawString(String.valueOf(balance), 980, 700);
        g.drawImage(placeholder, 100, 700, null);
        g.drawImage(placeholder, 675, 450, null);
        g.drawImage(placeholder, 1250, 700, null);
        g.drawImage(placeholder, 100, 50, null);
        g.drawImage(placeholder, 675, 50, null);
        g.drawImage(placeholder, 1250, 50, null);
        g.drawImage(placeholder, 100, 375, null);
        g.drawImage(placeholder, 1250, 375, null);
        g.drawImage(dealerButton, 675, 450, null);
        
        if(folded == false)
        {
            g.drawImage(raise, 450, 500, null);
            if(someoneHasBet == false)
            {
                g.drawImage(check, 620, 500, null);
            }
            else if(betting = false)
            {
                g.drawImage(call, 620, 500, null);
            }
            g.drawImage(fold, 790, 500, null);
            g.drawImage(myCard1, 575, 650, null);
            g.drawImage(myCard2, 675, 650, null);
        }
        else
        {
            g.drawImage(cardBack, 575, 600, null);
            g.drawImage(cardBack, 675, 600, null);
        }
        if(betting == true && turnCard == false)
        {
            g.drawImage(t, 770, 200, null);
            turnCard = true;
            betting = false;
        }
        if(betting == true && turnCard == true)
        {
            g.drawImage(t, 770, 200, null);
            g.drawImage(r, 920, 200, null);
        }
    }

    //need for keyboard input
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();  //indicates which key was pressed
        System.out.println("key pressed:  " + key);  //shows you key code values for other keys
        if (key == 38)  //tests if "up" arrow was pressed
        {
            imageY -= 10;  //image should now be drawn 10 pixels higher
            repaint();  //indicates Display must be redrawn (Java will call paintComponent)
        }
    }

    public void keyReleased(KeyEvent e) { }

    public void keyTyped(KeyEvent e) { }

    //need for mouse input
    public void mousePressed(MouseEvent e)
    {
        if(e.getX() > 628 && e.getX() < 770 && e.getY() > 539 && e.getY() < 595)
        {
            betting = true;
            repaint();
        }
        else if(e.getX() > 797 && e.getX() < 942 && e.getY() > 539 && e.getY() < 595)
        {
            folded = true;
            repaint();
        }
        else if(e.getX() > 458 && e.getX() < 604 && e.getY() > 539 && e.getY() < 595)
        {
            String s = JOptionPane.showInputDialog(this, "Bet (minimum " + betAmount + ")");
            int incomingBet = Integer.valueOf(s);
            System.out.println(incomingBet);
            pot = pot + incomingBet;
            balance = balance - incomingBet;
            if(incomingBet > betAmount)
            {
                betAmount = incomingBet;
            }
            repaint();
        }
    }

    public void mouseReleased(MouseEvent e) { }

    public void mouseClicked(MouseEvent e) { }

    public void mouseEntered(MouseEvent e) { }

    public void mouseExited(MouseEvent e) { }

    //need for automation (graphical changes not prompted by the keyboard or mouse)
    public void run()
    {

    }
}