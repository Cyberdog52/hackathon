package ch.zuehlke.challenge.bot.brain;

import ch.zuehlke.challenge.bot.client.MatchClient;
import ch.zuehlke.challenge.bot.service.MatchService;
import ch.zuehlke.challenge.bot.service.ShutDownService;
import ch.zuehlke.common.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MatchServiceTest {

    private MatchService matchService;

    private MatchClient matchClientMock;

    private Brain brainMock;

    private ShutDownService shutDownServiceMock;

    @BeforeEach
    void setUp() {
        matchClientMock = mock(MatchClient.class);
        brainMock = mock(Brain.class);
        shutDownServiceMock = mock(ShutDownService.class);
        matchService = new MatchService(brainMock, matchClientMock, shutDownServiceMock);
    }

    @Test
    void joinGame_callGameClient_successfully() {
        PlayerId expectedPlayerId = new PlayerId();
        when(matchClientMock.join()).thenReturn(expectedPlayerId);
        matchService.joinGame();

        assertThat(matchService.getPlayerId()).isEqualTo(expectedPlayerId);
        verify(matchClientMock, times(1)).join();
    }

    @Test
    void onGameUpdate_withImportantUpdate_playsMove() {
        PlayerId playerId = new PlayerId();
        GameState state = new GameState(Set.of(new PlayRequest(playerId)), List.of());
        GameUpdate gameUpdate = new GameUpdate(new GameDto(new GameId(1), List.of(), GameStatus.IN_PROGRESS, state, null));
        when(brainMock.decide(any())).thenReturn(GameAction.ROCK);

        matchService.setPlayerId(playerId);
        matchService.onGameUpdate(gameUpdate);

        verify(brainMock, times(1)).decide(Set.of(GameAction.values()));
        verify(matchClientMock, times(1)).play(any());
    }

    @Test
    void onGameUpdate_whenGameIsNotStarted_doesNotPlayMove() {
        PlayerId playerId = new PlayerId();
        GameState state = new GameState(Set.of(), List.of());
        GameUpdate gameUpdate = new GameUpdate(new GameDto(new GameId(1), List.of(), GameStatus.NOT_STARTED, state, null));
        when(brainMock.decide(any())).thenReturn(GameAction.ROCK);

        matchService.setPlayerId(playerId);
        matchService.onGameUpdate(gameUpdate);

        verify(brainMock, times(0)).decide(any());
        verify(matchClientMock, times(0)).play(any());
    }

    @Test
    void onGameUpdate_whenGameIsFinished_doesNotPlayMove() {
        PlayerId playerId = new PlayerId();
        GameState state = new GameState(Set.of(), List.of());
        GameUpdate gameUpdate = new GameUpdate(new GameDto(new GameId(1), List.of(), GameStatus.FINISHED, state, null));
        when(brainMock.decide(any())).thenReturn(GameAction.ROCK);

        matchService.setPlayerId(playerId);
        matchService.onGameUpdate(gameUpdate);

        verify(brainMock, times(0)).decide(any());
        verify(matchClientMock, times(0)).play(any());
        verify(shutDownServiceMock, times(1)).shutDown();
    }

    @Test
    void onGameUpdate_whenCalledTwice_onlyPlaysOneMove() {
        PlayerId playerId = new PlayerId();
        GameState state = new GameState(Set.of(new PlayRequest(playerId)), List.of());
        GameUpdate gameUpdate = new GameUpdate(new GameDto(new GameId(1), List.of(), GameStatus.IN_PROGRESS, state, null));
        when(brainMock.decide(any())).thenReturn(GameAction.ROCK);

        matchService.setPlayerId(playerId);
        matchService.onGameUpdate(gameUpdate);
        matchService.onGameUpdate(gameUpdate); // call twice

        verify(brainMock, times(1)).decide(Set.of(GameAction.values()));
        verify(matchClientMock, times(1)).play(any());
    }

    @Test
    void onGameUpdate_whenItsForADifferentPlayer_doesNotPlayMove() {
        PlayerId playerId = new PlayerId();
        GameState state = new GameState(Set.of(new PlayRequest(new PlayerId())), List.of());
        GameUpdate gameUpdate = new GameUpdate(new GameDto(new GameId(1), List.of(), GameStatus.IN_PROGRESS, state, null));
        when(brainMock.decide(any())).thenReturn(GameAction.ROCK);

        matchService.setPlayerId(playerId);
        matchService.onGameUpdate(gameUpdate);

        verify(brainMock, times(0)).decide(any());
        verify(matchClientMock, times(0)).play(any());
    }
}