package ch.zuehlke.challenge.bot.brain;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.Set;

@Component
@Profile("bestbot")
public class BestBrain implements Brain {

    //public GameAction decide(Set<GameAction> possibleActions) {
    //    thinkForALongTime();
    //    return possibleActions.stream()
    //            .skip(new Random().nextInt(possibleActions.size()))
    //            .findFirst()
    //            .orElseThrow();
    //}

    private static void thinkForALongTime() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
            // ignore
        }
    }
}
