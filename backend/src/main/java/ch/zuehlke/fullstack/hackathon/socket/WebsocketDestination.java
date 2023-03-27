package ch.zuehlke.fullstack.hackathon.socket;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WebsocketDestination {

    TOPIC_GAMES("/topic/game"),
    TOPIC("/game"),
    UPDATE("/update");

    @NonNull
    private final String destination;

}
