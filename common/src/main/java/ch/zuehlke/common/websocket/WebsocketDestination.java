package ch.zuehlke.common.websocket;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WebsocketDestination {

    TOPIC_GAMES("/topic/game"),
    TOPIC("/game"),
    UPDATE("/update"),
    GAME_SPECTATOR("/spectate");

    @NonNull
    private final String destination;

}
