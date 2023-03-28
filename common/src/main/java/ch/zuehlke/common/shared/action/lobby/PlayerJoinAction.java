package ch.zuehlke.common.shared.action.lobby;

import lombok.Builder;
import lombok.NonNull;

import java.util.UUID;

@Builder
public record PlayerJoinAction(
        @NonNull UUID playerId,
        UUID gameId
) {

}
