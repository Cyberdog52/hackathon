package ch.zuehlke.fullstack.hackathon.mapper;

import ch.zuehlke.common.shared.event.lobby.PlayerJoinEvent;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class PlayerJoinEventMapper {

    public PlayerJoinEvent mapToPlayerJoinEvent(final UUID lobbyId, final UUID playerId) {
        return PlayerJoinEvent.builder()
                .playerId(playerId)
                .gameId(lobbyId)
                .build();
    }

}
