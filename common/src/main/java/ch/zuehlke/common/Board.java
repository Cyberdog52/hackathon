package ch.zuehlke.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class Board {

    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    private String playerId;
    private String playerName;
    private List<Ship> ships;
    private final ShootState[][] shots = new ShootState[WIDTH][HEIGHT];

    public Board(String playerId, String playerName, List<Ship> ships) {
        this.playerId = playerId;
        this.ships = ships;
        this.playerName = playerName;
    }

    public boolean shipsValid() {

        if (ships.size() != ShipType.values().length || !hasUniqueShips()) {
            return false;
        }

        int totalPositions = ships.stream().mapToInt(ship -> ship.type.length).sum();
        int uniquePositions = ships.stream().flatMap(ship -> ship.positions.stream()).collect(Collectors.toSet()).size();
        if (totalPositions != uniquePositions) {
            return false;
        }

        for (Ship ship : ships) {
            if (isOutOfBounds(ship))
                return false;
        }
        return true;
    }

    private boolean hasUniqueShips() {
        return ships.stream().map(ship -> ship.type).collect(Collectors.toSet()).size() == ShipType.values().length;
    }

    private boolean isOutOfBounds(Ship ship) {
        if (ship.x < 0 || ship.y < 0) {
            return true;
        }
        return ship.orientation.equals(Orientation.HORIZONTAL) && ship.x + ship.type.length > WIDTH ||
                ship.orientation.equals(Orientation.VERTICAL) && ship.y + ship.type.length > HEIGHT;
    }

    public boolean allShipsDestroyed() {
        return ships.stream()
                .map(Ship::isDestroyed)
                .allMatch(Predicate.isEqual(Boolean.TRUE));
    }

    public ShootResult executeShot(int x, int y) {
        Optional<Ship> hitShip = ships.stream().filter(ship -> ship.hits(x, y)).findFirst();
        if (hitShip.isPresent()) {
            if (hitShip.get().isDestroyed()) {
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
