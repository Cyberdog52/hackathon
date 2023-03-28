package ch.zuehlke.challenge.bot.brain;

import ch.zuehlke.common.Coordinate;
import ch.zuehlke.common.GameAction;
import ch.zuehlke.common.shared.action.playing.AttackTurnAction;
import ch.zuehlke.common.shared.event.setup.GameConfigEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Profile("simplebot")
public class SimpleBrain implements Brain {

    public GameAction decide(Set<GameAction> possibleActions) {
        think();
        return GameAction.ROCK;
    }

    @Override
    public AttackTurnAction attack() {
        return null;
    }

    @Override
    public List<Coordinate> chooseBoatCoordinates(GameConfigEvent event) {
        return null;
    }

    @Override
    public Coordinate chooseNewBoatCoordinate(List<Coordinate> coordinates, Coordinate failedCoordinate) {
        return null;
    }

    private static void think() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
            // ignore
        }
    }
}