package ch.zuehlke.fullstack.hackathon.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

@RequiredArgsConstructor
@Getter
public class ThunderShipsGame {
  private final UUID uuid;
  private final List<ThunderShipsPlayer> players;
  private ThunderShipsPlayer winner;

  public Optional<ThunderShipsPlayer> determineWinner() {
    var winner = players.stream()
        .filter(Predicate.not(ThunderShipsPlayer::hasAnyBoatsLeft))
        .findFirst();
    winner.ifPresent(player -> this.winner = player);
    return winner;
  }

}
