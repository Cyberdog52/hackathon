package ch.zuehlke.fullstack.hackathon.controller.game;

import ch.zuehlke.common.shared.action.lobby.PlayerJoinAction;
import ch.zuehlke.common.shared.event.lobby.PlayerJoinEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lobby")
public class LobbyController {

    @PostMapping("/join")
    public ResponseEntity<PlayerJoinEvent> join(final PlayerJoinAction request) {
        return ResponseEntity.ok().build();
    }

}
