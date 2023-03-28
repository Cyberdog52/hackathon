package ch.zuehlke.common;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardTest {

    @Test
    void createValidBoard() {

        List<Ship> ships = new ArrayList<>();

        ships.add(new Ship(ShipType.BATTLESHIP, 6, 0, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.AIRCRAFT_CARRIER, 0, 6, Orientation.VERTICAL));

        Board board = new Board(ships);

        assertThat(board.shipsValid()).isTrue();
    }

    @Test
    void createBoardWithCrossingShipsFails() {

        List<Ship> ships = new ArrayList<>();

        ships.add(new Ship(ShipType.SUBMARINE, 3, 4, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.AIRCRAFT_CARRIER, 4, 3, Orientation.VERTICAL));

        Board board = new Board(ships);

        assertThat(board.shipsValid()).isFalse();
    }

    @Test
    void createBoardFailsWhenStartingPositionIsNegative() {

        List<Ship> ships = new ArrayList<>();

        ships.add(new Ship(ShipType.SUBMARINE, -1, -1, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.AIRCRAFT_CARRIER, 4, 3, Orientation.VERTICAL));

        Board board = new Board(ships);

        assertThat(board.shipsValid()).isFalse();
    }

    @Test
    void createBoardFailsWhenStartingPositionIsTooLarge() {

        List<Ship> ships = new ArrayList<>();

        ships.add(new Ship(ShipType.SUBMARINE, 100, 3, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.AIRCRAFT_CARRIER, 4, 3, Orientation.VERTICAL));

        Board board = new Board(ships);

        assertThat(board.shipsValid()).isFalse();
    }

    @Test
    void createBoardFailsWhenEndOfShipIsOutOfBounds() {

        List<Ship> ships = new ArrayList<>();

        ships.add(new Ship(ShipType.SUBMARINE, 9, 3, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.AIRCRAFT_CARRIER, 4, 3, Orientation.VERTICAL));

        Board board = new Board(ships);

        assertThat(board.shipsValid()).isFalse();
    }

}
