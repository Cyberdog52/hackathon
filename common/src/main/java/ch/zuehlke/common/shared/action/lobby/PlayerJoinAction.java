package ch.zuehlke.common.shared.action.lobby;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PlayerJoinAction(
        UUID playerId,
        UUID gameId
) {

}
