package ch.zuehlke.common;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class Board {

    List<Ship> ships;
    ShotResult[][] shots = new ShotResult[10][10];

    public Board(List<Ship> ships) {
        this.ships = ships;
//        ships.add(new Ship(ShipType.AIRCRAFT_CARRIER, 0, 0, Orientation.HORIZONTAL));
//        ships.add(new Ship(ShipType.BATTLESHIP, 0, 1, Orientation.HORIZONTAL));
//        ships.add(new Ship(ShipType.SUBMARINE, 0, 2, Orientation.HORIZONTAL));
//        ships.add(new Ship(ShipType.CRUISER, 0, 3, Orientation.HORIZONTAL));
//        ships.add(new Ship(ShipType.DESTROYER, 0, 4, Orientation.HORIZONTAL));
    }

    public boolean shipsValid() {
        // ToDo: add validation logic to check if ships are valid...
        return true;
    }

    public void executeShot(int x, int y) {
        Optional<Ship> hitShip = ships.stream().filter(ship -> ship.hits(x, y)).findFirst();
        if (hitShip.isPresent()) {
            hitShip.get().hits++;
            if (hitShip.get().type.length == hitShip.get().hits) {
                markShipAsSunk(hitShip.get());
            } else {
                shots[x][y] = ShotResult.HIT;
            }
        } else {
            shots[x][y] = ShotResult.MISS;
        }
    }

    private void markShipAsSunk(Ship ship) {
        for (int i = 0; i < ship.type.length; i++) {
            if (ship.orientation == Orientation.HORIZONTAL) {
                shots[ship.x + i][ship.y] = ShotResult.SUNK;
            } else {
                shots[ship.x][ship.y + i] = ShotResult.SUNK;
            }
        }
    }
}
