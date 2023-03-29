package ch.zuehlke.challenge.bot.service;

import ch.zuehlke.challenge.bot.client.StompClient;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StompService {

    @NonNull
    private final StompClient stompClient;

    public void subscribeToLobby(final UUID lobbyId) {
        stompClient.subscribe(lobbyId);
    }


}
