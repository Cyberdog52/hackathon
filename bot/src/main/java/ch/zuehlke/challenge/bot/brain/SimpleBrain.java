package ch.zuehlke.challenge.bot.brain;

import ch.zuehlke.common.Player;
import ch.zuehlke.common.gameplay.PlaceShipsRequest;
import ch.zuehlke.common.gameplay.ShootRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Profile("simplebot")
public class SimpleBrain implements Brain {

    //public GameAction decide(Set<GameAction> possibleActions) {
    //    think();
    //    throw new IllegalArgumentException("Not implemented yet...");
    //    // return new GameAction(12,12);
    //}

    private static void think() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
            // ignore
        }
    }

    @Override
    public PlaceShipsRequest createGame(String gameId, Player player) {
        return null;
    }

    @Override
    public ShootRequest shootRequest(String gameId) {
        return null;
    }
}