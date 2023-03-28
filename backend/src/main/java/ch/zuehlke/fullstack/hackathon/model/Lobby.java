package ch.zuehlke.fullstack.hackathon.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.NonNull;

import java.util.Set;
import java.util.UUID;

@Builder
public record Lobby(
        @NonNull UUID lobbyId,
        @NonNull @NotEmpty Set<UUID> playerIds,
        int minPlayerCount,
        int maxPlayerCount) {

    public boolean addPlayer(UUID playerId) {
        if (playerIds.size() < maxPlayerCount) {
            return playerIds.add(playerId);
        }
        return false;
    }

    public boolean canStartGame() {
        return playerIds.size() >= minPlayerCount;
    }

}
