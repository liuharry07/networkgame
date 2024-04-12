import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Client extends Thread implements ActionListener {
    private BufferedReader in; //for receiving messages from the server
    private PrintWriter out; //for sending messages to the server
    private JFrame frame;
    private JButton button;
    private JTextArea hand;
    private Card[] cards;

    public Client(String ipAddress) {
        try {
            frame = new JFrame("poker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            cards = null;

            frame.getContentPane().setLayout(new GridLayout(1, 2));

            Font font = new Font(null, Font.PLAIN, 72);

            button = new JButton("bet");
            button.setFont(font);
            button.setEnabled(false);
            button.setActionCommand("bet");
            button.addActionListener(this);
            frame.getContentPane().add(button);

            if(cards != null) {
                hand = new JTextArea(cards[0] + " " + cards[1]);
                frame.getContentPane().add(hand);
            }

            frame.pack();

            //connect to server running on port 9000 of given ipAddress
            Socket socket = new Socket(ipAddress, 9000);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            frame.setVisible(true);

            start();
            //the run method has started
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
                    button.setEnabled(true);
                }
                if(tokens[0].equals("go")) {
                    button.setEnabled(true);
                    for(int i = 1; i < 5; i += 2) {
                        cards[i - 1] = new Card(Integer.parseInt(tokens[i]), Integer.parseInt(tokens[i + 1]));
                    }
                }
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

    //called when button is pressed
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        send("bet");
    }
}
