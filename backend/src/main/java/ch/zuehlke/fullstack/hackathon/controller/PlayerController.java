package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.common.Player;
import ch.zuehlke.common.PlayerException;
import ch.zuehlke.fullstack.hackathon.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/player")
@Slf4j
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @Operation(summary = "Returns the list of online players",
            description = "Returns all available players")
    @ApiResponse(responseCode = "200", description = "The list of all players")
    @GetMapping()
    public Set<Player> getAllPlayers() {
        return this.playerService.getPlayers();
    }

    @Operation(summary = "Create a new player",
            description = "Creates a new player and returns the uuid")
    @ApiResponse(responseCode = "200", description = "Successfully created a new player")
    @PostMapping("/create/{name}/{icon}")
    public ResponseEntity<UUID> createPlayer(@PathVariable final String name, @PathVariable final String icon) {
        try {
            return ResponseEntity.ok(this.playerService.addPlayer(name, icon).id());
        } catch (final PlayerException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Delete a player")
    @DeleteMapping("/{playerId}")
    public void deletePlayer(@PathVariable final String playerId) {
        this.playerService.find(UUID.fromString(playerId))
                .ifPresent(this.playerService::remove);
    }
}


