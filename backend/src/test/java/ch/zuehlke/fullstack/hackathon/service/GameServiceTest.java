package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.model.GameId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GameServiceTest {

    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameService = new GameService();
    }

    @Test
    void getGames_returnsMockedGame() {
        List<Game> games = gameService.getGames();

        assertThat(games).hasSize(1);
        assertThat(games.get(0).getGameId()).isEqualTo(new GameId(0));
    }

    @Test
    void createGame() {
        Game createdGame = gameService.createGame();

        assertThat(createdGame.getGameId()).isEqualTo(new GameId(1));
        assertThat(gameService.getGames()).hasSize(2);
    }
}