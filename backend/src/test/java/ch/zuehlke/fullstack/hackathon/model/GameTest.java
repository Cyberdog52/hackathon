package ch.zuehlke.fullstack.hackathon.model;

import ch.zuehlke.common.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {

    @Test
    void addPlayer_successfully() {
        Player player = new Player(new PlayerId("id"), new PlayerName("name"));
        Game game = new Game(new GameId(1));

        game.addPlayer(player);

        assertThat(game.getPlayers()).hasSize(1);
        assertThat(game.getPlayers().get(0)).isEqualTo(player);
    }

    @Test
    void startGame_successfully() {
        Game game = new Game(new GameId(1));

        game.startGame();

        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
    }

    @Test
    void finishGame_successfully() {
        Game game = new Game(new GameId(1));

        game.finishGame();

        assertEquals(GameStatus.FINISHED, game.getStatus());
    }

    @Test
    void deleteGame_successfully() {
        Game game = new Game(new GameId(1));

        game.deleteGame();

        assertEquals(GameStatus.DELETED, game.getStatus());
    }
}