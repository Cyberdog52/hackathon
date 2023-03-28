package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.common.PlayerCreateDto;
import ch.zuehlke.fullstack.hackathon.model.Player;
import ch.zuehlke.fullstack.hackathon.model.exception.PlayerException;
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
    @PostMapping("/create")
    public ResponseEntity<UUID> createPlayer(final PlayerCreateDto createDto) {
        try {
            return ResponseEntity.ok(this.playerService.addPlayer(createDto.name(), createDto.icon()).id());
        } catch (PlayerException e) {
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


