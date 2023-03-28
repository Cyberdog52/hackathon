package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.fullstack.hackathon.model.Player;
import ch.zuehlke.fullstack.hackathon.model.exception.PlayerException;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class PlayerService {

    @Getter
    private Set<Player> players;

    public PlayerService() {
        this.players = new HashSet<>();
    }

    public Player addPlayer(final String name, final String icon) throws PlayerException {
        if (this.playerNameExists(name)) {
            throw new PlayerException("A player with the name '%s' already exists".formatted(name));
        }
        final var player = new Player(UUID.randomUUID(), name, icon);
        this.players.add(player);
        return player;
    }

    public Optional<Player> find(final UUID playerId) {
        return this.players.stream()
                .filter(player -> player.id().equals(playerId))
                .findFirst();
    }

    public void remove(final Player player) {
        this.players.remove(player);
    }

    private boolean playerNameExists(final String name) {
        return this.players.stream()
                .anyMatch(player -> player.name().equals(name));
    }
}
