package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.common.*;
import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.model.GameMapper;
import ch.zuehlke.fullstack.hackathon.service.GameService;
import ch.zuehlke.fullstack.hackathon.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lobby")
@RequiredArgsConstructor
public class LobbyController {

    // Improve: Make endpoints secure

    // Improve: Create ExceptionInterceptor for custom exceptions in the backend

    private final GameService gameService;

    private final NotificationService notificationService;

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
        notificationService.notifyGameUpdate(new GameId(gameId));
        return ResponseEntity.ok(new JoinResponse(joinResult.playerId()));
    }

    @Operation(summary = "Plays a move",
            description = "Plays a move")
    @ApiResponse(responseCode = "200", description = "Successfully played the move")
    @ApiResponse(responseCode = "400", description = "Player is not part of the game or the move is invalid")
    @ApiResponse(responseCode = "404", description = "Game was not found")
    @PostMapping("/game/{gameId}/play")
    public ResponseEntity<Void> play(@PathVariable int gameId, @RequestBody Move move) {
        PlayResult playResult = gameService.play(move, new GameId(gameId));
        if (playResult.resultType() == PlayResult.PlayResultType.GAME_NOT_FOUND) {
            return ResponseEntity.notFound().build();
        }
        if (playResult.resultType() == PlayResult.PlayResultType.PLAYER_NOT_FOUND || playResult.resultType() == PlayResult.PlayResultType.INVALID_ACTION) {
            return ResponseEntity.badRequest().build();
        }
        notificationService.notifyGameUpdate(new GameId(gameId));
        return ResponseEntity.ok().build();
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

    @Operation(summary = "Starts a game",
            description = "Starts a game")
    @ApiResponse(responseCode = "200", description = "Successfully started the game")
    @ApiResponse(responseCode = "400", description = "Not enough players to start the game")
    @ApiResponse(responseCode = "404", description = "Game did not exist and can therefore not be started")
    @PostMapping("/game/{gameId}/start")
    public ResponseEntity<Void> startGame(@PathVariable int gameId) {
        StartResult result = gameService.startGame(gameId);
        if (result.resultType() == StartResult.StartResultType.GAME_NOT_FOUND) {
            return ResponseEntity.notFound().build();
        }
        if (result.resultType() == StartResult.StartResultType.NOT_ENOUGH_PLAYERS) {
            return ResponseEntity.badRequest().build();
        }
        notificationService.notifyGameUpdate(new GameId(gameId));
        return ResponseEntity.ok().build();
    }


}
