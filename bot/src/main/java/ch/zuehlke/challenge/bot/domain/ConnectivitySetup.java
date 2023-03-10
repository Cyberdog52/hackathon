package ch.zuehlke.challenge.bot.domain;

import java.net.URI;

public record ConnectivitySetup(BotName name, URI uri, GameId gameId) {
}
