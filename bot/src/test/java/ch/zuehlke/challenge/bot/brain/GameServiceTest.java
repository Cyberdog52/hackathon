package ch.zuehlke.challenge.bot.brain;

import ch.zuehlke.challenge.bot.client.GameClient;
import ch.zuehlke.challenge.bot.service.GameService;
import ch.zuehlke.challenge.bot.service.ShutDownService;
import ch.zuehlke.common.*;
import ch.zuehlke.common.Board;
import ch.zuehlke.common.Coordinates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GameServiceTest {

    private GameService gameService;

    private GameClient gameClientMock;

    private Brain brainMock;

    private ShutDownService shutDownServiceMock;

    @BeforeEach
    void setUp() {
        gameClientMock = mock(GameClient.class);
        brainMock = mock(Brain.class);
        shutDownServiceMock = mock(ShutDownService.class);
        gameService = new GameService(brainMock, gameClientMock, shutDownServiceMock);
    }

    @Test
    void joinGame_callGameClient_successfully() {
        PlayerId expectedPlayerId = new PlayerId();
        when(gameClientMock.join()).thenReturn(expectedPlayerId);
        gameService.joinGame();

        assertThat(gameService.getPlayerId()).isEqualTo(expectedPlayerId);
        verify(gameClientMock, times(1)).join();
    }

    @Test
    void onGameUpdate_withImportantUpdate_playsMove() {
        PlayerId playerId = new PlayerId();
        var board = Board.createInitialBoard();
        GameState state = new GameState(Set.of(new PlayRequest(playerId, new GameId(1), true, board, Set.of())), List.of());
        GameUpdate gameUpdate = new GameUpdate(new GameDto(new GameId(1), List.of(), GameStatus.IN_PROGRESS, state, null));
        when(brainMock.decide(anyBoolean(), any(), any())).thenReturn(new GameAction(new Coordinates(0, 0), new Coordinates(0, 0)));

        gameService.setPlayerId(playerId);
        gameService.onGameUpdate(gameUpdate);

        verify(brainMock, times(1)).decide(true, board, Set.of());
        verify(gameClientMock, times(1)).play(any());
    }

    @Test
    void onGameUpdate_whenGameIsNotStarted_doesNotPlayMove() {
        PlayerId playerId = new PlayerId();
        GameState state = new GameState(Set.of(), List.of());
        GameUpdate gameUpdate = new GameUpdate(new GameDto(new GameId(1), List.of(), GameStatus.NOT_STARTED, state, null));
        when(brainMock.decide(anyBoolean(), any(), any())).thenReturn(new GameAction(new Coordinates(0, 0), new Coordinates(0, 0)));

        gameService.setPlayerId(playerId);
        gameService.onGameUpdate(gameUpdate);

        verify(brainMock, times(0)).decide(anyBoolean(), any(), any());
        verify(gameClientMock, times(0)).play(any());
    }

    @Test
    void onGameUpdate_whenGameIsFinished_doesNotPlayMove() {
        PlayerId playerId = new PlayerId();
        GameState state = new GameState(Set.of(), List.of());
        GameUpdate gameUpdate = new GameUpdate(new GameDto(new GameId(1), List.of(), GameStatus.FINISHED, state, null));
        when(brainMock.decide(anyBoolean(), any(), any())).thenReturn(new GameAction(new Coordinates(0, 0), new Coordinates(0, 0)));

        gameService.setPlayerId(playerId);
        gameService.onGameUpdate(gameUpdate);

        verify(brainMock, times(0)).decide(anyBoolean(), any(), any());
        verify(gameClientMock, times(0)).play(any());
        verify(shutDownServiceMock, times(1)).shutDown();
    }

    @Test
    void onGameUpdate_whenCalledTwice_onlyPlaysOneMove() {
        PlayerId playerId = new PlayerId();
        var board = Board.createInitialBoard();
        GameState state = new GameState(Set.of(new PlayRequest(playerId, new GameId(1), true, board, Set.of())), List.of());
        GameUpdate gameUpdate = new GameUpdate(new GameDto(new GameId(1), List.of(), GameStatus.IN_PROGRESS, state, null));
        when(brainMock.decide(anyBoolean(), any(), any())).thenReturn(new GameAction(new Coordinates(0, 0), new Coordinates(0, 0)));

        gameService.setPlayerId(playerId);
        gameService.onGameUpdate(gameUpdate);
        gameService.onGameUpdate(gameUpdate); // call twice

        verify(brainMock, times(1)).decide(true, board, Set.of());
        verify(gameClientMock, times(1)).play(any());
    }

    @Test
    void onGameUpdate_whenItsForADifferentPlayer_doesNotPlayMove() {
        PlayerId playerId = new PlayerId();
        GameState state = new GameState(Set.of(new PlayRequest(new PlayerId(), new GameId(1), true, Board.createInitialBoard(), Set.of())), List.of());
        GameUpdate gameUpdate = new GameUpdate(new GameDto(new GameId(1), List.of(), GameStatus.IN_PROGRESS, state, null));
        when(brainMock.decide(anyBoolean(), any(), any())).thenReturn(new GameAction(new Coordinates(0, 0), new Coordinates(0, 0)));

        gameService.setPlayerId(playerId);
        gameService.onGameUpdate(gameUpdate);

        verify(brainMock, times(0)).decide(anyBoolean(), any(), any());
        verify(gameClientMock, times(0)).play(any());
    }
}