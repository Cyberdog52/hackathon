package ch.zuehlke.common;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MoveTest {

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