package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.common.shared.event.GameEndEvent;
import ch.zuehlke.common.shared.event.GameStartPlayingEvent;
import ch.zuehlke.common.shared.event.lobby.PlayerJoinEvent;
import ch.zuehlke.common.shared.event.playing.AttackEvent;
import ch.zuehlke.common.shared.event.playing.TakeTurnEvent;
import ch.zuehlke.common.shared.event.setup.GameConfigEvent;
import ch.zuehlke.common.shared.event.setup.PlaceBoatEvent;
import ch.zuehlke.common.websocket.WebsocketDestination;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    @NonNull
    private final SimpMessagingTemplate template;

    public void notifyLobbyPlayerJoined(final PlayerJoinEvent playerJoinEvent) {
        String destination = String.format("%s/%s",
            WebsocketDestination.TOPIC_GAMES.getDestination(), playerJoinEvent.gameId());
        template.convertAndSend(destination, playerJoinEvent);
    }

    public void notifyGameInitialised(final GameConfigEvent gameConfigEvent) {
        String destination = String.format("%s/%s",
            WebsocketDestination.TOPIC_GAMES.getDestination(), gameConfigEvent.gameId());
        template.convertAndSend(destination, gameConfigEvent);
    }

    public void notifySpectatorGameInitialised(final GameConfigEvent gameConfigEvent) {
        String destination = String.format("%s/%s%s",
            WebsocketDestination.TOPIC_GAMES.getDestination(), gameConfigEvent.gameId(),
            WebsocketDestination.SPECTATE.getDestination());
        template.convertAndSend(destination, gameConfigEvent);
    }

    public void notifySpectatorPlayerJoined(final PlayerJoinEvent playerJoinEvent) {
        String destination = String.format("%s/%s/%s",
                WebsocketDestination.TOPIC_GAMES.getDestination(), playerJoinEvent.gameId(),
                WebsocketDestination.SPECTATE.getDestination());
        template.convertAndSend(destination, playerJoinEvent);
    }

    public void notifySpectatorGameStarted(final GameStartPlayingEvent gameStartPlayingEvent) {

    }

    public void notifySpectatorPlayerAttacked(final AttackEvent attackEvent) {
        String destination = String.format("%s/%s/%s",
            WebsocketDestination.TOPIC_GAMES.getDestination(), attackEvent.gameId(),
            WebsocketDestination.SPECTATE.getDestination());
        template.convertAndSend(destination, attackEvent);
    }

    public void notifyBoatPlaced(final PlaceBoatEvent placeBoatEvent, final UUID gameId) {
        // should only be for the spectator
        String destination = String.format("%s/%s/%s", WebsocketDestination.TOPIC_GAMES.getDestination(), gameId,
                WebsocketDestination.SPECTATE.getDestination());
        template.convertAndSend(destination, placeBoatEvent);
    }

    public void notifySpectatorPlayerTurn(final TakeTurnEvent takeTurnEvent) {
        String destination = String.format("%s/%s/%s",
            WebsocketDestination.TOPIC_GAMES.getDestination(), takeTurnEvent.gameId(),
            WebsocketDestination.SPECTATE.getDestination());
        template.convertAndSend(destination, takeTurnEvent);
    }

    public void notifySpectatorGameEnded(final GameEndEvent gameEndEvent) {
        String destination = String.format("%s/%s/%s",
            WebsocketDestination.TOPIC_GAMES.getDestination(), gameEndEvent.gameId(),
            WebsocketDestination.SPECTATE.getDestination());
        template.convertAndSend(destination, gameEndEvent);
    }

    public void notifyGameStarted(final TakeTurnEvent takeTurnEvent) {
        String destination = String.format("%s/%s",
            WebsocketDestination.TOPIC_GAMES.getDestination(), takeTurnEvent.gameId());
        template.convertAndSend(destination, takeTurnEvent);
    }

    public void notifyPlayerAttacked(AttackEvent attackEvent) {
        String destination = String.format("%s/%s",
            WebsocketDestination.TOPIC_GAMES.getDestination(), attackEvent.gameId());
        template.convertAndSend(destination, attackEvent);
    }

    public void notifyGameEnded(GameEndEvent gameEndEvent) {
        String destination = String.format("%s/%s",
            WebsocketDestination.TOPIC_GAMES.getDestination(), gameEndEvent.gameId());
        template.convertAndSend(destination, gameEndEvent);
    }

    public void notifyPlayerTakeTurn(TakeTurnEvent takeTurnEvent) {
        String destination = String.format("%s/%s",
            WebsocketDestination.TOPIC_GAMES.getDestination(), takeTurnEvent.gameId());
        template.convertAndSend(destination, takeTurnEvent);
    }
}
