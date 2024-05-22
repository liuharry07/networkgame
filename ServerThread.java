import java.io.*;
import java.net.*;

public class ServerThread extends Thread {
    private int playerNum;
    private BufferedReader in;
    private PrintWriter out;
    private Server server;

    public ServerThread(Socket socket, Server server, int playerNum) throws IOException {
        this.server = server;
        this.playerNum = playerNum;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        start();
    }

    public void run() {
        try {
            while(true) {
                String message = in.readLine();
                System.out.println("ServerThread " + playerNum + " received: " + message);
                String[] tokens = message.split(" ");

                if(tokens[0].equals("bet")) {
                    server.setBetValue(Integer.parseInt(tokens[1]));
                }
                if(tokens[0].equals("fold")) {
                    server.fold(playerNum);
                }
                if(tokens[0].equals("reset")) {
                    server.reset();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //send message to this player's client
    public void send(String message) {
        System.out.println("ServerThread " + playerNum + " sending: " + message);
        out.println(message);
    }
}
