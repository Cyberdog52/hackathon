package ch.zuehlke.fullstack.hackathon.model;

import java.util.List;
import java.util.UUID;

public record MatchResult(UUID matchId, List<PlayerScore> playerScores) {
}
