package ch.zuehlke.fullstack.hackathon.service.orchestrator;

import ch.zuehlke.common.shared.event.lobby.PlayerJoinEvent;
import ch.zuehlke.fullstack.hackathon.mapper.PlayerJoinEventMapper;
import ch.zuehlke.fullstack.hackathon.model.Lobby;
import ch.zuehlke.fullstack.hackathon.service.LobbyService;
import ch.zuehlke.fullstack.hackathon.service.NotificationService;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LobbyOrchestrator {

    @NonNull
    private final LobbyService lobbyService;

    @NonNull
    private final NotificationService notificationService;

    public Lobby join(@NonNull final UUID playerId, @Nullable final UUID lobbyId) {
        Lobby lobby = lobbyService.join(playerId, lobbyId);
        PlayerJoinEvent playerJoinEvent = PlayerJoinEventMapper.mapToPlayerJoinEvent(lobby.lobbyId(), playerId);
        notificationService.notifyLobbyPlayerJoined(playerJoinEvent);
        return lobby;
    }

}
