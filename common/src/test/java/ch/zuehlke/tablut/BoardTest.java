package ch.zuehlke.tablut;

import ch.zuehlke.common.Board;
import ch.zuehlke.common.Coordinates;
import ch.zuehlke.common.Field;
import ch.zuehlke.common.FieldState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardTest {

    private static Stream<Arguments> boardSetup() {
        // TODO: restliche Felder testen
        return Stream.of(
                Arguments.of(4, 0, FieldState.ATTACKER),
                Arguments.of(0, 4, FieldState.ATTACKER),
                Arguments.of(4, 8, FieldState.ATTACKER),
                Arguments.of(8, 4, FieldState.ATTACKER),
                Arguments.of(4, 2, FieldState.DEFENDER),
                Arguments.of(2, 4, FieldState.DEFENDER),
                Arguments.of(4, 6, FieldState.DEFENDER),
                Arguments.of(6, 4, FieldState.DEFENDER)
        );
    }

    @Test
    void initialBoard_sizeIs9x9() {
        var board = Board.createInitialBoard();

        assertThat(board.fields().length).isEqualTo(9);
        assertThat(board.fields()[0].length).isEqualTo(9);
    }

    @Test
    void initialBoard_firstFieldIsNotCastle() {
        var board = Board.createInitialBoard();

        assertThat(board.getFieldForCoordinate(new Coordinates(0, 0)).isCastle()).isFalse();
    }

    @Test
    void initialBoard_centerFieldIsCastleField() {
        var board = Board.createInitialBoard();

        assertThat(board.getFieldForCoordinate(new Coordinates(4, 4)).isCastle()).isTrue();
    }

    @Test
    void initialBoard_centerFieldIsOccupiedWithKing() {
        var board = Board.createInitialBoard();

        assertThat(board.getFieldForCoordinate(new Coordinates(4, 4)).state()).isEqualTo(FieldState.KING);
    }

    @ParameterizedTest
    @MethodSource("boardSetup")
    void initialBoard_correctSetup(int x, int y, FieldState state) {
        Field expected = new Field(new Coordinates(x, y), state);
        var board = Board.createInitialBoard();

        assertThat(board.fields()[x][y]).isEqualToComparingFieldByField(expected);
    }
}