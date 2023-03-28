package ch.zuehlke.common;

import lombok.Builder;

import java.util.List;

@Builder
public record GameDto(String id, List<Player> players, GameStatus status, GameState state, PlayerId winner) {
}
