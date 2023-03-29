package ch.zuehlke.challenge.bot.brain;

import ch.zuehlke.common.Board;
import ch.zuehlke.common.Orientation;
import ch.zuehlke.common.Ship;
import ch.zuehlke.common.ShipType;
import ch.zuehlke.common.gameplay.ShootRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Profile("bestbot")
public class BestBrain implements Brain {

    public Board createGame(String gameId) {
        List<Ship> ships = new ArrayList<>();
        think();
        ships.add(new Ship(ShipType.BATTLESHIP, 0, 0, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.CRUISER, 0, 1, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.DESTROYER, 0, 2, Orientation.HORIZONTAL));

        ships.add(new Ship(ShipType.SUBMARINE, 3, 4, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.AIRCRAFT_CARRIER, 4, 3, Orientation.VERTICAL));

        return new Board(gameId, ships);

    }

    public ShootRequest shootRequest(String gameId) {
        think();
        int x = ThreadLocalRandom.current().nextInt(0, 9 + 1);
        int y = ThreadLocalRandom.current().nextInt(0, 9 + 1);
        return new ShootRequest(gameId, "playerId", "playerToken", x, y);
    }

    private static void think() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
            // ignore
        }
    }
}
