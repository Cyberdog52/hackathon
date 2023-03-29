package ch.zuehlke.fullstack.hackathon.model;

import ch.zuehlke.common.MatchLobby;
import ch.zuehlke.common.Player;
import ch.zuehlke.fullstack.hackathon.model.exception.MatchStartException;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

public class Match {

    @Getter
    private final UUID id;
    @Getter
    private final List<Player> players;
    private Game currentGame;
    private List<MatchResult> results;

    private Match(final UUID id, final List<Player> players) {
        this.id = id;
        this.players = players;
    }

    public static Match fromLobby(final MatchLobby lobby) throws MatchStartException {
        if (lobby.canStart()) {
            return new Match(lobby.getId(), lobby.getPlayers());
        }
        throw new MatchStartException("Unable to start match, not enough players");
    }
}
