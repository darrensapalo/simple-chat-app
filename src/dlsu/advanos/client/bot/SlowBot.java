package dlsu.advanos.client.bot;

import java.util.ArrayList;

/**
 * Created by Darren on 11/4/2015.
 */
public class SlowBot extends Bot {

    public SlowBot(){
        super("Slow Ogre");
        setDelayMs(3000, 2000);
    }

    @Override
    protected void populateMessages(ArrayList<String> messages) {
        messages.add("I'm a slow bot; I just want to sleep...");
        messages.add("I like to play Witch Doctor");
        messages.add("Zzzzz....");

    }
}
