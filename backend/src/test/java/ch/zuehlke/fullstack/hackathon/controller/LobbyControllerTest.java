package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.common.shared.action.lobby.PlayerJoinAction;
import ch.zuehlke.common.shared.event.lobby.PlayerJoinEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static java.util.UUID.randomUUID;

@SpringBootTest
class LobbyControllerTest {

    @Autowired
    private LobbyController lobbyController;

    @Test
    void join() {
        PlayerJoinAction action = PlayerJoinAction.builder()
                .playerId(randomUUID())
                .gameId(randomUUID())
                .build();

        ResponseEntity<PlayerJoinEvent> response = lobbyController.join(action);
    }
}