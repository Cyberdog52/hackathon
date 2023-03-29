package ch.zuehlke.common.websocket;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WebsocketDestination {

    TOPIC_GAMES("/topic/game"),
    SPECTATE("/spectate"),
    TOPIC("/game"),
    UPDATE("/update");

    @NonNull
    private final String destination;

}
