package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.common.JoinRequest;
import ch.zuehlke.fullstack.hackathon.model.Match;
import ch.zuehlke.fullstack.hackathon.model.MatchLobby;
import ch.zuehlke.fullstack.hackathon.model.Player;
import ch.zuehlke.fullstack.hackathon.model.exception.LobbySizeException;
import ch.zuehlke.fullstack.hackathon.model.exception.MatchStartException;
import ch.zuehlke.fullstack.hackathon.model.exception.PlayerException;
import ch.zuehlke.fullstack.hackathon.service.MatchService;
import ch.zuehlke.fullstack.hackathon.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/lobby")
@Slf4j
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;
    private final PlayerService playerService;

    @Operation(summary = "Returns the list of waiting matches",
            description = "Returns all waiting matches")
    @ApiResponse(responseCode = "200", description = "Successfully returned the list of waiting matches")
    @GetMapping("/waiting")
    public List<MatchLobby> getWaitingMatches() {
        return this.matchService.getWaitingMatches();
    }

    @Operation(summary = "Create a new match",
            description = "Creates a new match and returns the game id")
    @ApiResponse(responseCode = "200", description = "Successfully created a new match")
    @PostMapping("/match")
    public String createMatch() {
        return this.matchService.createMatch()
                .getId()
                .toString();
    }

    @Operation(summary = "Get a match",
            description = "Get the information for a match")
    @ApiResponse(responseCode = "200", description = "Successfully created a new match")
    @GetMapping("/match/{matchId}")
    public ResponseEntity<MatchLobby> getMatch(@PathVariable final String matchId) {
        try {
            return ResponseEntity.ok(this.findLobby(matchId));
        } catch (MatchStartException e) {
            log.warn("Cloud not find match lobby with the id ({})", matchId);
            return ResponseEntity.notFound().build();
        }

    }

    @Operation(summary = "Join a match",
            description = "Join a waiting match")
    @ApiResponse(responseCode = "200", description = "Successfully joined the match")
    @ApiResponse(responseCode = "400", description = "The match is already full")
    @ApiResponse(responseCode = "404", description = "The match does not exist")
    @PostMapping("/match/{matchId}/join")
    public ResponseEntity<MatchLobby> join(@PathVariable final String matchId, @RequestBody JoinRequest joinRequest) {
        MatchLobby matchLobby;
        try {
            matchLobby = this.findLobby(matchId);
        } catch (MatchStartException e) {
            log.warn("Cloud not find match lobby with the id ({})", matchId);
            return ResponseEntity.notFound().build();
        }
        Player player;
        try {
            player = this.findPlayer(joinRequest.playerId());
        } catch (PlayerException e) {
            log.warn("Cloud not find player with id ({})", joinRequest.playerId());
            return ResponseEntity.badRequest().build();
        }
        try {
            matchLobby.join(player);
        } catch (LobbySizeException | PlayerException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(matchLobby);
    }

    @Operation(summary = "Start a match",
            description = "Start a match with the players from its lobby")
    @ApiResponse(responseCode = "200", description = "Successfully started the match")
    @PostMapping("/match/{matchId}/start")
    public ResponseEntity<Match> startMatch(@PathVariable String matchId) {
        final MatchLobby lobby;
        try {
            lobby = this.findLobby(matchId);
        } catch (MatchStartException e) {
            return ResponseEntity.notFound().build();
        }
        try {
            return ResponseEntity.ok(this.matchService.startMatch(lobby));
        } catch (MatchStartException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private MatchLobby findLobby(final String matchId) throws MatchStartException {
        final var uuId = UUID.fromString(matchId);
        return this.matchService.getLobby(uuId)
                .orElseThrow(() -> new MatchStartException("Unable to start match %s because it does not exist".formatted(matchId)));
    }

    private Player findPlayer(final String playerId) throws PlayerException {
        return this.playerService.find(UUID.fromString(playerId))
                .orElseThrow(() -> new PlayerException("Player with the id (%s) does not exist".formatted(playerId)));
    }

}
