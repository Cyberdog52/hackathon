package ch.zuehlke.fullstack.hackathon.controller.game;

import ch.zuehlke.common.shared.action.playing.AttackTurnAction;
import ch.zuehlke.common.shared.event.playing.AttackEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/playing")
public class PlayingController {

    @PostMapping("/attack")
    public ResponseEntity<AttackEvent> attack(final AttackTurnAction request) {
        return ResponseEntity.ok().build();
    }

}
