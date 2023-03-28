package ch.zuehlke.common.shared.event.lobby;

import lombok.Builder;
import lombok.NonNull;

import java.util.UUID;

@Builder
public record PlayerJoinEvent(
        @NonNull UUID playerId,
        @NonNull UUID gameId
) {

}