package ch.zuehlke.fullstack.hackathon.persistence;

import ch.zuehlke.fullstack.hackathon.model.Lobby;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public class LobbyRepository {

    private Lobby lobby;

    public Optional<Lobby> get(final UUID lobbyId) {
        return Optional.of(lobby);
    }

    public Lobby create(final UUID playerId) {
        return Lobby.builder()
                .maxPlayerCount(2)
                .minPlayerCount(2)
                .playerIds(new HashSet<>(Set.of(playerId)))
                .lobbyId(UUID.fromString("00000000-0000-0000-00000000"))
                .build();
    }

}
