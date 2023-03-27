package ch.zuehlke.common.shared.event.lobby;

import java.util.UUID;

record PlayerJoinEvent(
        UUID playerId,
        UUID gameId
) {

}