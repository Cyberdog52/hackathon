package ch.zuehlke.common;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Board {

    List<Ship> ships = new ArrayList<>();
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
}
