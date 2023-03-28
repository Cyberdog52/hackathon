package ch.zuehlke.common.shared.event.setup;

import lombok.Builder;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Builder
public record GameConfigEvent(
        int mapSizeX,
        int mapSizeY,
        int numberOfBoats,
        Duration timeLimit,
        List<UUID> playerIds,
        UUID gameId
) {
}