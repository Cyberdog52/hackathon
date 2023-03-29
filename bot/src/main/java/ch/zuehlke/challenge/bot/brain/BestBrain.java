package ch.zuehlke.challenge.bot.brain;

import ch.zuehlke.common.GameAction;
import ch.zuehlke.common.Board;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
@Profile({"bestbot", "bestbot2","andre","andres"})
public class BestBrain implements Brain {

    public GameAction decide(boolean attacker, Board board, Set<GameAction> possibleActions) {
        thinkForALongTime();

        if (attacker) {
            return attack(board, possibleActions);
        } else {
            return defend(board, possibleActions);
        }
    }

    private GameAction attack(Board board, Set<GameAction> possibleActions) {
        return possibleActions.stream()
                .filter(action -> action.movesNextToTheKing(board))
                .findFirst().orElse(getRandomAction(possibleActions));
    }

    private GameAction defend(Board board, Set<GameAction> possibleActions) {
        return possibleActions.stream()
                .filter(action -> action.isAWinningDefendingMove(board))
                .findFirst().orElse(getRandomAction(possibleActions));
    }

    private GameAction getRandomAction(Set<GameAction> possibleActions) {
        List<GameAction> list = new ArrayList<>(possibleActions);
        int randIdx = new Random().nextInt(list.size());
        return list.get(randIdx);
    }

    private static void thinkForALongTime() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
            // ignore
        }
    }
}
