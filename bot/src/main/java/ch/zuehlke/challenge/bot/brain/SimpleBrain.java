package ch.zuehlke.challenge.bot.brain;

import ch.zuehlke.common.GameAction;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Profile("simplebot")
public class SimpleBrain implements Brain {


    @Override
    public GameAction decide(final Set<GameAction> possibleActions) {
        //If simple bot can finish, it will finish
        if (possibleActions.contains(GameAction.FINISH)) {
            return GameAction.FINISH;
        }
        

        return null;
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