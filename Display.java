import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;

//The Display is the region in the window where drawing occurs.
public class Display extends JComponent implements
  KeyListener,  //need for keyboard input
  MouseListener  //need for mouse input
{
  //main method for testing
  public static void main(String[] args)
  {
    Display display = new Display();
    display.run();
  }

  private int imageX;  //position of left edge of image
  private int imageY;  //position of top edge of image
  
  public Display()
  {
    imageX = 200;
    imageY = 200;
    
    JFrame frame = new JFrame();  //create window
    frame.setTitle("Title");  //set title of window
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //closing window will exit program
    setPreferredSize(new Dimension(400, 400));  //set size of drawing region
    
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
    g.setColor(Color.WHITE);  //set pen color to white
    g.fillRect(0, 0, width, height);  //fill with white rectangle
    g.fillRect(imageX, imageY, 50, 50);
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
    imageX = e.getX();  //get x-coordinate of mouse (and move image to it)
    imageY = e.getY();  //get y-coordinate of mouse (and move image to it)
    System.out.println("mouse clicked:  " + imageX + ", " + imageY);
    repaint();  //indicates Display must be redrawn (Java will call paintComponent)
  }
  public void mouseReleased(MouseEvent e) { }
  public void mouseClicked(MouseEvent e) { }
  public void mouseEntered(MouseEvent e) { }
  public void mouseExited(MouseEvent e) { }
  
  //need for automation (graphical changes not prompted by the keyboard or mouse)
  public void run()
  {
    while (true)
    {
      imageY += 1;  //move image down 1 pixel
      repaint();  //indicates Display must be redrawn (Java will call paintComponent)
      try{Thread.sleep(100);}catch(Exception e){}  //give Java 100ms to run paintComponent
    }
  }
}