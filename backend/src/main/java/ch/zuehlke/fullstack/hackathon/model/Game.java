package ch.zuehlke.fullstack.hackathon.model;

import ch.zuehlke.fullstack.hackathon.model.game.GameConfig;
import lombok.Builder;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

@Builder
public record Game(
        UUID gameId,
        Set<ThunderShipsPlayer> players,
        GameConfig gameConfig
) {
    public Optional<ThunderShipsPlayer> determineWinner() {
        var winner = players.stream()
                .filter(Predicate.not(ThunderShipsPlayer::hasAnyBoatsLeft))
                .findFirst();
        return winner;
    }

    public boolean addBoat(final UUID playerId, final Boat boat) {
        Optional<ThunderShipsPlayer> player = players.stream()
                .filter(p -> p.id().equals(playerId))
                .findFirst();

        if (player.isPresent()) {
            int numberOfBoatsPlaced = player.get().gameMap().boats().size();
            if (numberOfBoatsPlaced < gameConfig.maxNumberOfBoats()) {
                return player.get().gameMap().placeBoat(boat);
            } else {
                return false;
            }
        }
        throw new RuntimeException("Cannot place boat for player with ID: " + playerId);
    }

    public boolean allBoatsPlaced() {
        return players.stream()
                .map(ThunderShipsPlayer::gameMap)
                .map(map -> map.boats().size() == gameConfig.maxNumberOfBoats())
                .reduce((a, b) -> a && b)
                .orElse(false);
    }

}
