package ch.zuehlke.fullstack.hackathon.model;

import ch.zuehlke.common.*;
import ch.zuehlke.tablut.Coordinates;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {

    @Test
    void addPlayer_successfully() {
        Player player = new Player(new PlayerId(), new PlayerName("name"));
        Game game = new Game(new GameId(1));

        game.addPlayer(player);

        assertThat(game.getPlayers()).hasSize(1);
        assertThat(game.getPlayers().get(0)).isEqualTo(player);
    }

    @Test
    void startGame_withTwoPlayers_successfully() {
        Game game = new Game(new GameId(1));
        PlayerId playerId1 = new PlayerId();
        game.addPlayer(new Player(playerId1, new PlayerName("name1")));
        PlayerId playerId2 = new PlayerId();
        game.addPlayer(new Player(playerId2, new PlayerName("name2")));

        game.startGame();

        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
        assertThat(game.getState().currentRequests()).hasSize(2);
        assertThat(game.getState().currentRequests().stream().anyMatch(request -> request.playerId().equals(playerId1))).isTrue();
        assertThat(game.getState().currentRequests().stream().anyMatch(request -> request.playerId().equals(playerId2))).isTrue();
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

    @Test
    void canStartGame_withZeroPlayers_false() {
        Game game = new Game(new GameId(1));

        boolean canStart = game.canStartGame();

        assertThat(canStart).isFalse();
    }

    @Test
    void canStartGame_withOnePlayer_false() {
        Game game = new Game(new GameId(1));
        game.addPlayer(new Player(new PlayerId(), new PlayerName("name")));

        boolean canStart = game.canStartGame();

        assertThat(canStart).isFalse();
    }

    @Test
    void canStartGame_withTwoPlayers_true() {
        Game game = new Game(new GameId(1));
        game.addPlayer(new Player(new PlayerId(), new PlayerName("name1")));
        game.addPlayer(new Player(new PlayerId(), new PlayerName("name2")));

        boolean canStart = game.canStartGame();

        assertThat(canStart).isTrue();
    }

    @Test
    void isMoveAllowed_withFinishedGame_returnsFalse() {
        Game game = new Game(new GameId(1));
        game.finishGame();

        boolean canPlayMove = game.isMoveAllowed(new Move(new PlayerId(), new RequestId(), new GameAction(new Coordinates(0, 3), new Coordinates(0, 0))));

        assertThat(canPlayMove).isFalse();
    }

    @Test
    void isMoveAllowed_withValidMove_returnsTrue() {
        Game game = new Game(new GameId(1));
        PlayerId playerId = new PlayerId();
        game.addPlayer(new Player(playerId, new PlayerName("name1")));
        game.addPlayer(new Player(new PlayerId(), new PlayerName("name2")));
        game.startGame();
        RequestId requestId = getRequestId(game, playerId);

        boolean canPlayMove = game.isMoveAllowed(new Move(playerId, requestId, new GameAction(new Coordinates(0, 3), new Coordinates(0, 0))));

        assertThat(canPlayMove).isTrue();
    }

    @Test
    void isMoveAllowed_withInvalidRequestId_returnsFalse() {
        Game game = new Game(new GameId(1));
        PlayerId playerId = new PlayerId();
        game.addPlayer(new Player(playerId, new PlayerName("name1")));
        game.addPlayer(new Player(new PlayerId(), new PlayerName("name2")));
        game.startGame();

        boolean canPlayMove = game.isMoveAllowed(new Move(playerId, new RequestId(), new GameAction(new Coordinates(0, 3), new Coordinates(0, 0))));

        assertThat(canPlayMove).isFalse();
    }

    @Test
    void isMoveAllowed_withInvalidPlayerId_returnsFalse() {
        Game game = new Game(new GameId(1));
        PlayerId playerId = new PlayerId();
        game.addPlayer(new Player(playerId, new PlayerName("name1")));
        game.addPlayer(new Player(new PlayerId(), new PlayerName("name2")));
        game.startGame();
        RequestId requestId = getRequestId(game, playerId);

        boolean canPlayMove = game.isMoveAllowed(new Move(new PlayerId(), requestId, new GameAction(new Coordinates(0, 3), new Coordinates(0, 0))));

        assertThat(canPlayMove).isFalse();
    }

    @Test
    void playMove_withValidMove_updatesStateSuccessfully() {
        Game game = new Game(new GameId(1));
        PlayerId playerId1 = new PlayerId();
        game.addPlayer(new Player(playerId1, new PlayerName("name1")));
        PlayerId playerId2 = new PlayerId();
        game.addPlayer(new Player(playerId2, new PlayerName("name2")));
        game.startGame();
        RequestId requestId1 = getRequestId(game, playerId1);

        game.playMove(new Move(playerId1, requestId1, new GameAction(new Coordinates(0, 3), new Coordinates(0, 0))));

        assertThat(game.getState().currentRequests()).hasSize(1);
        assertThat(game.getState().currentRequests()).noneMatch(request -> request.playerId().equals(playerId1));
        assertThat(game.getState().currentRequests()).anyMatch(request -> request.playerId().equals(playerId2));
        assertThat(game.getCurrentMoves()).hasSize(1);
        assertThat(game.getCurrentMoves()).anyMatch(move -> move.playerId().equals(playerId1));
        assertThat(game.getCurrentMoves()).noneMatch(move -> move.playerId().equals(playerId2));
    }

    @Test
    void playMove_bothPlayersPlaySame_finishesGameSuccessfully() {
        Game game = new Game(new GameId(1));
        PlayerId playerId1 = new PlayerId();
        game.addPlayer(new Player(playerId1, new PlayerName("name1")));
        PlayerId playerId2 = new PlayerId();
        game.addPlayer(new Player(playerId2, new PlayerName("name2")));
        game.startGame();
        RequestId requestId1 = getRequestId(game, playerId1);
        RequestId requestId2 = getRequestId(game, playerId2);

        game.playMove(new Move(playerId1, requestId1, new GameAction(new Coordinates(0, 3), new Coordinates(0, 0))));
        game.playMove(new Move(playerId2, requestId2, new GameAction(new Coordinates(0, 3), new Coordinates(0, 0))));

        assertThat(game.getState().currentRequests()).hasSize(0);
        assertThat(game.getCurrentMoves()).hasSize(2);
        assertThat(game.getCurrentMoves()).anyMatch(move -> move.playerId().equals(playerId1));
        assertThat(game.getCurrentMoves()).anyMatch(move -> move.playerId().equals(playerId2));
        assertThat(game.getStatus()).isEqualTo(GameStatus.FINISHED);
        assertThat(game.getWinner()).isEmpty();
    }

    @Test
    void playMove_firstPlayerWins_finishesGameSuccessfully() {
        Game game = new Game(new GameId(1));
        PlayerId playerId1 = new PlayerId();
        game.addPlayer(new Player(playerId1, new PlayerName("name1")));
        PlayerId playerId2 = new PlayerId();
        game.addPlayer(new Player(playerId2, new PlayerName("name2")));
        game.startGame();
        RequestId requestId1 = getRequestId(game, playerId1);
        RequestId requestId2 = getRequestId(game, playerId2);

        game.playMove(new Move(playerId1, requestId1, new GameAction(new Coordinates(0, 3), new Coordinates(0, 0))));
        game.playMove(new Move(playerId2, requestId2, new GameAction(new Coordinates(0, 3), new Coordinates(0, 0))));

        assertThat(game.getState().currentRequests()).hasSize(0);
        assertThat(game.getCurrentMoves()).hasSize(2);
        assertThat(game.getCurrentMoves()).anyMatch(move -> move.playerId().equals(playerId1));
        assertThat(game.getCurrentMoves()).anyMatch(move -> move.playerId().equals(playerId2));
        assertThat(game.getStatus()).isEqualTo(GameStatus.FINISHED);
        assertThat(game.getWinner()).isPresent();
        assertThat(game.getWinner().get()).isEqualTo(playerId1);
    }

    private static RequestId getRequestId(Game game, PlayerId playerId1) {
        return game.getState().currentRequests().stream()
                .filter(request -> request.playerId().equals(playerId1))
                .map(PlayRequest::requestId)
                .findFirst()
                .orElse(new RequestId());
    }
}