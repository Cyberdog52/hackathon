package ch.zuehlke.fullstack.hackathon.service;

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

    public Game startNewGame(final List<Player> players) {
        final var gameId = UUID.randomUUID();
        final var cardStack = new CardStack(Deck.generateNewDeck());
        final var playerHands = players.stream()
                .map(player -> dealPlayer(player, cardStack))
                .toList();
        final var game = new Game(gameId, this.shufflePlayers(playerHands), cardStack);
        this.games.add(game);
        return game;
    }

    private PlayerHand dealPlayer(final Player player, final CardStack cardStack){
        return new PlayerHand(player, cardStack.take(7));
    }

    private void playGame(final Game game) {
        this.notificationService.notifyGameStart(game);
        final var players = game.getPlayers();
        final var roundRobin = new RoundRobin<>(players);
        for (PlayerHand player : roundRobin) {
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
