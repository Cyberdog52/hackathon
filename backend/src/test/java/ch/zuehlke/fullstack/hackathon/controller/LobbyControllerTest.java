package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.common.*;
import ch.zuehlke.fullstack.hackathon.controller.JoinResult.JoinResultType;
import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class LobbyControllerTest {

    private LobbyController lobbyController;
    private GameService gameServiceMock;

    @BeforeEach
    void setUp() {
        gameServiceMock = mock(GameService.class);
        lobbyController = new LobbyController(gameServiceMock);
    }

    @Test
    void getGames_emptyList_successfully() {
        when(gameServiceMock.getGames()).thenReturn(List.of());

        ResponseEntity<List<GameDto>> response = lobbyController.getGames();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getBody()).isEqualTo(List.of());
        verify(gameServiceMock, times(1)).getGames();
    }

    @Test
    void createGame_successfully() {
        GameId gameId = new GameId(42);
        when(gameServiceMock.createGame()).thenReturn(new Game(gameId));

        ResponseEntity<GameId> response = lobbyController.createGame();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getBody()).isEqualTo(gameId);
        verify(gameServiceMock, times(1)).createGame();
    }

    @Test
    void join_successfully() {
        JoinResult joinResult = new JoinResult(new PlayerId("id"), JoinResultType.SUCCESS);
        when(gameServiceMock.join(anyInt(), any())).thenReturn(joinResult);

        PlayerName playerName = new PlayerName("name");
        JoinRequest joinRequest = new JoinRequest(playerName);
        ResponseEntity<JoinResponse> response = lobbyController.join(42, joinRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getBody()).isEqualTo(new JoinResponse(joinResult.playerId()));
        verify(gameServiceMock, times(1)).join(42, playerName);
    }

    @Test
    void join_whenGameIsFull_returns400() {
        JoinResult joinResult = new JoinResult(null, JoinResultType.GAME_FULL);
        when(gameServiceMock.join(anyInt(), any())).thenReturn(joinResult);

        PlayerName playerName = new PlayerName("name");
        JoinRequest joinRequest = new JoinRequest(playerName);
        ResponseEntity<JoinResponse> response = lobbyController.join(42, joinRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        assertThat(response.getBody()).isNull();
        verify(gameServiceMock, times(1)).join(42, playerName);
    }

    @Test
    void join_whenGameIsNotFound_returns404() {
        JoinResult joinResult = new JoinResult(null, JoinResultType.GAME_NOT_FOUND);
        when(gameServiceMock.join(anyInt(), any())).thenReturn(joinResult);

        PlayerName playerName = new PlayerName("name");
        JoinRequest joinRequest = new JoinRequest(playerName);
        ResponseEntity<JoinResponse> response = lobbyController.join(42, joinRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
        assertThat(response.getBody()).isNull();
        verify(gameServiceMock, times(1)).join(42, playerName);
    }

    @Test
    void deleteGame_successfully() {
        when(gameServiceMock.deleteGame(anyInt())).thenReturn(true);

        ResponseEntity<Void> response = lobbyController.deleteGame(42);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getBody()).isNull();
    }

    @Test
    void deleteGame_whenGameDidNotExist_returns404() {
        when(gameServiceMock.deleteGame(anyInt())).thenReturn(false);

        ResponseEntity<Void> response = lobbyController.deleteGame(666);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
        assertThat(response.getBody()).isNull();
    }
}