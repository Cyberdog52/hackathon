package ch.zuehlke.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Improve: Split this into DTO and domain object
public record TournamentState(Set<PlayRequest> currentRequests, List<GameId> games) {

    public TournamentState() {
        this(new HashSet<>(), new ArrayList<>());
    }
}
