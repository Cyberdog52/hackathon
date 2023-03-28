package ch.zuehlke.fullstack.hackathon.model;

import ch.zuehlke.common.GameAction;
import ch.zuehlke.tablut.Coordinates;
import ch.zuehlke.tablut.Field;
import ch.zuehlke.tablut.FieldState;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class InternalGameStateTest {

    @Test
    void initialGame_FirstMove_PossibleMoveForBlack() {
        var gameState = new InternalGameState();

        assertThat(gameState.getPossibleActions()).contains(new GameAction(new Coordinates(0, 3), new Coordinates(0, 0)));
    }

    @Test
    void initialGame_FirstMove_NoMoveWithDefenders() {
        var gameState = new InternalGameState();

        Set<Coordinates> coordinatesDefender = Set.of(
                new Coordinates(4, 2),
                new Coordinates(4, 3),
                new Coordinates(2, 4),
                new Coordinates(3, 4),
                new Coordinates(4, 4),
                new Coordinates(5, 4),
                new Coordinates(6, 4),
                new Coordinates(4, 5),
                new Coordinates(4, 6)
        );

        assertThat(gameState.getPossibleActions().stream().map(GameAction::from).collect(Collectors.toSet()))
                .doesNotContainAnyElementsOf(coordinatesDefender);
    }

    @Test
    void getPossibleMoves_ForLeftAttackerAtStart() {
        var gameState = new InternalGameState();
        Set<Coordinates> expectedGameActions = Set.of(
                new Coordinates(0, 0),
                new Coordinates(0, 1),
                new Coordinates(0, 2),
                new Coordinates(1, 3),
                new Coordinates(2, 3),
                new Coordinates(3, 3)
        );


        assertThat(gameState.getPossibleActions().stream()
                .filter(a -> a.from().equals(new Coordinates(0, 3)))
                .map(GameAction::to)
                .collect(Collectors.toSet())).isEqualTo(expectedGameActions);
    }

    @Test
    void getPossibleMoves_ForTopAttackerAtStart() {
        var gameState = new InternalGameState();
        Set<Coordinates> expectedGameActions = Set.of();

        assertThat(gameState.getPossibleActions().stream()
                .filter(a -> a.from().equals(new Coordinates(4, 0)))
                .map(GameAction::to)
                .collect(Collectors.toSet())).isEqualTo(expectedGameActions);
    }

    @Test
    void getPossibleMoves_ForRightAttackerAtStart() {
        var gameState = new InternalGameState();
        Set<Coordinates> expectedGameActions = Set.of(
                new Coordinates(5, 5),
                new Coordinates(6, 5),
                new Coordinates(7, 5),
                new Coordinates(8, 6),
                new Coordinates(8, 7),
                new Coordinates(8, 8)
        );

        assertThat(gameState.getPossibleActions().stream()
                .filter(a -> a.from().equals(new Coordinates(8, 5)))
                .map(GameAction::to)
                .collect(Collectors.toSet())).isEqualTo(expectedGameActions);
    }

    @Test
    void getPossibleMoves_ForBottomAttackerAtStart() {
        var gameState = new InternalGameState();
        Set<Coordinates> expectedGameActions = Set.of(
                new Coordinates(0, 7),
                new Coordinates(1, 7),
                new Coordinates(2, 7),
                new Coordinates(3, 7),
                new Coordinates(5, 7),
                new Coordinates(6, 7),
                new Coordinates(7, 7),
                new Coordinates(8, 7)
        );

        assertThat(gameState.getPossibleActions().stream()
                .filter(a -> a.from().equals(new Coordinates(4, 7)))
                .map(GameAction::to)
                .collect(Collectors.toSet())).isEqualTo(expectedGameActions);
    }

    @Test
    void getPossibleActions_kingCanMoveOutOfTheCastle() {
        var gameState = new InternalGameState();

        // remove two pieces to the left of the king
        gameState.board().updateField(new Field(new Coordinates(3, 4), FieldState.EMPTY));
        gameState.board().updateField(new Field(new Coordinates(2, 4), FieldState.EMPTY));

        // make a move with attacker so that defender is playing next
        gameState.playAction(new GameAction(0, 3, 0, 0));

        var expectedDestinations = Set.of(
                new Coordinates(3, 4),
                new Coordinates(2, 4)
        );

        assertThat(gameState.getPossibleActions().stream()
                .filter(a -> a.from().equals(new Coordinates(4, 4)))
                .map(GameAction::to)
                .collect(Collectors.toSet())).isEqualTo(expectedDestinations);
    }

    @Test
    void getPossibleActions_kingCanMoveIntoTheCastle() {
        var gameState = new InternalGameState();

        // Move king one field to the left (replacing the defender there)
        gameState.board().updateField(new Field(new Coordinates(4, 4), FieldState.EMPTY));
        gameState.board().updateField(new Field(new Coordinates(3, 4), FieldState.KING));

        // make a move with attacker so that defender is playing next
        gameState.playAction(new GameAction(0, 3, 0, 0));

        assertThat(gameState.getPossibleActions().stream()
                .filter(a -> a.from().equals(new Coordinates(3, 4)))
                .map(GameAction::to)
                .collect(Collectors.toSet())).contains(new Coordinates(4, 4));
    }

    @Test
    void getPossibleActions_kingCanMoveThroughTheCastle() {
        var gameState = new InternalGameState();

        // Move king one field to the left (replacing the defender there)
        gameState.board().updateField(new Field(new Coordinates(4, 4), FieldState.EMPTY));
        gameState.board().updateField(new Field(new Coordinates(3, 4), FieldState.KING));

        // also clear the field to the right of the castle
        gameState.board().updateField(new Field(new Coordinates(5, 4), FieldState.EMPTY));

        // make a move with attacker so that defender is playing next
        gameState.playAction(new GameAction(0, 3, 0, 0));

        assertThat(gameState.getPossibleActions().stream()
                .filter(a -> a.from().equals(new Coordinates(3, 4)))
                .map(GameAction::to)
                .collect(Collectors.toSet())).contains(new Coordinates(5, 4));
    }

    @Test
    void getPossibleActions_attackersCanNotMoveIntoTheCastle() {
        var gameState = new InternalGameState();

        // Remove the middle row of the defenders
        gameState.board().updateField(new Field(new Coordinates(2, 4), FieldState.EMPTY));
        gameState.board().updateField(new Field(new Coordinates(3, 4), FieldState.EMPTY));
        gameState.board().updateField(new Field(new Coordinates(4, 4), FieldState.EMPTY));
        gameState.board().updateField(new Field(new Coordinates(5, 4), FieldState.EMPTY));
        gameState.board().updateField(new Field(new Coordinates(6, 4), FieldState.EMPTY));

        assertThat(gameState.getPossibleActions().stream()
                .filter(a -> a.from().equals(new Coordinates(1, 4)))
                .map(GameAction::to)
                .collect(Collectors.toSet())).doesNotContain(new Coordinates(4, 4));
    }

    @Test
    void getPossibleActions_attackersCanNotMoveThroughTheCastle() {
        var gameState = new InternalGameState();

        // Remove the middle row of the defenders
        gameState.board().updateField(new Field(new Coordinates(2, 4), FieldState.EMPTY));
        gameState.board().updateField(new Field(new Coordinates(3, 4), FieldState.EMPTY));
        gameState.board().updateField(new Field(new Coordinates(4, 4), FieldState.EMPTY));
        gameState.board().updateField(new Field(new Coordinates(5, 4), FieldState.EMPTY));
        gameState.board().updateField(new Field(new Coordinates(6, 4), FieldState.EMPTY));

        assertThat(gameState.getPossibleActions().stream()
                .filter(a -> a.from().equals(new Coordinates(1, 4)))
                .map(GameAction::to)
                .collect(Collectors.toSet())).doesNotContain(new Coordinates(5, 4));
    }

    @Test
    void getPossibleActions_defendersCanNotMoveIntoTheCastle() {
        var gameState = new InternalGameState();

        // Remove the middle row of the defenders
        gameState.board().updateField(new Field(new Coordinates(2, 4), FieldState.EMPTY));
        gameState.board().updateField(new Field(new Coordinates(3, 4), FieldState.EMPTY));
        gameState.board().updateField(new Field(new Coordinates(4, 4), FieldState.EMPTY));
        gameState.board().updateField(new Field(new Coordinates(5, 4), FieldState.EMPTY));
        gameState.board().updateField(new Field(new Coordinates(6, 4), FieldState.EMPTY));

        // make a move with attacker so that defender is playing next
        gameState.playAction(new GameAction(0, 3, 0, 0));

        assertThat(gameState.getPossibleActions().stream()
                .filter(a -> a.from().equals(new Coordinates(4, 2)))
                .map(GameAction::to)
                .collect(Collectors.toSet())).doesNotContain(new Coordinates(4, 4));
    }

    @Test
    void isDraw() {
        var gameState = new InternalGameState();

        assertThat(gameState.isDraw()).isFalse();

        for (int i = 0; i < 210; i++) {
            gameState.playAction(new GameAction(0, 0, 0, 0));
        }

        assertThat(gameState.isDraw()).isTrue();
    }

    @Test
    void hasAttackerWon() {
        var gameState = new InternalGameState();

        assertThat(gameState.hasAttackerWon()).isFalse();

        gameState.board().updateField(new Field(new Coordinates(4, 4), FieldState.EMPTY));

        assertThat(gameState.hasAttackerWon()).isTrue();
    }

    @Test
    void hasDefenderWon() {
        var gameState = new InternalGameState();

        assertThat(gameState.hasDefenderWon()).isFalse();

        gameState.board().updateField(new Field(new Coordinates(4, 4), FieldState.EMPTY));
        gameState.board().updateField(new Field(new Coordinates(0, 0), FieldState.KING));

        assertThat(gameState.hasDefenderWon()).isTrue();
    }

    @Test
    void attackerCaptures_toTheLeft() {
        var gameState = new InternalGameState();

        gameState.board().updateField(new Field(new Coordinates(0, 0), FieldState.ATTACKER));
        gameState.board().updateField(new Field(new Coordinates(1, 0), FieldState.DEFENDER));

        assertThat(gameState.board().getFieldForCoordinate(new Coordinates(1, 0)).state())
                .isEqualTo(FieldState.DEFENDER);

        gameState.playAction(new GameAction(3, 0, 2, 0));

        assertThat(gameState.board().getFieldForCoordinate(new Coordinates(1, 0)).state())
                .isEqualTo(FieldState.EMPTY);
    }

    @Test
    void defenderCaptures_toTheLeft() {
        var gameState = new InternalGameState();

        gameState.board().updateField(new Field(new Coordinates(0, 0), FieldState.DEFENDER));
        gameState.board().updateField(new Field(new Coordinates(1, 0), FieldState.ATTACKER));

        assertThat(gameState.board().getFieldForCoordinate(new Coordinates(1, 0)).state())
                .isEqualTo(FieldState.ATTACKER);

        gameState.playAction(new GameAction(2, 4, 2, 0));

        assertThat(gameState.board().getFieldForCoordinate(new Coordinates(1, 0)).state())
                .isEqualTo(FieldState.EMPTY);
    }

    @Test
    void defenderCapturesFeaturingKing_toTheLeft() {
        var gameState = new InternalGameState();

        gameState.board().updateField(new Field(new Coordinates(0, 0), FieldState.KING));
        gameState.board().updateField(new Field(new Coordinates(1, 0), FieldState.ATTACKER));

        assertThat(gameState.board().getFieldForCoordinate(new Coordinates(1, 0)).state())
                .isEqualTo(FieldState.ATTACKER);

        gameState.playAction(new GameAction(2, 4, 2, 0));

        assertThat(gameState.board().getFieldForCoordinate(new Coordinates(1, 0)).state())
                .isEqualTo(FieldState.EMPTY);
    }

    @Test
    void defenderCapturesWithKing_toTheLeft() {
        var gameState = new InternalGameState();

        gameState.board().updateField(new Field(new Coordinates(0, 0), FieldState.DEFENDER));
        gameState.board().updateField(new Field(new Coordinates(1, 0), FieldState.ATTACKER));
        gameState.board().updateField(new Field(new Coordinates(2, 3), FieldState.KING));

        assertThat(gameState.board().getFieldForCoordinate(new Coordinates(1, 0)).state())
                .isEqualTo(FieldState.ATTACKER);

        gameState.playAction(new GameAction(2, 3, 2, 0));

        assertThat(gameState.board().getFieldForCoordinate(new Coordinates(1, 0)).state())
                .isEqualTo(FieldState.EMPTY);
    }

}