package ch.zuehlke.common.shared.event.playing;

import ch.zuehlke.common.GamePlayingAction;

import java.util.List;
import java.util.UUID;

public record TakeTurnEvent (
    UUID playerId,
    List<GamePlayingAction> actions
) {
    public String getType() {
        return "TakeTurnEvent";
    }
}

