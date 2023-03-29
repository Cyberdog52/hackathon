package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.common.Coordinate;
import ch.zuehlke.common.GamePlayingAction;
import ch.zuehlke.common.shared.event.GameEndEvent;
import ch.zuehlke.common.shared.event.GameStartPlayingEvent;
import ch.zuehlke.common.shared.event.lobby.PlayerJoinEvent;
import ch.zuehlke.common.shared.event.playing.AttackEvent;
import ch.zuehlke.common.shared.event.playing.AttackEvent.AttackStatus;
import ch.zuehlke.common.shared.event.playing.TakeTurnEvent;
import ch.zuehlke.common.shared.event.setup.PlaceBoatEvent;
import ch.zuehlke.common.websocket.WebsocketDestination;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;
import java.util.Random;
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
        String expectedDestination = "%s/%s".formatted(
            WebsocketDestination.TOPIC_GAMES.getDestination(), playerJoinEvent.gameId());

        notificationService.notifyLobbyPlayerJoined(playerJoinEvent);

        verify(simpMessagingTemplate, times(1)).convertAndSend(expectedDestination, playerJoinEvent);
    }

    @Test
    void notifySpectatorPlayerJoined() {
        PlayerJoinEvent playerJoinEvent = PlayerJoinEvent.builder()
            .playerId(UUID.randomUUID())
            .gameId(UUID.randomUUID())
            .build();
        String expectedDestination = "%s/%s/%s".formatted(
            WebsocketDestination.TOPIC_GAMES.getDestination(), playerJoinEvent.gameId(),
            WebsocketDestination.SPECTATE.getDestination());

        notificationService.notifySpectatorPlayerJoined(playerJoinEvent);

        verify(simpMessagingTemplate, times(1)).convertAndSend(expectedDestination, playerJoinEvent);
    }

    @Test
    void notifySpectatorBoatPlaced() {
        PlaceBoatEvent placeBoatEvent = PlaceBoatEvent.builder()
            .gameId(UUID.randomUUID())
            .coordinate(randomCoordinate())
            .playerId(UUID.randomUUID())
            .successful(true)
            .build();
        String expectedDestination = "%s/%s/%s".formatted(
            WebsocketDestination.TOPIC_GAMES.getDestination(), placeBoatEvent.gameId(),
            WebsocketDestination.SPECTATE.getDestination());

      notificationService.notifyBoatPlaced(placeBoatEvent, placeBoatEvent.gameId());

      verify(simpMessagingTemplate, times(1)).convertAndSend(expectedDestination, placeBoatEvent);
    }

    @Test
    void notifySpectatorGameStarted() {
        GameStartPlayingEvent gameStartPlayingEvent = GameStartPlayingEvent.builder()
            .gameId(UUID.randomUUID())
            .playerTurnOrder(List.of(UUID.randomUUID(), UUID.randomUUID()))
            .build();
        String expectedDestination = "%s/%s/%s".formatted(
            WebsocketDestination.TOPIC_GAMES.getDestination(), gameStartPlayingEvent.gameId(),
            WebsocketDestination.SPECTATE.getDestination());

        notificationService.notifySpectatorGameStarted(gameStartPlayingEvent);

        verify(simpMessagingTemplate, times(1)).convertAndSend(expectedDestination,
            gameStartPlayingEvent);
    }

    @Test
    void notifySpectatorPlayerAttacked() {
        AttackEvent attackEvent = AttackEvent.builder()
            .attackingPlayerId(UUID.randomUUID())
            .coordinate(randomCoordinate())
            .status(AttackStatus.HIT)
            .gameId(UUID.randomUUID())
            .build();
        String expectedDestination = "%s/%s/%s".formatted(
            WebsocketDestination.TOPIC_GAMES.getDestination(), attackEvent.gameId(),
            WebsocketDestination.SPECTATE.getDestination());

        notificationService.notifySpectatorPlayerAttacked(attackEvent);

        verify(simpMessagingTemplate, times(1)).convertAndSend(expectedDestination, attackEvent);
    }

    @Test
    void notifySpectatorPlayerTurn() {
        TakeTurnEvent takeTurnEvent = TakeTurnEvent.builder()
            .playerId(UUID.randomUUID())
            .gameId(UUID.randomUUID())
            .actions(List.of(GamePlayingAction.ATTACK))
            .build();
        String expectedDesetination = "%s/%s/%s".formatted(
            WebsocketDestination.TOPIC_GAMES.getDestination(), takeTurnEvent.gameId(),
            WebsocketDestination.SPECTATE);

        notificationService.notifySpectatorPlayerTurn(takeTurnEvent);

        verify(simpMessagingTemplate, times(1)).convertAndSend(expectedDesetination, takeTurnEvent);
    }

    @Test
    void notifySpectatorGameEnded() {
        GameEndEvent gameEndEvent = GameEndEvent.builder()
            .gameId(UUID.randomUUID())
            .winnerId(UUID.randomUUID())
            .build();
        String expectedDestination = "%s/%s/%s".formatted(
            WebsocketDestination.TOPIC_GAMES.getDestination(), gameEndEvent.gameId(),
            WebsocketDestination.SPECTATE.getDestination());

        notificationService.notifySpectatorGameEnded(gameEndEvent);

        verify(simpMessagingTemplate, times(1)).convertAndSend(expectedDestination, gameEndEvent);
    }

    private Coordinate randomCoordinate() {
        return Coordinate.builder()
            .x(randomIntWithinBounds(0, 24))
            .y(randomIntWithinBounds(0, 24))
            .build();
    }

    private int randomIntWithinBounds(int max, int min) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}