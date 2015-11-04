package dlsu.advanos.client.bot;

import java.util.ArrayList;

/**
 * Created by Darren on 11/4/2015.
 */
public class NoisyBot extends Bot {

    public NoisyBot(){
        super("Noisy Lina");
        setDelayMs(1500, 5050);
    }

    @Override
    protected void populateMessages(ArrayList<String> messages) {
        messages.add("I'm a noisy bot; in a noisy world!");
        messages.add("I'm fantastic, intergalactic!");
        messages.add("I don't really know the lyrics");
        messages.add("I would rather much be playing DotA right now");
        messages.add("I'm tired I want to sleep");

    }
}
