package ch.zuehlke.challenge.bot.io;

import ch.zuehlke.challenge.bot.domain.ConnectivitySetup;
import ch.zuehlke.challenge.bot.domain.GameId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class CommandLineService {

    private final IOUtil<URI> uriIOUtil;
    private final IOUtil<GameId> gameIdIOUtil;

    public ConnectivitySetup setup() {
        URI uri = requestUri();
        GameId gameId = requestGameId();
        return new ConnectivitySetup(uri, gameId);
    }

    private URI requestUri() {
        return uriIOUtil.requestInput("Please enter the URI of the server: ", URI::create);
    }

    private GameId requestGameId() {
        return gameIdIOUtil.requestInput("Please enter the game id: ", GameId::fromString);
    }
}
