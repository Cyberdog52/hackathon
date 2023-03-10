package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.fullstack.hackathon.model.*;
import ch.zuehlke.fullstack.hackathon.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lobby")
@RequiredArgsConstructor
public class LobbyController {

    // Improve: Make endpoints secure

    // Improve: Create ExceptionInterceptor for custom exceptions in the backend

    private final GameService gameService;

    @Operation(summary = "Returns the list of games",
            description = "Returns all games, whether they are in progress or not")
    @ApiResponse(responseCode = "200", description = "Successfully returned the list of games")
    @GetMapping("/games")
    public ResponseEntity<List<Game>> getGames() {
        List<Game> games = gameService.getGames();
        return ResponseEntity.ok(games);
    }

    @Operation(summary = "Creates a new game",
            description = "Creates a new game and returns the game id")
    @ApiResponse(responseCode = "200", description = "Successfully created a new game")
    @PostMapping("/game")
    public ResponseEntity<GameId> createGame() {
        Game game = gameService.createGame();
        return ResponseEntity.ok(game.getGameId());
    }

    @Operation(summary = "Joins a game",
            description = "Joins a game and returns the socket url")
    @ApiResponse(responseCode = "200", description = "Successfully joined the game")
    @ApiResponse(responseCode = "404", description = "The game does not exist")
    @PostMapping("/game/{gameId}/join")
    public ResponseEntity<JoinResponse> join(@PathVariable int gameId, @RequestBody JoinRequest joinRequest) {
        Optional<Game> game = gameService.getGame(gameId);
        if (game.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Player newPlayer = new Player(new PlayerId(), joinRequest.name());
        game.get().addPlayer(newPlayer);
        return ResponseEntity.ok(new JoinResponse("socketUrl", newPlayer.playerId().value()));
    }

    @Operation(summary = "Deletes a game",
            description = "Deletes a game")
    @ApiResponse(responseCode = "200", description = "Successfully deleted the game")
    @DeleteMapping("/game/{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable int gameId) {
        gameService.deleteGame(gameId);
        return ResponseEntity.ok().build();
    }


}
