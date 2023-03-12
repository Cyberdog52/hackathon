package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.common.*;
import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.model.GameMapper;
import ch.zuehlke.fullstack.hackathon.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<GameDto>> getGames() {
        List<Game> games = gameService.getGames();
        List<GameDto> gameDtos = games.stream()
                .map(GameMapper::map)
                .toList();
        return ResponseEntity.ok(gameDtos);
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
    @ApiResponse(responseCode = "400", description = "Game is already full")
    @ApiResponse(responseCode = "404", description = "The game does not exist")
    @PostMapping("/game/{gameId}/join")
    public ResponseEntity<JoinResponse> join(@PathVariable int gameId, @RequestBody JoinRequest joinRequest) {
        JoinResult joinResult = gameService.join(gameId, joinRequest.name());

        if (joinResult.resultType() == JoinResult.JoinResultType.GAME_NOT_FOUND) {
            return ResponseEntity.notFound().build();
        }
        if (joinResult.resultType() == JoinResult.JoinResultType.GAME_FULL) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new JoinResponse(joinResult.playerId()));
    }

    @Operation(summary = "Deletes a game",
            description = "Deletes a game")
    @ApiResponse(responseCode = "200", description = "Successfully deleted the game")
    @ApiResponse(responseCode = "404", description = "Game did not exist and can therefore not be deleted")
    @DeleteMapping("/game/{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable int gameId) {
        boolean success = gameService.deleteGame(gameId);
        if (!success) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @MessageMapping("/update/{id}")
    @SendTo("/topic/game/{id}")
    public GameUpdate send(@DestinationVariable Integer gameId) {
        return new GameUpdate(new GameId(gameId));
    }


}
