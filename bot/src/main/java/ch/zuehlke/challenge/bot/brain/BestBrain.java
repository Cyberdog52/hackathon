package ch.zuehlke.challenge.bot.brain;

import ch.zuehlke.common.Orientation;
import ch.zuehlke.common.Player;
import ch.zuehlke.common.Ship;
import ch.zuehlke.common.ShipType;
import ch.zuehlke.common.gameplay.PlaceShipsRequest;
import ch.zuehlke.common.gameplay.ShootRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Profile("bestbot")
public class BestBrain implements Brain {

    @Override
    public PlaceShipsRequest createGame(String gameId, Player player) {
        List<Ship> ships = new ArrayList<>();
        think();
        ships.add(new Ship(ShipType.BATTLESHIP, 0, 0, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.CRUISER, 0, 1, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.DESTROYER, 0, 2, Orientation.HORIZONTAL));

        ships.add(new Ship(ShipType.SUBMARINE, 0, 3, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.AIRCRAFT_CARRIER, 0, 4, Orientation.HORIZONTAL));

        return new PlaceShipsRequest(gameId, player,  ships);
    }

    public ShootRequest shootRequest(String gameId, Player player) {
        think();
        int x = ThreadLocalRandom.current().nextInt(0, 9 + 1);
        int y = ThreadLocalRandom.current().nextInt(0, 9 + 1);
        return new ShootRequest(gameId, player.getId(), player.getToken(), x, y);

    }

    private static void think() {
        try {
            Thread.sleep(THINK_TIME);
        } catch (InterruptedException ignored) {
            // ignore
        }
    }
}
