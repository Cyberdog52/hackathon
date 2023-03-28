package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.persistence.GameRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {

    @NonNull
    private final GameRepository gameRepository;

    public Game get(final UUID gameId) {
        Optional<Game> gameOptional = gameRepository.get(gameId);

        if (gameOptional.isPresent()) {
            return gameOptional.get();
        }

        throw new RuntimeException("Cannot get game with ID: " + gameId);
    }

    public Game create(final Game game) {
        return gameRepository.create(game);
    }

}
