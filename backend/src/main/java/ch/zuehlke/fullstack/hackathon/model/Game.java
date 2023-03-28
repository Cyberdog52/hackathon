package ch.zuehlke.fullstack.hackathon.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

@RequiredArgsConstructor
@Getter
public class Game {

    private final UUID uuid;
    private final Set<ThunderShipsPlayer> players = new HashSet<>();

    public Optional<ThunderShipsPlayer> determineWinner() {
        var winner = players.stream()
            .filter(Predicate.not(ThunderShipsPlayer::hasAnyBoatsLeft))
            .findFirst();
        return winner;
    }
}
