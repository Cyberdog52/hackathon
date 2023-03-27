package ch.zuehlke.common.shared.action.lobby;

import lombok.NonNull;

import java.util.UUID;

public record PlayerJoinAction(
        @NonNull UUID playerId,
        UUID gameId
) {

}
