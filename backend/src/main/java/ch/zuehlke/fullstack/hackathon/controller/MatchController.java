package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.common.MatchLobby;
import ch.zuehlke.fullstack.hackathon.model.Match;
import ch.zuehlke.fullstack.hackathon.model.exception.MatchStartException;
import ch.zuehlke.fullstack.hackathon.service.MatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/match")
@Slf4j
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @Operation(summary = "Start a match",
            description = "Start a match with the players from its lobby")
    @ApiResponse(responseCode = "200", description = "Successfully started the match")
    @PostMapping("/{matchId}/start")
    public ResponseEntity<Match> startMatch(@PathVariable final String matchId) {
        final MatchLobby lobby;
        try {
            lobby = this.matchService.findLobby(matchId);
        } catch (final MatchStartException e) {
            return ResponseEntity.notFound().build();
        }
        try {
            return ResponseEntity.ok(this.matchService.startMatch(lobby));
        } catch (final MatchStartException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
