package ch.zuehlke.challenge.bot.brain;

import ch.zuehlke.challenge.bot.client.GameClient;
import ch.zuehlke.challenge.bot.service.GameService;
import ch.zuehlke.challenge.bot.service.ShutDownService;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

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

}