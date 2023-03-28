package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.common.shared.event.lobby.PlayerJoinEvent;
import ch.zuehlke.common.websocket.WebsocketDestination;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    @NonNull
    private final SimpMessagingTemplate template;

    public void notifyLobbyPlayerJoined(final PlayerJoinEvent playerJoinEvent) {
        String destination = String.format("%s/%s", WebsocketDestination.TOPIC_GAMES.getDestination(), playerJoinEvent.gameId());
        template.convertAndSend(destination, playerJoinEvent);
    }

}
