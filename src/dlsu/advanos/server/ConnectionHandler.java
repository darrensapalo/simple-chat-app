package dlsu.advanos.server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Darren on 11/3/2015.
 */
public class ConnectionHandler extends Thread{
    private ChatServer server;
    private Socket socket;

    public ConnectionHandler(ChatServer server, Socket socket){
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run() {
        setName("Connection handler");
            try {
                InputStream inputStream = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(isr);

                do {
                    String input = br.readLine();
                    server.writeOnChat(getUsername(), input);
                }while (true);

            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    private String getUsername() {
        return "Message from port " + socket.getPort() + "";
    }
}
