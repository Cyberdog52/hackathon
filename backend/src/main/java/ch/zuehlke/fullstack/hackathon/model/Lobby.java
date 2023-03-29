package ch.zuehlke.fullstack.hackathon.model;

import lombok.Builder;
import lombok.NonNull;

import java.util.Set;
import java.util.UUID;

@Builder
public record Lobby(
        @NonNull UUID lobbyId,
        int minPlayerCount,
        int maxPlayerCount,
        Set<UUID> playerIds) {

    public boolean addPlayer(final UUID playerId) {
        if (playerIds.size() < maxPlayerCount) {
            return playerIds.add(playerId);
        }
        return false;
    }

    public boolean canStartGame() {
        return playerIds.size() >= minPlayerCount;
    }

}
