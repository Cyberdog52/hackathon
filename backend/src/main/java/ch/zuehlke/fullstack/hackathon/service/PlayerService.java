package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.fullstack.hackathon.model.Player;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class PlayerService {

    private Set<Player> players;

    public void addPlayer(final Player player) {
        this.players.add(player);
    }

    public Optional<Player> find(final UUID playerId) {
        return this.players.stream()
                .filter(player -> player.id().equals(playerId))
                .findFirst();
    }

    public void remove(final Player player) {
        this.players.remove(player);
    }
}
