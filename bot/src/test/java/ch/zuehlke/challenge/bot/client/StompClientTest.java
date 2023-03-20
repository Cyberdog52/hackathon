package ch.zuehlke.challenge.bot.client;

import ch.zuehlke.challenge.bot.service.GameService;
import ch.zuehlke.challenge.bot.util.ApplicationProperties;
import ch.zuehlke.common.GameUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class StompClientTest {

    private StompClient stompClient;

    private ApplicationProperties applicationPropertiesMock;

    private GameService gameServiceMock;

    // Improve: Write more tests, probably split up StompClient to make it easier to test

    @BeforeEach
    void setUp() {
        applicationPropertiesMock = mock(ApplicationProperties.class);
        gameServiceMock = mock(GameService.class);
        stompClient = new StompClient(applicationPropertiesMock, gameServiceMock);
    }

    @Test
    void handleFrame_updatesGame_successfully() {
        GameUpdate emptyGameUpdate = new GameUpdate(null);
        stompClient.handleFrame(null, emptyGameUpdate);

        verify(gameServiceMock, times(1)).onGameUpdate(emptyGameUpdate);
    }
}