package ch.zuehlke.common.shared.action.lobby;

import java.util.UUID;

public record PlayerJoinAction (
        UUID playerId,
        UUID gameId
) {

}
