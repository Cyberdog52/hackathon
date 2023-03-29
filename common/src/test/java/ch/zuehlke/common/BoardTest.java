package ch.zuehlke.common;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    private String id = "1234567890";

    @Test
    void createValidBoard() {

        List<Ship> ships = new ArrayList<>();
        ships.add(new Ship(ShipType.SUBMARINE, 0, 0, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.CRUISER, 0, 1, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.DESTROYER, 0, 2, Orientation.HORIZONTAL));

        ships.add(new Ship(ShipType.BATTLESHIP, 6, 0, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.AIRCRAFT_CARRIER, 4, 0, Orientation.VERTICAL));

        Board board = new Board(id, ships);

        assertThat(board.shipsValid()).isTrue();
    }

    @Test
    void createBoardWithCrossingShipsFails() {

        List<Ship> ships = new ArrayList<>();

        ships.add(new Ship(ShipType.BATTLESHIP, 0, 0, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.CRUISER, 0, 1, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.DESTROYER, 0, 2, Orientation.HORIZONTAL));

        ships.add(new Ship(ShipType.SUBMARINE, 3, 4, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.AIRCRAFT_CARRIER, 4, 3, Orientation.VERTICAL));

        Board board = new Board(id, ships);

        assertThat(board.shipsValid()).isFalse();
        assertThat(board.playerId).isEqualTo(id);
    }

    @Test
    void createBoardFailsWhenStartingPositionIsNegative() {

        List<Ship> ships = new ArrayList<>();
        ships.add(new Ship(ShipType.BATTLESHIP, 0, 0, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.CRUISER, 0, 1, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.DESTROYER, 0, 2, Orientation.HORIZONTAL));

        ships.add(new Ship(ShipType.SUBMARINE, -1, -1, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.AIRCRAFT_CARRIER, 4, 3, Orientation.VERTICAL));

        Board board = new Board(id, ships);

        assertThat(board.shipsValid()).isFalse();
    }

    @Test
    void createBoardFailsWhenStartingPositionIsTooLarge() {

        List<Ship> ships = new ArrayList<>();
        ships.add(new Ship(ShipType.BATTLESHIP, 0, 0, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.CRUISER, 0, 1, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.DESTROYER, 0, 2, Orientation.HORIZONTAL));

        ships.add(new Ship(ShipType.SUBMARINE, 100, 3, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.AIRCRAFT_CARRIER, 4, 3, Orientation.VERTICAL));

        Board board = new Board(id, ships);

        assertThat(board.shipsValid()).isFalse();
    }

    @Test
    void createBoardFailsWhenEndOfShipIsOutOfBounds() {

        List<Ship> ships = new ArrayList<>();
        ships.add(new Ship(ShipType.BATTLESHIP, 0, 0, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.CRUISER, 0, 1, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.DESTROYER, 0, 2, Orientation.HORIZONTAL));

        ships.add(new Ship(ShipType.SUBMARINE, 9, 3, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.AIRCRAFT_CARRIER, 4, 3, Orientation.VERTICAL));

        Board board = new Board(id, ships);

        assertThat(board.shipsValid()).isFalse();
    }

}
