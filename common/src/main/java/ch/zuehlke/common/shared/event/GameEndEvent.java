package ch.zuehlke.common.shared.event;

import lombok.Builder;

import java.util.UUID;

@Builder
public record GameEndEvent(
        UUID winnerId,
        UUID gameId
) {

    public String getType() {
        return "GameEndEvent";
    }
}