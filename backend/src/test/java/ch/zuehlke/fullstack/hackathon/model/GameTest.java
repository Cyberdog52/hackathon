//package ch.zuehlke.fullstack.hackathon.model;
//
//import ch.zuehlke.common.*;
//import org.junit.jupiter.api.Test;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class GameTest {
//
//    @Test
//    void addPlayer_successfully() {
//        Player player = new Player(new PlayerId(), new PlayerName("name"));
//        Match game = new Match(new GameId(1));
//
//        game.addPlayer(player);
//
//        assertThat(game.getPlayers()).hasSize(1);
//        assertThat(game.getPlayers().get(0)).isEqualTo(player);
//    }
//
//    @Test
//    void startGame_withTwoPlayers_successfully() {
//        Match game = new Match(new GameId(1));
//        PlayerId playerId1 = new PlayerId();
//        game.addPlayer(new Player(playerId1, new PlayerName("name1")));
//        PlayerId playerId2 = new PlayerId();
//        game.addPlayer(new Player(playerId2, new PlayerName("name2")));
//
//        game.startGame();
//
//        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
//        assertThat(game.getState().currentRequests()).hasSize(2);
//        assertThat(game.getState().currentRequests().stream().anyMatch(request -> request.playerId().equals(playerId1))).isTrue();
//        assertThat(game.getState().currentRequests().stream().anyMatch(request -> request.playerId().equals(playerId2))).isTrue();
//    }
//
//    @Test
//    void finishGame_successfully() {
//        Match game = new Match(new GameId(1));
//
//        game.finishGame();
//
//        assertEquals(GameStatus.FINISHED, game.getStatus());
//    }
//
//    @Test
//    void deleteGame_successfully() {
//        Match game = new Match(new GameId(1));
//
//        game.deleteGame();
//
//        assertEquals(GameStatus.DELETED, game.getStatus());
//    }
//
//    @Test
//    void canStartGame_withZeroPlayers_false() {
//        Match game = new Match(new GameId(1));
//
//        boolean canStart = game.canStartGame();
//
//        assertThat(canStart).isFalse();
//    }
//
//    @Test
//    void canStartGame_withOnePlayer_false() {
//        Match game = new Match(new GameId(1));
//        game.addPlayer(new Player(new PlayerId(), new PlayerName("name")));
//
//        boolean canStart = game.canStartGame();
//
//        assertThat(canStart).isFalse();
//    }
//
//    @Test
//    void canStartGame_withTwoPlayers_true() {
//        Match game = new Match(new GameId(1));
//        game.addPlayer(new Player(new PlayerId(), new PlayerName("name1")));
//        game.addPlayer(new Player(new PlayerId(), new PlayerName("name2")));
//
//        boolean canStart = game.canStartGame();
//
//        assertThat(canStart).isTrue();
//    }
//
//    @Test
//    void isMoveAllowed_withFinishedGame_returnsFalse() {
//        Match game = new Match(new GameId(1));
//        game.finishGame();
//
//        boolean canPlayMove = game.isMoveAllowed(new Move(new PlayerId(), new RequestId(), GameAction.ROCK));
//
//        assertThat(canPlayMove).isFalse();
//    }
//
//    @Test
//    void isMoveAllowed_withValidMove_returnsTrue() {
//        Match game = new Match(new GameId(1));
//        PlayerId playerId = new PlayerId();
//        game.addPlayer(new Player(playerId, new PlayerName("name1")));
//        game.addPlayer(new Player(new PlayerId(), new PlayerName("name2")));
//        game.startGame();
//        RequestId requestId = getRequestId(game, playerId);
//
//        boolean canPlayMove = game.isMoveAllowed(new Move(playerId, requestId, GameAction.ROCK));
//
//        assertThat(canPlayMove).isTrue();
//    }
//
//    @Test
//    void isMoveAllowed_withInvalidRequestId_returnsFalse() {
//        Match game = new Match(new GameId(1));
//        PlayerId playerId = new PlayerId();
//        game.addPlayer(new Player(playerId, new PlayerName("name1")));
//        game.addPlayer(new Player(new PlayerId(), new PlayerName("name2")));
//        game.startGame();
//
//        boolean canPlayMove = game.isMoveAllowed(new Move(playerId, new RequestId(), GameAction.ROCK));
//
//        assertThat(canPlayMove).isFalse();
//    }
//
//    @Test
//    void isMoveAllowed_withInvalidPlayerId_returnsFalse() {
//        Match game = new Match(new GameId(1));
//        PlayerId playerId = new PlayerId();
//        game.addPlayer(new Player(playerId, new PlayerName("name1")));
//        game.addPlayer(new Player(new PlayerId(), new PlayerName("name2")));
//        game.startGame();
//        RequestId requestId = getRequestId(game, playerId);
//
//        boolean canPlayMove = game.isMoveAllowed(new Move(new PlayerId(), requestId, GameAction.ROCK));
//
//        assertThat(canPlayMove).isFalse();
//    }
//
//    @Test
//    void playMove_withValidMove_updatesStateSuccessfully() {
//        Match game = new Match(new GameId(1));
//        PlayerId playerId1 = new PlayerId();
//        game.addPlayer(new Player(playerId1, new PlayerName("name1")));
//        PlayerId playerId2 = new PlayerId();
//        game.addPlayer(new Player(playerId2, new PlayerName("name2")));
//        game.startGame();
//        RequestId requestId1 = getRequestId(game, playerId1);
//
//        game.playMove(new Move(playerId1, requestId1, GameAction.ROCK));
//
//        assertThat(game.getState().currentRequests()).hasSize(1);
//        assertThat(game.getState().currentRequests()).noneMatch(request -> request.playerId().equals(playerId1));
//        assertThat(game.getState().currentRequests()).anyMatch(request -> request.playerId().equals(playerId2));
//        assertThat(game.getCurrentMoves()).hasSize(1);
//        assertThat(game.getCurrentMoves()).anyMatch(move -> move.playerId().equals(playerId1));
//        assertThat(game.getCurrentMoves()).noneMatch(move -> move.playerId().equals(playerId2));
//    }
//
//    @Test
//    void playMove_bothPlayersPlaySame_finishesGameSuccessfully() {
//        Match game = new Match(new GameId(1));
//        PlayerId playerId1 = new PlayerId();
//        game.addPlayer(new Player(playerId1, new PlayerName("name1")));
//        PlayerId playerId2 = new PlayerId();
//        game.addPlayer(new Player(playerId2, new PlayerName("name2")));
//        game.startGame();
//        RequestId requestId1 = getRequestId(game, playerId1);
//        RequestId requestId2 = getRequestId(game, playerId2);
//
//        game.playMove(new Move(playerId1, requestId1, GameAction.ROCK));
//        game.playMove(new Move(playerId2, requestId2, GameAction.ROCK));
//
//        assertThat(game.getState().currentRequests()).hasSize(0);
//        assertThat(game.getCurrentMoves()).hasSize(2);
//        assertThat(game.getCurrentMoves()).anyMatch(move -> move.playerId().equals(playerId1));
//        assertThat(game.getCurrentMoves()).anyMatch(move -> move.playerId().equals(playerId2));
//        assertThat(game.getStatus()).isEqualTo(GameStatus.FINISHED);
//        assertThat(game.getWinner()).isEmpty();
//    }
//
//    @Test
//    void playMove_firstPlayerWins_finishesGameSuccessfully() {
//        Match game = new Match(new GameId(1));
//        PlayerId playerId1 = new PlayerId();
//        game.addPlayer(new Player(playerId1, new PlayerName("name1")));
//        PlayerId playerId2 = new PlayerId();
//        game.addPlayer(new Player(playerId2, new PlayerName("name2")));
//        game.startGame();
//        RequestId requestId1 = getRequestId(game, playerId1);
//        RequestId requestId2 = getRequestId(game, playerId2);
//
//        game.playMove(new Move(playerId1, requestId1, GameAction.ROCK));
//        game.playMove(new Move(playerId2, requestId2, GameAction.SCISSORS));
//
//        assertThat(game.getState().currentRequests()).hasSize(0);
//        assertThat(game.getCurrentMoves()).hasSize(2);
//        assertThat(game.getCurrentMoves()).anyMatch(move -> move.playerId().equals(playerId1));
//        assertThat(game.getCurrentMoves()).anyMatch(move -> move.playerId().equals(playerId2));
//        assertThat(game.getStatus()).isEqualTo(GameStatus.FINISHED);
//        assertThat(game.getWinner()).isPresent();
//        assertThat(game.getWinner().get()).isEqualTo(playerId1);
//    }
//
//    private static RequestId getRequestId(Match game, PlayerId playerId1) {
//        return game.getState().currentRequests().stream()
//                .filter(request -> request.playerId().equals(playerId1))
//                .map(PlayRequest::requestId)
//                .findFirst()
//                .orElse(new RequestId());
//    }
//}