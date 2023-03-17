package ch.zuehlke.common;

import lombok.Builder;

import java.util.List;

@Builder
public record GameDto(GameId id, List<Player> players, GameStatus status, GameState state, PlayerId winner) {
}
