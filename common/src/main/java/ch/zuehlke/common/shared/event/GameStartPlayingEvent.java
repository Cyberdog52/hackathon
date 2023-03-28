package ch.zuehlke.common.shared.event;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record GameStartPlayingEvent(
        List<UUID> playerTurnOrder,
        UUID gameId
) {

}
