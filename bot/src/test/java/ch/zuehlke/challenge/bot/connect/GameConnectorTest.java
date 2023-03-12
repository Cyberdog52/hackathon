package ch.zuehlke.challenge.bot.connect;

import ch.zuehlke.challenge.bot.util.ApplicationProperties;
import ch.zuehlke.common.JoinRequest;
import ch.zuehlke.common.JoinResponse;
import ch.zuehlke.common.PlayerId;
import ch.zuehlke.common.PlayerName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;

class GameConnectorTest {

    private GameConnector gameConnector;

    private RestTemplate restTemplateMock;
    private ApplicationProperties applicationPropertiesMock;

    @BeforeEach
    void setUp() {
        restTemplateMock = mock(RestTemplate.class);
        applicationPropertiesMock = mock(ApplicationProperties.class);
        gameConnector = new GameConnector(restTemplateMock, applicationPropertiesMock);
    }

    @Test
    void join_successfully() {
        when(applicationPropertiesMock.getGameId()).thenReturn(1);
        when(applicationPropertiesMock.getName()).thenReturn("name");
        when(applicationPropertiesMock.getBackendJoinUrl()).thenReturn("/game/{gameId}/join");

        ResponseEntity<JoinResponse> response = ResponseEntity.ok(new JoinResponse(new PlayerId("id")));
        when(restTemplateMock.postForEntity(any(), any(), eq(JoinResponse.class), anyInt())).thenReturn(response);

        gameConnector.join();

        JoinRequest expectedRequest = new JoinRequest(new PlayerName("name"));
        verify(restTemplateMock, times(1)).postForEntity("/game/{gameId}/join", expectedRequest, JoinResponse.class, 1);
    }

}