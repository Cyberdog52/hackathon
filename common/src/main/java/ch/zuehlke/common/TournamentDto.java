package ch.zuehlke.common;

import lombok.Builder;

import java.util.List;

@Builder
public record TournamentDto(TournamentId id, List<Player> players, TournamentStatus status, TournamentState state, PlayerId winner) {
}
