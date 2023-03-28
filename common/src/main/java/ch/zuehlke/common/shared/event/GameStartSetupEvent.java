package ch.zuehlke.common.shared.event;

import lombok.Builder;

import java.util.UUID;

@Builder
public record GameStartSetupEvent(
        UUID gameId
) {

    public String getType() {
        return "GameStartSetupEvent";
    }

}
