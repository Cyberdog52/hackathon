package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.fullstack.hackathon.model.Lobby;
import ch.zuehlke.fullstack.hackathon.persistence.LobbyRepository;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LobbyService {

    @NonNull
    private final LobbyRepository lobbyRepository;

    public Lobby join(@NonNull final UUID playerId, @Nullable final UUID lobbyId) {
        if (lobbyId == null) {
            return createLobby(playerId);
        }

        // check a lobby already exists with the given game id
        Optional<Lobby> lobby = getLobby(lobbyId);
        if (lobby.isPresent()) {
            if (lobby.get().maxPlayerCount() == lobby.get().playerIds().size()) {
                throw new RuntimeException("Player could not be added to the lobby because the lobby is full.");
            }

            boolean added = lobby.get().addPlayer(playerId);
            if (!added) throw new RuntimeException("Player could not be added to the lobby.");
            return lobby.get();
        } else {
            // create a new lobby
            return createLobby(playerId);
        }
    }

    private Lobby createLobby(final UUID playerId) {
        return lobbyRepository.create(playerId);
    }

    private Optional<Lobby> getLobby(final UUID lobbyId) {
        return lobbyRepository.get(lobbyId);
    }

}
