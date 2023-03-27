package ch.zuehlke.common.shared.event.lobby;

import java.util.UUID;

public record PlayerJoinEvent(
        UUID playerId,
        UUID gameId
) {

}