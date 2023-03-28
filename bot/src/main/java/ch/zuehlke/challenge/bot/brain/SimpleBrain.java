package ch.zuehlke.challenge.bot.brain;

import ch.zuehlke.common.GameAction;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Profile("simplebot")
public class SimpleBrain implements Brain {

    public GameAction decide(Set<GameAction> possibleActions) {
        think();
        return GameAction.ROCK;
    }

    private static void think() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
            // ignore
        }
    }

    @Override
    public String name() {
        return "SimpleBrain";
    }

    @Override
    public String icon() {
        return "SimpleBrainIcon";
    }
}