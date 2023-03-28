package ch.zuehlke.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class Board {

    private final List<Ship> ships;
    private final ShootState[][] shots = new ShootState[10][10];

    public Board(List<Ship> ships) {
        this.ships = ships;
    }

    public boolean shipsValid() {
        // ToDo: add validation logic to check if ships are valid...
        return true;
    }

    public ShootResult executeShot(int x, int y) {
        Optional<Ship> hitShip = ships.stream().filter(ship -> ship.hits(x, y)).findFirst();
        if (hitShip.isPresent()) {
            hitShip.get().hits++;
            if (hitShip.get().type.length == hitShip.get().hits) {
                markShipAsSunk(hitShip.get());
                return new ShootResult(ShootState.SUNK, 0);
            } else {
                shots[x][y] = ShootState.HIT;
                return new ShootResult(ShootState.HIT, 0);
            }
        }
        shots[x][y] = ShootState.MISS;
        int shortestDistance = 0;
        for (Ship ship : ships) {
            shortestDistance = ship.shortestDistanceToShot(x, y);
        }
        return new ShootResult(ShootState.MISS, shortestDistance);
    }

    private void markShipAsSunk(final Ship ship) {
        for (int i = 0; i < ship.type.length; i++) {
            if (ship.orientation == Orientation.HORIZONTAL) {
                shots[ship.x + i][ship.y] = ShootState.SUNK;
            } else {
                shots[ship.x][ship.y + i] = ShootState.SUNK;
            }
        }
    }
}
