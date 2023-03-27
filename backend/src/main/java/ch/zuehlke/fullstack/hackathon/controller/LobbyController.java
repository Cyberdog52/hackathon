package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.common.shared.action.lobby.PlayerJoinAction;
import ch.zuehlke.common.shared.event.lobby.PlayerJoinEvent;
import ch.zuehlke.fullstack.hackathon.service.orchestrator.LobbyOrchestrator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lobby")
@RequiredArgsConstructor
public class LobbyController {

    @NonNull
    private final LobbyOrchestrator lobbyOrchestrator;

    @PostMapping("/join")
    public ResponseEntity<PlayerJoinEvent> join(final PlayerJoinAction request) {
        return ResponseEntity.ok()
                .body(lobbyOrchestrator.join(request.playerId(), request.gameId()));
    }

}
