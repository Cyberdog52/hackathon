package ch.zuehlke.common;

import lombok.Builder;

@Builder
public record PlayerDto(PlayerId id, PlayerName name, PlayerIcon icon) {
}
