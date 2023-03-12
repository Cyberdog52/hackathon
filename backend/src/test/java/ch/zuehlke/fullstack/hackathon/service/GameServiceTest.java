package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.common.GameId;
import ch.zuehlke.common.PlayerName;
import ch.zuehlke.fullstack.hackathon.controller.JoinResult;
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

        boolean success = gameService.deleteGame(game.getGameId().value());

        assertThat(gameService.getGames()).hasSize(0);
        assertThat(success).isTrue();
    }

    @Test
    void deleteGame_nonExistingGame_returnsFalse() {
        assertThat(gameService.getGames()).hasSize(1);

        boolean success = gameService.deleteGame(666);

        assertThat(gameService.getGames()).hasSize(1);
        assertThat(success).isFalse();
    }

    @Test
    void joinGame_successfully() {
        Game game = gameService.createGame();

        JoinResult joinResult = gameService.join(game.getGameId().value(), new PlayerName("name"));

        assertThat(joinResult.resultType()).isEqualTo(JoinResult.JoinResultType.SUCCESS);
        assertThat(joinResult.playerId()).isNotNull();
    }

    @Test
    void joinGame_threeTimes_gameIsFull() {
        Game game = gameService.createGame();

        gameService.join(game.getGameId().value(), new PlayerName("name1"));
        gameService.join(game.getGameId().value(), new PlayerName("name2"));
        JoinResult joinResult = gameService.join(game.getGameId().value(), new PlayerName("name3"));

        assertThat(joinResult.resultType()).isEqualTo(JoinResult.JoinResultType.GAME_FULL);
        assertThat(joinResult.playerId()).isNull();
    }

    @Test
    void joinGame_nonExistingGame_gameIsNotFound() {
        JoinResult joinResult = gameService.join(666, new PlayerName("name"));

        assertThat(joinResult.resultType()).isEqualTo(JoinResult.JoinResultType.GAME_NOT_FOUND);
        assertThat(joinResult.playerId()).isNull();
    }
}