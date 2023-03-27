package ch.zuehlke.fullstack.hackathon.model;

import java.util.List;
import java.util.UUID;

public class Match {

    private final UUID id;
    private Game currentGame;
    private List<Player> players;
    private List<MatchResult> results;

    public Match(final UUID id, final List<Player> players) {
        this.id = id;
        this.players = players;
    }

    public Game newGame() {
        this.currentGame = new Game(UUID.randomUUID());
        return this.currentGame;
    }

}
