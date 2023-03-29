package ch.zuehlke.common.shared.event.setup;

import ch.zuehlke.common.shared.BoatRequirements;
import lombok.Builder;
import lombok.NonNull;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Builder
public record GameConfigEvent(
        @NonNull UUID gameId,
        int mapSizeX,
        int mapSizeY,
        int numberOfBoats,
        BoatRequirements boatRequirements,
        Duration timeLimit,
        @NonNull List<UUID> playerIds
) {
}