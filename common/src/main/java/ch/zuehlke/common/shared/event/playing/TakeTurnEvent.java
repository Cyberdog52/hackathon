package ch.zuehlke.common.shared.event.playing;

import ch.zuehlke.common.GamePlayingAction;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record TakeTurnEvent (
    UUID playerId,
    List<GamePlayingAction> actions,
    UUID gameId
) {

}

