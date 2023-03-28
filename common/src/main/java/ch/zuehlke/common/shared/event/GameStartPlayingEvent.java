package ch.zuehlke.common.shared.event;

import ch.zuehlke.common.GamePlayingAction;

import java.util.List;
import java.util.UUID;

public record GameStartPlayingEvent(
        List<UUID> playerTurnOrder
) {

    public String getType() {
        return "GameStartPlayingEvent";
    }

}
