package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.fullstack.hackathon.model.CardStack;
import ch.zuehlke.fullstack.hackathon.model.Deck;
import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class GameService {

    private final Map<UUID, Game> games;

    public GameService() {
        this.games = new HashMap<>();
    }

    public Game startNewGame(final List<Player> players) {
        final var gameId = UUID.randomUUID();
        final var cardStack = new CardStack(Deck.generateNewDeck());
        final var game = new Game(gameId, this.shufflePlayers(players), cardStack);
        this.games.put(gameId, game);
        return game;
    }

    private List<Player> shufflePlayers(final List<Player> players) {
        final var shuffledPlayers = new ArrayList<>(players);
        Collections.shuffle(shuffledPlayers);
        return shuffledPlayers;
    }
}
