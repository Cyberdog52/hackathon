package ch.zuehlke.fullstack.hackathon.persistence;

import ch.zuehlke.fullstack.hackathon.model.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class GameRepository {

    private Game game;

    public Optional<Game> get(final UUID gameId) {
        return Optional.ofNullable(game);
    }

    public Game create(final Game game) {
        this.game = game;
        return game;
    }

}
