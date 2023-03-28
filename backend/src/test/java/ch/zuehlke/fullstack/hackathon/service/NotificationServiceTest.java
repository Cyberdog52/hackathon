package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.common.shared.event.lobby.PlayerJoinEvent;
import ch.zuehlke.common.websocket.WebsocketDestination;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void notifyLobbyPlayerJoined() {
        PlayerJoinEvent playerJoinEvent = PlayerJoinEvent.builder()
                .playerId(UUID.randomUUID())
                .gameId(UUID.randomUUID())
                .build();
        String expectedDestination = String.format(WebsocketDestination.TOPIC_GAMES.getDestination());

        notificationService.notifyLobbyPlayerJoined(playerJoinEvent);

        verify(simpMessagingTemplate, times(1)).convertAndSend(expectedDestination, playerJoinEvent);
    }
}