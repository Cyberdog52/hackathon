package ch.zuehlke.challenge.bot.brain;

import ch.zuehlke.common.GameAction;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Profile("bestbot")
public class BestBrain implements Brain {

    @Override
    public GameAction decide(Set<GameAction> possibleActions) {
        return null;
    }

    @Override
    public String name() {
        return "BestBrain";
    }

    @Override
    public String icon() {
        return "BestBrainIcon";
    }
}
