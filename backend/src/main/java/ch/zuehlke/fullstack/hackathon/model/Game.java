package ch.zuehlke.fullstack.hackathon.model;

import ch.zuehlke.common.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
@Getter
public class Game {

    public static final int REQUIRED_NUMBER_OF_PLAYERS = 2;
    public static final int MIN_PLAYERS = 2;

    private Map<String, Board> boardsByPlayerId = new HashMap<>();
    private final String gameId = UUID.randomUUID().toString();
    private final List<Player> players = new ArrayList<>();

    private GameStatus status = GameStatus.CREATED;

    private final GameState state = new GameState();

    // moves is not exposed to the GameDto to avoid cheating
    private List<Move> currentMoves;


    public boolean addPlayer(Player player) {
        if (players.size() >= REQUIRED_NUMBER_OF_PLAYERS) {
            return false;
        }
        players.add(player);
        return true;
    }

    public void placeShips(Player player, List<Ship> ships) {

        // ToDo: validate player token
        var board = new Board(ships);

        if (!board.shipsValid()) {
            throw new IllegalArgumentException("Board is not valid");
        }

        boardsByPlayerId.put(player.getId(), board);

        if (boardsByPlayerId.size() == REQUIRED_NUMBER_OF_PLAYERS) {
            this.status = GameStatus.SHOOT;
        }
    }

    public boolean canStartGame() {
        return players.size() == 2 &&
                status == GameStatus.CREATED;
    }

    public void startGame() {
        if (!canStartGame()) {
            return;
        }
        status = GameStatus.PLACE_SHIPS;
        startRound();
    }

    private void startRound() {
        currentMoves = new ArrayList<>();
        //players.forEach(player -> state.currentRequests().add(new PlayRequest(player.id())));
    }

    public void finishGame() {
        status = GameStatus.FINISHED;
    }

    public void deleteGame() {
        status = GameStatus.DELETED;
    }

    public boolean isMoveAllowed(Move move) {
        return status == GameStatus.SHOOT &&
                state.currentRequests().stream()
                        .anyMatch(request -> request.playerId().equals(move.playerId()) && request.requestId().equals(move.requestId()));
    }

    public void playMove(Move move) {
        state.currentRequests().removeIf(request -> request.playerId().equals(move.playerId()));
        currentMoves.add(move);

        // SHOOT Action, differentiate between SHOOT and PLACE
        Player enemy = players.stream().filter(player -> !player.getId().equals(move.playerId())).findFirst().orElseThrow(() -> new RuntimeException("No enemy found"));
        var enemyBoard = boardsByPlayerId.get(enemy.getId());
        move.action().execute(enemyBoard);

        if (state.currentRequests().isEmpty()) {
            finishRound();
        }
    }

    private void finishRound() {
        Round currentRound = new Round(currentMoves.get(0), currentMoves.get(1));
        state.rounds().add(currentRound);

        if (getWinner().isPresent()) {
            finishGame();
        } else {
            startRound();
        }
    }

    public Optional<PlayerId> getWinner() {
        if (status != GameStatus.FINISHED || state.rounds().isEmpty()) {
            return Optional.empty();
        }
        // Improve: Handle multiple rounds
        return Optional.empty();
    }
}
