package ch.zuehlke.common.shared.event.playing;

import ch.zuehlke.common.GamePlayingAction;
import lombok.Builder;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

@Builder
public record TakeTurnEvent (
    @NonNull UUID playerId,
    List<GamePlayingAction> actions,
    @NonNull UUID gameId
) {
    public String getType() {
        return "TakeTurnEvent";
    }
}

