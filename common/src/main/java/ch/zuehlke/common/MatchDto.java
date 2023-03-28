package ch.zuehlke.common;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record MatchDto(UUID id, List<PlayerDto> players, MatchStatus status, PlayerId winner) {
}
