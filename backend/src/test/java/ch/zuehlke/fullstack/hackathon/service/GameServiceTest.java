package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.common.*;
import ch.zuehlke.fullstack.hackathon.controller.JoinResult;
import ch.zuehlke.fullstack.hackathon.controller.PlayResult;
import ch.zuehlke.fullstack.hackathon.controller.StartResult;
import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.tablut.Coordinates;
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
        gameService.createGame();
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

    @Test
    void startGame_withZeroPlayers_notEnoughPlayers() {
        Game game = gameService.createGame();

        StartResult startResult = gameService.startGame(game.getGameId().value());

        assertThat(startResult.resultType()).isEqualTo(StartResult.StartResultType.NOT_ENOUGH_PLAYERS);
        assertThat(game.getStatus()).isEqualTo(GameStatus.NOT_STARTED);
    }

    @Test
    void startGame_nonExistingGame_gameIsNotFound() {
        StartResult startResult = gameService.startGame(666);

        assertThat(startResult.resultType()).isEqualTo(StartResult.StartResultType.GAME_NOT_FOUND);
    }

    @Test
    void startGame_gameIsFull_successfully() {
        Game game = gameService.createGame();
        gameService.join(game.getGameId().value(), new PlayerName("name1"));
        gameService.join(game.getGameId().value(), new PlayerName("name2"));


        StartResult startResult = gameService.startGame(game.getGameId().value());

        assertThat(startResult.resultType()).isEqualTo(StartResult.StartResultType.SUCCESS);
        assertThat(game.getStatus()).isEqualTo(GameStatus.IN_PROGRESS);
    }

    @Test
    void playGame_playerOnePlays_successfully() {
        Game game = gameService.createGame();
        JoinResult joinResult1 = gameService.join(game.getGameId().value(), new PlayerName("name1"));
        gameService.join(game.getGameId().value(), new PlayerName("name2"));
        PlayerId playerId1 = joinResult1.playerId();
        gameService.startGame(game.getGameId().value());
        RequestId requestIdForPlayer1 = getRequestIdForPlayer(playerId1, game);

        Move move = new Move(playerId1, requestIdForPlayer1, new GameAction(new Coordinates(0, 3), new Coordinates(0, 0)));
        PlayResult playResult = gameService.play(move, game.getGameId());

        assertThat(playResult.resultType()).isEqualTo(PlayResult.PlayResultType.SUCCESS);
    }

    @Test
    void playGame_playerOnePlaysTwice_returnsInvalidAction() {
        Game game = gameService.createGame();
        JoinResult joinResult1 = gameService.join(game.getGameId().value(), new PlayerName("name1"));
        gameService.join(game.getGameId().value(), new PlayerName("name2"));
        PlayerId playerId1 = joinResult1.playerId();
        gameService.startGame(game.getGameId().value());
        RequestId requestIdForPlayer1 = getRequestIdForPlayer(playerId1, game);

        Move move = new Move(playerId1, requestIdForPlayer1, new GameAction(new Coordinates(0, 3), new Coordinates(0, 0)));
        gameService.play(move, game.getGameId());
        PlayResult playResult = gameService.play(move, game.getGameId());

        assertThat(playResult.resultType()).isEqualTo(PlayResult.PlayResultType.INVALID_ACTION);
    }

    @Test
    void playGame_withNonExistingGameId_returnsGameNotFound() {
        Game game = gameService.createGame();
        JoinResult joinResult1 = gameService.join(game.getGameId().value(), new PlayerName("name1"));
        gameService.join(game.getGameId().value(), new PlayerName("name2"));
        PlayerId playerId1 = joinResult1.playerId();
        gameService.startGame(game.getGameId().value());
        RequestId requestIdForPlayer1 = getRequestIdForPlayer(playerId1, game);

        Move move = new Move(playerId1, requestIdForPlayer1, new GameAction(new Coordinates(0, 3), new Coordinates(0, 0)));
        PlayResult playResult = gameService.play(move, new GameId(666));

        assertThat(playResult.resultType()).isEqualTo(PlayResult.PlayResultType.GAME_NOT_FOUND);
    }

    @Test
    void playGame_withInvalidRequestId_returnsInvalidAction() {
        Game game = gameService.createGame();
        JoinResult joinResult1 = gameService.join(game.getGameId().value(), new PlayerName("name1"));
        gameService.join(game.getGameId().value(), new PlayerName("name2"));
        PlayerId playerId1 = joinResult1.playerId();
        gameService.startGame(game.getGameId().value());

        Move move = new Move(playerId1, new RequestId(), new GameAction(new Coordinates(0, 3), new Coordinates(0, 0)));
        PlayResult playResult = gameService.play(move, game.getGameId());

        assertThat(playResult.resultType()).isEqualTo(PlayResult.PlayResultType.INVALID_ACTION);
    }

    private RequestId getRequestIdForPlayer(PlayerId playerId, Game game) {
        return game.getState().currentRequests().stream()
                .filter(request -> request.playerId().equals(playerId))
                .findFirst()
                .map(PlayRequest::requestId)
                .orElse(new RequestId());
    }
}