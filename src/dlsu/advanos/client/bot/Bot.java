package dlsu.advanos.client.bot;

import dlsu.advanos.client.ChatClient;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Darren on 11/4/2015.
 */
public abstract class Bot extends ChatClient {

    private int delayMs;
    private ArrayList<String> messages;

    protected Bot(String name) {
        super(name);
        messages = new ArrayList<>();
        messages.add("Hello world!");
        delayMs = 3000;
        populateMessages(messages);
    }

    protected abstract void populateMessages(ArrayList<String> messages);

    @Override
    public void run() {
        String hostName = "localhost";
        setName("Thread chat client");
        try {
            Socket echoSocket = new Socket(hostName, port);
            Writer bos = new OutputStreamWriter(echoSocket.getOutputStream());
            final BufferedWriter writer = new BufferedWriter(bos);
            Random random = new Random();
            while (true) {
                try {
                    int idx = random.nextInt(messages.size());
                    String message = messages.get(idx);
                    writer.write(name + ": " + message + "\n");
                    writer.flush();
                    Thread.sleep(delayMs);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getDelayMs() {
        return delayMs;
    }

    public void setDelayMs(int delayMs, int variance) {
        this.delayMs = delayMs + new Random().nextInt(variance);
    }
}
