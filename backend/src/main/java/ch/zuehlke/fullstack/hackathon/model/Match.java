package ch.zuehlke.fullstack.hackathon.model;

import ch.zuehlke.fullstack.hackathon.model.exception.MatchStartException;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

public class Match {

    @Getter
    private final UUID id;
    private Game currentGame;
    private List<Player> players;
    private List<MatchResult> results;

    private Match(final UUID id, final List<Player> players) {
        this.id = id;
        this.players = players;
    }

    public static Match fromLobby(final MatchLobby lobby) throws MatchStartException {
        if(lobby.canStart()){
            return new Match(lobby.getId(), lobby.getPlayers());
        }
        throw new MatchStartException("Unable to start match, not enough players");
    }

    public Game newGame() {
        this.currentGame = new Game(UUID.randomUUID());
        return this.currentGame;
    }

}
