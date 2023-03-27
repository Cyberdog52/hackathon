package ch.zuehlke.common;

import java.util.UUID;

public class Player {

    private final String id;
    private final String token;
    private final String playerName;

    public Player(String playerName) {
        id = UUID.randomUUID().toString();
        token = UUID.randomUUID().toString();
        this.playerName = playerName;
    }

    public String id() {
        return id;
    }

    public String token() {
        return token;
    }

    public String playerName() {
        return playerName;
    }
}
