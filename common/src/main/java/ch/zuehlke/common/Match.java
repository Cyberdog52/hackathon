package ch.zuehlke.common;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record Match(String id, List<PlayerDto> players, boolean full) {
}
