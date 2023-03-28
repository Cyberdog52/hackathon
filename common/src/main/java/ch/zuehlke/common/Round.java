package ch.zuehlke.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
public class Round {

    private Map<String, Shoot> shootByPlayerId = new HashMap();
    private boolean finished;

    public void addShoot(Shoot shoot) {
        if (receivedBothMoves()) {
            throw new IllegalArgumentException("Both moves were already set.");
        }
        if (!isPlayerAllowedToShoot(shoot.playerId())) {
            throw new IllegalArgumentException("Player did already submit a move");
        }
        shootByPlayerId.put(shoot.playerId(), shoot);
    }

    public List<Shoot> getShootByPlayerId() {
        return shootByPlayerId.values().stream().toList();
    }

    public boolean isPlayerAllowedToShoot(String playerId) {
        return Objects.isNull(shootByPlayerId.get(playerId));
    }

    public boolean receivedBothMoves() {
        return shootByPlayerId.size() == 2;
    }

    public void finishRound() {
        finished = true;
    }
}
