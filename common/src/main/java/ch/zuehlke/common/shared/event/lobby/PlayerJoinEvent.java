package ch.zuehlke.common.shared.event.lobby;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PlayerJoinEvent(
        UUID playerId,
        UUID gameId
) {

}