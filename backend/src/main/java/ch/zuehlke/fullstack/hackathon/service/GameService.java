package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.common.GameStatus;
import ch.zuehlke.fullstack.hackathon.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameService {

    private final List<Game> games = new ArrayList<>();

    private final NotificationService notificationService;

    public void startNewGame(final List<Player> players) {
        final var gameId = UUID.randomUUID();
        final var cardStack = new CardStack(Deck.generateNewDeck());
        final var playerHands = players.stream()
                .map(player -> dealPlayer(player, cardStack))
                .toList();
        final var game = new Game(gameId, this.shufflePlayers(playerHands), cardStack);
        this.games.add(game);
        this.playGame(game);
    }

    private PlayerHand dealPlayer(final Player player, final CardStack cardStack) {
        return new PlayerHand(player, cardStack.take(7));
    }

    private void playGame(final Game game) {
        game.setStatus(GameStatus.IN_PROGRESS);
        this.notificationService.notifyGameStart(game);
        final var players = game.getPlayers();
        for (final PlayerHand player : players) {
            this.notificationService.notifyPlayerStart(game, player);
        }
        final var roundRobin = new RoundRobin<>(players);
        for (PlayerHand player : roundRobin) {
            if (game.getStatus() == GameStatus.FINISHED) {
                break;
            }
            this.notificationService.notifyPlayerTurn(player.player().id());
        }
    }

    private <T> List<T> shufflePlayers(final List<T> items) {
        final var shuffledPlayers = new ArrayList<T>(items);
        Collections.shuffle(shuffledPlayers);
        return shuffledPlayers;
    }

    public Optional<Game> getGame(final UUID gameId) {
        return this.games.stream().filter(game -> game.getId().equals(gameId)).findAny();
    }
}
