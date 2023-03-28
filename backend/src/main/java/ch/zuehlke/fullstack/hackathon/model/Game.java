package ch.zuehlke.fullstack.hackathon.model;

import ch.zuehlke.common.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

import static ch.zuehlke.common.GameStatus.CREATED;

@RequiredArgsConstructor
@Getter
public class Game {

    public static final int REQUIRED_NUMBER_OF_PLAYERS = 2;
    public static final int MIN_PLAYERS = 2;

    private Map<String, Board> boardsByPlayerId = new HashMap<>();
    private final String gameId = UUID.randomUUID().toString();
    private final List<Player> players = new ArrayList<>();

    private GameStatus status = CREATED;
    private List<Player> winner = new ArrayList<>();

    private final GameState state = new GameState();

    private List<Round> rounds = new ArrayList<>();

    public boolean addPlayer(Player player) {
        if (status != CREATED) {
            throw new IllegalArgumentException("cannot add players in current phase: " + status);
        }
        if (players.size() > REQUIRED_NUMBER_OF_PLAYERS) {
            throw new IllegalArgumentException("A game cannot have more than " + REQUIRED_NUMBER_OF_PLAYERS + " players");
        }
        players.add(player);
        if (players.size() == REQUIRED_NUMBER_OF_PLAYERS) {
            status = GameStatus.PLACE_SHIPS;
        }
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

    public ShootResult shoot(Player player, int x, int y) {

        var currentRound = getCurrentRound();

        if (!currentRound.isPlayerAllowedToShoot(player.getId())) {
            throw new IllegalArgumentException("Player did already shoot this round!");
        }

        var shoot = new Shoot(player.getId(), x, y);
        currentRound.addShoot(shoot);
        var shootResult = executeShoot(shoot);

        if (currentRound.receivedBothMoves()) {
            if (hasWinner()) {
                status = GameStatus.FINISHED;
                winner  = getWinnerIds().stream()
                        .map(this::getPlayerById)
                        .toList();
            }
            currentRound.finishRound();
            rounds.add(new Round());
        }

        return shootResult;
    }

    private ShootResult executeShoot(Shoot shoot) {
        Player enemy = players.stream().filter(p -> !p.getId().equals(shoot.playerId())).findFirst().orElseThrow(() -> new RuntimeException("No enemy found"));
        Board enemyBoard = boardsByPlayerId.get(enemy.getId());
        return enemyBoard.executeShot(shoot.x(), shoot.y());
    }

    public boolean isPlayerAllowedToShoot(Player player) {
        return getCurrentRound().isPlayerAllowedToShoot(player.getId());
    }

    public boolean canStartGame() {
        return players.size() == 2 &&
                status == CREATED;
    }

    public void startGame() {
        if (!canStartGame()) {
            return;
        }
        status = GameStatus.PLACE_SHIPS;
    }

    private void startRound() {
        rounds = new ArrayList<>();
        //players.forEach(player -> state.currentRequests().add(new PlayRequest(player.id())));
    }

    public void finishGame() {
        status = GameStatus.FINISHED;
    }

    public void deleteGame() {
        status = GameStatus.DELETED;
    }

    private void finishRound() {
        Round currentRound = getCurrentRound();
        state.rounds().add(currentRound);

        if (getWinnerIds().isEmpty()) {
            finishGame();
        }
    }

    public Player getPlayerById(String playerId) {
        return players.stream().filter(player -> player.getId().equals(playerId)).findFirst().orElseThrow(() -> new RuntimeException("Player not found"));
    }

    public Round getCurrentRound() {
        if (rounds.isEmpty()) {
            var round = new Round();
            rounds.add(round);
            return round;
        }
        return rounds.get(rounds.size() - 1);
    }

    public List<String> getWinnerIds() {
        return boardsByPlayerId.entrySet().stream()
                .filter(entry -> entry.getValue().allShipsDestroyed())
                .map(Map.Entry::getKey)
                .toList();
    }

    public boolean hasWinner() {
        return !getWinnerIds().isEmpty();
    }
}
