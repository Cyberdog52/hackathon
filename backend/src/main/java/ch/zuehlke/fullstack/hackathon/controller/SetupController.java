package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.common.shared.action.setup.PlaceBoatAction;
import ch.zuehlke.common.shared.event.setup.GameConfigEvent;
import ch.zuehlke.common.shared.event.setup.PlaceBoatEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/setup")
public class SetupController {

    @PostMapping("/placeBoat")
    public ResponseEntity<PlaceBoatEvent> placeBoat(final PlaceBoatAction request) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/config")
    public ResponseEntity<GameConfigEvent> config() {
        return ResponseEntity.ok().build();
    }

}
