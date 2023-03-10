package ch.zuehlke.challenge.bot.io;

import ch.zuehlke.challenge.bot.domain.ConnectivitySetup;
import ch.zuehlke.challenge.bot.domain.GameId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CommandLineServiceTest {

    private CommandLineService commandLineService;
    private IOUtil<URI> uriIOUtil;
    private IOUtil<GameId> gameIdIOUtil;

    @BeforeEach
    void setUp() {
        uriIOUtil = mock(IOUtil.class);
        gameIdIOUtil = mock(IOUtil.class);

        commandLineService = new CommandLineService(uriIOUtil, gameIdIOUtil);
    }

    @Test
    void setup_withValidInput_shouldReturnConnectivitySetup() {
        URI uri = URI.create("http://localhost:8080");
        when(uriIOUtil.requestInput(anyString(), any())).thenReturn(uri);
        GameId gameId = new GameId(1);
        when(gameIdIOUtil.requestInput(anyString(), any())).thenReturn(gameId);

        ConnectivitySetup setup = commandLineService.setup();

        assertThat(setup).isNotNull();
        assertThat(setup.uri()).isEqualTo(uri);
        assertThat(setup.gameId()).isEqualTo(gameId);
        verify(uriIOUtil, times(1)).requestInput(anyString(), any());
        verify(gameIdIOUtil, times(1)).requestInput(anyString(), any());
    }

}