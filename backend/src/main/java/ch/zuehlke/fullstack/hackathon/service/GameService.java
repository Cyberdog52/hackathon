package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.fullstack.hackathon.model.CardStack;
import ch.zuehlke.fullstack.hackathon.model.Deck;
import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.model.Player;
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
        final var game = new Game(gameId, this.shufflePlayers(players), cardStack);
        this.games.add(game);
        return game;
    }

    private void playGame(final Game game){
        this.notificationService.notifyGameStart(game.getId());
        final var players = game.getPlayers();

    }

    private List<Player> shufflePlayers(final List<Player> players) {
        final var shuffledPlayers = new ArrayList<>(players);
        Collections.shuffle(shuffledPlayers);
        return shuffledPlayers;
    }

    public Optional<Game> getGame(final UUID gameId) {
        return this.games.stream().filter(game -> game.getId().equals(gameId)).findAny();
    }
}
