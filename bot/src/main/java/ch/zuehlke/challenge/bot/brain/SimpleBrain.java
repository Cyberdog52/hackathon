package ch.zuehlke.challenge.bot.brain;

import ch.zuehlke.common.GameAction;
import ch.zuehlke.tablut.Board;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
@Profile({"simplebot","simplebot2"})
public class SimpleBrain implements Brain {

    public GameAction decide(boolean attacker, Board board, Set<GameAction> possibleActions) {
        think();
        List<GameAction> list = new ArrayList<>(possibleActions);
        int randIdx = new Random().nextInt(list.size());
        return list.get(randIdx);
    }

    private static void think() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {
            // ignore
        }
    }
}