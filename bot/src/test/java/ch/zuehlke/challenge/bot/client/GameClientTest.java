package ch.zuehlke.challenge.bot.client;

import ch.zuehlke.challenge.bot.service.ShutDownService;
import ch.zuehlke.challenge.bot.util.ApplicationProperties;
import ch.zuehlke.common.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GameClientTest {

    private GameClient gameClient;

    private RestTemplate restTemplateMock;
    private ApplicationProperties applicationPropertiesMock;

    private ShutDownService shutDownServiceMock;

    @BeforeEach
    void setUp() {
        restTemplateMock = mock(RestTemplate.class);
        applicationPropertiesMock = mock(ApplicationProperties.class);
        shutDownServiceMock = mock(ShutDownService.class);
        gameClient = new GameClient(restTemplateMock, applicationPropertiesMock, shutDownServiceMock);
    }

    @Test
    void register_successfully() {
        when(applicationPropertiesMock.getGameId()).thenReturn(1);
        when(applicationPropertiesMock.getName()).thenReturn("Player1234");
        when(applicationPropertiesMock.getBackendRegisterUrl()).thenReturn("/api/lobby/register");

        var expectedPlayer = new Player("Player1234");
        var response = ResponseEntity.ok(new RegisterResponse(expectedPlayer));
        when(restTemplateMock.postForEntity(eq(applicationPropertiesMock.getBackendRegisterUrl()), any(), eq(RegisterResponse.class))).thenReturn(response);

        var player = gameClient.register();

        assertThat(player.getPlayerName()).isEqualTo(expectedPlayer.getPlayerName());
        verify(restTemplateMock, times(1)).postForEntity(eq("/api/lobby/register"), any(), eq(RegisterResponse.class));
    }

//    @Test
//    void play_successfully() {
//        when(applicationPropertiesMock.getGameId()).thenReturn(1);
//        when(applicationPropertiesMock.getName()).thenReturn("name");
//        when(applicationPropertiesMock.getBackendPlayUrl()).thenReturn("/game/{gameId}/play");
//        when(restTemplateMock.postForEntity(any(), any(), eq(Void.class), anyInt())).thenReturn(ResponseEntity.ok(null));
//
//        Move move = new Move(new PlayerId(), new RequestId(), GameAction.ROCK);
//        gameClient.play(move);
//
//        verify(restTemplateMock, times(1)).postForEntity("/game/{gameId}/play", move, Void.class, 1);
//    }

}