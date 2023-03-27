package ch.zuehlke.fullstack.hackathon.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game {

    private final UUID gameId;

    private final List<Card> stack;
    private final List<Card> discarded;

    public Game(final UUID gameId) {
        this.gameId = gameId;
        stack = new ArrayList<>();
        discarded = new ArrayList<>();
    }
}
