package ch.zuehlke.common;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public final class MatchLobby {

    private static final int MAX_PLAYER_COUNT = 4;
    private static final int MIN_PLAYER_COUNT = 2;

    private final UUID id;
    private final List<Player> players;

    public MatchLobby() {
        this(UUID.randomUUID());
    }

    public MatchLobby(final UUID id) {
        this.id = id;
        this.players = new ArrayList<>();
    }

    public void join(final Player player) throws LobbySizeException, PlayerException {
        if (this.isFull()) {
            throw new LobbySizeException();
        }
        if (this.players.contains(player)) {
            throw new PlayerException("Player %s has already joined this match".formatted(player.name()));
        }
        this.players.add(player);
    }

    public void leave(final Player player) {
        this.players.remove(player);
    }

    public boolean canStart() {
        return this.players.size() >= MIN_PLAYER_COUNT;
    }

    public boolean isFull() {
        return this.players.size() >= MAX_PLAYER_COUNT;
    }

}
