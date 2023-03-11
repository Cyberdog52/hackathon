package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.common.GameId;
import ch.zuehlke.fullstack.hackathon.model.Game;
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
    void getGames_returnsEmptyList() {
        List<Game> games = gameService.getGames();

        assertThat(games).hasSize(0);
    }

    @Test
    void createGame_addsNewGameToList() {
        Game createdGame = gameService.createGame();

        assertThat(createdGame.getGameId()).isEqualTo(new GameId(1));
        assertThat(gameService.getGames()).hasSize(1);
    }

    @Test
    void deleteGame_successfully() {
        Game game = gameService.createGame();
        assertThat(gameService.getGames()).hasSize(1);

        gameService.deleteGame(game.getGameId().value());

        assertThat(gameService.getGames()).hasSize(0);
    }
}