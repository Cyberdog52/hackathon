package ch.zuehlke.fullstack.hackathon.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/end")
public class EndController {

    @GetMapping("/replay")
    public ResponseEntity<Void> replay(@PathVariable UUID gameId) {
        // todo implement replay response
        return ResponseEntity.ok().build();
    }

}
