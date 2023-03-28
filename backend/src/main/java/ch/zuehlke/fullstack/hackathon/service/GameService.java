package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.fullstack.hackathon.model.CardStack;
import ch.zuehlke.fullstack.hackathon.model.Deck;
import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class GameService {

    public Game initNew(final List<Player> players) {
        final var gameId = UUID.randomUUID();
        final var cardStack = new CardStack(Deck.generateNewDeck());
        return new Game(gameId, this.shufflePlayers(players), cardStack);
    }

    private List<Player> shufflePlayers(final List<Player> players) {
        final var shuffledPlayers = new ArrayList<>(players);
        Collections.shuffle(shuffledPlayers);
        return shuffledPlayers;
    }
}
