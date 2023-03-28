package ch.zuehlke.common;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MoveTest {


    @Test
    void getDistanceBetweenShotAndShip() {
        List<Ship> ships = new ArrayList<>();

        ships.add(new Ship(ShipType.AIRCRAFT_CARRIER, 0, 4, Orientation.HORIZONTAL));
        Board board = new Board(ships);

        ShootResult shootResult = board.executeShot(8, 8);

        assertThat(shootResult.state()).isEqualTo(ShootState.MISS);
        assertThat(shootResult.distance()).isEqualTo(4);
    }

    @Test
    void getDistanceBetweenShotAndClosestShip() {
        List<Ship> ships = new ArrayList<>();

        ships.add(new Ship(ShipType.BATTLESHIP, 6, 0, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.BATTLESHIP, 0, 6, Orientation.VERTICAL));

        Board board = new Board(ships);

        ShootResult shootResult = board.executeShot(8, 8);

        assertThat(shootResult.state()).isEqualTo(ShootState.MISS);
        assertThat(shootResult.distance()).isEqualTo(8);
    }

    @Test
    void hitShip() {
        List<Ship> ships = new ArrayList<>();

        ships.add(new Ship(ShipType.DESTROYER, 0, 0, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.CRUISER, 6, 7, Orientation.VERTICAL));

        Board board = new Board(ships);

        ShootResult shootResult = board.executeShot(1, 0);

        assertThat(shootResult.state()).isEqualTo(ShootState.HIT);
        assertThat(shootResult.distance()).isZero();
    }

    @Test
    void markShipAsSUNK() {
        List<Ship> ships = new ArrayList<>();

        ships.add(new Ship(ShipType.DESTROYER, 0, 0, Orientation.HORIZONTAL));

        Board board = new Board(ships);
        board.executeShot(0,0);
        ShootResult shootResult = board.executeShot(1, 0);

        assertThat(shootResult.state()).isEqualTo(ShootState.SUNK);
        assertThat(shootResult.distance()).isZero();
    }
}