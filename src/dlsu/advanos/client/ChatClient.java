package dlsu.advanos.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;

/**
 * Created by Darren on 11/4/2015.
 */
public class ChatClient extends Thread {
    protected int port;
    protected String name;
    private JTextField textField_input;
    private JFrame frame_client;

    protected ChatClient(String name) {
        this.name = name;

    }

    private void initialize(){
        frame_client = new JFrame("Chat client");
        frame_client.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame_client.setPreferredSize(new Dimension(480, 480));
        Container contentPane = frame_client.getContentPane();
        textField_input = new JTextField();
        textField_input.setPreferredSize(new Dimension(450, 35));
        textField_input.setBackground(frame_client.getBackground());
        contentPane.add(textField_input);
        frame_client.pack();
        frame_client.setLocationRelativeTo(null);
        frame_client.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame_client.setVisible(true);
    }

    public static ChatClient createInstance(String name) {
        ChatClient chatClient = new ChatClient(name);
        chatClient.initialize();
        return chatClient;
    }

    public void connect(int port) {
        this.port = port;
        start();
    }

    @Override
    public void run() {
        String hostName = "localhost";
        setName("Thread chat client");
        try {
            Socket echoSocket = new Socket(hostName, port);
            Writer bos = new OutputStreamWriter(echoSocket.getOutputStream());
            final BufferedWriter writer = new BufferedWriter(bos);

            frame_client.setTitle("Chat client  - " + echoSocket.getLocalPort());

            textField_input.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        try {
                            String message = textField_input.getText().trim();
                            textField_input.setText("");
                            writer.write(name + ":" + message + "\n");
                            writer.flush();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setPositionRelativeTo(JFrame chatroom) {
        frame_client.setLocationRelativeTo(chatroom);
        frame_client.setLocation(frame_client.getX() - frame_client.getWidth(), frame_client.getY());
    }
}
