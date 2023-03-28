package ch.zuehlke.common.shared.event;

import java.util.UUID;

public record GameEndEvent(
        UUID winnerId
) {

    public String getType() {
        return "GameEndEvent";
    }
}