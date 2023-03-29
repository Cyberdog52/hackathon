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

class MatchClientTest {

    private MatchClient matchClient;

    private RestTemplate restTemplateMock;
    private ApplicationProperties applicationPropertiesMock;

    private ShutDownService shutDownServiceMock;

    @BeforeEach
    void setUp() {
        restTemplateMock = mock(RestTemplate.class);
        applicationPropertiesMock = mock(ApplicationProperties.class);
        shutDownServiceMock = mock(ShutDownService.class);
        matchClient = new MatchClient(restTemplateMock, applicationPropertiesMock, shutDownServiceMock);
    }

    @Test
    void join_successfully() {
        when(applicationPropertiesMock.getGameId()).thenReturn(1);
        when(applicationPropertiesMock.getName()).thenReturn("name");
        //when(applicationPropertiesMock.getBackendJoinUrl()).thenReturn("/game/{gameId}/join");

        final PlayerId expectedPlayerId = new PlayerId();
        final ResponseEntity<JoinResponse> response = ResponseEntity.ok(new JoinResponse(expectedPlayerId));
        when(restTemplateMock.postForEntity(any(), any(), eq(JoinResponse.class), anyInt())).thenReturn(response);

        final var actualPlayerId = matchClient.join(null, null);

        assertThat(actualPlayerId).isEqualTo(expectedPlayerId);
        final JoinRequest expectedRequest = new JoinRequest("name");
        verify(restTemplateMock, times(1)).postForEntity("/game/{gameId}/join", expectedRequest, JoinResponse.class, 1);
    }

    @Test
    void play_successfully() {
        when(applicationPropertiesMock.getGameId()).thenReturn(1);
        when(applicationPropertiesMock.getName()).thenReturn("name");
        when(applicationPropertiesMock.getBackendPlayUrl()).thenReturn("/game/{gameId}/play");
        when(restTemplateMock.postForEntity(any(), any(), eq(Void.class), anyInt())).thenReturn(ResponseEntity.ok(null));

        final Move move = new Move(new PlayerId(), new RequestId(), null);
        //matchClient.play(move);

        verify(restTemplateMock, times(1)).postForEntity("/game/{gameId}/play", move, Void.class, 1);
    }

}