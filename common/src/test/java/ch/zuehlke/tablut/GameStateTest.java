package ch.zuehlke.tablut;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

}