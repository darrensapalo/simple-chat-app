package dlsu.advanos.server;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Darren on 11/3/2015.
 */
public class ChatServer {
    private static ChatServer instance;

    private int port;
    private Thread thread;

    private final JTextArea textArea_content;
    private JFrame frame_main;

    public static ChatServer getInstance() {
        if (instance == null)
            instance = new ChatServer();
        return instance;
    }

    private ChatServer() {
        port = -1;
        connections = new ArrayList<>();
        frame_main = new JFrame("Chat server");
        frame_main.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame_main.setPreferredSize(new Dimension(480, 480));
        Container contentPane = frame_main.getContentPane();

        textArea_content = new JTextArea(16, 58);
        Dimension preferredSize = new Dimension(450, 400);
        textArea_content.setSize(preferredSize);
        textArea_content.setEditable(false);
        textArea_content.setLineWrap(true);
        textArea_content.setWrapStyleWord(true);


        DefaultCaret caret = (DefaultCaret) textArea_content.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JScrollPane jScrollPane = new JScrollPane(textArea_content);
        jScrollPane.setPreferredSize(preferredSize);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        contentPane.add(jScrollPane);
        frame_main.pack();
        frame_main.setLocationRelativeTo(null);
        frame_main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame_main.setVisible(true);
    }

    public void setPort(int port) {
        this.port = port;
        frame_main.setTitle("Chat server - " + port);
    }

    public synchronized void writeOnChat(String name, String mes) {
        Calendar instance = GregorianCalendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String timestamp = sdf.format(instance.getTime());
        String message = timestamp + "\n" + name + "\n" + mes + "\n\n";
        textArea_content.append(message);
    }

    public void launchServer() {
        thread = new Thread(serverRunnable);
        thread.start();
    }

    private ServerSocket serverSocket;
    private ArrayList<ConnectionHandler> connections;

    private Runnable serverRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket();

                if (port == -1)
                    port = 8080;

                serverSocket.bind(new InetSocketAddress(port));


                while (true) {
                    try {
                        Socket newConnectionSocket = serverSocket.accept();
                        writeOnChat(newConnectionSocket.getInetAddress().getHostName() + ":" + newConnectionSocket.getPort(), "has connected!");
                        ConnectionHandler handler = new ConnectionHandler(ChatServer.this, newConnectionSocket);
                        connections.add(handler);
                        handler.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public JFrame getChatroom() {
        return frame_main;
    }
}
