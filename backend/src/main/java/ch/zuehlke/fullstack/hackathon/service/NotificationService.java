package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.common.shared.event.GameEndEvent;
import ch.zuehlke.common.shared.event.GameStartPlayingEvent;
import ch.zuehlke.common.shared.event.lobby.PlayerJoinEvent;
import ch.zuehlke.common.shared.event.playing.AttackEvent;
import ch.zuehlke.common.shared.event.playing.TakeTurnEvent;
import ch.zuehlke.common.shared.event.setup.PlaceBoatEvent;
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

    public void notifySpectatorPlayerJoined(final PlayerJoinEvent playerJoinEvent) {
        String destination = String.format("%s/%s/%s",
            WebsocketDestination.TOPIC_GAMES.getDestination(), playerJoinEvent.gameId(),
            WebsocketDestination.GAME_SPECTATOR.getDestination());
        template.convertAndSend(destination, playerJoinEvent);
    }

    public void notifySpectatorBoatPlaced(final PlaceBoatEvent placeBoatEvent) {

    }

    public void notifySpectatorGameStarted(final GameStartPlayingEvent gameStartPlayingEvent) {

    }

    public void notifySpectatorPlayerAttacked(final AttackEvent attackEvent) {

    }

    public void notifySpectatorPlayerTurn(final TakeTurnEvent takeTurnEvent) {

    }

    public void notifySpectatorGameEnded(final GameEndEvent gameEndEvent) {

    }
}
