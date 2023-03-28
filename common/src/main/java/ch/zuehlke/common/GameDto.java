package ch.zuehlke.common;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record GameDto(UUID id, List<PlayerDto> players, GameStatus status, GameState state, PlayerId winner) {
}
