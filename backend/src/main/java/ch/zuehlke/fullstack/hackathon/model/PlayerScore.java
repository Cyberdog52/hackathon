package ch.zuehlke.fullstack.hackathon.model;

import java.util.UUID;

public record PlayerScore(UUID playerId, int score) {
}
