import dlsu.advanos.client.ChatClient;
import dlsu.advanos.server.*;
import dlsu.advanos.client.bot.NoisyBot;
import dlsu.advanos.client.bot.SlowBot;

/**
 * Created by Darren on 11/3/2015.
 */
public class Driver {
    public static void main(String[] args) {
        ChatServer chatServer = ChatServer.getInstance();
        chatServer.setPort(723);
        chatServer.launchServer();

        ChatClient instance = ChatClient.createInstance("Darren");
        instance.setPositionRelativeTo(chatServer.getChatroom());
        instance.connect(723);

        NoisyBot noisyBot = new NoisyBot();
        noisyBot.connect(723);

        SlowBot slowBot = new SlowBot();
        slowBot.connect(723);
    }
}
