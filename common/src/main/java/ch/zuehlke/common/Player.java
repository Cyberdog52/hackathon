package ch.zuehlke.common;

import java.util.UUID;

public class Player {

    private final String id;
    private final String token;
    private final String playerName;

    public Player() {
        this("No Name provided");
    }

    public Player(String playerName) {
        id = UUID.randomUUID().toString();
        token = UUID.randomUUID().toString();
        this.playerName = playerName;
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getPlayerName() {
        return playerName;
    }
}
