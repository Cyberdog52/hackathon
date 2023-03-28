package ch.zuehlke.common;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MoveTest {

    List<Ship> ships = new ArrayList<>();

    @Test
    void getDistanceBetweenShotAndShip() {
        ships.add(new Ship(ShipType.AIRCRAFT_CARRIER, 0, 4, Orientation.HORIZONTAL));
        Board board = new Board(ships);

        ShootResult shootResult = board.executeShot(8, 8);

        assertThat(shootResult.state()).isEqualTo(ShootState.MISS);
        assertThat(shootResult.distance()).isEqualTo(4);
    }

    @Test
    void getDistanceBetweenShotAndClosestShip() {
        ships.add(new Ship(ShipType.BATTLESHIP, 6, 0, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.BATTLESHIP, 0, 6, Orientation.VERTICAL));

        Board board = new Board(ships);

        ShootResult shootResult = board.executeShot(8, 8);

        assertThat(shootResult.state()).isEqualTo(ShootState.MISS);
        assertThat(shootResult.distance()).isEqualTo(8);
    }

    @Test
    void hitShip() {
        ships.add(new Ship(ShipType.DESTROYER, 0, 0, Orientation.HORIZONTAL));
        ships.add(new Ship(ShipType.CRUISER, 6, 7, Orientation.VERTICAL));

        Board board = new Board(ships);

        ShootResult shootResult = board.executeShot(1, 0);

        assertThat(shootResult.state()).isEqualTo(ShootState.HIT);
        assertThat(shootResult.distance()).isZero();
    }

    @Test
    void markShipAsSUNK() {
        ships.add(new Ship(ShipType.DESTROYER, 0, 0, Orientation.HORIZONTAL));

        Board board = new Board(ships);
        board.executeShot(0,0);
        ShootResult shootResult = board.executeShot(1, 0);

        assertThat(shootResult.state()).isEqualTo(ShootState.SUNK);
        assertThat(shootResult.distance()).isZero();
    }

/*    @Test
    void getWinningPlayer_sameMoves_returnsNull() {
        Move move1 = new Move(new PlayerId(), new RequestId(), GameAction.ROCK);
        Move move2 = new Move(new PlayerId(), new RequestId(), GameAction.ROCK);

        Optional<PlayerId> winningPlayer = Move.getWinningPlayer(move1, move2);

        assertThat(winningPlayer).isEmpty();
    }

    @Test
    void getWinningPlayer_rockBeatsScissors_returnsPlayer1() {
        Move move1 = new Move(new PlayerId(), new RequestId(), GameAction.ROCK);
        Move move2 = new Move(new PlayerId(), new RequestId(), GameAction.SCISSORS);

        Optional<PlayerId> winningPlayer = Move.getWinningPlayer(move1, move2);

        assertThat(winningPlayer).isPresent();
        assertThat(winningPlayer.get()).isEqualTo(move1.playerId());
    }*/
}