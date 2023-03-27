package ch.zuehlke.tablut;

import ch.zuehlke.common.GameAction;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class GameStateTest {

    @Test
    void initalGame_FirstMove_PossibleMoveForBlack() {
        var gameState = new GameState();

        assertThat(gameState.getPossibleActions()).contains(new GameAction(new Coordinates(0, 3), new Coordinates(0, 0)));
    }

    @Test
    void initalGame_FirstMove_NoMoveWithDefenders() {
        var gameState = new GameState();

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
        var gameState = new GameState();
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
        var gameState = new GameState();
        Set<Coordinates> expectedGameActions = Set.of();

        assertThat(gameState.getPossibleActions().stream()
                .filter(a -> a.from().equals(new Coordinates(4, 0)))
                .map(GameAction::to)
                .collect(Collectors.toSet())).isEqualTo(expectedGameActions);
    }

    @Test
    void getPossibleMoves_ForRightAttackerAtStart() {
        var gameState = new GameState();
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
        var gameState = new GameState();
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
}