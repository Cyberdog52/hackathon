package ch.zuehlke.tablut;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class BoardTest {

    private static Stream<Arguments> boardSetup() {
        // TODO: restliche Felder testen
        return Stream.of(
                Arguments.of(4, 0, NormalFieldState.ATTACKER),
                Arguments.of(0, 4, NormalFieldState.ATTACKER),
                Arguments.of(4, 8, NormalFieldState.ATTACKER),
                Arguments.of(8, 4, NormalFieldState.ATTACKER),
                Arguments.of(4, 2, NormalFieldState.DEFENDER),
                Arguments.of(2, 4, NormalFieldState.DEFENDER),
                Arguments.of(4, 6, NormalFieldState.DEFENDER),
                Arguments.of(6, 4, NormalFieldState.DEFENDER)
        );
    }

    @Test
    void initialBoard_sizeIs9x9() {
        var board = Board.createInitialBoard();

        assertThat(board.fields().length).isEqualTo(9);
        assertThat(board.fields()[0].length).isEqualTo(9);
    }

    @Test
    void initialBoard_firstFieldIsNormalField() {
        var board = Board.createInitialBoard();

        assertThat(board.getField(new Coordinates(0, 0))).isInstanceOf(Field.NormalField.class);
    }

    @Test
    void initialBoard_centerFieldIsCastleField() {
        var board = Board.createInitialBoard();

        assertThat(board.getField(new Coordinates(4, 4))).isInstanceOf(Field.CastleField.class);
    }

    @Test
    void initialBoard_centerFieldIsOccupied() {
        var board = Board.createInitialBoard();

        if (board.getField(new Coordinates(4, 4)) instanceof Field.CastleField castleField) {
            assertThat(castleField.state).isEqualTo(CastleFieldState.OCCUPIED);
        } else {
            fail("Not a CastleField");
        }
    }

    @ParameterizedTest
    @MethodSource("boardSetup")
    void initialBoard_correctSetup(int x, int y, NormalFieldState state) {
        Field.NormalField expected = new Field.NormalField(state);
        var board = Board.createInitialBoard();

        assertThat(board.fields()[x][y]).isEqualToComparingFieldByField(expected);
    }
}